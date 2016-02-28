package ssh;

import java.net.URL;

import xml.xmlBuild;
import xml.xmlConfig;

public class JenkinsBuild {
	
	private Shell shell;
	private String proj_name;
	private String gitUrl;
    private String gitId;
    private String branch;
    private int port;
	
	public JenkinsBuild(String proj_name,String gitUrl,String gitId,String branch,int port){
		this.proj_name = proj_name;
		this.gitUrl = gitUrl;
    	this.gitId = gitId;
    	this.branch = branch;
        this.port = port;
	}
	
	//开启容器服务
	public  void jenkinsBuild() throws InterruptedException{
		
		this.shell = new Shell("159.226.57.12", 22, "liulinyu", "liulinyu123");
		
		xmlConfig config = new xmlConfig(gitUrl,gitId,branch,proj_name,port);     //修改build.xml和config.xml文件
		xmlBuild xml = new xmlBuild(proj_name);
		config.modifySon();
		xml.modifySon();
		
		//String cpConfigXml = "cp -a -f ./config.xml  /home/liulinyu/";
		String createProj = "java -jar jenkins-cli.jar -s http://159.226.57.12:8080/ create-job " + proj_name +" < config.xml";		
		//System.out.println(String.format("copy config.xml to %s", configPath));
		//String[] commands_cpConfigXml = {createProj};
		//shell.executeCommands(commands_cpConfigXml);												//生成config.xml，并在/home/liulinyu目录下运行jenkins-cli创建jenkins项目工程
		//Thread.sleep(3000);
		//System.out.println(shell.getResponse()); 
		
		String fisrtBuild = "java -jar jenkins-cli.jar -s http://159.226.57.12:8080/ build "+proj_name+" -s";
		//String[] commands_buildProj = {buildProj};
		//shell.executeCommands(commands_buildProj);                                                //初次build,建立workspace文件夹
		
		String cpBuildXml = "sudo cp -a -f /home/liulinyu/build.xml /var/lib/jenkins/jobs/"+proj_name +"/workspace/";
		//String[] commands_cpBuildXml = {cpBuildXml};
		//shell.executeCommands(commands_cpBuildXml);												//build.xml生成并copy到${JENKINS_HOME}/jobs/proj_name/workspace/目录下
		//System.out.println(shell.getResponse()); 
		
		String secondBuild = "java -jar jenkins-cli.jar -s http://159.226.57.12:8080/ build "+proj_name+" -s";
																																//第二次build
		String[] commands = {createProj,fisrtBuild,cpBuildXml,secondBuild};							
		shell.executeCommands(commands);
		//System.out.println(shell.getResponse()); 
		
	}
}