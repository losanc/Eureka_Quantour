package logic.supportservice;

public interface SortArrayInterface{

	/**
	 * @Description: 从小到大排序，并根据nums每一个index的值，返回该值在排序后的index
	 * @author: 	 hzp
	 * @date:        2017-06-01
	 * @param        nums       sort nums and return every num's order
	 */
	int[] getSortIndexMinToMax( double[] nums );

	
	/**
	 * @Description: 从大到小排序，并根据nums每一个index的值，返回该值在排序后的index
	 * @author: 	 hzp
	 * @date:        2017-06-01
	 * @param        nums       sort nums and return every num's order
	 */
	int[] getSortIndexMaxToMin( double[] nums );

	
	/**
	 * @Description: 将传入数组内容 反序
	 * @author: hzp
	 * @date: 2017年6月3日
	 */
	int[] arrayConverse( int[] index );
}