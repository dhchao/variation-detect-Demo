/**
 * 
 */
package variationDetect;

import htsjdk.samtools.util.IntervalTree.Node;

/**
 * @author Administrator
 *
 */
public class ClusterNode {
	private String chrom;
	private int leftFirst,  rightFirst, leftSecond, rightSecond, insertSize;
	private String sample;
	/**
	 * @return the chrom
	 */
	
	public String getChrom() {
		return this.chrom;
	}
	
	public ClusterNode(String chrom, int leftFirst, int rightFirst,
			int leftSecond, int rightSecond, int insertSize, String sample) {
		super();
		this.chrom = chrom;
		this.leftFirst = leftFirst;
		this.rightFirst = rightFirst;
		this.leftSecond = leftSecond;
		this.rightSecond = rightSecond;
		this.insertSize = insertSize;
		this.sample = sample;
	}
	/**
	 * @param chrom the chrom to set
	 */
	public void setChrom(String chrom) {
		this.chrom = chrom;
	}
	/**
	 * @return the leftFirst
	 */
	public int getLeftFirst() {
		return this.leftFirst;
	}
	/**
	 * @param leftFirst the leftFirst to set
	 */
	public void setLeftFirst(int leftFirst) {
		this.leftFirst = leftFirst;
	}
	/**
	 * @return the rightFirst
	 */
	public int getRightFirst() {
		return this.rightFirst;
	}
	/**
	 * @param rightFirst the rightFirst to set
	 */
	public void setRightFirst(int rightFirst) {
		this.rightFirst = rightFirst;
	}
	/**
	 * @return the leftSecond
	 */
	public int getLeftSecond() {
		return this.leftSecond;
	}
	/**
	 * @param leftSecond the leftSecond to set
	 */
	public void setLeftSecond(int leftSecond) {
		this.leftSecond = leftSecond;
	}
	/**
	 * @return the rightSecond
	 */
	public int getRightSecond() {
		return this.rightSecond;
	}
	/**
	 * @param rightSecond the rightSecond to set
	 */
	public void setRightSecond(int rightSecond) {
		this.rightSecond = rightSecond;
	}
	/**
	 * @return the insertSize
	 */
	public int getInsertSize() {
		return this.insertSize;
	}
	/**
	 * @param insertSize the insertSize to set
	 */
	public void setInsertSize(int insertSize) {
		this.insertSize = insertSize;
	}
	/**
	 * @return the sample
	 */
	public String getSample() {
		return this.sample;
	}
	/**
	 * @param sample the sample to set
	 */
	public void setSample(String sample) {
		this.sample = sample;
	}
	
}
