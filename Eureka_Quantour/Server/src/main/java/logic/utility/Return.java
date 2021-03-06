package logic.utility;

import data.service.ICompanyDataInterface;
import data.service.IDataInterface;
import data.service.IStockDataInterface;
import data.service.IStockSetInterface;
import data.service.IStrategyDataInterface;
import data.serviceimpl.CompanyDataController;
import data.serviceimpl.DataInterfaceImpl;
import data.serviceimpl.StockDataController_2;
import data.serviceimpl.StockSetDataController;
import data.serviceimpl.StrategyDataController;
import exception.*;
import po.SingleStockInfoPO;
import vo.SaleVO;
import vo.StrategyConditionVO;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by huihantao on 2017/4/6.
 */
public class Return {
    private Utility utility=Utility.getInstance();
    private IStockDataInterface stock = StockDataController_2.getInstance();

    private List<String> stockcode;
    private LocalDate begin;
    private LocalDate end;
    private SaleVO salevo;
    private StrategyConditionVO strategyConditionVO;
    private int days;
    private Comparator<SingleStockInfoPO> comparator;
    private List<LocalDate> timelist;
    private List<Double> jizhunfudu=new ArrayList<>();
    private List<Double> celuefudu=new ArrayList<>();

    private List<Double> jizhunshouyilv;
    private List<Double> celueshouyilv;


    public Return(List<String> stockcode, LocalDate begin, LocalDate end, SaleVO salevo, StrategyConditionVO strategyConditionVO) {
        this.stockcode = stockcode;
        this.begin = begin;
        this.end = end;
        this.salevo = salevo;
        this.strategyConditionVO = strategyConditionVO;
        this.days=salevo.getTiaocangqi();
        this.timelist=new ArrayList<>();
        String type=strategyConditionVO.getName();
        System.out.println(strategyConditionVO.getExtra());
        comparator=getcelue(strategyConditionVO.getName());



    }

    //get alpha 

    public Double getAlpha(){
        double rp=gerYearReturn();
        double rm=gerBasicYearReturn();
        double rf=0.04;
        return rp-(rf+(rm-rf)*getBeta());


    }
    //get beta

    public Double getBeta(){
        return utility.getCorvariance(jizhunfudu,celuefudu)/utility.getCorvariance(jizhunfudu,jizhunfudu);
    }

    // get strategy return when it uses for a year
    public Double gerYearReturn(){
        return getYear(celueshouyilv);
    }

    // get the return without a strategy

    public Double gerBasicYearReturn(){
        return getYear(jizhunshouyilv);
    }

    //abstract method for calculation
    private Double getYear(List<Double> list){
        double shuzi=list.get(list.size()-1);
        int i=0;
        LocalDate itr=LocalDate.of(begin.getYear(),begin.getMonth(),begin.getDayOfMonth());
        for (;itr.compareTo(end)<=0;itr=itr.plusDays(1)){
            i++;
        }
        double a=1+shuzi;
        double b=365.0/i;
        return Math.pow(a,b)-1;
    }
    //get sharp
    public Double getSharpe(){
        double ri=gerYearReturn();
        double rf=0.04;
        double seta=utility.getCorvariance(celueshouyilv,celueshouyilv);
        return (ri-rf)/seta;
    }

    //get zuidahuiche

    public Double getzuidaguiceh(){
        double max=0;
        for (int i=1;i<celueshouyilv.size();i++){
            max=Math.max(max,(celueshouyilv.get(i-1)-celueshouyilv.get(i)/celueshouyilv.get(i-1)));
        }
        return max;
    }

    // get the time list of strategy

    public List<LocalDate> getTimelist() {
        return timelist;
    }

    // calculate the return list without a strategy

