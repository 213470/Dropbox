import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Testing {

	public static void main(String[] args) {

//		Crawler crawler = new Crawler(args[1]);
		
//		crawler.scanFiles();
//		crawler.prepareJSON("mats");
//		crawler.checkForDifference();
//		for(File f : crawler.getDifferenceList()) {
//			client;
//		}
		
//		String s = "/home/madmatts/Dropbox/Matts/";
//		String n = "/home/madmatts/Dropbox/Matts/all/atom1";
//		System.out.println(n.lastIndexOf(File.separator, s.length()-1));
//		
		
		Queue<Integer> queue = Collections.asLifoQueue(new LinkedList<Integer>());	
		queue.add(1);
		queue.add(2);
		queue.add(3);
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}

}
