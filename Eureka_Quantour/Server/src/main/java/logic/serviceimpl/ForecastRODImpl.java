package logic.serviceimpl;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.time.LocalDate;

import data.service.IDataInterface;
import data.serviceimpl.DataInterfaceImpl;
import exception.NullDateException;
import exception.NullStockIDException;
import logic.service.ForecastRODInterface;
import po.SingleStockInfoPO;
import vo.StockRODVO;

public class ForecastRODImpl implements ForecastRODInterface{

	private static DecimalFormat df = new DecimalFormat("0.0000");
	
	private IDataInterface idata = new DataInterfaceImpl();
	
	@Override
	public StockRODVO getStockROD(String stockcode, LocalDate begindate, LocalDate enddate) throws RemoteException{
		// TODO Auto-generated method stub
		StockRODVO srod = new StockRODVO();
		
		double ROD = 0;
		int idate = 0;
		int iROD = 0;
		
		double close1 = 0;
		double close2 = 0;
		LocalDate date = begindate;
		SingleStockInfoPO ssi1 = new SingleStockInfoPO();
		SingleStockInfoPO ssi2 = new SingleStockInfoPO();
		
		try{
			idate = ForecastRODImpl.getDayOfWeek(date)-1;
			while( idate==-1){
				date = date.plusDays(1);
				idate = ForecastRODImpl.getDayOfWeek(date)-1;
			}
			srod.nodata[idate][0]++;
			
			ssi1 = idata.getSingleStockInfo(stockcode, date);
			date = date.plusDays(1);
			ssi2 = idata.getSingleStockInfo(stockcode, date);
			close1 = ssi1.getClose();
			close2 = ssi2.getClose();
			ROD = (close2-close1)/close1;

			iROD = ForecastRODImpl.doubletoindex( ROD );
			
			srod.wROD[idate][iROD]++;
			srod.RODw[iROD][idate]++;
		}catch ( NullStockIDException e ){
			e.printStackTrace();
			date = date.plusDays(1);
		}catch ( NullDateException e){
			srod.nodata[idate][1]++;
			e.printStackTrace();
			date = date.plusDays(1);
		}
		
		date = date.minusDays(1);
		
		while( date.compareTo(enddate)<=0 ){
			try{
				date = date.plusDays(1);
				
				idate = ForecastRODImpl.getDayOfWeek(date)-1;
				while( idate==-1){
					date = date.plusDays(1);
					idate = ForecastRODImpl.getDayOfWeek(date)-1;
				}
				srod.nodata[idate][0]++;
				
				ssi1 = ssi2;
				close1 = close2;
				ssi2 = idata.getSingleStockInfo(stockcode, date);
				close2 = ssi2.getClose();

				ROD = (close2-close1)/close1;

				iROD = ForecastRODImpl.doubletoindex( ROD );
			
				srod.wROD[idate][iROD]++;
				srod.RODw[iROD][idate]++;

			}catch ( NullStockIDException e ){
				e.printStackTrace();
			}catch ( NullDateException e){
				srod.nodata[idate][1]++;
				e.printStackTrace();
			}
			
		}
		return srod;
	}
	
	private static int getDayOfWeek( LocalDate date){
		String dow = date.getDayOfWeek().toString();
		if( dow.equals("MONDAY"))
			return 1;
		else if( dow.equals("TUESDAY") )
			return 2;
		else if( dow.equals("WEDNESDAY") )
			return 3;
		else if( dow.equals("THURSDAY") )
			return 4;
		else if( dow.equals("FRIDAY"))
			return 5;
		else
			return 0;
	}
	
	private static int doubletoindex( double ROD){
		String str = df.format(ROD);
		double d = Double.valueOf(str);
		
		if( d<-0.1)
			return 0;
		else if( d<-0.09 && d>=-0.1 )
			return 1;
		else if( d<-0.08 && d>=-0.09 )
			return 2;
		else if( d<-0.07 && d>=-0.08 )
			return 3;
		else if( d<-0.06 && d>=-0.07 )
			return 4;
		else if( d<-0.05 && d>=-0.06 )
			return 5;
		else if( d<-0.04 && d>=-0.05 )
			return 6;
		else if( d<-0.03 && d>=-0.04 )
			return 7;
		else if( d<-0.02 && d>=-0.03 )
			return 8;
		else if( d<-0.01 && d>=-0.02 )
			return 9;
		else if( d<0 && d>=-0.01 )
			return 10;
		else if( d==0 )
			return 11;
		else if( d<=0.01 && d>0 )
			return 12;
		else if( d<=0.02 && d>0.01 )
			return 13;
		else if( d<=0.03 && d>0.02 )
			return 14;
		else if( d<=0.04 && d>0.03 )
			return 15;
		else if( d<=0.05 && d>0.04 )
			return 16;
		else if( d<=0.06 && d>0.05 )
			return 17;
		else if( d<=0.07 && d>0.06 )
			return 18;
		else if( d<=0.08 && d>0.07 )
			return 19;
		else if( d<=0.09 && d>0.08 )
			return 20;
		else if( d<=0.1 && d>0.09 )
			return 21;
		else
			return 22;
	}
	
	public static void main(String args[]){
		LocalDate date = LocalDate.of(2017, 5, 1);
		for( int i=0; i<5; i++){
			System.out.print(date.getDayOfWeek()+"  ");
			System.out.println( ForecastRODImpl.getDayOfWeek(date) );
			date = date.plusDays(1);
		}
	}
	

}