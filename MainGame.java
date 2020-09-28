import java.util.*;
import java.util.TimerTask;
import java.util.Timer;

public class MainGame {
	static Scanner in = new Scanner(System.in);
	static int interval;
	static Timer timer;
	
	//main method for user interface
	public static void main(String[] args) {
		int choice = 0;
		
		System.out.println("Welcome!");
		do {
			System.out.println("What would you like to play?");
			System.out.println("1 for Basic Game\n2 for Speed Game\n3 to Quit");
			choice = in.nextInt();
			
			//Chooses game based of choice
			if(choice == 1) {gameOne();}
			else if(choice == 2) {gameTwo();}
			else if(choice == 3) {break;}
			else{
				choice = 0; 
				System.out.println("What you've entered is NOT an option. Try again.");
			}
		} while(choice >= 0 && choice <= 3);
		System.out.println("Thanks for playing!");
	}
	
	//runs Basic Game (choice 1)
	public static void gameOne() {
		boolean choice = true;
		double points = 0.0;
		int attempts = 1;
		double average = 0;
		do{
			//shows attempt number and gives question
			System.out.printf("Attempt %d:\n",attempts);
			System.out.print("What name matches this letter?: ");
			String l = returnLetter(); System.out.println(l);
			String response = in.next();
			
			//checks user response and adds points accordingly
			response = response.toUpperCase();
			boolean result = check(l,response);
			result = levenshteinDistance(response, returnName(l),2);
			if(result == true) {System.out.println("CORRECT! You get one point!!"); points++;}
			else {System.out.printf("Incorrect. The correct answer was %s.You've earned NO points.\n",returnName(l));}
			
			//calculates average and asks to continue
			average = points/attempts * 100;
			System.out.printf("You have %.2f points.\n",points);
			System.out.printf("Percentage Correct: %.02f\n",average);
			System.out.println("Do you want to continue? (y/n)");
			String decide = in.next(); 
			attempts++;
			if(decide.equals("n") || decide.equals("N")){choice = false;}
		}while(choice == true);
	}
	
	//plays Speed Game (choice 2)		
	public static void gameTwo() {
		//asks how much time is wanted for questions
		System.out.println("You will need to answer questions within a certain amount of time.");
		System.out.println("How many seconds will you give yourself for each question?");
		int seconds = in.nextInt();
		//establishes parameters to build countdown timer
		int delay = 1000;
		int period = 1000;
		interval = seconds;
		
		//more parameters for other actions in gameTwo
		boolean choice = true;
		double points = 0.0;
		int attempts = 1;
		double average = 0;
		String decide = "";

		
		//begins timed game
		System.out.print("GET READY!! This game will begin now. "); 
				
		do{
			//countdown timer begins
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
		        public void run() {System.out.println(setInterval());}
		        },delay, period);
			
			//shows attempt number and gives question
			System.out.print("What name matches this letter?: ");
			String l = returnLetter(); System.out.println(l);
			in.nextLine();	//I have no idea why this line of code makes it work.
			String response = in.next();
			timer.cancel();
			
