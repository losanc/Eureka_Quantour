package logic.supportimpl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import data.service.IDataInterface;
import data.serviceimpl.DataInterfaceImpl;
import exception.NullDateException;
import exception.NullStockIDException;
import vo.SingleStockInfoVO;

public class testBPNetImpl {
	
	private static IDataInterface idata = new DataInterfaceImpl();
	
	private static DecimalFormat df = new DecimalFormat("#0.00"); 
	
	private static LocalDate zuizao = LocalDate.of(2005,2,1);
	private static String stockcode = "000938";
	
	private static final int characterNum = 5;
	private static int maxNum = 0;
	
	private static int openIndex = 0;
	private static int highIndex = 1;
	private static int closeIndex = 2;
	private static int lowIndex = 3;
	private static int volumeIndex = 4;
	
	
	public static void main( String args[] ){
		maxNum = new BPNetSupportImpl().getMaxNUm();
		SingleStockInfoVO ssi = new SingleStockInfoVO();
		int vLen = 0;
		
		// get before 100 days' data
		vLen = 100;
		// 创建序列
	    double[] openPrice = new double[vLen];
	    double[] highPrice = new double[vLen];
	    double[] closePrice = new double[vLen];
	    double[] lowPrice = new double[vLen];
	    double[] vol = new double[vLen];
	    	
		LocalDate dateT = LocalDate.of(2008, 4, 1);
		int index = vLen-1;
		while( index>-1 && dateT.compareTo(zuizao)>0 ){
			try{
				dateT = new testBPNetImpl().getValidBeforeDate( dateT );
				ssi = new SingleStockInfoVO( idata.getSingleStockInfo(stockcode, dateT) );
				
				openPrice[index] =  ssi.getOpen() ;
				highPrice[index] =  ssi.getHigh();
				closePrice[index] =  ssi.getClose();
				lowPrice[index] =  ssi.getLow();
				vol[index] =  (double)ssi.getVolume();
				
				index--;
			}catch ( NullStockIDException e ){
				e.printStackTrace();
			}catch ( NullDateException e){}
		}
		
		vLen = maxNum;
		double[][] QMaxNumDayData = new double[maxNum][characterNum];
		index = vLen-1;
		while( index>-1 && dateT.compareTo(zuizao)>0 ){
			try{
				dateT = new testBPNetImpl().getValidBeforeDate( dateT );
				ssi = new SingleStockInfoVO( idata.getSingleStockInfo(stockcode, dateT) );
				
				QMaxNumDayData[index][openIndex] =  ssi.getOpen();
				QMaxNumDayData[index][highIndex] =  ssi.getHigh();
				QMaxNumDayData[index][closeIndex] =  ssi.getClose();
				QMaxNumDayData[index][lowIndex] =  ssi.getLow();
				QMaxNumDayData[index][volumeIndex] =  (double)ssi.getVolume();
				
				index--;
			}catch ( NullStockIDException e ){
				e.printStackTrace();
			}catch ( NullDateException e){}
		}
		double close = -1;
		while( close==-1 && dateT.compareTo(zuizao)>0 ){
			try{
				dateT = new testBPNetImpl().getValidBeforeDate( dateT );
				ssi = new SingleStockInfoVO( idata.getSingleStockInfo(stockcode, dateT) );
				close = ssi.getClose();
			}catch ( NullStockIDException e ){
				e.printStackTrace();
			}catch ( NullDateException e){}
		}
		
	    BPNetSupportImpl m = new BPNetSupportImpl();
	    double[][] dataset = m.bpTrain( closePrice, highPrice, lowPrice, openPrice, vol, QMaxNumDayData );
	    double[][] target = new double[dataset.length][1];
	    for( int i=0; i<dataset.length; i++ ){
            if( i<dataset.length-2)
	    	    target[i][0] = closePrice[i+1];
            else
                target[i][0] = close;
	    }
	    
	    
/*********************************************************************************************************/	    
	    
	    
	    vLen = 100;
	    //创建序列
	    double[] openPrice2 = new double[vLen];
	    double[] highPrice2 = new double[vLen];
	    double[] closePrice2 = new double[vLen];
	    double[] lowPrice2 = new double[vLen];
	    double[] vol2 = new double[vLen];
        
        // get before 100 days' data
        dateT = LocalDate.of(2008, 4, 1);
        index = vLen-1;
        while( index>-1 && dateT.compareTo(zuizao)>0 ){
        	try{
        		dateT = new testBPNetImpl().getValidLatterDate( dateT );
        		ssi = new SingleStockInfoVO( idata.getSingleStockInfo(stockcode, dateT) );
        		
        		openPrice2[index] =  ssi.getOpen() ;
				highPrice2[index] =  ssi.getHigh();
				closePrice2[index] =  ssi.getClose();
				lowPrice2[index] =  ssi.getLow();
				vol2[index] =  (double)ssi.getVolume();
        		
        		index--;
        	}catch ( NullStockIDException e ){
        		e.printStackTrace();
        	}catch ( NullDateException e){}
        }
        close = -1;
        while( close==-1 && dateT.compareTo(zuizao)>0 ){
            try{
                dateT = new testBPNetImpl().getValidLatterDate( dateT );
                ssi = new SingleStockInfoVO( idata.getSingleStockInfo(stockcode, dateT) );
                close = ssi.getClose();
            }catch ( NullStockIDException e ){
                e.printStackTrace();
            }catch ( NullDateException e){}
        }
        
        vLen = maxNum;
		double[][] QMaxNumDayData2 = new double[maxNum][characterNum];
		dateT = LocalDate.of(2008, 4, 1);
		index = vLen-1;
		while( index>-1 && dateT.compareTo(zuizao)>0 ){
			try{
				dateT = new testBPNetImpl().getValidBeforeDate( dateT );
				ssi = new SingleStockInfoVO( idata.getSingleStockInfo(stockcode, dateT) );
				
				QMaxNumDayData2[index][openIndex] =  ssi.getOpen();
				QMaxNumDayData2[index][highIndex] =  ssi.getHigh();
				QMaxNumDayData2[index][closeIndex] =  ssi.getClose();
				QMaxNumDayData2[index][lowIndex] =  ssi.getLow();
				QMaxNumDayData2[index][volumeIndex] =  (double)ssi.getVolume();
				
				index--;
			}catch ( NullStockIDException e ){
				e.printStackTrace();
			}catch ( NullDateException e){}
		}
        
        BPNetSupportImpl m2 = new BPNetSupportImpl();
        double[][] dataset2 = m2.bpTrain( closePrice2, highPrice2, lowPrice2, openPrice2, vol2, QMaxNumDayData2 );
        double[] target2 = new double[dataset2.length];
        for( int i=0; i<dataset2.length; i++ ){
            if( i<dataset2.length-2 )
        	    target2[i] = closePrice2[i+1];
            else
                target2[i] = close;
        }
        
        
/**********************************************************************************************************/        
        
        
        BPNetImpl bp = new BPNetImpl( new int[]{6,13,13,1}, 0.15, 0.8 );
        //迭代训练5000次
        for( int n=0; n<5000; n++ )
            for( int i=0; i<dataset.length; i++ )
                bp.train( dataset[i], target[i] );


        //测试数据集
        double[] result = new double[dataset2.length];
        List<ArrayList<Double>> resultList = new ArrayList<ArrayList<Double>>();
        for( int i=1; i<dataset2.length; i++ ){
            double[] a = bp.computeOut( dataset2[i] );
            ArrayList<Double> list = new ArrayList<Double>();
            result[i] = 100*(-Math.log(1/a[0]-1));
            list.add(result[i]);
            list.add(target2[i]);
            resultList.add(list);
            
            System.out.print(" [ ");
            for( int j=0; j<dataset2[i].length; j++ )
            	System.out.print( df.format(dataset2[i][j]) + " " );
            System.out.print(" ] : ");
            System.out.println( df.format(result[i]-target2[i-1]) + "  :  " + df.format(target2[i]-target2[i-1]) );
        }
		
	}
	

/****************************************************************************************************************/
	 private int getDayOfWeek( LocalDate date){
 		String dow = date.getDayOfWeek().toString();
 		if( dow.equals("MONDAY"))
 			return 0;
 		else if( dow.equals("TUESDAY") )
 			return 1;
 		else if( dow.equals("WEDNESDAY") )
 			return 2;
 		else if( dow.equals("THURSDAY") )
 			return 3;
 		else if( dow.equals("FRIDAY"))
 			return 4;
 		else
 			return -1;
 	}
     
	 
     private LocalDate getValidLatterDate( LocalDate date ){
 		date = date.plusDays(1);
 		while( getDayOfWeek(date)==-1 )
 			date = date.plusDays(1);
 		return date;
 	}

     
 	private LocalDate getValidBeforeDate( LocalDate date ){
 		date = date.minusDays(1);
 		while( getDayOfWeek(date)==-1 )
 			date = date.minusDays(1);
 		return date;
 	}
}