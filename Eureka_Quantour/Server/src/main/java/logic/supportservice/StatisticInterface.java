package logic.supportservice;

/**
 * @Description: 
 * @author: hzp
 * @date: 2017年6月3日
 * @version: V1.0
 */
public interface StatisticInterface {

	/**
	 * 
	 * @Description: judge if real value in the zhi xin qu jian
	 * @author: hzp
	 * @date: May 17, 2017
	 * @param: @param num : chou yang ci shu
	 * @param: @param alpha : ji suan zhi xin qu jian de α
	 * @param: @param real
	 */
	boolean predictROE( double average, double square, int num, double alpha, double real);
	
	/**
	 * 进过测试，结果和为 1 
	 * @Description: function : Wi = w0 + i * d; k Wi's values' sum = 1
	 * @author: hzp
	 * @date: May 24, 2017
	 * @param: @param k : number of factor's weight 
	 * @param: @param i : index of factor
	 */
	double getWeight( int k, int i );
}