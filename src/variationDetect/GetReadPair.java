/**
 * 
 */
package variationDetect;

import htsjdk.samtools.DefaultSAMRecordFactory;
import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SamPairUtil;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;
import htsjdk.samtools.ValidationStringency;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Administrator
 *
 */
public class GetReadPair {
	String inputFile;
	String outputFile;
	float median;
	float standard_deviation;
	public GetReadPair() {
	}
	
	public GetReadPair(String inputFile, String outputFile, float median, float standard_devition) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.median = median;
		this.standard_deviation = standard_devition;
	}
	
	void getDRP() throws IOException {
		GetMedianAndsStaDeviation getMedianAndsStaDeviation = new GetMedianAndsStaDeviation();
		final int MIX_INSERTSIZE_SIZE = (int) getMedianAndsStaDeviation.mix;
		final SamReader reader = SamReaderFactory.makeDefault().open(new File(this.inputFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.outputFile));
		 bw.write("CHROM"
	                + "\tALIGNMENTSTART"
	                + "\tALIGNMENTEND"
	                + "\tMATEALIGNMENTSTART"
	                + "\tMATEALIGNMENTEND"
	                + "\tINSERTSIZE"
	                + "\tSAMPLEID"
	                + "\r\n");
		int i = 0;
		for(SAMRecord record:reader){
			i++;
			System.out.println(i);
			System.out.println(record.getNotPrimaryAlignmentFlag());
			if(record.getNotPrimaryAlignmentFlag()||record.getReadUnmappedFlag()||record.getMateUnmappedFlag()
					||record.getDuplicateReadFlag()||!record.getReadPairedFlag()||record.isSecondaryOrSupplementary()){
				continue;
			}
			String id = record.getReadGroup().getSample();
			SamPairUtil.PairOrientation orientation = SamPairUtil.getPairOrientation(record);
            if (orientation == SamPairUtil.PairOrientation.FR)
            {
            	if (record.getReferenceName().equals(record.getMateReferenceName())
                        && record.getAlignmentStart() <= record.getMateAlignmentStart()){
            		int insertSize = Math.abs(record.getInferredInsertSize());
//            		System.out.println(record.getReferenceName()+"  "+record.getAlignmentStart()+"  "+ record.getAlignmentEnd()
//        					+"  "+record.getMateAlignmentStart()+"  "+(record.getMateAlignmentStart()+Math.abs(record.getInferredInsertSize())-1)
//        					+"  "+Math.abs(record.getInferredInsertSize())+"  "+record.getReadGroup().getSample());
            		if (insertSize > (this.median + 3 * this.standard_deviation)
                            && insertSize < MIX_INSERTSIZE_SIZE){
	            		bw.write(record.getReferenceName() + "\t" + record.getAlignmentStart()
	                            + "\t" + record.getAlignmentEnd()
	                            + "\t" + record.getMateAlignmentStart()
	                            + "\t" + (record.getMateAlignmentStart() + insertSize - 1)
	                            + "\t" + insertSize
	                            + "\t" + id + "\r\n");
            		}
            	}
            }
		}
		reader.close();
		bw.close();
	}
	//get the outputFile 
	String getDRPFile(){
		return this.outputFile;
		
	}
}
