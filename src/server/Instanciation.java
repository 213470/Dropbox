package server;

import java.io.File;

public class Instanciation {

	private String homeDir;

	public Instanciation(String homeDir) {
		super();
		this.homeDir = homeDir;
		onCreateInstance();
	}

	private void onCreateInstance() {
		File home = new File(homeDir);
		File[] list = home.listFiles();
		int serverCount = 0;
		for (File f : list) {
			if (f.isDirectory())
				serverCount++;
		}
		if (serverCount == 0) {
			for (int i = 1; i < 6; i++) {
				File f = new File(homeDir + File.separator + "server" + i);
				f.mkdir();
				System.out.println("Created folder named " + f.getName());
			}
		}else if(serverCount == 5) {
			System.out.println("Dropbox server instanciated");
		} else {
			System.out.println("Wrong configuration!");
		}

	}

}
