package chess;

import java.lang.Math;

/**
 * This class is used to represent a Bishop in the game.
 * As such, this Piece is allowed to move in any of its diagonals, provided that no Piece is on its path.
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public class Bishop extends Piece {
	
	/**
	 * This constructor creates a Bishop object using the super constructor of the Piece class.
	 * 
	 * @param id	'wB' or 'bB'.
	 * @param color	Which color the Bishop belongs to ('w' = White or 'b' = Black).
	 * @param type	'B' for Bishop.
	 * @param col	What column index the Bishop is currently on in 'board' (0 to 7).
	 * @param row	What row index the Bishop is currently on in 'board' (0 to 7).
	 * @param board	The Board object that the Bishop belongs to.
	 */
	public Bishop(String id, char color, char type, int col, int row, Board board) {
		super(id, color, type, col, row, board);
	}
	
	/**
	 * This method will check if the passed row and column is a valid spot for the Bishop to move to.
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
		
		boolean valid = false;
		if(Math.abs(file - col) == Math.abs(rank - row)) {
			valid = true;
		}
		
		// check if move is valid for Bishop
		if(!valid) {
			return false;
		}
		
		// check if a piece is in the way of the bishop
		int steps = Math.abs(file - col); // Number of steps away from the Bishop's current location
		if(rank > row) {
			if(file > col) {
				for(int i = 1; i < steps; i++) {
					if(gameBoard[row+i][col+i] != null) {
						return false;
					}
				}
			}
			else {
				for(int i = 1; i < steps; i++) {
					if(gameBoard[row+i][col-i] != null) {
						return false;
					}
				}
			}
		}
		else {
			if(file > col) {
				for(int i = 1; i < steps; i++) {
					if(gameBoard[row-i][col+i] != null) {
						return false;
					}
				}
			}
			else {
				for(int i = 1; i < steps; i++) {
					if(gameBoard[row-i][col-i] != null) {
						return false;
					}
				}
			}
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
	
	/**
	 * This method will move the Bishop to the given rank and file.
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
