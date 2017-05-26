package utility;

import connection.Uploader;
import model.UserInfo;

public class Synchronizer extends Thread {
	
	private Crawler crawler;
	private UserInfo userInfo;

	public Synchronizer(Crawler crawler, UserInfo userInfo) {
		super();
		this.userInfo = userInfo;
		this.crawler = crawler;
	}

	@Override
	public void run() {
		while (true) {
			crawler.scanFiles();
			if (crawler.checkForDifference()) {
				System.out.println(crawler.getDifferenceList());

				Uploader ul = new Uploader(crawler.getDifferenceList());

				ul.connect();
				ul.sendFiles();

				crawler.updateJSON(userInfo.getUsername());
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	public Crawler getCrawler() {
		return crawler;
	}

}
