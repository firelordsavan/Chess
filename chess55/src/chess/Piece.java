package chess;

/**
 * This class contains all common attributes and methods that belong to each Chess piece.
 * This class is also used in the Board class to create the virtual Chess Board.
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public abstract class Piece {

	/**
	 * The id of the Piece. (Example: "wR", "bp", etc.).
	 */
	String id;
	/**
	 * Which color the Piece belongs to ('w' = White or 'b' = Black).
	 */
	char color;
	/**
	 * What type the Piece is ('p' = Pawn, 'Q' = Queen, 'K' = King, 'R' = Rook, 'N' = Knight, 'B' = Bishop).
	 */
	char type;
	/**
	 * What column index the Piece is currently on in 'board' (0 to 7).
	 */
	int col;
	/**
	 * What row index the Piece is currently on in 'board' (0 to 7).
	 */
	int row;
	/**
	 * The Board object that the Piece belongs to.
	 */
	Board board;
	
	/**
	 * This constructor creates a Piece object with the given parameters.
	 * 
	 * @param id	The id of the Piece. (Example: "wR", "bp", etc.).
	 * @param color	Which color the Piece belongs to ('w' = White or 'b' = Black).
	 * @param type	What type the Piece is ('p' = Pawn, 'Q' = Queen, 'K' = King, 'R' = Rook, 'N' = Knight, 'B' = Bishop).
	 * @param col	What column index the Piece is currently on in 'board' (0 to 7).
	 * @param row	What row index the Piece is currently on in 'board' (0 to 7).
	 * @param board	The Board object that the Piece belongs to.
	 */
	public Piece(String id, char color, char type, int col, int row, Board board) {
		this.id = id;
		this.color = color;
		this.type = type;
		this.col = col;
		this.row = row;
		this.board = board;
	}
	
	/**
	 * Returns Piece's id.
	 * @return	id of Piece.
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Changes the id of the Piece.
	 * @param id	The id of the Piece. (Example: "wR", "bp", etc.).
	 */
	public void setID(String id) {
		this.id = id;
	}
	
	/**
	 * Returns Piece's color.
	 * @return	color of Piece.
	 */
	public char getColor() {
		return this.color;
	}
	
	/**
	 * Changes the color of the Piece.
	 * @param color	Which color the Piece belongs to ('w' = White or 'b' = Black).
	 */
	public void setColor(char color) {
		this.color = color;
	}
	
	/**
	 * Returns Piece's type.
	 * @return	Piece's type.
	 */
	public char getType() {
		return this.type;
	}
	
	/**
	 * Changes the type of the Piece.
	 * @param type	What type the Piece is ('p' = Pawn, 'Q' = Queen, 'K' = King, 'R' = Rook, 'N' = Knight, 'B' = Bishop).
	 */
	public void setType(char type) {
		this.type = type;
	}
	
	/**
	 * Returns Piece's column index.
	 * @return	Piece's column index.
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 * Changes the column index of the Piece.
	 * @param col	What column index the Piece is currently on in 'board' (0 to 7).
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * Returns Piece's row index.
	 * @return	Piece's row index.
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Changes the row index of the Piece.
	 * @param row	What row index the Piece is currently on in 'board' (0 to 7).
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Abstract method to be implemented by classes who inherit from Piece.
	 * This method will check if the passed row and column is a valid spot for the given Piece to move to.
	 * @param rank	The inputed rank of the next position (0 to 7). 
	 * @param file	The inputed file of the next position (0 to 7).
	 * @return	True if the move is valid. False if the move is invalid.
	 */
	public abstract boolean isValid(int rank, int file);
	
	/**
	 * Abstract method to be implemented by classes who inherit from Piece.
	 * This method will move Piece to the given rank and file.
	 * @param rank	The inputed rank of the next position (0 to 7). 
	 * @param file	The inputed file of the next position (0 to 7).
	 * @return	2 if there is a checkmate. 1 if the move doesn't lead to checkmate but does lead to check. 0 if neither is true.
	 */
	public abstract int movePiece(int rank, int file);
	
}
