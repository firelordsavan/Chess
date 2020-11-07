package chess;

/**
 * This class is used to represent a Pawn in the game.
 * As such, Piece object of this kind will be allowed to move in one direction, perform en passant, move one space at a time,
 * move two spaces in the first move, attack any pieces that are diagonal to it,
 * and change to another Piece if Pawn reaches other side of board.
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public class Pawn extends Piece {
	
	/**
	 * Boolean to keep track of whether a Pawn had move or not already.
	 * Used when checking if a Pawn can move two spaces.
	 */
	boolean firstMove;
	
	/**
	 * Boolean to keep track of whether the Pawn can get en passant.
	 */
	boolean enpassant;

	/**
	 * This constructor creates a Pawn object using the super constructor of the Piece class.
	 * 
	 * @param id	'wp' or 'bp'.
	 * @param color	Which color the Pawn belongs to ('w' = White or 'b' = Black).
	 * @param type	'p' for Pawn.
	 * @param col	What column index the Pawn is currently on in 'board' (0 to 7).
	 * @param row	What row index the Pawn is currently on in 'board' (0 to 7).
	 * @param board	The Board object that the Pawn belongs to.
	 */
	public Pawn(String id, char color, char type, int col, int row, Board board) {
		super(id, color, type, col, row, board);
		this.firstMove = true;
		this.enpassant = false;
	}
	
	/**
	 * This method will check if the passed row and column is a valid spot for the Pawn to move to.
	 * @param rank	The inputed rank of the next position (0 to 7). 
	 * @param file	The inputed file of the next position (0 to 7).
	 * @return	True if the move is valid. False if the move is invalid.
	 */
	public boolean isValid(int rank, int file){
		Piece[][] gameBoard = board.getBoard();

		// check to see if new file and rank are within bounds
		if(file < 0 || file > 7 || rank < 0 || rank > 7) {
			return false;
		}
		
		if(color == 'w') {
			boolean valid = false;
			//  check if pawn spaces are valid
			if(file == col && rank == row-1 && gameBoard[rank][file] == null) {
				valid = true;
			}
			else if(file == col-1 && rank == row-1 && gameBoard[rank][file] != null && gameBoard[rank][file].getColor() != color) {
				valid = true;
			}
			else if(file == col+1 && rank == row-1 && gameBoard[rank][file] != null && gameBoard[rank][file].getColor() != color) {
				valid = true;
			}
			else if(file == col && rank == row-2 && firstMove && gameBoard[rank][file] == null) {
				valid = true;
				enpassant = true;
			}
			else if((file == col-1 || file == col+1) && rank == row-1) {
				int[] currentEnpassant = board.getEnpassant();
				char currentEnpassantColor = board.getEnpassantColor();
				if(currentEnpassant[0] == rank + 1 && currentEnpassant[1] == file && currentEnpassantColor != color) {
					valid = true;
					gameBoard[rank+1][file] = null;
				}
			}
			
			// check if move is valid for pawn
			if(!valid) {
				return false;
			}
			
			// check if a piece of that user's color is already there
			if(gameBoard[rank][file] != null && gameBoard[rank][file].getColor() == color) {
				return false;
			}
			
			// Move the piece and check to make sure user isn't put in check and if opponent is in check
			Piece temp = gameBoard[rank][file];
			gameBoard[rank][file] = this;
			gameBoard[row][col] = null;
			
			if(!board.check(color)) {
				gameBoard[row][col] = this;
				gameBoard[rank][file] = temp;
				return false;
			}
			
			gameBoard[row][col] = this;
			gameBoard[rank][file] = temp;
			
			return true;
		}
		else {
			boolean valid = false;
			if(file == col && rank == row+1 && gameBoard[rank][file] == null) {
				valid = true;
			}
			else if(file == col-1 && rank == row+1 && gameBoard[rank][file] != null && gameBoard[rank][file].getColor() != color) {
				valid = true;
			}
			else if(file == col+1 && rank == row+1 && gameBoard[rank][file] != null && gameBoard[rank][file].getColor() != color) {
				valid = true;
			}
			else if(file == col && rank == row+2 && firstMove && gameBoard[rank][file] == null) {
				valid = true;
				enpassant = true;
			}
			else if((file == col-1 || file == col+1) && rank == row+1) {
				int[] currentEnpassant = board.getEnpassant();
				char currentEnpassantColor = board.getEnpassantColor();
				if(currentEnpassant[0] == rank - 1 && currentEnpassant[1] == file && currentEnpassantColor != color) {
					valid = true;
					gameBoard[rank-1][file] = null;
				}
			}
			
			// check if move is valid for pawn
			if(!valid) {
				return false;
			}
			
			// check if a piece of that user's color is already there
			if(gameBoard[rank][file] != null && gameBoard[rank][file].getColor() == color) {
				return false;
			}
			
			// Move the piece and check to make sure user isn't put in check and if opponent is in check
			Piece temp = gameBoard[rank][file];
			gameBoard[rank][file] = this;
			gameBoard[row][col] = null;
			
			if(!board.check(color)) {
				gameBoard[row][col] = this;
				gameBoard[rank][file] = temp;
				return false;
			}
			
			gameBoard[row][col] = this;
			gameBoard[rank][file] = temp;
			
			return true;
		}
	}
	
	/**
	 * This method will move the Pawn to the given rank and file, and update board's en passant.
	 * @param rank	The inputed rank of the next position (0 to 7). 
	 * @param file	The inputed file of the next position (0 to 7).
	 * @return	2 if there is a checkmate. 1 if the move doesn't lead to checkmate but does lead to check. 0 if neither is true.
	 */
	public int movePiece(int rank, int file) {
		Piece[][] gameBoard = board.getBoard();
		gameBoard[rank][file] = this;
		gameBoard[row][col] = null;
		
		row = rank;
		col = file;
		
		// if code reaches this point, that means the first move is already completed.
		firstMove = false;
		// set en passant on board to indicate potential for next round
		if(enpassant){
			board.setEnpassant(rank, file);
			board.setEnpassantColor(color);
		}
		enpassant = false;
		
		char oppColor = color == 'w' ? 'b' : 'w';
		if(!board.check(oppColor)) {
			if(board.checkmate(oppColor)) {
				return 2;
			}
			else {
				return 1;
			}
		}
		
		return 0;
	}

}

