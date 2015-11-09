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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 *
 */
public class ClusterDRP {
	String discordantReadPairFilePath;
	int median;
	int standard_deviation;
	int windowSize;
	
	public ClusterDRP(String discordantReadPairFilePath, int median, int standard_deviation){
		this.discordantReadPairFilePath = discordantReadPairFilePath;
		this.median = median;
		this.standard_deviation = standard_deviation;	
	}
	public void clusterDRP(String outClusterFile) throws IOException {
        List<ClusterNode> nodes = new ArrayList<ClusterNode>();
        BufferedWriter bw = new BufferedWriter(new FileWriter(outClusterFile));
        BufferedReader br = new BufferedReader(new FileReader(new File(this.discordantReadPairFilePath)));
        String line = null;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] record = line.split("\t");
            String chrom = record[0];
            int leftFirst = Integer.parseInt(record[1]);
            int rightFirst = Integer.parseInt(record[2]);
            int leftSecond = Integer.parseInt(record[3]);
            int rightSecond = Integer.parseInt(record[4]);
            int insertSize = Integer.parseInt(record[5]);
            String sample = record[6];
            nodes.add(new ClusterNode(chrom, leftFirst, rightFirst, leftSecond, rightSecond, insertSize, sample));
        }
        int[] visit = new int[nodes.size()];
        Cluster cluster = null;
        for (int i = 0; i < nodes.size(); i++) {
            if (visit[i] == 0) {
                cluster = new Cluster();
                cluster.add(nodes.get(i));
                visit[i] = 1;
                for (int j = i + 1; j < nodes.size(); j++) {
                    if (visit[j] == 0) {
                        ClusterNode node = nodes.get(j);
                        if (!node.getChrom().equals(cluster.getChrom())) {
                            break;
                        }
                        if (node.getLeftFirst() <= cluster.getStartNode().getLeftFirst()
                                && cluster.getStartNode().getLeftFirst() <= node.getLeftFirst() + this.median + 3 * this.standard_deviation
                                && Math.abs(
                                cluster.getStartNode().getLeftFirst() - node.getLeftFirst()
                                - (cluster.getStartNode().getRightSecond() - node.getRightSecond())) <= 6 * this.standard_deviation) {
                            cluster.add(node);
                            visit[j] = 1;
                        }
                    }
                }
                if (cluster.getCount() > 4) {
                    int left = cluster.getStart();
                    int right = cluster.getEnd();
                    int startBin = (left + 1) / windowSize;
                    int endBin = (right + 1) / windowSize;
                    for (int j = startBin; j <= endBin; j++) {
                        bw.write(cluster.getChrom()
                                + "\t" + (j * windowSize + 1)
                                + "\t" + (j + 1) * windowSize
                               // + "\t" + cluster.getCount(samples[0])
                               // + "\t" + cluster.getCount(samples[1])
                                + "\n");
                    }
                }
            }
        }
        br.close();
        bw.close();
    }
}