    public List<Double> getBasicReturn ()
            throws PriceTypeException, NullStockIDException {
    	if(jizhunshouyilv==null){
    		 List<Double> list=new ArrayList<>();
    	        LocalDate iter=LocalDate.of(begin.getYear(),begin.getMonth(),begin.getDayOfMonth());

    	        double init=100.0;
    	        try {
    	            for (;
    	                 iter.compareTo(end)<=0;
    	                 iter=stock.addDays(iter,days))
    	            {
    	                double zheci=0;
    	                double shangci=0;


                            //fetch the data before some days
    	                    for (String name : stockcode) {
    	                        try {
    	                        SingleStockInfoPO po1 = stock.getSingleStockInfo(name, iter);
    	                        SingleStockInfoPO po2 = stock.getSingleStockInfo(name, stock.addDays(iter, days));
    	                        zheci = zheci + getjiage(po1);
    	                        shangci = shangci + getjiage(po2);
    	                        }
    	                        catch (NullDateException e) {
    	                            continue;
    	                        }
    	                    }

    	                    if(zheci==0) {
    	                        continue;
    	                    }
    	                    timelist.add(iter);
    	                jizhunfudu.add(shangci/zheci);
    	                init=init*(shangci/zheci);
    	                double rate=(init-100)/100;
    	                list.add(rate);

    	            }
    	        } catch (DateOverException e) {
//    	            e.printStackTrace();
    	        }
    	        jizhunshouyilv=list;

    	        return jizhunshouyilv;
    	}
    	else{
    		return jizhunshouyilv;
    	}
    }

    //get the return list of a strategy
    public List<Double> getStragetyReturn ( ) throws NullStockIDException, PriceTypeException {
        if(celueshouyilv==null){
        	double init=100.0;
            List<Double> list=new ArrayList<>();
            LocalDate iter=LocalDate.of(begin.getYear(),begin.getMonth(),begin.getDayOfMonth());
            try {
                for (;
                     iter.compareTo(end)<=0;
                     iter=stock.addDays(iter,days))
                {
                    double zheci=0;
                    double shangci=0;
                    List<SingleStockInfoPO> polist=new ArrayList<>();

                    for(String name:stockcode){
                        try {

                            polist.add(stock.getSingleStockInfo(name,iter));
                        } catch (NullDateException e) {
                            continue;
                        }
                    }
                    if(polist.size()==0) continue;


                    Collections.sort(polist,comparator);
//                    for (int as=0;as<polist.size();as++)
//                    System.out.println(polist.get(as).getName());

                    List<String> jilu=new ArrayList<>();



//                    System.out.println("assd");
                    int i=0;
                    int j=0;
                    while (j<Math.min(strategyConditionVO.getNums(),polist.size())&&i<Math.min(strategyConditionVO.getNums(),polist.size())){
//                        System.out.print(getjiage(polist.get(i)));

                        SingleStockInfoPO pozheci=polist.get(i);
                        i++;
                        SingleStockInfoPO poshangci = null;

                        try {
                            poshangci = stock.getSingleStockInfo(pozheci.getCode(),stock.addDays(iter,days));
                        } catch (NullDateException e) {
//                            System.out.println("as");
                            continue;
                        }
                        zheci=zheci+getjiage(pozheci);
                        shangci+=getjiage(poshangci);

                        j++;
                    }


                    if(zheci==0) continue;
                    celuefudu.add(shangci/zheci);
                    init=init*(shangci/zheci);

                    double rate=(init-100)/100;
                    list.add(rate);


                }
            } catch (DateOverException e) {
//                e.printStackTrace();
            }
            celueshouyilv=list;
            return list;
        }
        else{
        	return celueshouyilv;
        }
    }
    
    

    private double getjiage(SingleStockInfoPO po) throws PriceTypeException {
        String type=salevo.getTiaocangjiage();
        if (type.equals("收盘价")) return po.getAftClose();
        if (type.equals("开盘价")) return po.getOpen();
        throw new PriceTypeException();
    }


    //comparison method a kind of strategy
    private  class junzhicelue implements Comparator<SingleStockInfoPO> {

        private int days;
        private junzhicelue(List<Integer> objects){
            this.days=(Integer) objects.get(0);
        }


        @Override
        public int compare(SingleStockInfoPO o1, SingleStockInfoPO o2) {
            String name1=o1.getCode();
            String name2=o2.getCode();
            LocalDate now=o1.getDate();
            double junzhi1=0.0;
            double junzhi2=0.0;



            try {
                junzhi1=utility.getEMA(name1,now,days);
                junzhi2=utility.getEMA(name2,now,days);
            } catch (DateOverException e) {
                e.printStackTrace();
            } catch (NullStockIDException e) {
                e.printStackTrace();
            }
            double rate1=0.0;
            double rate2=0.0;

            if (junzhi1==Integer.MAX_VALUE && junzhi2==Integer.MAX_VALUE)
            {
                return 0;
            }
            if (junzhi1== Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }
            if (junzhi2== Integer.MAX_VALUE){
                return -Integer.MAX_VALUE;
            }
            try {
                rate1=(getjiage(o1)-junzhi1)/junzhi1;
                rate2=(getjiage(o2)-junzhi2)/junzhi2;
            } catch (PriceTypeException e) {
                e.printStackTrace();
            }

            int p=(int)(rate1*10000);
            int q=(int)(rate2*10000);


            return q-p;

        }
    }
    //comparison method a kind of strategy

