package chess;

/**
 * This class is used to represent a Knight in the game.
 * As such, this Piece is allowed to move in the special 8 directions that Knights are allowed to move in.
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public class Knight extends Piece {
	
	/**
	 * This constructor creates a Knight object using the super constructor of the Piece class.
	 * 
	 * @param id	'wN' or 'bN'.
	 * @param color	Which color the Knight belongs to ('w' = White or 'b' = Black).
	 * @param type	'N' for Knight.
	 * @param col	What column index the Knight is currently on in 'board' (0 to 7).
	 * @param row	What row index the Knight is currently on in 'board' (0 to 7).
	 * @param board	The Board object that the Knight belongs to.
	 */
	public Knight(String id, char color, char type, int col, int row, Board board) {
		super(id, color, type, col, row, board);
	}
	
	/**
	 * This method will check if the passed row and column is a valid spot for the Knight to move to.
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
		if(file == col-1) {
			if(rank == row+2) {
				valid = true;
			}
			else if(rank == row-2){
				valid = true;
			}
		}
		else if(file == col+1) {
			if(rank == row+2) {
				valid = true;
			}
			else if(rank == row-2){
				valid = true;
			}
		}
		else if(file == col-2) {
			if(rank == row+1) {
				valid = true;
			}
			else if(rank == row-1){
				valid = true;
			}
		}
		else if(file == col+2) {
			if(rank == row+1) {
				valid = true;
			}
			else if(rank == row-1){
				valid = true;
			}
		}
		
		// check if move is valid for knight
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
	
	/**
	 * This method will move the Knight to the given rank and file.
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
