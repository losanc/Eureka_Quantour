package presentation.chart.lineChart;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import en_um.ChartKind;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import presentation.chart.chartService;
import presentation.chart.function.ListToArray;
import presentation.chart.function.ListToArrayService;
import vo.YieldChartDataVO;

/**
 * @Description: 收益对比图，基准收益和策略收益比较
 * @author: hzp 
 * @date: Apr 17, 2017
 */
public class YieldComparedChart implements chartService {
	
	private ListToArrayService listToArray = new ListToArray();
	
	private DecimalFormat df = new DecimalFormat("#0.00");
	private NumberFormat nf = NumberFormat.getPercentInstance();
	
	private AnchorPane pane = new AnchorPane();
	private AnchorPane chartpane = new AnchorPane();
	private AnchorPane infopane = new AnchorPane();
	
	private ComparedChart comparedChart;
    private int space;

	public YieldComparedChart( YieldChartDataVO ycd ){
		LocalDate[] date = listToArray.changeLocalDate(ycd.getDatelist());
    	List<Double[]> doubleList = new ArrayList<>();
    	List<String> dataName = new ArrayList<>();
    	doubleList.add( listToArray.changeDouble(ycd.getJizhunlist()) );
    	dataName.add("基础");
    	doubleList.add( listToArray.changeDouble(ycd.getCeluelist()) );
    	dataName.add("策略");
    	
    	LocalDate[] dates = new LocalDate[date.length/2];
    	for( int i=0; i<dates.length; i++)
    		dates[i] = date[i];
    	comparedChart = new ComparedChart( date, doubleList, dataName, ChartKind.YIELDCOMPARED);
    	
    	double layout = 70;
        space = 40;
    	
    	/**
         * @author: hzp
         * @time: 2017年4月16日
         * @param: layout: to set the label's width
         * @param: space: to set the label's height
         * @param: yearyield:年化收益率
         * @param: basicyearyield:基准年化收益率
         * @param: α:阿尔法
         * @param: β:贝塔
         * @param: sharpe:夏普比率
         * @param: yieldbodong:收益波动率
         * @param: infobilve:信息比率
         * @param: maxre:最大回撤
         */
    	infopane.setPrefSize(layout*8, space);
    	Label yearyield = new Label();
    	Label basicyearyield = new Label();
    	Label alpha = new Label();
    	Label beta = new Label();
    	Label sharpe = new Label();
//    	Label yieldbodong = new Label();
//    	Label infobilv = new Label();
    	Label maxre = new Label();
    	
    	yearyield.setPrefSize(layout, space);
    	basicyearyield.setPrefSize(layout*9/5, space);
    	alpha.setPrefSize(layout*3/5, space);
    	beta.setPrefSize(layout*3/5, space);
    	sharpe.setPrefSize(layout, space);
//    	yieldbodong.setPrefSize(layout, space);
//    	infobilv.setPrefSize(layout, space);
    	maxre.setPrefSize(layout, space);
    	
    	yearyield.setText( "年化收益率\n" + nf.format( ycd.getYearreturn() ) );
    	basicyearyield.setText( "基准年化收益率\n" + nf.format( ycd.getJizhunyearreturn() ));
    	alpha.setText( "α\n" + nf.format( ycd.getAlpha() ) );
    	beta.setText( "β\n" + df.format( ycd.getBeta() ) );
    	sharpe.setText( "夏普比率\n" + df.format( ycd.getSharpe() ) );
//    	yieldbodong.setText( "收益波动率\n" + nf.format( 0.249 ) );
//    	infobilv.setText( "信息比率\n" + df.format( 1.03 ) );
    	maxre.setText( "最大回撤\n" + nf.format( ycd.getZuidahuiche() ) );

    	yearyield.getStyleClass().add("label-1");
    	basicyearyield.getStyleClass().add("label-1");
    	alpha.getStyleClass().add("label-2");
    	beta.getStyleClass().add("label-2");
    	sharpe.getStyleClass().add("label-2");
//    	yieldbodong.getStyleClass().add("label-3");
//    	infobilv.getStyleClass().add("label-3");
    	maxre.getStyleClass().add("label-3");
    	
    	infopane.getChildren().add(yearyield);
    	infopane.getChildren().add(basicyearyield);
    	infopane.getChildren().add(alpha);
    	infopane.getChildren().add(beta);
    	infopane.getChildren().add(sharpe);
//    	infopane.getChildren().add(yieldbodong);
//    	infopane.getChildren().add(infobilv);
    	infopane.getChildren().add(maxre);
    	
    	double index = 10;
    	AnchorPane.setLeftAnchor(yearyield, index);
    	
    	index += layout;
    	AnchorPane.setLeftAnchor(basicyearyield, index);
    	
    	index += layout*9/5;
    	AnchorPane.setLeftAnchor(alpha, index);
    	
    	index += layout*3/5;
    	AnchorPane.setLeftAnchor(beta, index);
    	
    	index += layout*3/5;
    	AnchorPane.setLeftAnchor(sharpe, index);
    	
//    	index += layout;
//    	AnchorPane.setLeftAnchor(yieldbodong, index);
    	
//    	index += layout;
//    	AnchorPane.setLeftAnchor(infobilv, index);
    	
    	index += layout;
    	AnchorPane.setLeftAnchor(maxre, index);
	}

    public YieldComparedChart( List<LocalDate> date, List<Double> jichu, List<Double> celve){
        space = 0;
        infopane.setPrefSize( 0, 0 );

        LocalDate[] dates = listToArray.changeLocalDate( date );
        List<Double[]> doubleList = new ArrayList<>();
        List<String> dataName = new ArrayList<>();
        doubleList.add( listToArray.changeDouble(jichu) );
        dataName.add("基础");
        doubleList.add( listToArray.changeDouble(celve) );
        dataName.add("策略");

        comparedChart = new ComparedChart( dates, doubleList, dataName, ChartKind.YIELDCOMPARED);
    }
	
	 @Override
	 public Pane getchart(int width, int height, boolean withdate) {
		 chartpane = (AnchorPane)comparedChart.getchart(width, height-space, withdate);
		 pane.getChildren().add(infopane);
		 AnchorPane.setTopAnchor(chartpane, (double)space);	
		 pane.getChildren().add(chartpane);
		 
		 pane.getStylesheets().add(
				 getClass().getResource("/styles/YieldComparedChart.css").toExternalForm() );
		 return pane;
	 }

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		comparedChart.setName(name);
	}
}
