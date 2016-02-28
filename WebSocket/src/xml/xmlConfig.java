package xml;

import java.io.File;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class xmlConfig {                                                         //修改config.xml文件，创建生成jenkins项目配置文件
	
	//private  String xmlPath = "src/config.xml";
    private String gitUrl;
    private String gitId;
    private String branch;
    private String proj_name;
    private int port;
    
    URL p = this.getClass().getClassLoader().getResource("config.xml"); 
    String path = "./config.xml";
    
    public xmlConfig(String gitUrl,String gitId,String branch,String proj_name,int port){
    	this.gitUrl = gitUrl;
    	this.gitId = gitId;
    	this.branch = branch;
        this.proj_name = proj_name;
        this.port = port;
        
        System.out.println(p);
    }
    
    public  void modifySon(){
    	
    	String proj_name_low = proj_name.toLowerCase();
    	
    	//shell命令行修改
    	String shell = "#!/bin/sh \n " +
    	
    "echo ' Get old container id;' \n"+

	"CID=$(docker ps | grep '"+proj_name_low+"' | awk '{print $1}') \n"+

"echo $CID \n" +

"cp "+proj_name+"/"+proj_name+".war /home/liulinyu/docker/tomcat7/tomcat/webapps \n"+

"docker build -t "+proj_name_low+" /home/liulinyu/docker/ | tee /var/lib/jenkins/jobs/"+proj_name+"/workspace/build_result.log \n" +

"RESULT=$(cat /var/lib/jenkins/jobs/"+proj_name+"/workspace/build_result.log | tail -n 1) \n"+

"#if [['$RESULT' != *Successfully*]];then \n"+

"#  exit -1 \n"+

"#fi \n" +

"echo ' Stopping old container' \n" +

"if [ '$CID' != ' ' ];then  \n"+
  "docker stop $CID  \n" +
"fi \n" +

"echo ' Starting new container' \n"+

"docker run -d -p "+port+":8081 "+proj_name_low+ "\n" +

"rm -rf /home/liulinyu/docker/tomcat7/tomcat/webapps/*.jar \n";
    	
    	
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        try{
    		
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document xmldoc=db.parse(path);
        
            Element root = xmldoc.getDocumentElement();
            
            
            Element url =(Element) selectSingleNode("//hudson.plugins.git.UserRemoteConfig", root); 															        
            url.getElementsByTagName("url").item(0).setTextContent(gitUrl);																		//------git url地址修改
            url.getElementsByTagName("credentialsId").item(0).setTextContent(gitId);														//-------git用户认证id修改
                													
            
            Element branchSet =(Element) selectSingleNode("//hudson.plugins.git.BranchSpec", root);
            branchSet.getElementsByTagName("name").item(0).setTextContent(branch);														//------git分支修改
            
            Element command =(Element) selectSingleNode("//hudson.tasks.Shell", root);
            command.getElementsByTagName("command").item(0).setTextContent(shell);														//修改shell命令
            
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            //System.out.println(String.format("generate config.xml to %s", path));
            former.transform(new DOMSource(xmldoc), new StreamResult(new File("./config.xml")));
    		
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //获取目标节点
    public  Node selectSingleNode(String express, Element source) {
        Node result=null;
        XPathFactory xpathFactory=XPathFactory.newInstance();
        XPath xpath=xpathFactory.newXPath();
        try {
            result=(Node) xpath.evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
