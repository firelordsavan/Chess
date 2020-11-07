package chess;

import java.util.Scanner;

/**
 * This class contains the main method and is used to start the program.
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public class Chess {

	/**
	 * Main method that is used to start the program.
	 * Main method also alternates turns between both players.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board board = new Board();
		Scanner scan = new Scanner(System.in);
		boolean gameStatus = true;
		while(gameStatus) {
			gameStatus = board.makeMove('w', "White", "Black", scan);
			if(!gameStatus)
				break;
			gameStatus = board.makeMove('b', "Black", "White", scan);
		}
		scan.close();
	}

}