    private class dongliangcelue implements Comparator<SingleStockInfoPO> {

        private int days;
        private dongliangcelue(List<Integer> objects){
            this.days=objects.get(0);
        }


        @Override
        public int compare(SingleStockInfoPO o1, SingleStockInfoPO o2) {

            String name1=o1.getCode();
            String name2=o2.getCode();

            LocalDate now=o1.getDate();
            LocalDate before=now.minusDays(days);
            try {
                before = stock.addDays(now,-days);
            } catch (DateOverException e) {
            }

//            System.out.println(before);
            SingleStockInfoPO o3=null;
            SingleStockInfoPO o4=null;
            double rate1=0;
            double rate2=0;
            try {
                o3 = stock.getSingleStockInfo(name1,before);


            } catch (NullStockIDException e) {
                e.printStackTrace();
            } catch (NullDateException e) {
//                e.printStackTrace();
            }

            try {
                o4 = stock.getSingleStockInfo(name2,before);
            } catch (NullStockIDException e) {
                e.printStackTrace();
            } catch (NullDateException e) {
//                e.printStackTrace();
            }

            if ((o3==null)&&(o4==null))
                return 0;

            if (o3==null)
                return Integer.MAX_VALUE;
            if (o4==null)
                return -Integer.MAX_VALUE;

            rate1=(o1.getAftClose()-o3.getAftClose())/o3.getAftClose();
            rate2=(o2.getAftClose()-o4.getAftClose())/o4.getAftClose();
            int p=(int)(rate1*10000);
            int q=(int)(rate2*10000);

            return q-p;

        }
    }

    private class pingjunshoupanjia implements Comparator<SingleStockInfoPO>{

        private int days;
        private pingjunshoupanjia(List<Integer> objects){
            this.days=objects.get(0);
        }

        @Override
        public int compare(SingleStockInfoPO o1, SingleStockInfoPO o2) {
            double avergae1=0;
            double avergae2=0;
            int j=0;
            int k=0;
            LocalDate now=o1.getDate();
            String code1=o1.getCode();
            String code2=o2.getCode();
            for (int i=0;i<days;i++){
                try {
                    SingleStockInfoPO po1=stock.getSingleStockInfo(code1,now.minusDays(i));
                    j++;
                    avergae1+=po1.getClose();
                } catch (NullStockIDException e) {
                    continue;
                } catch (NullDateException e) {
                    continue;
                }

                try {
                    SingleStockInfoPO po2=stock.getSingleStockInfo(code2,now.minusDays(i));
                    k++;
                    avergae2+=po2.getClose();
                } catch (NullStockIDException e) {
                    continue;
                } catch (NullDateException e) {
                    continue;
                }
            }
            avergae1=avergae1/j;
            avergae2=avergae2/k;


            return (int) (  10000*avergae1-10000*avergae2);

        }
    }

    private class maxdiff implements Comparator<SingleStockInfoPO>{

        @Override
        public int compare(SingleStockInfoPO o1, SingleStockInfoPO o2) {
            double diff1=o1.getClose()-o1.getOpen();
            double diff2=o2.getClose()-o2.getOpen();

            return (int) (  10000*diff1-10000*diff2);
        }
    }

    private Comparator<SingleStockInfoPO> getcelue(String mingcheng){

        if (mingcheng.equals("动量策略")) return new dongliangcelue(strategyConditionVO.getExtra());
        if (mingcheng.equals("均值策略")) return new junzhicelue(strategyConditionVO.getExtra());
        if (mingcheng.equals("平均收盘价")) return new pingjunshoupanjia(strategyConditionVO.getExtra());
        if (mingcheng.equals("最大差别")) return new maxdiff();
        return new maxdiff();
    }







}
