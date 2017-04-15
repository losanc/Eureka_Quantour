package presentation.strategyUI;

import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import dataController.DataContorller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class StrategyUIController implements Initializable{
	
	@FXML
	ComboBox<String> stockSetComboBox;
	
	@FXML
	DatePicker beginTimeDatePicker;
	
	@FXML
	RadioButton momentumRadioButton;
	
	@FXML
	RadioButton meanRadioButton;
	
	@FXML
	TextField holdPeriodTextField;
	
	@FXML
	RadioButton closeRadioButton;
	
	@FXML
	RadioButton openRadioButton;
	
	@FXML
	TextField numOfStockTextField;
	
	@FXML
	Button makeStrategyButton;
	
	@FXML
	Button saveButton;

	@FXML
	AnchorPane chart1AnchorPane;
	
	@FXML
	AnchorPane chart2AnchorPane;
	
	@FXML
	AnchorPane chart3AnchorPane;
	
	@FXML
	ToggleGroup strategy;
	
	@FXML
	ToggleGroup price;
	
	@FXML
	Label timeLabel;
	
	private DataContorller dataController;
	
	@FXML
	protected void makeStrategy(ActionEvent e){
		
	}

	
	
	@FXML
	protected void saveTime(ActionEvent e){
		LocalDate begin = beginTimeDatePicker.getValue();
		if(begin.isBefore((LocalDate)dataController.get("SystemTime"))){
			timeLabel.setText(begin.toString());
			dataController.upDate("BeginTime", begin);
		}else{
			System.out.println("No");
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		dataController = DataContorller.getInstance();
		Image saveImage = new Image(getClass().getResourceAsStream("save.png"));
		saveButton.setGraphic(new ImageView(saveImage));
		beginTimeDatePicker.setShowWeekNumbers(false);
	    Locale.setDefault(Locale.ENGLISH);
	    LocalDate systemTime = (LocalDate) dataController.get("SystemTime");
	    LocalDate beginTime = systemTime.minusDays(20);
	    timeLabel.setText(beginTime.toString());
	    dataController.upDate("BeginTime", beginTime);
	}

}
