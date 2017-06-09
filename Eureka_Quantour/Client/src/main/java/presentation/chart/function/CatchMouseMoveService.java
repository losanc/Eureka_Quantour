package presentation.chart.function;

import java.util.Map;

import en_um.ChartKind;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * @Description: catch mouse movement and calculate corresponding data according to mouse's location
 * @author: hzp
 * @time: 2017年4月6日
 */
public interface CatchMouseMoveService {
	/**
	 * @Description: according to AnchorPane's feature and mouse's location, 
	 * 				 set label's layoutX and layoutY
	 * @author: hzp
	 * @time: 2017年4月12日
	 * @return: Label
	 */
	Label catchMouseReturnInfoForAnchorPaneSN( 
			XYChart<String, Number> chart, Map<String, String> dataMap, String[] dates, String name, int index);

	/**
	 * @Description: according to StackPane's feature and mouse's location, 
	 * 				 set label's layoutX as StackPane.LEFT or StackPane.RIGHT
	 * @author: hzp
	 * @time: 2017年4月12日
	 * @return: Label
	 */
	Label catchMouseReturnInfoForStackPaneSN( 
			XYChart<String, Number> chart, Map<String, String> dataMap, String[] dates, String name, int index);
	
	Label catchMouseReturnInfoForStackPaneNN( XYChart<Number, Number> chart, 
			Map<String, String> dataMap, String[] dates, String name, int index, ChartKind kind);

	Label catchMouseReturnInfoForStackPaneNS( XYChart<Number, String> chart, 
			Map<String, String> dataMap, String[] dates, String name, int index, ChartKind kind);
	
	/**
	 * @Description: according to AnchorPane's feature and mouse's location, 
	 * 				 set label's layoutX and layoutY
	 * @author: hzp
	 * @time: 2017年4月12日
	 * @return: Label
	 */
	Label catchMouseReturnInfoForYieldDistributeChart( 
			XYChart<String, Number> chart, Map<String, String> dataMap, String[] dates, String name, int index);
}
