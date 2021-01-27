
public interface BaccaratInfo {

	static String[] suits = {"Hearts","Spades","Clubs","Diamonds"};
	static boolean[][] bankerDraw = {
			{ false, false, false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false,  true,  true, false, false },
			{  true, false, false, false, false,  true,  true,  true,  true, false, false },
			{  true, false, false,  true,  true,  true,  true,  true,  true, false, false },
			{  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
			{  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
			{  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true }, 
			{  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true }
			};
	
}
