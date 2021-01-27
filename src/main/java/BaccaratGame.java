import java.util.ArrayList;

import com.sun.prism.paint.Color;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BaccaratGame extends Application {
	//UI data members
	MenuBar menu;
	Scene scene;
	Stage window;
	double ratio;
	double height;
	GameInfo gInfo; //Used to transfer and receiver data to and from alerts 
	
	//Game data members
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	double currentBet;
	double pool; //Starting money to dispose of
	double totalWinnings;
	String betTarget;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	//start function
	//Sets up menu and window specifics, gives control of the stage to startScene()
	@Override
	public void start(Stage primaryStage) throws Exception {
		//holds height and height to width ratio to use for size adjusments in the layout
		ratio = 1.5/1.0;
		height = 850; 
		
		//Window setup
		window = primaryStage;
		window.setTitle("Let's Play Baccarat!!!");
		gInfo = new GameInfo();
		
		//MENU SETUP
		menu = new MenuBar(); 
		Menu gameMenu = new Menu("Options");
		
		//MENU ITEMS
		MenuItem newGame = new MenuItem("New Game");
		MenuItem quit = new MenuItem("Quit");

		gameMenu.getItems().addAll(newGame,quit);
		menu.getMenus().addAll(gameMenu);
		
		//Quit option for menu
		quit.setOnAction(e->{
			Platform.exit();
			System.exit(0);
		});
		
		//New Game option for menu
		newGame.setOnAction(e->{
			//Warns user through an alert that they'll restart the game
			//Gets final decision from PopUp
			PopUps.displayNG(gInfo);
			if(gInfo.newGameOption) {
				newGameScene();
			}
		});

		
		startScene();
		
	}
	
	//First scene method
	public void startScene() {
		final int rowSize = 4;
		final int colSize = 4;
		
		//Grid to hold buttons in place
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setMinSize(height*ratio, height);
		grid.setMaxSize(height*3*ratio,height*3);
		grid.setPadding(new Insets(50,50,50,50));
		
		//Set up rows and columns to help position buttons
		for(int i = 0; i < rowSize; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100/rowSize);
			grid.getRowConstraints().add(row);
		}
		
		for(int i = 0; i < colSize; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(100/colSize);
			grid.getColumnConstraints().add(col);
		}
		
		//Buttons for this scene
		Button newGame = orangeButton("New Game");
		Button howToPlay = orangeButton("How to Play");
		grid.add(newGame, 0,2);
		grid.add(howToPlay, 1,2);
		
		//Pass control of the stage to newGameScene()
		newGame.setOnAction(e->{newGameScene();});
		howToPlay.setOnAction(e->{howToPlayScene();});
		
		
		for(Node n: grid.getChildren()) {
			GridPane.setHalignment(n, HPos.CENTER);
			GridPane.setValignment(n, VPos.TOP);
		}
		
		//VBox to hold menu and the grid, gets passed to scene as layout
		VBox mainLayout = new VBox();
		mainLayout.getChildren().add(menu);
		mainLayout.getChildren().add(grid);
		mainLayout.setStyle( "-fx-background-image: url(" +
                "'StartBackground.png'" +
            "); " +
            "-fx-background-size: cover;");
		
		//Set scene and show it
		scene = new Scene(mainLayout,height*ratio,height);
		window.setScene(scene);
		window.minWidthProperty().bind(scene.heightProperty().multiply(ratio));
		window.minHeightProperty().bind(scene.widthProperty().divide(ratio));
		window.show();
	}
	
	//How to play scene, shows image and return button
	private void howToPlayScene() {
		VBox box = new VBox();
		box.setPadding(new Insets(20,20,20,20));
		ImageView howTP = new ImageView(new Image("HowToPlay2.png",1000,1000,true,true));
		
		Button back = orangeButton("Go back");
		
		back.setOnAction(e->startScene());
		box.getChildren().addAll(howTP,back);
		VBox mainLayout = new VBox();
		mainLayout.getChildren().add(menu);
		mainLayout.getChildren().add(box);
		
		//Set scene and show it
		scene = new Scene(mainLayout,height*ratio,height);
		window.setScene(scene);
		window.minWidthProperty().bind(scene.heightProperty().multiply(ratio));
		window.minHeightProperty().bind(scene.widthProperty().divide(ratio));
		window.show();
	}
	
	//New Game Scene, allows the user to pick a starting pool and start a new game
	private void newGameScene() {
		final int rowSize = 4;
		final int colSize = 4;
		//Grid to hold buttons for this scene
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setMinSize(height*ratio, height);
		grid.setMaxSize(height*3*ratio,height*3);
		grid.setPadding(new Insets(20,20,20,20));
		
		
		//Setup rows and columns to help positioning
		for(int i = 0; i < rowSize; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100.0/rowSize);
			grid.getRowConstraints().add(row);
		}
		
		for(int i = 0; i < colSize; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(100.0/colSize);
			grid.getColumnConstraints().add(col);
		}
		
		//Button to start game
