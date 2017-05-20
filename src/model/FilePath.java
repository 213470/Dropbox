package model;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FilePath {

	private String username;
	private int fileNo;
	private List<File> fileList;
	
	public FilePath() {
		this.username = "";
		this.fileNo = 0;
		this.fileList = new LinkedList<>();
	}

	public FilePath(String username) {
		this.username = username;
		this.fileNo = 0;
		this.fileList = new LinkedList<>();
	}

	public FilePath(String username, List<File> fileList) {
		this.username = username;
		this.fileNo = fileList.size();
		this.fileList = fileList;
	}

	public FilePath(String username, int fileNo, List<File> fileList) {
		this.username = username;
		this.fileNo = fileNo;
		this.fileList = fileList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	@Override
	public boolean equals(Object obj) {
		return this.username.equals(((FilePath) obj).getUsername()) && this.fileNo == ((FilePath) obj).getFileNo();
	}

}