			//checks user response 
			response = response.toUpperCase();
			boolean result = check(l,response);
			result = levenshteinDistance(response, returnName(l),2);
			//if answer is correct, code will continue to next question
			if(interval > 0 && result == true) {
				System.out.println("CORRECT! You get one point!!"); points++;
				timer = new Timer();
				interval = seconds;
				}
			//if answer is wrong, then it will show statistics 
			else if(interval == 0 || result == false){
				if(interval == 0) {System.out.printf("Sorry. You ran out of time. The correct answer was %s.\n",returnName(l));}
				else {System.out.printf("Incorrect. The correct answer was %s.\n",returnName(l));}
				System.out.println("You've earned NO points.");
				System.out.printf("You have %.2f points.\n",points);
				System.out.println("Do you want to continue? (y/n)");
				
				decide = in.next();
				if(decide.equals("Y") || decide.equals("y")){
					attempts = 1;
					points = 0;
					timer = new Timer();
					interval = seconds;
				}
				else {choice = false;}
				}
			attempts++;
		}while(choice == true);
		//shows average of game
		average = points/attempts * 100;
		System.out.printf("Percentage Correct: %.02f\n",average);
		
	}
	
	//returns a random letter
	private static String returnLetter() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int pick = (int)(Math.random() * alphabet.length());
		return alphabet.substring(pick,pick+1);
	}
	
	//returns phonetic name based of given letter
	private static String returnName(String letter) {
		switch(letter)
		{
			case "A": {return "ALPHA";}
			case "B": {return "BRAVO";}
			case "C": {return "CHARLIE";}
			case "D": {return "DELTA";}
			case "E": {return "ECHO";}
			case "F": {return "FOXTROT";}
			case "G": {return "GOLF";}
			case "H": {return "HOTEL";}
			case "I": {return "INDIA";}
			case "J": {return "JULIETT";}
			case "K": {return "KILO";}
			case "L": {return "LIMA";}
			case "M": {return "MIKE";}
			case "N": {return "NOVEMBER";}
			case "O": {return "OSCAR";}
			case "P": {return "PAPA";}
			case "Q": {return "QUEBEC";}
			case "R": {return "ROMEO";}
			case "S": {return "SIERRA";}
			case "T": {return "TANGO";}
			case "U": {return "UNIFORM";}
			case "V": {return "VICTOR";}
			case "W": {return "WHISKEY";}
			case "X": {return "XRAY";}
			case "Y": {return "YANKEE";}
			case "Z": {return "ZULU";}
		}
		return "";	
	}
	//decides if given letter matches it's phonetic name
	private static boolean check(String letter, String answer) {
		switch(letter)
		{
			case "A": if(answer.equals("ALPHA")){return true;}
			case "B": if(answer.equals("BRAVO")){return true;}
			case "C": if(answer.equals("CHARLIE")){return true;}
			case "D": if(answer.equals("DELTA")){return true;}
			case "E": if(answer.equals("ECHO")){return true;}
			case "F": if(answer.equals("FOXTROT")){return true;}
			case "G": if(answer.equals("GOLF")){return true;}
			case "H": if(answer.equals("HOTEL")){return true;}
			case "I": if(answer.equals("INDIA")){return true;}
			case "J": if(answer.equals("JULIETT")){return true;}
			case "K": if(answer.equals("KILO")){return true;}
			case "L": if(answer.equals("LIMA")){return true;}
			case "M": if(answer.equals("MIKE")){return true;}
			case "N": if(answer.equals("NOVEMBER")){return true;}
			case "O": if(answer.equals("OSCAR")){return true;}
			case "P": if(answer.equals("PAPA")){return true;}
			case "Q": if(answer.equals("QUEBEC")){return true;}
			case "R": if(answer.equals("ROMEO")){return true;}
			case "S": if(answer.equals("SIERRA")){return true;}
			case "T": if(answer.equals("TANGO")){return true;}
			case "U": if(answer.equals("UNIFORM")){return true;}
			case "V": if(answer.equals("VICTOR")){return true;}
			case "W": if(answer.equals("WHISKEY")){return true;}
			case "X": if(answer.equals("XRAY")){return true;}
			case "Y": if(answer.equals("YANKEE")){return true;}
			case "Z": if(answer.equals("ZULU")){return true;}
		}
		return false;
	}
	
	//for countdown timer to return seconds remaining to be printed in gameTwo
	private static final int setInterval() {
	    if (interval == 1)
	        timer.cancel();
	    return interval--;
	}
	
	//determines if the Levenshtein Distance between two Strings exceeds given interger limit.
	private static boolean levenshteinDistance(String a, String b, int limit) {
		a = " " + a; 
		b = " " + b;
		int[][] subproblems = new int[a.length()][b.length()];
		int indexOne = 0;
		int indexTwo = 0;
		int value = 0;
		
		//immediately returns false if string a and b are different in size
		if(a.length() != b.length()) {return false;}
		
		//sets values for first row
		while(indexOne<a.length()) {
			if(a.substring(indexOne, indexOne+1).equals(b.substring(indexOne,indexOne+1))) {
				subproblems[0][indexOne] = value;
			}
			else {
				subproblems[0][indexOne] = value;
				value++;
				}
			//checks to see if new distance exceeds limit
			if(subproblems[0][indexOne] > limit) {return false;}
			indexOne++;
		}
		
		//resets value for next loop
		value = 0;
		
		//sets values for first indexes on each row
		while(indexTwo<b.length()) {
			if(b.substring(indexTwo,indexTwo+1).equals(a.substring(indexTwo,indexTwo+1))) {
				subproblems[indexTwo][0] = value;
			}
			else {
				subproblems[indexTwo][0] = value;
				value++;
				}
			//checks to see if new distance exceeds limit
			if(subproblems[indexTwo][0] > limit) {return false;}
			indexTwo++;
		}
		
		//finishes the rest of the subproblems
		for(int i=1; i<b.length(); i++) {
			
			for(int j=1; j<a.length();j++) {
				int deletions = subproblems[i-1][j];
				int replacements = subproblems[i-1][j-1];
				int insertions = subproblems[i][j-1];
				
				int lowestDist = deletions;
				if(replacements < lowestDist) {lowestDist = replacements;}
				if(insertions < lowestDist) {lowestDist = insertions;}
				
				if(a.substring(0,i+1).equals(b.substring(0,j+1))) {
					subproblems[i][j] = replacements;
				}
				else {subproblems[i][j] = lowestDist;}
				
				//checks to see if new distance exceeds limit
				if(subproblems[i][j] > limit) {return false;}
			}
		}
		int levensteinDist = subproblems[a.length()-1][b.length()-1];
		
		//decides to return true or false by comparing levensteinDistance with given limit
		if(levensteinDist > limit) {return false;}
		else {return true;}
	}
}
