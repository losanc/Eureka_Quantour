package logic.supportservice;

import java.time.LocalDate;
import java.util.List;

import po.SingleStockInfoPO;

public interface PredictInterface {

	/**
	 * @author H2P
	 * @date   2017-06-01
	 * @param  closes     datas of close price
	 * @param  m          length of vector
	 * @param  k          number of relevant character
	 */
	double KNNPredictPrice( List<Double> closes, List<LocalDate> dates, int m, int k );
	
	
	/**
	 * @Description: a very simple function to prediction
	 * @author: hzp
	 * @date: 2017年6月14日
	 * @param ZPrice	昨天价格
	 * @param QPrice	前天价格
	 */
	double SBPredictPrice( double ZPrice, double QPrice );
	
	
	/**
	 * @Description: calculate tomorrow price
	 * @author: hzp
	 * @date: 2017年6月13日
	 * @param stockcode
	 * @param date
	 * @param len	容量池的大小
	 * @param m		length of vector
	 * @param k		number of relevant character
	 */
	double KNNPredictPriceForStrategy( String stockcode, LocalDate date, int len, 
			int m, int k, SingleStockInfoPO next );
	
	
	/**
	 * @Description: calculate tomorrow ROD 
	 * @author: hzp
	 * @date: 2017年6月13日
	 * @param stockcode
	 * @param date
	 * @param len	容量池的大小
	 * @param m		length of vector
	 * @param k		number of relevant character
	 */
	double KNNPredictRODForStrategy( String stockcode, LocalDate date, int len, 
			int m, int k, SingleStockInfoPO next );
}
