package utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class HomeCrawler {
	
	private String homeDir;
	private List<File> fileList;
	
	public HomeCrawler(String homeDir) {
		this.homeDir = homeDir;
		fileList = new LinkedList<File>();
	}
	
	public void mapDirectoriesToList(){
		try {
			Files.walk(Paths.get(homeDir))
			.filter(Files::isRegularFile)
			.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
