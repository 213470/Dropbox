package utility;

public class Dispatcher {
	
	private String serverPath;
	
	public Dispatcher(String serverPath){
		this.setServerPath(serverPath);
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

}
