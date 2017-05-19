package utility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.FilePath;

public class Crawler {

	private String homeDir;
	private List<File> fileList;

	public Crawler(String homeDir) {
		this.homeDir = homeDir;
		fileList = new LinkedList<File>();
	}

	public void mapDirectoriesToList() {
		walk(homeDir);
	}

	private void walk(String path) {
		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		Arrays.sort(list, new Comparator<Object>() {
			@Override
			public int compare(Object f1, Object f2) {
				return ((File) f1).getPath().compareTo(((File) f2).getPath());
			}
		});
		;

		for (File f : list) {
			if (!f.isDirectory()) {
				if (!f.getPath().endsWith("files.json")) {
					fileList.add(f);
					System.out.println(f.getPath());
				}
			} else {
				walk(f.getPath());
			}

		}

	}

	public void prepareJSON(String username) {
		File home = new File(homeDir + "/files.json");
		FilePath fp = new FilePath(username, fileList);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(home, fp);
		} catch (JsonGenerationException e) {
			System.out.println(e.getClass());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println(e.getClass());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getClass());
			e.printStackTrace();
		}
	}

}
