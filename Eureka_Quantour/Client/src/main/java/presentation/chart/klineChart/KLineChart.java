package presentation.chart.klineChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import presentation.chart.chartService;
import vo.SingleStockInfoVO;



public class KLineChart implements chartService {


    private CandleStickChart candlestickchart;

    public KLineChart(List<SingleStockInfoVO> stocklist){
        this.candlestickchart=new CandleStickChart("k线图", stocklist);
        candlestickchart.getStylesheets().add(getClass().getResource("/styles/CandleStickChartStyles.css").toExternalForm());

    }

    @Override
    public XYChart<String, Number> getchart() {
        return candlestickchart;
    }

    public void setCandlewidth(int candlewidth){
        candlestickchart.setCandlewidth(candlewidth);
    }




}
