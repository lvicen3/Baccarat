import java.util.ArrayList;

public class VisualResources implements BaccaratInfo{
	static String[][] cardImages;
	
	//Initialize cardImages, which will contain strings to help form names of the imagees
	static {
		cardImages = new String[4][14];
		char[] suitInitial = {'H','S','C','D'};
		for(int i = 0; i < 4; i++) {
			cardImages[i][1] = "A" + suitInitial[i];
			for(int j = 2; j < 11; j++) {
				cardImages[i][j] = "" + j + suitInitial[i];
			}
			cardImages[i][11] = "J" + suitInitial[i];
			cardImages[i][12] = "Q" + suitInitial[i];
			cardImages[i][13] = "K" + suitInitial[i];
		}
	}
	
	//return string for image URL depending on card
	public static String getCardImageAddress(Card c) {
		//TODO: Add exception for invalid suite?
		int suiteNum = 0;
		
		switch(c.suite){
			case "Hearts": 
				suiteNum = 0;
				break;
			case "Spades":
				suiteNum = 1;
				break;
			case "Clubs":
				suiteNum = 2;
				break;
			case "Diamonds":
				suiteNum = 3;
				break;
		}
		
		return "/cards/" + cardImages[suiteNum][c.value] + ".png";
	}
	
	//yellowButton css style override
	public static String yellowButtonStyle() {
		//CSS by Jasper Potts
		//obtained from url:http://fxexperience.com/2011/12/styling-fx-buttons-with-css/
		
		return "-fx-background-color: \n" + 
				"        linear-gradient(#ffd65b, #e68400),\n" + 
				"        linear-gradient(#ffef84, #f2ba44),\n" + 
				"        linear-gradient(#ffea6a, #efaa22),\n" + 
				"        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" + 
				"        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" + 
				"    -fx-background-radius: 30;\n" + 
				"    -fx-background-insets: 0,1,2,3,0;\n" + 
				"    -fx-text-fill: #654b00;\n" + 
				"    -fx-font-weight: bold;\n" + 
				"    -fx-font-size: 18px;\n" + 
				"    -fx-padding: 10 20 10 20;";
	} 

	//Modified gradients for pressed yellow button
	public static String yellowButtonStylePressed() {
		return "-fx-background-color: \n" + 
				"        linear-gradient(#ffd65b, #e68400),\n" + 
				"        linear-gradient(#ffef84, #f2ba44),\n" + 
				"        linear-gradient(#ffea6a, #efaa22),\n" + 
				"        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" + 
				"        linear-gradient(from 100% 100% to 0% 0%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" + 
				"    -fx-background-radius: 30;\n" + 
				"    -fx-background-insets: 0,1,2,3,0;\n" + 
				"    -fx-text-fill: #654b00;\n" + 
				"    -fx-font-weight: bold;\n" + 
				"    -fx-font-size: 18px;\n" + 
				"    -fx-padding: 10 20 10 20;";
	}
	
	public static String handTotalStyle(int size) {
		return " -fx-font-size: "+ size + "px;" + 
				"   -fx-font-family: \"Droid serif \";" + 
				"   -fx-text-fill: #FFFFFF;" + 
				"	-fx-font-style:italic;" +
				" 	-fx-font-weight:bold;";
				
				//+"	-fx-border-color: #DC9946;";
//				"	-fx-border-width: 2px;" + 
//				"	-fx-padding: 2px;";
	}
	
	public static String betButtonStyle() {
		return "-fx-font-size: 14px;\n" + 
				"    -fx-font-weight: bold;\n" + 
				"    -fx-text-fill: #515151;\n" + 
				" -fx-color: #E6BC2C;";
	}
	
}
