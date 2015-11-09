/**
 * 
 */
package variationDetect;

import java.io.File;

import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;

/**
 * @author Administrator
 *
 */
public class GetMedianAndsStaDeviation {
	float median;
	float standard_deviation;
	int mix;
	void getMedian(){
		GetReadPair getReadPair = new GetReadPair();
		String file = getReadPair.getDRPFile();
		final SamReader reader = SamReaderFactory.makeDefault().open(new File("E:/na.bam"));
		long count = 0;
		long sum = 0;
		float res = 0;
		int i = 0;
		int mix_InsertSize = 0;
		for(SAMRecord record:reader){
			i++;
			count++;
			int insertSize = Math.abs(record.getInferredInsertSize());
			if(insertSize > mix_InsertSize)
				mix_InsertSize = insertSize;
			if(count%5==0){
				sum+=insertSize;
				float temp = insertSize - this.median;
				res += temp*temp;
				continue;
			}
			System.out.println(i);
		}
		this.median = 5*sum/count;
		this.standard_deviation = (float) Math.sqrt(5*res/count);
		this.mix = mix_InsertSize;
	}
}
