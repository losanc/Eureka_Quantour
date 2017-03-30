package presentation.mainScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainScreenController implements Initializable{
	
	@FXML
	DatePicker nowDatePicker;
	
	@FXML
	Button editButton;
	
	@FXML
	Button saveButton;
	
	@FXML
	Button exitButton;
	
	@FXML
	Label nameLabel;
	
	@FXML
	Button stockSetButton;
	
	@FXML
	Button marketButton;
	
	@FXML
	Button singleStockButton;
	
	@FXML
	Button strategyButton;
	
	@FXML
	AnchorPane mainAnchorPane;
	
	@FXML
	protected void browseStockSet(ActionEvent e) throws IOException{
		ObservableList<Node> nodeList = mainAnchorPane.getChildren();
		nodeList.clear();
		AnchorPane stockSetPane = (AnchorPane)FXMLLoader.load(getClass().getClassLoader().getResource("presentation/stockSetUI/StockSetUI.fxml"));
		mainAnchorPane.getChildren().add(stockSetPane);
	}
	
	@FXML
	protected void browseMarket(ActionEvent e) throws IOException{
		ObservableList<Node> nodeList = mainAnchorPane.getChildren();
		nodeList.clear();
		AnchorPane marketPane = (AnchorPane)FXMLLoader.load(getClass().getClassLoader().getResource("presentation/marketUI/MarketUI.fxml"));
		mainAnchorPane.getChildren().add(marketPane);
	}
	
	@FXML
	protected void browseSingleStock(ActionEvent e) throws IOException{
		ObservableList<Node> nodeList = mainAnchorPane.getChildren();
		nodeList.clear();
		AnchorPane singleStockPane = (AnchorPane)FXMLLoader.load(getClass().getClassLoader().getResource("presentation/singleStockUI/SingleStockUI.fxml"));
		mainAnchorPane.getChildren().add(singleStockPane);
	}
	
	@FXML
	protected void browseStrategy(ActionEvent e) throws IOException{
		ObservableList<Node> nodeList = mainAnchorPane.getChildren();
		nodeList.clear();
		AnchorPane strategyPane = (AnchorPane)FXMLLoader.load(getClass().getClassLoader().getResource("presentation/strategyUI/StrategyUI.fxml"));
		mainAnchorPane.getChildren().add(strategyPane);
	}
	
	@FXML
	protected void edit(ActionEvent e){
		
	}
	
	@FXML
	protected void saveTime(ActionEvent e){
		
	}
	
	@FXML
	protected void exit(ActionEvent e){
		Stage root = (Stage) exitButton.getScene().getWindow();
		root.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image editImage = new Image(getClass().getResourceAsStream("edit.png"));
		editButton.setGraphic(new ImageView(editImage));
		Image exitImage = new Image(getClass().getResourceAsStream("exit.png"));
		exitButton.setGraphic(new ImageView(exitImage));
		Image saveImage = new Image(getClass().getResourceAsStream("save.png"));
		saveButton.setGraphic(new ImageView(saveImage));
		stockSetButton.setText("股池");
		marketButton.setText("市场");
		singleStockButton.setText("个股");
		strategyButton.setText("策略");
		
	}

}