//		Button start = orangeButton("Start Game");
//		grid.add(start, 1, 2);

		
		//Buttons to start game
		Button thousand = orangeButton("$1,000");
		Button hundredThousand = orangeButton("$100,000");
		Button million = orangeButton("$1,000,000");
		
		grid.add(thousand, 0, 1);
		grid.add(hundredThousand, 1, 1);
		grid.add(million, 2, 1);
		
		Label startGameInstruction = new Label("Choose a starting pool:");
		startGameInstruction.setStyle(VisualResources.handTotalStyle(32));
		grid.add(startGameInstruction, 0, 0,2,1);
	
		//Pass control of the stage to gameScene, which holds the actual game
		thousand.setOnAction(e->gameScene(1000));
		hundredThousand.setOnAction(e->gameScene(100000));
		million.setOnAction(e->gameScene(1000000));
		
		for(Node n: grid.getChildren()) {
			GridPane.setHalignment(n, HPos.CENTER);
			GridPane.setValignment(n, VPos.CENTER); 
		}
		
		//VBox that holds menu and button grid
		VBox mainLayout = new VBox();
		mainLayout.getChildren().add(menu);
		mainLayout.getChildren().add(grid);
		mainLayout.setStyle( "-fx-background-image: url(" +
                "'NewGameBackground.png'" +
            "); " +
            "-fx-background-size: cover;");
		
		//Setup and show scene
		scene = new Scene(mainLayout,height*ratio,height);
		window.setScene(scene);
		window.show();
	}
	
	//gameScene, holds actual game, gets a startingAmount to setup as starting pool money
	private void gameScene(double startingAmount) {
		//GameLogic setup
		theDealer = new BaccaratDealer();
		gameLogic = new BaccaratGameLogic();
		pool = startingAmount;
		totalWinnings = 0;		
		theDealer.shuffleDeck();
		
		//UI setup
		final int pauseNumber = 11;
		
		//Define pause transitions for later use
		ArrayList<PauseTransition> pauses = new ArrayList<PauseTransition>();
		for(int i = 0; i < pauseNumber; i++) {
			pauses.add(new PauseTransition(Duration.seconds(1)));
		}	
		
		//Define scale transitions, used to make items popup to make it easier to notice they have changed
		ScaleTransition scaleT = new ScaleTransition(Duration.seconds(.1));
		scaleT.setByX(0.025);
		scaleT.setByY(0.025);
		scaleT.setAutoReverse(true);
		scaleT.setCycleCount(2);
		
		//Layout uses a VBox like methods above. This time the VBox gets a BorderPane instead of a Grid Pane
		
		//LEFT
		//Elements that will be set on the left of the BorderPane
		//Holds: TotalWinnings label and label number
		VBox bLeft = new VBox();
		Label totalWLabel = new Label("Total Winnings: ");
		totalWLabel.setStyle("-fx-font-size: 28px;\n" + 
				"    -fx-font-weight: bold;\n" + 
				"    -fx-text-fill: #FFFFFF;\n" + 
				"    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
		Label winningsLabel = new Label("$0");
		winningsLabel.setStyle(" -fx-font-size: 28px;" + 
				"   -fx-font-family: \"Droid serif \";" + 
				"   -fx-text-fill: #F7EB3D;" + 
				"	-fx-font-style:italic;" +
				" 	-fx-font-weight:bold;");
		
		Label poolLabel = new Label("Your pool: ");
		poolLabel.setStyle("-fx-font-size: 24px;\n" + 
				"    -fx-font-weight: bold;\n" + 
				"    -fx-text-fill: #FFFFFF;\n" + 
				"    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
		Label poolAmount = new Label("$"+pool);
		poolAmount.setStyle(" -fx-font-size: 24px;" + 
				"   -fx-font-family: \"Droid serif \";" + 
				"   -fx-text-fill: #F7EB3D;" + 
				"	-fx-font-style:italic;" +
				" 	-fx-font-weight:bold;");
		
		bLeft.getChildren().addAll(totalWLabel,winningsLabel,poolLabel,poolAmount);
		
		//Setup cardBack image, to have as placeholders before drawing cards
		Image cardBack = new Image("/cards/gray_back.png", 1000, 200, true, true);
		
		//CENTER
		//Elements that will be set on the center of the BorderPane placed on a grid
		//Holds: cards, round results and a button to go to the next round
		GridPane centerGrid = new GridPane(); 
		centerGrid.setHgap(10);
		centerGrid.setVgap(10);
		final int rowSizeC = 4;
		final int colSizeC = 4;
		
		//Center rows setup
		RowConstraints row1C = new RowConstraints();
		RowConstraints row2C = new RowConstraints();
		RowConstraints row3C = new RowConstraints();
		RowConstraints row4C = new RowConstraints();
		row1C.setPercentHeight(30);
		row2C.setPercentHeight(10);
		row3C.setPercentHeight(50);
		row4C.setPercentHeight(10);
		centerGrid.getRowConstraints().add(row1C);
		centerGrid.getRowConstraints().add(row2C);
		centerGrid.getRowConstraints().add(row3C);
		centerGrid.getRowConstraints().add(row4C);
		
		//Center columns setup
		ColumnConstraints col1C = new ColumnConstraints();
		ColumnConstraints col2C = new ColumnConstraints();
		ColumnConstraints col3C = new ColumnConstraints();
		ColumnConstraints col4C = new ColumnConstraints();
		col1C.setPercentWidth(20);
		col2C.setPercentWidth(30);
		col3C.setPercentWidth(30);
		col4C.setPercentWidth(20);
		centerGrid.getColumnConstraints().add(col1C);
		centerGrid.getColumnConstraints().add(col2C);
		centerGrid.getColumnConstraints().add(col3C);
		centerGrid.getColumnConstraints().add(col4C);
		
		centerGrid.setGridLinesVisible(false);
		
		//Cards display setup
		//Create HBox to hold player cards
		HBox playerCardsBox = new HBox();
		playerCardsBox.setPadding(new Insets(5,5,5,5));
		playerCardsBox.setSpacing(10);
		playerCardsBox.setAlignment(Pos.CENTER);
		GridPane.setHgrow(playerCardsBox, Priority.ALWAYS);
		
		
		//Base Hands
		//PlayerHand
		ImageView playerCard1 = new ImageView(cardBack);
		ImageView playerCard2 = new ImageView(cardBack);
		
		playerCardsBox.getChildren().add(playerCard1);
		playerCardsBox.getChildren().add(playerCard2);
		centerGrid.add(playerCardsBox, 1, 2);

		//Create HBox to hold banker cards
		HBox bankerCardsBox = new HBox();
		bankerCardsBox.setPadding(new Insets(5,5,5,5));
		bankerCardsBox.setSpacing(10);
		bankerCardsBox.setAlignment(Pos.CENTER);
		GridPane.setHgrow(bankerCardsBox, Priority.ALWAYS);
		
		//BankerHand
		ImageView bankerCard1 = new ImageView(cardBack);
		ImageView bankerCard2 = new ImageView(cardBack);
		
		bankerCardsBox.getChildren().addAll(bankerCard1,bankerCard2);
		centerGrid.add(bankerCardsBox, 2, 2);
		
		//Labels to hold hand totals
		Label playerHandLabel = new Label("Player Total: ");
		playerHandLabel.setStyle(VisualResources.handTotalStyle(28));
		centerGrid.add(playerHandLabel, 1, 1);
		
		Label bankerHandLabel = new Label("Banker Total: ");
		bankerHandLabel.setStyle(VisualResources.handTotalStyle(28));
		centerGrid.add(bankerHandLabel, 2, 1);
		
		//Extra cards
		//Setup for Player's 3rd card
		ImageView playerCard3 = new ImageView();
		centerGrid.add(playerCard3,0,2);
		
		//Setup for Banker's 3rd card
		ImageView bankerCard3 = new ImageView();
		
		//Round results
		Label roundResultLabel = new Label();
		roundResultLabel.setVisible(false);
		roundResultLabel.setStyle(" -fx-font-size: 32px;" + 
				"   -fx-font-family: \"Droid serif \";" + 
				"   -fx-text-fill: #000000;" + 
				"	-fx-font-style:italic;" +
				" 	-fx-font-weight:bold;" +
				"	-fx-background-color: #FFFFFF;"+
				"	-fx-border-color: #DC9946;" +
				"	-fx-border-width:2px;");
		centerGrid.add(roundResultLabel, 1, 0, 2, 1);
		
		//Next round button
		Button nextRoundButton = orangeButton("Next Round");
		nextRoundButton.setVisible(false);
		nextRoundButton.setDisable(true);
		centerGrid.add(nextRoundButton, 3, 0);
		
		//Setup for loading dots that let the user know the program is working
		GrowingString dots = new GrowingString();
		Label dotsLabel = new Label("");
		dotsLabel.setPadding(new Insets(10,10,10,10));
		dotsLabel.setStyle(VisualResources.handTotalStyle(28));
		Timeline dotsTimeline = new Timeline(new KeyFrame(Duration.seconds(0.4), ev -> {
		    if(dots.getString().length() < 3) {
		    	dots.addDot();
		    } else {
		    	dots.reset();
		    }
	    	dotsLabel.setText(dots.getString());
		}));
		dotsTimeline.setCycleCount(Animation.INDEFINITE);
		
		centerGrid.add(dotsLabel, 3, 3);

		//Final details of centerGrid
		centerGrid.add(bankerCard3,3,2);
		centerGrid.setStyle("-fx-background-color: #2F7826;" + 
							"-fx-border-color: #DC9946;" + 
							"-fx-border-width: 10px;"
							);
		
		for(Node n: centerGrid.getChildren()) {
			GridPane.setHalignment(n, HPos.CENTER);
			GridPane.setValignment(n, VPos.CENTER);
		}
		
		GridPane.setHalignment(dotsLabel, HPos.RIGHT);
		GridPane.setValignment(dotsLabel, VPos.BOTTOM);
		
		//BOTTOM
		//Holds: bet field to take input for the bet, a toggle group to take input for bet target
		GridPane bottomGrid = new GridPane();	
		bottomGrid.setPadding(new Insets(20,20,20,20));	
		final int rowSizeB = 3;
		bottomGrid.setHgap(10);
		bottomGrid.setVgap(10);
		bottomGrid.setPadding(new Insets(10,10,10,10));
		
		
		//Setting grid col and row pattern
		for(int i = 0; i < rowSizeB; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(100/rowSizeB);
			bottomGrid.getRowConstraints().add(row);
		}		
		
		ColumnConstraints col1B = new ColumnConstraints();
		ColumnConstraints col2B = new ColumnConstraints();
		ColumnConstraints col3B = new ColumnConstraints();
		col1B.setPercentWidth(40);
		col2B.setPercentWidth(20);
		col3B.setPercentWidth(40);
		bottomGrid.getColumnConstraints().add(col1B);
		bottomGrid.getColumnConstraints().add(col2B);
		bottomGrid.getColumnConstraints().add(col3B);
		
		//Elements for the Bottom Grid
		Button betButton = orangeButton("Confirm Bet");
		bottomGrid.add(betButton, 2, 1);
		
		//ToggleGroup and buttons belonging to it
		ToggleGroup betOnWho = new ToggleGroup();
		ToggleButton playerBet = new ToggleButton(" PLAYER ");
		playerBet.setStyle(VisualResources.betButtonStyle());
		playerBet.setMaxSize(Double.MAX_VALUE, 80);
		
		ToggleButton bankerBet = new ToggleButton(" BANKER ");
		bankerBet.setStyle(VisualResources.betButtonStyle());
		bankerBet.setMaxSize(Double.MAX_VALUE, 80);
		
		ToggleButton drawBet = new ToggleButton(" TIE ");
		drawBet.setStyle(VisualResources.betButtonStyle());
		drawBet.setMaxSize(Double.MAX_VALUE, 80);
		
		playerBet.setToggleGroup(betOnWho);
		bankerBet.setToggleGroup(betOnWho);
		drawBet.setToggleGroup(betOnWho);
		
		bottomGrid.add(playerBet, 1, 1);
		bottomGrid.add(bankerBet, 1, 2);
		bottomGrid.add(drawBet, 1, 0);

		HBox betHBox = new HBox();
		Label betLabel = new Label(" Your bet: ");
		betLabel.setStyle("-fx-font-size: 28px;\n" + 
				"    -fx-font-weight: bold;\n" + 
				"    -fx-text-fill: #FFFFFF;\n" + 
				"    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
		TextField betField = new TextField();
		betField.setMinHeight(50);
		betField.setMaxWidth(150);
		betField.setStyle("-fx-font-size: 28px;" + 
				"    -fx-font-weight: bold;"  + 
				"	 -fx-text-fill: #8A8A8A; ");
	
		betHBox.getChildren().addAll(betLabel,betField);
		bottomGrid.add(betHBox, 0, 1);
		
		for(Node n: bottomGrid.getChildren()) {
			GridPane.setHalignment(n, HPos.CENTER);
			GridPane.setValignment(n, VPos.CENTER);
		}
		
		//Event to set up a new round, resets all necessary data and ui elements
		nextRoundButton.setOnAction(e1->{
			totalWinnings += evaluateWinnings();
			pool +=evaluateWinnings();
			winningsLabel.setText("$" + (int)totalWinnings);
			poolAmount.setText("$" + (int)pool);
			playerHand.clear();
			bankerHand.clear();
			theDealer.generateDeck();
			theDealer.shuffleDeck();
			roundResultLabel.setVisible(false);
			playerHandLabel.setText("Player Total: ");
			bankerHandLabel.setText("Banker Total: ");
			playerCard1.setImage(cardBack);
			playerCard2.setImage(cardBack);		
			playerCard3.setImage(null);
			bankerCard1.setImage(cardBack);
			bankerCard2.setImage(cardBack);
			bankerCard3.setImage(null);
			nextRoundButton.setDisable(true);
			nextRoundButton.setVisible(false);
			betButton.setDisable(false);
		});
		
		//Bet Button confirms input and processes the round
		betButton.setOnAction(e->{	
			//Check input is valid number
			try{
				betButton.setDisable(true);
				double temp = Double.parseDouble(betField.getText());
				if(temp > 0) {
					if(temp < pool) {
						if(betOnWho.getSelectedToggle().equals(playerBet)) {
							betTarget = "Player";
						} else if(betOnWho.getSelectedToggle().equals(bankerBet)) {
							betTarget = "Banker";
						} else if(betOnWho.getSelectedToggle().equals(drawBet)) {
							betTarget = "Draw";
						} else {
							PopUps.displayWrongBet(PopUps.betERROR.OTHER);
							betButton.setDisable(false);
							return;
						}
						
						//Hand setup
						currentBet = temp;
						playerHand = theDealer.dealHand();
						bankerHand = theDealer.dealHand();
						
						//Transition setup
						//Use a sequential transition to hold the steps of the game and pause on each one
						//Parallel transition will contain the sequential transition and the dots animation transition to play them at the same time
						SequentialTransition seqT = new SequentialTransition ();
						ParallelTransition parT = new ParallelTransition();
						
						//Setup animations for player's first 2 cards
						pauses.get(0).setOnFinished(e1->{
							playerCard1.setImage(new Image(VisualResources.getCardImageAddress(playerHand.get(0)),1000,200,true,true ));
							scaleT.setNode(playerCard1);
							scaleT.play();
						});
						pauses.get(1).setOnFinished(e1->{
							playerCard2.setImage(new Image(VisualResources.getCardImageAddress(playerHand.get(1)),1000,200,true,true ));
							scaleT.setNode(playerCard2);
							scaleT.play();
						});
						//Calculate hand total and show it
						pauses.get(2).setOnFinished(e1->{
							scaleT.setNode(playerHandLabel);
							scaleT.play();
							playerHandLabel.setText("Player Total: " + gameLogic.handTotal(playerHand));
						});
						
						//Setup animations for banker's first 2 cards
						pauses.get(3).setOnFinished(e1->{
							bankerCard1.setImage(new Image(VisualResources.getCardImageAddress(bankerHand.get(0)),1000,200,true,true ));
							scaleT.setNode(bankerCard1);
							scaleT.play();
						});
						pauses.get(4).setOnFinished(e1->{
							bankerCard2.setImage(new Image(VisualResources.getCardImageAddress(bankerHand.get(1)),1000,200,true,true ));
							scaleT.setNode(bankerCard2);
							scaleT.play();
						});
						
						//Calculate hand total and show it
						pauses.get(5).setOnFinished(e1->{
							scaleT.setNode(bankerHandLabel);
							scaleT.play();
							bankerHandLabel.setText("Banker Total: " + gameLogic.handTotal(bankerHand));
						});
						
						//Add all already declared animations to seqT
						seqT.getChildren().addAll(pauses.subList(0, 6));
						
						//Check for more draws, add necessary animations
						if(!gameLogic.NaturalWin(playerHand, bankerHand)) {
							if(gameLogic.evaluatePlayerDraw(playerHand)) {
								pauses.get(6).setOnFinished(e1->{
									playerHand.add(theDealer.drawOne());
									playerCard3.setImage(new Image(VisualResources.getCardImageAddress(playerHand.get(2)),1000,200,true,true ));
									scaleT.setNode(playerCard3);
									scaleT.play();
								});
								pauses.get(7).setOnFinished(e1->{
									playerHandLabel.setText("Player Total: " + gameLogic.handTotal(playerHand));
									scaleT.setNode(playerHandLabel);
									scaleT.play();
								});
								seqT.getChildren().addAll(pauses.get(6),pauses.get(7));
							}
							
							if(gameLogic.evaluateBankerDraw(bankerHand, (playerHand.size() < 3) ? null : playerHand.get(2))) {
								pauses.get(8).setOnFinished(e1->{
									bankerHand.add(theDealer.drawOne());
									bankerCard3.setImage(new Image(VisualResources.getCardImageAddress(bankerHand.get(2)),1000,200,true,true ));
									scaleT.setNode(bankerCard3);
									scaleT.play();
								});
								pauses.get(9).setOnFinished(e1->{
									bankerHandLabel.setText("Banker Total: " + gameLogic.handTotal(bankerHand));
									scaleT.setNode(bankerHandLabel);
									scaleT.play();
								});
								seqT.getChildren().addAll(pauses.get(8),pauses.get(9));
							}
						}
						
						//enable nextRound button and show results for the winner hand of the round
						pauses.get(10).setOnFinished(e1->{
							String result = "";
							switch(gameLogic.whoWon(playerHand, bankerHand)) {
								case "Player":
									result = " Player's hand wins! ";
									break;
								case "Banker":
									result = " Banker's hand wins! ";
									break;
								case "Draw":
									result = " Hands are tied! ";
									break;
							}
							if(evaluateWinnings() < 0) {
								result+=" You lose.";
							} else {
								result+=" You win!.";
							}
							roundResultLabel.setText(result);
							roundResultLabel.setVisible(true);
							nextRoundButton.setDisable(false);
							nextRoundButton.setVisible(true);
						});
						
						seqT.getChildren().add(pauses.get(10));
						
						//Stop dots animation when seqT ends
						seqT.setOnFinished(e1->{
							parT.stop();
							dotsLabel.setText("");
						});
						
						//add seqT and dots animations to the parallel transition and play all of it
						parT.getChildren().addAll(seqT,dotsTimeline);
						parT.play();
						
					} else {
						PopUps.displayWrongBet(PopUps.betERROR.NOT_ENOUGH);
						betButton.setDisable(false);
					}
				} else {
					PopUps.displayWrongBet(PopUps.betERROR.NEGATIVE);
					betButton.setDisable(false);					
				} 
			} catch(NumberFormatException | NullPointerException nfe) {
				if(nfe.getClass() == NumberFormatException.class)
					PopUps.displayWrongBet(PopUps.betERROR.NAN);
				else 
					PopUps.displayWrongBet(PopUps.betERROR.OTHER);
				betButton.setDisable(false);
			}
			
		});
		
		//Final setup for scene
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(20,20,20,20));
		mainLayout.setLeft(bLeft);
		mainLayout.setBottom(bottomGrid);
		mainLayout.setCenter(centerGrid);
		mainLayout.setStyle( "-fx-background-image: url(" +
                "'gameBackground2.png'" +
            "); " +
            "-fx-background-size: cover;");
		
		VBox finalVB = new VBox();
		finalVB.getChildren().addAll(menu,mainLayout);
		VBox.setVgrow(mainLayout, Priority.ALWAYS);
		scene = new Scene(finalVB,height*ratio,height);
		window.setScene(scene);
		window.show();
	}
	
	//Method to evaluate if user won or lost money
	public double evaluateWinnings() {
		if(betTarget.equals(gameLogic.whoWon(playerHand, bankerHand))) 
			return currentBet*2;
		return (-1.0)*currentBet;
	}
	
	//orangeButton creator method
	private Button orangeButton(String s) {
		Button button = new Button(s);
		button.setMinHeight(50);
		button.setMinWidth(160);
		button.setStyle(VisualResources.yellowButtonStyle());
		button.setOnMousePressed(e->{button.setStyle(VisualResources.yellowButtonStylePressed());});
		button.setOnMouseReleased(e->{button.setStyle(VisualResources.yellowButtonStyle());});
		
		return button;
	}

	//orangeButton creator method with size parameters
	private Button orangeButton(String s, int h, int w) {
		Button button = orangeButton(s);
		button.setMinHeight(h);
		button.setMinWidth(w);
		return button;
	}
	
	//Class to create a string that grows by method calls
	//Allows a string to be changed in an Action Event anon class
	private class GrowingString{
		private String s;
		GrowingString(){
			s = ".";
		}
		public void addDot() {
			s+=".";
		}
		
		public void reset() {
			s = ".";
		}

		public String getString() {
			return s;
		}
	}
	
	private class Number{
		private double d;
		Number(){
			d = 0.0; 
		}
		public void setNumber(double d) {
			this.d = d;
		}

		public double getNumber() {
			return d;
		}
	}

}
