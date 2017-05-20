import utility.Crawler;

public class Testing {

	public static void main(String[] args) {

		Crawler crawler = new Crawler(args[1]);
		crawler.mapDirectoriesToList();
//		crawler.prepareJSON("mats");
		crawler.checkForDifference();
	
		
//		try {
//			System.out.println();
//			System.out.println(f.canExecute());
//			System.out.println(f.canRead());
//			System.out.println(f.canWrite());
//			System.out.println(f.setReadOnly());
//			System.out.println(f.getCanonicalPath());
//			System.out.println(f.getAbsolutePath());
//			System.out.println(f.getFreeSpace());
//			System.out.println(f.getName());
//			System.out.println(f.getParent());
//			System.out.println(f.getPath());
//			System.out.println(f.getTotalSpace());
//			System.out.println(f.getUsableSpace());
//			System.out.println(f.lastModified());
//			System.out.println(f.length());
//			System.out.println(f.toPath());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
