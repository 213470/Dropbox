import utility.Crawler;

public class Testing {

	public static void main(String[] args) {

		Crawler crawler = new Crawler(args[1]);
		
		crawler.mapDirectoriesToList();
//		crawler.prepareJSON("mats");
		crawler.checkForDifference();
//		for(File f : crawler.getDifferenceList()) {
//			client;
//		}
		
		
		
	}

}
