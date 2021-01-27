import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Contains static methods to display alerts depending on the case
public class PopUps {
	
	enum betERROR{
		NEGATIVE, NOT_ENOUGH, NAN, OTHER
	}
	
	//Display warning for the user when they try to start a new game
	public static void displayNG(GameInfo gInfo) {
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("New Game");
		stage.setWidth(300);
		stage.setHeight(150);
		
		Label text = new Label("Are you sure you want to start a new game?\n "
				+ "All progress will be erased");
		
		Button cancel = new Button("No");
		cancel.setMinSize(80, 20);
		cancel.setOnAction(e->stage.close());
		
		Button newGame = new Button("Yes");
		newGame.setMinSize(80, 20);
		
		newGame.setOnAction(e->{
			gInfo.setNewGame(true);
			stage.close();
		});
		
		cancel.setOnAction(e->{
			gInfo.setNewGame(false);
			stage.close();
		});
		
		HBox buttonLayout = new HBox(20,newGame,cancel);
		buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
		
		VBox mainLayout = new VBox(20, text, buttonLayout);
		mainLayout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(mainLayout);
		
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	//Warning for when user inputs a non accepted value into the betField
	public static void displayWrongBet(betERROR bE) {
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Rejected Bet");
		stage.setWidth(300);
		stage.setHeight(150);
		
		String labelString;
		switch(bE) {
			case NOT_ENOUGH:
				labelString = "You don't have enough money";
				break;
			case NEGATIVE:
				labelString = "Gotta bet something";
				break;
			case NAN:
				labelString = "Please use numbers only";
				break;
			default:
				labelString = "Choose someone to bet on";
				break;
		}
		
		Label text = new Label(labelString);
		
		Button ok = new Button("Ok");
		ok.setMinSize(80, 20);
		ok.setOnAction(e->stage.close());
		
		ok.setOnAction(e->{
			stage.close();
		});
		

		HBox buttonLayout = new HBox(20,ok);
		buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
		
		VBox mainLayout = new VBox(20, text, buttonLayout);
		mainLayout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(mainLayout);
		
		stage.setScene(scene);
		stage.showAndWait();
	}
}
