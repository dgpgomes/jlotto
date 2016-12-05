// Importing packages
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainProject { // Beginning of the class

	public static void main(String[] args) { // Beginning of the main method

		// Declaring integer variables
		int numberGames = 0;
		int auxNumberGames = 0; 		
		int counterNumForGame = 0;
		int newNumber = 0;
		int toDraw = 0;
		int draw [] = new int [6];
		// Declaring string variables
		String YorNConf = "Y";
		// Declaring boolean variables
		boolean isInTheList = false;
		boolean playAgain = true;
		

		
		// Creating buffered reader
		BufferedReader br = new BufferedReader(new InputStreamReader( System.in ));
			
		// Invoking method to print the lotto card to the player
		printLottoCard();
		
		// Loop responsible to repeat the whole block if the user does not want
		// to confirm his entered numbers
		while (YorNConf.equals("Y")) {
			// Loop responsible to repeat the whole game if the user
			// decides to play again after the numbers get drawn and the
			// results displayed
			while (playAgain) {
				// Sentinel variable, used in the loop to go through all lines
				auxNumberGames = 0;
				
				// Variable responsible to store the number of lines (games) choosen by the user
				// invoked method sends buffered reader as parameter and returns 
				// the number of lines that the user wants to play 
				numberGames = getNumberOfGames(br);
				
				// Message to user, notifying the game will begin
				System.out.println("Perfect! " + numberGames + " lines it is then! Lets begin..." );		
				
				// Creates matrix size based on the number of lines choosen
				int [][] MatrixLines = new int [numberGames][5]; 
				
				// While the main sentinel is lower than the number of lines choosen...
				while (auxNumberGames < numberGames) {
															
					// Prints message of which line is going to be asked to the user
					System.out.println("Please enter the numbers for the line " + (auxNumberGames + 1));
					// Sets/resets inner sentinel variable to 0, so it can be used again 
					counterNumForGame = 0;
					
					// While inner sentinel is lower/equal than 4 (5 positions)
					while (counterNumForGame <= 4) {	
						// Resets variable to 0, to be used again inside the loop
						newNumber = 0;
						// Tries to...
						try { 
							// While the new number is lower than 1 or higher than 45...
							while ((newNumber < 1) || (newNumber > 45)) {
								// Instructs the user to put the number inside the line/game
								System.out.print("Number " + (counterNumForGame + 1) + " : " );	
								// Reads from user
								newNumber = Integer.parseInt( br.readLine() );
								// If the new number is lower than 1 or higher than 45
								if  ((newNumber < 1) || (newNumber > 45)) {
									// Reminds the user of the range of valid numbers
									System.out.println("Remember only numbers between 1-45 are valid!");
								} // End if
							} // End while loop
													
							// Boolean variable receives return of method which checks if number
							// is in the list already. Passes the line and the new number as parameter
							isInTheList = checkNumber( MatrixLines[auxNumberGames], newNumber );
							// If not in the list
							if (!isInTheList) {
								// Coordinates "[Line][Number]" of the matrix receives new number
								MatrixLines[auxNumberGames][counterNumForGame] = newNumber;
								// Increments sentinel
								counterNumForGame++;
							// Else...	
							} else {
								// Tells the user the number is repeated
								System.out.println("You have choosen this number already, please select other."); 
							} // End if	
													
						} // End try				

						catch (IOException ioe) { 
							System.out.println("I/O error. Please try again!"); 
						}		
						catch ( NumberFormatException e ){ // Wrong number format error					
							System.out.println("Invalid input. Please enter a number between 1 and 45.");			
						} // End catches		
						
					} // End while loop (for the line)
					System.out.println("Line (game) saved! " );
					
					// Sorts the array that stored the last line
					Arrays.sort(MatrixLines[auxNumberGames]);
					
					// Breaks the line
					System.out.println();
					// Increments sentinel
					auxNumberGames++;
					
				} // End while loop (for the lines)
				
				// Prints all the lines (games) and the numbers entered
				printMyGame(MatrixLines);
				
				// Confirms with the user if he wants to confirm
				System.out.println("-----------------------");
				System.out.println("Are you sure you want to confirm the lines (games) above?");
								   
				// Resets/cleans viariable
				YorNConf = "";
				
				// While the input of the user is diff than Y or N 
				while ((!YorNConf.equals("Y")) && (!YorNConf.equals("N"))) {
					// Prints options to user
					System.out.println("(Y)es or (N)o");
					
					// Tries to...
					try { 	
						// Variable receives input from user
						// It is also being forced to uppercase
						YorNConf = ( br.readLine().toUpperCase() ); 
					} // End try				
					catch (IOException ioe) { // I/O error
						System.out.println("I/O error! Please try again! "); 
					} // End catch
					
					// If user chooses NO...
					if (YorNConf.equals("N")) {
						// Program starts again
						System.out.println(" No problem! let's begin again...")  ; 
					} // End if
					
				} // End while loop 
				
				// If the confirmation of the user is Yes
				if (YorNConf.equals("Y")) {
					// Sets numbers to draw to 6 (5 + bonus)
					toDraw = 6;		
					// Passes number by parameter and returns drawn numbers into an array
					draw = drawNumbers(toDraw);
					
					// Invokes method to print the drawn numbers, passing the array as parameter
					printDrawn(draw);

					// Invokes method to display the results, passing the matrix (lines) and the array (drawn numbers)
					// as parameter
					displayResult( MatrixLines, draw );
					
					// Asks user if the wants to play again
					System.out.println("\nWould you like to play again?");
					
					// Boolean variable receives return from method, which sends buffered reader as parameter
					playAgain = checkIfUserWantsToPlayAgain(br);
					
				} // End if
				
			} // End of the "play again" while loop
	
		} // End of the "confirmation" (Y or N) while loop
		
	} // End of the main method

	
	// Boolean method checking if user wants to play again
	// Receives buffered reader as parameter
	private static boolean checkIfUserWantsToPlayAgain(BufferedReader br) {
		String answer = "";
		
		// While user input is diff than N or Y...
		while ( (!answer.equals("N")) || (!answer.equals("Y")) ) {
			// Prints options to the user
			System.out.println("(Y)es or (N)o?");
			// Tries to...		
			try { 		
				// Collects input from user, forcing uppercase into variable
				answer = ( br.readLine().toUpperCase() ); 
			} // End try				
			catch (IOException ioe) { // I/O error
				System.out.println("I/O error! Please try again! "); 
			} // End catch		
				
			// If user doesn't wish to play again...
			if (answer.equals("N")) {
				// Thanks the user
				System.out.println("Thank you for playing! Come back soon!");
				// Finishes the program
				System.exit(0);

			// If user chooses "Yes"	
			} else if (answer.equals("Y")) {
				// Notifies user program will restart
				System.out.println("Great! Let's give another try!");
				// Method returns true				
				return true;
			} // End if			
		} // End  while 
		// Returns false
		return false;	
	} // End of the method

	// Method getting number of lines (games) from user
	// Rceives buffered reader as parameter
	private static int getNumberOfGames(BufferedReader br) {
		// Declaring variables
		int numberGames = 0;
		boolean isANumber = false;
		
		// While input is not a number or less than 1...
		while ((!isANumber) || (numberGames < 1)){	
			
			// Sets error flag as false
			boolean error = false;
			
			// Tries to...
			try { 
				// Asks user how many lines he wants to play
				System.out.print("How many lines (games) would you like to play? ");
				// Collects input into a variable
				numberGames = Integer.parseInt( br.readLine() ); 
			    // If no exception occurrs, set boolean as true 
				isANumber = true;
			} // End try			
			catch (IOException ioe) { // I/O error
				// Shows error message on screen
				System.out.println("I/O error! Please try again!"); 
				// Sets flag as true
				error = true;
			} 		
			catch ( NumberFormatException e ){ // Wrong number format error	
				// Shows error message on screen
				System.out.println("Error! Input needs to be a number.");
				// Sets flag as true
				error = true;
			} // End catches
			catch ( NegativeArraySizeException e ){ // Wrong number format error	
				// Sets flag as true
				error = true;
			} // End catches			
			
			
			// If number is 0 and flag false (no I/O or format error)...
			if ((numberGames < 1 ) && (error == false)) {
				// Tells user number has to be higher
				System.out.println("If you want to play, number has to be bigger than 0...");
			} // End if
			
		} // End while loop
		
		// Returns input
		return numberGames;
	} // End of the method

	// Method printing drawn numbers
	// Receives array with drawn numbers stored
	private static void printDrawn(int[] draw) {
		// Tells user numbers are being drawn
		System.out.println("Drawing numbers!... Good luck!");
		// Prints header
		System.out.print("Numbers drawn: ");
		// For loop going from 0 to array length
		for(int X = 0; X < draw.length; X++) {
			// Prints value in the sentinel's position			
			System.out.print(draw[X] + " ");
			// This block puts a delay between each print for the numbers
			// Simulating a real lottery, with numbers being drawn slowly
			try {
			    Thread.sleep(1000);     
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			// If array index = 4... 
			if (X == 4) {
				// Separates the bonus from others
				System.out.print("- Bonus: ");
			} // End if			
		} // End for loop		
	} // End of the method

	// Method welcoming the user and showing up range of numbers to choose
	// Has no parameters
	private static void printLottoCard() {
		
		// Welcome message
		System.out.println("Welcome to JLotto!");
		System.out.println("This is the lottery card to choose the numbers from. \n" +
					      "Choose 5 for each game. Good luck!");		
		// Header
		System.out.println("-------- LOTTO CARD --------");
		// For loop from 1 to 45
		for (int x = 1 ; x <= 45 ; x++) {
			// Print statement with internal "if", to adjust alignment
			System.out.print((x < 10) ? (" "+ x + " ") : (x + " ") );
			// If number mod 9 is 0...
			if (x % 9 == 0) {
				// Draws the edge of the card and breaks the line
				System.out.println("|");				
			} // End if
		} // End of the for loop
		// Foot
		System.out.println("----------------------------");		
	} // End of the method

	// Method displaying the results
	// Receives the matrix (lines) and the array witht he drawn numbers
	private static void displayResult(int[][] arrayNumbs, int[] draw) {
		// Declaring counters
		int CounterMatches = 0;
		int CounterBMatches = 0;
		// Printing header
		System.out.println();
		System.out.println("--------------------------------");	
		// For loop (outer) going though the lines (games)
		for ( int Line = 0 ; Line < arrayNumbs.length ; Line++ ){ // Lines
			// Resets each counter, to count the next line again
			CounterMatches = 0;
			CounterBMatches = 0;
			// "Header"
			System.out.print("Line " + (Line + 1) + " - ");
			// For loop (inner) going though the columns (numbers)
		    for ( int Number = 0 ; Number < arrayNumbs[Line].length ; Number++ ){ // Numbers	   
		    	// Separates the number stored in the [Line][Number] into a variable
		    	int num = arrayNumbs[Line][Number];
		    	// Flag to control/fix extra space when the * has to be printed
		    	boolean extraSpace = true;
		    	// Prints the number on the screen, fixing the allignment with a internal If statement
		    	System.out.print((num < 10) ? ( "  " + num ) : (" " + num ) );
		    	
		    	// For each number inside the line, compares to the draw array
		    	for ( int d = 0 ; d < 6 ; d++ ){
		    		// If the current number of the draw array (and not the bonus number) is equal to "num"... 
		    		if ((draw[d] == num) && (d < 5))  { // Matches with draws w/o bonus
		    			// Increases counter
		    			CounterMatches++;	
		    			// Prints * beside the matched number
		    			System.out.print("*");
		    			// Sets flag as true
		    			extraSpace = false;
		    		} // End if
		    		// If the current number of the draw array (bonus number) is equal to "num"... 
		    		if ((draw[5] == num) && (d == 5)) { // Matches with bonus
		    			// Increases counter
		    			CounterBMatches++;
		    			// Prints * beside the matched number
		    			System.out.print("*");
		    			// Sets flag as true
		    			extraSpace = false;
		    		} // End if		    				    		
		    	 } // End for loop
		    	// If flag is false
		    	if (extraSpace) {
		    		// Adds a space to compensate the missing *
		    		System.out.print(" ");
		    	} // End if
		    			    	
		     } // End inner loop	 
		    
		    // Shows to the user how many numbers matched (normal and plus/bonus)
		    System.out.println("  |  Matches: " + 
		    				   CounterMatches + " - Bonus Matched: " + CounterBMatches);	
		} // End outer loop
		// Prints foot
		System.out.println("--------------------------------");			
	} // End of the method	

	// Method drawing the numbers
	private static int [] drawNumbers(int toDraw) {
		// Declaring variables
		int x = 0;
		int draw [] = new int [toDraw];
		boolean isInTheList = false;
		int newNumber = 0;
		
		// While loop going through all the array
		while (x < draw.length) {				
				// Variable receives random number between 1-45
				newNumber = (int)(Math.random() * 45 + 1 );
				// Booleanvariable receives the return of the checkNumber method
				// Method sends the array and the new number as parameters
				isInTheList = checkNumber( draw, newNumber );
				// If not in the list
				if (!isInTheList) {
					// Adds to the array
					draw[x] = newNumber;
					// Increments sentinel
					x++;
				} // End if
		} // End while loop
		// Returns array populated
		return draw;
	} // End of the method

	// Method checking if the number is already in the array
	private static boolean checkNumber(int[] list, int newNumber) {
		// For loop going through all the array
		for(int x = 0; x < list.length; x++) {
			// Checks if the number is equal o 0 (never populated before)
			if (list[x] == 0) {
				// Returns false
				return false;
			// If the number is equal to the new number	
			} else if (list[x] == newNumber) { 
				// Returns true (number already in the array)
				return true; 
			} // End if
		} // End for loop
			// Retuns false
			return false; 				
	} // End of the method
	
	// Method printing game to the user before confirmation
	private static void printMyGame(int[][] ArrayNumbs) {
		// Breaks the line and puts header
		System.out.println();
		System.out.println("TICKET ----------------");
		// For loop going (outer) through all the lines
		for ( int line = 0 ; line < ArrayNumbs.length ; line++ ){
			// Prints the line
			System.out.print("Line " + (line + 1) + " - ");
			// For loop (inner) going through all the numbers
		    for ( int number = 0 ; number < ArrayNumbs[line].length ; number++ ){	    	
		    	// Catches the number in the [line][number] posiiton
		    	int X = ArrayNumbs[line][number];			    	
		    	// Prints it
			    System.out.print((X < 10) ? (" "+ X + " ") : (X + " ") );	
		    } // End inner for loop
		    // Breaks the line
		    System.out.println();
		} // End outer for loop
	} // End of the method		
} // End of the class
