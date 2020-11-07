package chess;

/**
 * This class is used to represent a Rook in the game.
 * As such, this Piece is allowed to move anywhere in its row or column, provided that no other Piece is on its path.
 * Rook are also able to castle with the King as long as castle requirements are met.
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public class Rook extends Piece{

	/**
	 * Boolean to keep track of whether the Rook had move or not already.
	 * Used when checking if a Rook can castle.
	 */
	boolean rfirstMove;
	
	/**
	 * This constructor creates a Rook object using the super constructor of the Piece class.
	 * 
	 * @param id	'wR' or 'bR'.
	 * @param color	Which color the Rook belongs to ('w' = White or 'b' = Black).
	 * @param type	'R' for Rook.
	 * @param col	What column index the Rook is currently on in 'board' (0 to 7).
	 * @param row	What row index the Rook is currently on in 'board' (0 to 7).
	 * @param board	The Board object that the Rook belongs to.
	 */
	public Rook(String id, char color, char type, int col, int row, Board board) {
		super(id, color, type, col, row, board);
		this.rfirstMove = true;
	}
	
	/**
	 * This method will check if the passed row and column is a valid spot for the Rook to move to.
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
		
		// check if spot is occupied by piece of same color
		if(gameBoard[rank][file] != null && gameBoard[rank][file].getColor() == color) {
			return false;
		}
		
		boolean valid = false;
		// check for any interferences on path
		if(file == col && rank != row) {
			valid = true;
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
			valid = true;
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
		
		// check if move is valid for rooke
		if(!valid) {
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
	 * This method will move the Rook to the given rank and file.
	 * @param rank	The inputed rank of the next position (0 to 7). 
	 * @param file	The inputed file of the next position (0 to 7).
	 * @return	2 if there is a checkmate. 1 if the move doesn't lead to checkmate but does lead to check. 0 if neither is true.
	 */
	public int movePiece(int rank, int file) {
		Piece[][] gameBoard = board.getBoard();
		gameBoard[rank][file] = this;
		gameBoard[row][col] = null;
		
		if (rfirstMove) {
			this.rfirstMove = false;
		}
		
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
	
	/**
	 * Gets whether the Rook made its first move or not.
	 * @return True if Rook had moved, False if Rook had never moved.
	 */
	public boolean getRGetFirstMove () { // tells us whether or not the rooke has move yet or not!
		return this.rfirstMove;
	}
	
}
