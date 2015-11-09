/**
 * 
 */
package variationDetect;

import java.io.IOException;

/**
 * @author Administrator
 *
 */
public class testDemo {
	public static void main(String []args){
		String inputFile = "E:/na12878u.bam";
		String outputFile = "E:/result.txt";
//		GetReadPair getReadPair = new GetReadPair(inputFile, outputFile);
//		try {
//			getReadPair.getDRP();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		GetMedianAndsStaDeviation getMedianAndsStaDeviation = new GetMedianAndsStaDeviation();
		getMedianAndsStaDeviation.getMedian();
		System.out.println(getMedianAndsStaDeviation.median);
	}
}
