package ssh;


public class test {												

	public  static void main(String[] args) throws InterruptedException{
	//	Docker test = new Docker("fly");				//操作docker容器
	//	test.pause();												//暂停
	//	test.unpause();											//开启
	//test.delete();												//删除
		
	//	xmlBuild xml = new xmlBuild("fly");
	//	xml.modifySon();										//修改名称，生成新的build.xml文件
		
	//	xmlConfig xml = new xmlConfig("http://159.226.57.11/fly/AutoDeploy.git","3cb058d1-9c54-4ef9-b9dd-3fd085ac2a80","*/master","AutoDeploy",30000);  //修改git地址、用户id认证、git分支、工程名字和部署端口号，生成新的config.xml文件
	//	xml.modifySon();
		
		JenkinsBuild build = new JenkinsBuild("AutoDeploy","http://159.226.57.11/fly/AutoDeploy.git","3cb058d1-9c54-4ef9-b9dd-3fd085ac2a80","*/master",30000);
		build.jenkinsBuild();
		
		
		
//		Shell shell = new Shell("159.226.57.12", 22, "liulinyu", "liulinyu123");
//		
//		String[] commands = {"sudo cp  -f /home/liulinyu/build.xml  /var/lib/jenkins/jobs/AutoDeploy/workspace/"};
//		if (shell.executeCommands(commands)) {
//			System.out.println("success");
//			System.out.println(shell.getResponse());
//		} else {
//			System.out.println("fail");
//			System.out.println(shell.getResponse());
//		}
//		Thread.sleep(4000);
//		System.out.println(shell.getResponse());
	}
}
