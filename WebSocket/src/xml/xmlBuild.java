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
import org.w3c.dom.NodeList;


public class xmlBuild {             												//修改build.xml文件，提供Ant编译脚本
    
    //private  String xmlPath = "src/build.xml";
    private String proj_name;
    //URL p2 = this.getClass().getClassLoader().getResource("build.xml");
    String path = "./build.xml";//p2.toString();
    
    public xmlBuild(String proj_name){
    	this.proj_name = proj_name;
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
    
    //修改节点
    public  void modifySon(){
    	
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        try{
            
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document xmldoc=db.parse(path);
    
            Element root = xmldoc.getDocumentElement();
            
            Element project =(Element) selectSingleNode("//project", root);
            project.setAttribute("name", proj_name);																		//------工程名称修改
            project.setAttribute("basedir", proj_name);																	//------工程名称修改
            
            Element property =(Element) selectSingleNode("//property", root);
            property.setAttribute("value", proj_name+".war");																//------生成war包名称修改
            
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            former.transform(new DOMSource(xmldoc), new StreamResult(new File("./build.xml")));
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
//    //解析文件
//    public  void getFamilyMemebers(){
//        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
//        dbf.setIgnoringElementContentWhitespace(true);
//        try {
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document doc = db.parse(xmlPath); // 使用dom解析xml文件
//
//            NodeList sonlist = doc.getElementsByTagName(" "); 
//            for (int i = 0; i < sonlist.getLength(); i++) // 循环处理对象
//            {
//                Element son = (Element)sonlist.item(i);
//                
//                for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling()){  
//                    if (node.getNodeType() == Node.ELEMENT_NODE){  
//                        String name = node.getNodeName();  
//                        String value = node.getFirstChild().getNodeValue();  
//                        System.out.println(name+" : "+value);
//                    }  
//                }  
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    //删除节点
//    public  void discardSon(){
//            
//        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
//        dbf.setIgnoringElementContentWhitespace(true);
//        
//        try{
//        
//            DocumentBuilder db=dbf.newDocumentBuilder();
//            Document xmldoc=db.parse(xmlPath);
//        
//            Element root = xmldoc.getDocumentElement();
//            
//            Element son =(Element) selectSingleNode(" ", root);
//            root.removeChild(son);
//
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer former = factory.newTransformer();
//            former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));
//            
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    
//    //新建节点
//    public  void createSon() {
//        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
//        dbf.setIgnoringElementContentWhitespace(false);
//        
//        try{
//        
//            DocumentBuilder db=dbf.newDocumentBuilder();
//            Document xmldoc=db.parse(xmlPath);
//        
//            Element root = xmldoc.getDocumentElement();
//            
//           //新增指定节点
//            
//            Element son =xmldoc.createElement("son");
//            son.setAttribute("id", "004");
//            
//            Element name = xmldoc.createElement("name");
//            name.setTextContent("小儿子");
//            son.appendChild(name);
//
//            Element age = xmldoc.createElement("name");
//            age.setTextContent("0");
//            son.appendChild(age);
//            
//            root.appendChild(son);
//            //保存
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer former = factory.newTransformer();
//            former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));
//            
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
  
}