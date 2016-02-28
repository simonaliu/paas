package ssh;

public class Docker {
	
	private String proj_name;
	private Shell shell;
	
	public Docker(String proj_name){
		this.proj_name = proj_name;
	}
	
	//开启容器服务
	public boolean unpause(){
		String commands_unpause = "docker ps -a | grep '" + proj_name + "' | awk '{print $1}' | xargs docker unpause";
		this.shell = new Shell("159.226.57.12", 22, "liulinyu", "liulinyu123");
		String[] commands = {commands_unpause};
		return shell.executeCommands(commands);
	}
	
	//暂停容器服务
	public boolean pause(){
		String commands_pause = "docker ps -a | grep '" + proj_name + "' | awk '{print $1}' | xargs docker pause";
		this.shell = new Shell("159.226.57.12", 22, "liulinyu", "liulinyu123");
		String[] commands = {commands_pause};
		return shell.executeCommands(commands);
	}
	
	//删除容器服务，并删除相应镜像
	public boolean delete(){
		String commands_stop = "docker ps -a | grep '" + proj_name + "' | awk '{print $1}'  | xargs docker stop";
		String commands_delete = "docker ps -a | grep '" + proj_name + "' | awk '{print $1}'  | xargs docker rm";
		String commands_deleteImages="docker images | grep '" + proj_name + "' | awk '{print $3 }' | xargs docker rmi";
		this.shell = new Shell("159.226.57.12", 22, "liulinyu", "liulinyu123");
		String[] commands = {commands_stop,commands_delete,commands_deleteImages};
		return shell.executeCommands(commands);
	}
	
}
