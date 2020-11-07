package chess;

/**
 * This class is used to represent a King in the game.
 * As such, this Piece is allowed to move one space at a time and is allowed to castle with its Rooks if castle requirements are met.
 * This Piece's location is also tracked by the Board object in order to check for checks.
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public class King extends Piece {

	/**
	 * Boolean to keep track of whether the King had move or not already.
	 * Used when checking if a King can castle.
	 */
	boolean kFirstMove;
	
	/**
	 * This constructor creates a King object using the super constructor of the Piece class.
	 * 
	 * @param id	'wK' or 'bK'.
	 * @param color	Which color the King belongs to ('w' = White or 'b' = Black).
	 * @param type	'K' for King.
	 * @param col	What column index the King is currently on in 'board' (0 to 7).
	 * @param row	What row index the King is currently on in 'board' (0 to 7).
	 * @param board	The Board object that the King belongs to.
	 */
	public King(String id, char color, char type, int col, int row, Board board) {
		super(id, color, type, col, row, board);
		this.kFirstMove = true;
	}
	
	/**
	 * This method will check if the passed row and column is a valid spot for the King to move to.
	 * @param rank	The inputed rank of the next position (0 to 7). 
	 * @param file	The inputed file of the next position (0 to 7).
	 * @return	True if the move is valid. False if the move is invalid.
	 */
	public boolean isValid(int rank, int file){
		Piece[][] gameBoard = board.getBoard();
		boolean canCastleLeft = true;
		boolean canCastleRight = true;
		Piece checker;
		if(kFirstMove) { // check for interference on left
			for(int i = 3; i > 0; i--) {
				checker = gameBoard[row][i];
				if(checker != null) { // interference on board
					canCastleLeft = false;
					break;
				}
			}
		}
		if(kFirstMove) { // check for interference on right
			for(int i = 5; i < 7; i++) {
				checker = gameBoard[row][i];
				if(checker != null) {
					canCastleRight = false;
					break;
				}
			}
		}

		// check to see if new file and rank are within bounds
		if(file < 0 || file > 7 || rank < 0 || rank > 7) {
			return false;
		}
		// check to see if spot you want to move to is occupied by your own piece or not
		if(gameBoard[rank][file] != null && gameBoard[rank][file].getColor() == color) {
			return false;
		}
		// check to see if movement is valid or not!
		boolean kvalid = false;
		if(file == col) { // king is moving up or down only
			if (rank - 1 == row) {
				kvalid = true;
			}
			else if(rank + 1 == row) {
				kvalid = true;
			}
		}
		else if(rank == row) { // king is moving left or right only
			if(file - 1 == col) {
				kvalid = true;
			}
			else if(file + 1 == col) {
				kvalid = true;
			}
		}
		// checking diagonal movement
		else if(rank - 1 == row) {
			if (file - 1 == col) {
				kvalid = true;
			}
			else if(file + 1 == col) {
				kvalid = true;
			}
		}
		else if (rank + 1 == row) {
			if (file - 1 == col) {
				kvalid = true;
			}
			else if(file + 1 == col) {
				kvalid = true;
			}	
		}
		// code for castling
		Piece lrook;
		Rook temp;
		Piece cross;
		if (file + 2 == col) { // lefthand castling
			lrook = gameBoard[row][0]; // left hand rooke
			if (!kFirstMove) { // not the first move for king
				return false;
			}
			else if(!canCastleLeft) { // interference on the board
				return false;
			}
			else if(!board.check(color)) { // king is in check, cannot castle!
				return false;
			}
			else if (lrook == null) { // no piece there at all
				return false;
			}
			else if (!(lrook instanceof Rook)) { // not a rooke there but another piece
				return false;
			}
			temp = (Rook) lrook;
			if (!temp.getRGetFirstMove()) { // rooke has already moved once
				return false;
			}
			else if(this.color != temp.getColor()) { // someone else's rook is there
				return false;
			}
			
			// have to check if the king is in check at the crossover spot 
			cross = gameBoard[row][file + 1];
			gameBoard[row][file + 1] = this;
			gameBoard[row][col] = null;
			board.setKing(color, row, file + 1);
			if(!board.check(color)) { // king is in check at the crossover spot!
				gameBoard[row][col] = this;
				gameBoard[rank][file + 1] = cross;
				board.setKing(color, row, col);
				return false;
			}
			// setting pieces back to regular spots for final check up!
			gameBoard[row][col] = this; 
			gameBoard[rank][file + 1] = cross;
			board.setKing(color, row, col);
			
			cross = gameBoard[row][file];
			gameBoard[row][file] = this;
			gameBoard[row][col] = null;
			board.setKing(color, row, file);
			if(!board.check(color)) { // king is in check at the destination spot
				gameBoard[row][col] = this;
				gameBoard[rank][file] = cross;
				board.setKing(color, row, col);
				return false;
			}
			// setting pieces back to where they are!
			gameBoard[row][col] = this;
			gameBoard[rank][file] = cross;
			board.setKing(color, row, col);
			kvalid = true;
			// move the rooke to its spot before crossover!
			temp.movePiece(row, file + 1);
		}
		Piece rrook;
		if (file - 2 == col) { // righthand castling
			rrook = gameBoard[row][7]; // right hand rooke
			if(!kFirstMove) { // not the first move for king
				return false;
			}
			else if(!canCastleRight) { // interference on the board
				return false;
			}
			else if(!board.check(color)) { // king is in check, cannot castle!
				return false;
			}
			else if(rrook == null) { // no piece there at all
				return false;
			}
			else if(!(rrook instanceof Rook)) { // not a rooke there but another piece
				return false;
			}
			temp = (Rook) rrook;
			if(!temp.getRGetFirstMove()) { // rook has already moved once
				return false;
			}
			else if(this.color != temp.getColor()) {
				return false;
			}
			
			// have to check if the king is in check at the crossover spot 
			cross = gameBoard[row][file - 1];
			gameBoard[row][file - 1] = this;
			gameBoard[row][col] = null;
			board.setKing(color, row, file - 1);
			if(!board.check(color)) { // king is in check at the crossover spot!
				gameBoard[row][col] = this;
				gameBoard[rank][file - 1] = cross;
				board.setKing(color, row, col);
				return false;
			}
			// setting pieces back to regular spots for final check up!
			gameBoard[row][col] = this; 
			gameBoard[rank][file - 1] = cross;
			board.setKing(color, row, col);
			
			cross = gameBoard[row][file];
			gameBoard[row][file] = this;
			gameBoard[row][col] = null;
			board.setKing(color, row, file);
			if(!board.check(color)) { // king is in check at the destination spot
				gameBoard[row][col] = this;
				gameBoard[rank][file] = cross;
				board.setKing(color, row, col);
				return false;
			}
			// setting pieces back to where they are!
			gameBoard[row][col] = this;
			gameBoard[rank][file] = cross;
			board.setKing(color, row, col);
			kvalid = true;
			// move the rooke to its spot before crossover!
			temp.movePiece(row, file - 1); 

		}
		if (!kvalid) { // no valid move for the king possible
			return false;
		}
		
		// stores the original position of king
		int[] oldPos = new int[2];
		oldPos[0] = board.getKing(color)[0];
		oldPos[1] = board.getKing(color)[1];

		Piece tempo = gameBoard[rank][file];
		board.setKing(color, rank, file);
		gameBoard[rank][file] = this;
		gameBoard[row][col] = null;
		
		if(!board.check(color)) {
			gameBoard[row][col] = this;
			gameBoard[rank][file] = tempo;
			board.setKing(color, oldPos[0], oldPos[1]);
			return false;
		}
		
		gameBoard[row][col] = this;
		gameBoard[rank][file] = tempo;
		board.setKing(color, oldPos[0], oldPos[1]);
		return true;
	}
	
	/**
	 * This method will move the King to the given rank and file, and update the board's coordinate.
	 * @param rank	The inputed rank of the next position (0 to 7). 
	 * @param file	The inputed file of the next position (0 to 7).
	 * @return	2 if there is a checkmate. 1 if the move doesn't lead to checkmate but does lead to check. 0 if neither is true.
	 */
	public int movePiece(int rank, int file) {
		Piece[][] gameBoard = board.getBoard();
		gameBoard[rank][file] = this;
		gameBoard[row][col] = null;
		
		if (kFirstMove) {
			this.kFirstMove = false;
		}
		
		row = rank;
		col = file;
		board.setKing(color, row, col);
		
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
