package chess;

/**
 * This class is used to represent a Queen in the game.
 * As such, this Piece is allowed to move anywhere in it diagonals, row, or column as long as no Piece is on its path..
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public class Queen extends Piece {
	
	/**
	 * This constructor creates a Queen object using the super constructor of the Piece class.
	 * 
	 * @param id	'wQ' or 'bQ'.
	 * @param color	Which color the Queen belongs to ('w' = White or 'b' = Black).
	 * @param type	'Q' for Queen.
	 * @param col	What column index the Queen is currently on in 'board' (0 to 7).
	 * @param row	What row index the Queen is currently on in 'board' (0 to 7).
	 * @param board	The Board object that the Queen belongs to.
	 */
	public Queen(String id, char color, char type, int col, int row, Board board) {
		super(id, color, type, col, row, board);
	}
	
	/**
	 * This method will check if the passed row and column is a valid spot for the Queen to move to.
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
		// check if a piece of that user's color is already there
		if(gameBoard[rank][file] != null && gameBoard[rank][file].getColor() == color) {
			return false;
		}
		boolean rvalid = false; // checking if the queen is behaving like a rooke
		boolean bvalid = false; // checking if the queen is behaving like a bishop
		// check for any interferences on path
		if(file == col && rank != row) {
			rvalid = true;
			if(rank > row) {
				for(int i = row + 1; i < rank; i++) {
					if(gameBoard[i][file] != null) {
						return false;
					}
				}
			}
			else {
				for(int i = row - 1; i > rank; i--) {
					if(gameBoard[i][file] != null) {
						return false;
					}
				}
			}
		}
		if(file != col && rank == row) {
			rvalid = true;
			if(file > col) {
				for(int i = col + 1; i < file; i++) {
					if(gameBoard[rank][i] != null) {
						return false;
					}
				}
			}
			else {
				for(int i = col - 1; i > file; i--) {
					if(gameBoard[rank][i] != null) {
						return false;
					}
				}
			}
		}
		if(!rvalid) { // this means the queen MUST behave like a bishop
			if(Math.abs(file - col) == Math.abs(rank - row)) {
				bvalid = true;
			}
			if(!bvalid) {
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
		}
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
	 * This method will move the Queen to the given rank and file.
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
