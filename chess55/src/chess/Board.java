package chess;

import java.util.Scanner;

/**
 * This class handles all moves that are done in the game as well as handles "draws", "resigns", "checks", and "checkmates".
 * This class also contains the "Chess Board", which contains all the current pieces that are currently in play, and prints the board as well.
 * 
 * @author Savan Patel
 * @author Naveenan Yogeswaran
 *
 */

public class Board {

	/**
	 * Array that represents the current Chess Board.
	 * Contains all references to active Pieces.
	 */
	Piece[][] board;
	/**
	 * Boolean to check if a draw request has been made
	 */
	boolean draw = false;
	
	/**
	 * Array to keep track of White's King's Coordinates which is used to Check.
	 */
	int[] bKing = {0, 4};
	/**
	 * Array to keep track of Black's King's Coordinates which is used to Check.
	 */
	int[] wKing = {7, 4};
	
	/**
	 * Array to keep track of the location of any Pawn who is vulnerable to an en passant.
	 */
	int[] enpassant = {-2, -2};
	/**
	 * Char to keep track of the color of the Pawn who is vulnerable to an en passant. ('w' = White, 'b' = Black, 'n' = None).
	 */
	char enpassantColor = 'n';
	
	/**
	 * Constructor which creates Board object and initializes all Pieces that are necessary for the game.
	 * Constructor also properly places these pieces into the indices where they belong.
	 */
	public Board() {
		this.board = new Piece[8][8];
		
		this.board[0][0] = new Rook("bR", 'b', 'R', 0, 0, this);
		this.board[0][1] = new Knight("bN", 'b', 'N', 1, 0, this);
		this.board[0][2] = new Bishop("bB", 'b', 'B', 2, 0, this);
		this.board[0][3] = new Queen("bQ", 'b', 'Q', 3, 0, this);
		this.board[0][4] = new King("bK", 'b', 'K', 4, 0, this);
		this.board[0][5] = new Bishop("bB", 'b', 'B', 5, 0, this);
		this.board[0][6] = new Knight("bN", 'b', 'N', 6, 0, this);
		this.board[0][7] = new Rook("bR", 'b', 'R', 7, 0, this);
		for(int i = 0; i < 8; i++) {
			this.board[1][i] = new Pawn("bp", 'b', 'p', i, 1, this);
		}
		
		for(int i = 0; i < 8; i++) {
			this.board[6][i] = new Pawn("wp", 'w', 'p', i, 6, this);
		}
		this.board[7][0] = new Rook("wR", 'w', 'R', 0, 7, this);
		this.board[7][1] = new Knight("wN", 'w', 'N', 1, 7, this);
		this.board[7][2] = new Bishop("wB", 'w', 'B', 2, 7, this);
		this.board[7][3] = new Queen("wQ", 'w', 'Q', 3, 7, this);
		this.board[7][4] = new King("wK", 'w', 'K', 4, 7, this);
		this.board[7][5] = new Bishop("wB", 'w', 'B', 5, 7, this);
		this.board[7][6] = new Knight("wN", 'w', 'N', 6, 7, this);
		this.board[7][7] = new Rook("wR", 'w', 'R', 7, 7, this);
	}
	
	/**
	 * Returns the Piece at the given row and column.
	 * @param row	Row index of the Piece to be returned.
	 * @param col	Column index of the Piece to be returned.
	 * @return	Piece object at the [row][column] of 'board'.
	 */
	public Piece getPiece(int row, int col) {
		return this.board[row][col];
	}
	
	/**
	 * Sets piece to indicated row and column.
	 * @param row	Row index where Piece is to be inserted.
	 * @param col	Column index where Piece is to be inserted.
	 * @param piece	Piece object to be inserted.
	 */
	public void setPiece(int row, int col, Piece piece) {
		this.board[row][col] = piece;
	}
	
	/**
	 * Returns the Piece[][] board of the Board object.
	 * @return	board.
	 */
	public Piece[][] getBoard(){
		return board;
	}
	
	/**
	 * Returns the current location of a Pawn that can get en passant.
	 * @return	Location of Pawn that can get en passant.
	 */
	public int[] getEnpassant() {
		return enpassant;
	}
	
	/**
	 * Returns the color of the Pawn that can get en passant.
	 * @return	'w' if Pawn is white, 'b' if Pawn is black, 'n' if there isn't a Pawn
	 */
	public char getEnpassantColor() {
		return enpassantColor;
	}
	
	/**
	 * Changes the location of the en passant.
	 * @param row	New row index of the en passant Pawn.
	 * @param col	New column index of the en passant Pawn.
	 */
	public void setEnpassant(int row, int col) {
		enpassant[0] = row;
		enpassant[1] = col;
	}
	
	/**
	 * Changes the color of the en passant.
	 * @param color	Color of the Pawn at en passant.
	 */
	public void setEnpassantColor(char color) {
		enpassantColor = color;
	}
	
	/**
	 * Gets the coordinates of the given King.
	 * @param color	Color of the King.
	 * @return	The coordinates of the King.
	 */
	public int[] getKing(char color) {
		if(color == 'w') {
			return wKing;
		}
		else {
			return bKing;
		}
	}
	
	/**
	 * Changes the location of the given King.
	 * @param color	Color of the King who's location needs to be updated.
	 * @param row	New row index of the King.
	 * @param col	New column index of the King.
	 */
	public void setKing(char color, int row, int col) {
		int[] king;
		if(color == 'w') {
			king = wKing;
		}
		else {
			king = bKing;
		}
		king[0] = row;
		king[1] = col;
	}
	
	/**
	 * Handles each player's turn.
	 * This method also checks for bad input, draws, resigns, resets en passant, prints the winner of the game, and calls promotion for Pawns when necessary.
	 * @param color			Color of the player who's turn it is ('w' or 'b').
	 * @param colorFullName	Full name of the color ('White' or 'Black').
	 * @param opponent		Full name of the color of the opponent ('White' or 'Black').
	 * @param scan			Scanner object that takes in input from user.
	 * @return				True if the game is still continuing, False if the game ended (either through draw, resign, checkmate, etc.)
	 */
	public boolean makeMove(char color, String colorFullName, String opponent, Scanner scan) {
		boolean valid = false; // Boolean to check if input is in correct format and a valid move
		
		System.out.println(toString()); // Print board
		while(!valid) {
			valid = true;
			System.out.print("\n" + colorFullName + "'s move: ");
			String input = scan.nextLine();
			
			// Check if user resigned
			if(input.equals("resign")) {
				System.out.println("\n" + opponent + " wins");
				return false;
			}
			
			// Check if draw request has been made and if user accepts
			if(draw && input.equals("draw")) {
				System.out.println("\ndraw");
				return false;
			}
			else {
				draw = false;
			}
			
			String[] move = input.split(" "); // Split input into array of 2 to 4 elements using " " as delimiter
			if(move.length > 4) {
				System.out.println("\nIllegal move, try again");
				valid = false;
				continue;
			}
			// check if inputs are valid
			if(move.length < 2 || 
					(move[0].length() != 2 || !Character.isLetter(move[0].charAt(0)) || !Character.isDigit(move[0].charAt(1))) ||
					(move[1].length() != 2 || !Character.isLetter(move[1].charAt(0)) || !Character.isDigit(move[1].charAt(1)))) {
				System.out.println("\nIllegal move, try again");
				valid = false;
				continue;
			}
			// Check if there is a piece at first FileRank and if it belongs to user
			// If so, check if the move is valid
			int file = move[0].charAt(0) - 97;
			int rank = 8 - Character.getNumericValue(move[0].charAt(1));
			int newFile = move[1].charAt(0) - 97;
			int newRank = 8 - Character.getNumericValue(move[1].charAt(1));
			
			// Check if player input promotion
			if(move.length > 2 && !move[2].equals("draw?")) {
				if(!move[2].equals("Q") && !move[2].equals("R") && !move[2].equals("N") && !move[2].equals("B")) {
					System.out.println("\nIllegal move, try again");
					valid = false;
					continue;
				}
				else if(board[rank][file] != null && !(board[rank][file] instanceof Pawn)) {
					System.out.println("\nIllegal move, try again");
					valid = false;
					continue;
				}
				else if(board[rank][file] != null && board[rank][file] instanceof Pawn && board[rank][file].getColor() == 'w') {
					if(newRank != 0) {
						System.out.println("\nIllegal move, try again");
						valid = false;
						continue;
					}
				}
				else if(board[rank][file] != null && board[rank][file] instanceof Pawn && board[rank][file].getColor() == 'b') {
					if(newRank != 7) {
						System.out.println("\nIllegal move, try again");
						valid = false;
						continue;
					}
				}
			}
			
			if(file >= 0 && file < 8 && rank >= 0 && rank < 8 && board[rank][file] != null && board[rank][file].getColor() == color && board[rank][file].isValid(newRank, newFile)) {
				// move the piece
				int moveResult = board[rank][file].movePiece(newRank, newFile);
				if(moveResult == 2) {
					System.out.println("\nCheckmate");
					System.out.println("\n" + colorFullName + " wins");
					return false;
				}
				else if(moveResult == 1){
					System.out.println("\nCheck");
				}
				
				// if move was valid, the piece moved was a pawn, and the pawn reached the other side of the board, promote it.
				if(((board[newRank][newFile].getColor() == 'w' && newRank == 0) || (board[newRank][newFile].getColor() == 'b' && newRank == 7)) && board[newRank][newFile] instanceof Pawn) {
					if(move.length > 2 && move[2].length() == 1) {
						promote(newFile, newRank, move[2].charAt(0), board[newRank][newFile].getColor());
					}
					else {
						promote(newFile, newRank, 'Q', board[newRank][newFile].getColor());
					}
					char oppColor = color == 'w' ? 'b' : 'w';
					if(!check(oppColor)) {
						if(checkmate(oppColor)) {
							System.out.println("\nCheckmate");
							System.out.println("\n" + colorFullName + " wins");
							return false;
						}
						System.out.println("\nCheck");
					}
				}
				if(board[newRank][newFile] != null && (board[newRank][newFile].getColor() != enpassantColor || newRank != enpassant[0] || newFile != enpassant[1])) {
					enpassant[0] = -2;
					enpassant[1] = -2;
					enpassantColor = 'n';
				}
				
			}
			else {
				System.out.println("\nIllegal move, try again");
				valid = false;
				continue;
			}
			// Check if user requested a draw
			if(move.length == 3) {
				if(move[2].equals("draw?")) {
					draw = true;
				}
			}
			if(move.length == 4) {
				if(move[3].equals("draw?")) {
					draw = true;
				}
			}
			System.out.println();
		}
		
		return true;
	}
	
	/**
	 * This method will promote the Pawn, at the given location, into a new Piece.
	 * @param file	Column index of the Pawn to be promoted.
	 * @param rank	Row index of the Pawn to be promoted.
	 * @param type	The new type that the Pawn should be promoted to.
	 * @param color	The color of the Pawn that is to be promoted.
	 */
	public void promote(int file, int rank, char type, char color) {
		if(type == 'N') {
			board[rank][file] = new Knight(color + "" + type, color, type, file, rank, this);
		}
		else if(type == 'R') {
			board[rank][file] = new Rook(color + "" + type, color, type, file, rank, this);
		}
		else if(type == 'B') {
			board[rank][file] = new Bishop(color + "" + type, color, type, file, rank, this);
		}
		else {
			board[rank][file] = new Queen(color + "" + type, color, type, file, rank, this);
		}
	}
	
	/**
	 * This method checks the King of the given color.
	 * This method checks the King's horizontal and vertical for Rooks and Queens, diagonals for Bishops and Queens, spots for Pawns, spots for Knights, and it's immediate surrounding for opposing King. 
	 * @param color	Color of the King that is being checked.
	 * @return	True if the King is not in check, False if King is in check.
	 */
	public boolean check(char color) {
		int[] king;
		if(color == 'w') {
			king = wKing;
		}
		else {
			king = bKing;
		}
		// Check for rooks or queens in horizontal and vertical
		for(int i = king[0]+1; i < 8; i++) {
			if(board[i][king[1]] != null) {
				if(board[i][king[1]].getColor() == color) {
					break;
				}
				else {
					if(board[i][king[1]].getType() == 'R' || board[i][king[1]].getType() == 'Q') {
						return false;
					}
					else {
						break;
					}
				}
			}
		}
		for(int i = king[0]-1; i >= 0; i--) {
			if(board[i][king[1]] != null) {
				if(board[i][king[1]].getColor() == color) {
					break;
				}
				else {
					if(board[i][king[1]].getType() == 'R' || board[i][king[1]].getType() == 'Q') {
						return false;
					}
					else {
						break;
					}
				}
			}
		}
		for(int i = king[1]+1; i < 8; i++) {
			if(board[king[0]][i] != null) {
				if(board[king[0]][i].getColor() == color) {
					break;
				}
				else {
					if(board[king[0]][i].getType() == 'R' || board[king[0]][i].getType() == 'Q') {
						return false;
					}
					else {
						break;
					}
				}
			}
		}
		for(int i = king[1]-1; i >= 0; i--) {
			if(board[king[0]][i] != null) {
				if(board[king[0]][i].getColor() == color) {
					break;
				}
				else {
					if(board[king[0]][i].getType() == 'R' || board[king[0]][i].getType() == 'Q') {
						return false;
					}
					else {
						break;
					}
				}
			}
		}
		
		// check for bishops or queens in diagonals
		for(int i = 1; king[0] + i < 8 && king[1] + i < 8; i++) {
			if(board[king[0]+i][king[1]+i] != null) {
				if(board[king[0]+i][king[1]+i].getColor() == color) {
					break;
				}
				else {
					if(board[king[0]+i][king[1]+i].getType() == 'B' || board[king[0]+i][king[1]+i].getType() == 'Q') {
						return false;
					}
					else {
						break;
					}
				}
			}
		}
		for(int i = 1; king[0] + i < 8 && king[1] - i >= 0; i++) {
			if(board[king[0]+i][king[1]-i] != null) {
				if(board[king[0]+i][king[1]-i].getColor() == color) {
					break;
				}
				else {
					if(board[king[0]+i][king[1]-i].getType() == 'B' || board[king[0]+i][king[1]-i].getType() == 'Q') {
						return false;
					}
					else {
						break;
					}
				}
			}
		}
		for(int i = 1; king[0] - i >= 0 && king[1] + i < 8; i++) {
			if(board[king[0]-i][king[1]+i] != null) {
				if(board[king[0]-i][king[1]+i].getColor() == color) {
					break;
				}
				else {
					if(board[king[0]-i][king[1]+i].getType() == 'B' || board[king[0]-i][king[1]+i].getType() == 'Q') {
						return false;
					}
					else {
						break;
					}
				}
			}
		}
		for(int i = 1; king[0] - i >= 0 && king[1] - i >= 0; i++) {
			if(board[king[0]-i][king[1]-i] != null) {
				if(board[king[0]-i][king[1]-i].getColor() == color) {
					break;
				}
				else {
					if(board[king[0]-i][king[1]-i].getType() == 'B' || board[king[0]-i][king[1]-i].getType() == 'Q') {
						return false;
					}
					else {
						break;
					}
				}
			}
		}
		
		// check for knights
		if(king[0] + 2 < 8) {
			if(king[1] + 1 < 8) {
				if(board[king[0]+2][king[1]+1] != null && board[king[0]+2][king[1]+1].getType() == 'N' && board[king[0]+2][king[1]+1].getColor() != color) {
					return false;
				}
			}
			if(king[1] - 1 >= 0) {
				if(board[king[0]+2][king[1]-1] != null && board[king[0]+2][king[1]-1].getType() == 'N' && board[king[0]+2][king[1]-1].getColor() != color) {
					return false;
				}
			}
		}
		if(king[0] - 2 >= 0) {
			if(king[1] + 1 < 8) {
				if(board[king[0]-2][king[1]+1] != null && board[king[0]-2][king[1]+1].getType() == 'N' && board[king[0]-2][king[1]+1].getColor() != color) {
					return false;
				}
			}
			if(king[1] - 1 >= 0) {
				if(board[king[0]-2][king[1]-1] != null && board[king[0]-2][king[1]-1].getType() == 'N' && board[king[0]-2][king[1]-1].getColor() != color) {
					return false;
				}
			}
		}
		if(king[1] + 2 < 8) {
			if(king[0] + 1 < 8) {
				if(board[king[0]+1][king[1]+2] != null && board[king[0]+1][king[1]+2].getType() == 'N' && board[king[0]+1][king[1]+2].getColor() != color) {
					return false;
				}
			}
			if(king[0] - 1 >= 0) {
				if(board[king[0]-1][king[1]+2] != null && board[king[0]-1][king[1]+2].getType() == 'N' && board[king[0]-1][king[1]+2].getColor() != color) {
					return false;
				}
			}
		}
		if(king[1] - 2 >= 0) {
			if(king[0] + 1 < 8) {
				if(board[king[0]+1][king[1]-2] != null && board[king[0]+1][king[1]-2].getType() == 'N' && board[king[0]+1][king[1]-2].getColor() != color) {
					return false;
				}
			}
			if(king[0] - 1 >= 0) {
				if(board[king[0]-1][king[1]-2] != null && board[king[0]-1][king[1]-2].getType() == 'N' && board[king[0]-1][king[1]-2].getColor() != color) {
					return false;
				}
			}
		}
		
		// check for pawns
		if(color == 'b') {
			if(king[0] + 1 < 8) {
				if(king[1] - 1 >= 0) {
					if(board[king[0]+1][king[1]-1] != null && board[king[0]+1][king[1]-1].getType() == 'p' && board[king[0]+1][king[1]-1].getColor() != color) {
						return false;
					}
				}
				if(king[1] + 1 < 8) {
					if(board[king[0]+1][king[1]+1] != null && board[king[0]+1][king[1]+1].getType() == 'p' && board[king[0]+1][king[1]+1].getColor() != color) {
						return false;
					}
				}
			}
		}
		else {
			if(king[0] - 1 >= 0) {
				if(king[1] - 1 >= 0) {
					if(board[king[0]-1][king[1]-1] != null && board[king[0]-1][king[1]-1].getType() == 'p' && board[king[0]-1][king[1]-1].getColor() != color) {
						return false;
					}
				}
				if(king[1] + 1 < 8) {
					if(board[king[0]-1][king[1]+1] != null && board[king[0]-1][king[1]+1].getType() == 'p' && board[king[0]-1][king[1]+1].getColor() != color) {
						return false;
					}
				}
			}
		}
		
		// check for other king
		int[] oppKing = color == 'w' ? bKing : wKing;
		if(Math.abs(king[0] - oppKing[0]) <= 1 && Math.abs(king[1] - oppKing[1]) <= 1) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks to see if there are any possible ways for the King to escape check.
	 * @param color	Color of the King that is being checked.
	 * @return	True if King is in checkmate, False if King is not in checkmate
	 */
	public boolean checkmate(char color) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null && board[i][j].getColor() == color) {
					// Check all Pawns to see if any can protect the King
					if(board[i][j] instanceof Pawn) {
						if(color == 'w') {
							if(board[i][j].isValid(i-1, j) || board[i][j].isValid(i-1, j-1) || board[i][j].isValid(i-1, j+1) || board[i][j].isValid(i-2, j)) {
								return false;
							}
						}
						else {
							if(board[i][j].isValid(i+1, j) || board[i][j].isValid(i+1, j-1) || board[i][j].isValid(i+1, j+1) || board[i][j].isValid(i+2, j)) {
								return false;
							}
						}
					}
					// Check if any Rooks can protect the King
					if(board[i][j] instanceof Rook) {
						for(int step = 0; step < 8; step++) {
							if(step != i && board[i][j].isValid(step, j)) {
								return false;
							}
							if(step != j && board[i][j].isValid(i, step)) {
								return false;
							}
						}
					}
					// Check if any Bishops can protect the King
					if(board[i][j] instanceof Bishop) {
						for(int step = 1; i + step < 8 && j + step < 8; step++) {
							if(board[i][j].isValid(i+step, j+step)) {
								return false;
							}
						}
						for(int step = 1; i - step >= 0 && j + step < 8; step++) {
							if(board[i][j].isValid(i-step, j+step)) {
								return false;
							}
						}
						for(int step = 1; i + step < 8 && j - step >= 0; step++) {
							if(board[i][j].isValid(i+step, j-step)) {
								return false;
							}
						}
						for(int step = 1; i - step >= 0 && j - step >= 0; step++) {
							if(board[i][j].isValid(i-step, j-step)) {
								return false;
							}
						}
					}
					// Check if Queen can protect the King
					if(board[i][j] instanceof Queen) {
						for(int step = 0; step < 8; step++) {
							if(step != i && board[i][j].isValid(step, j)) {
								return false;
							}
							if(step != j && board[i][j].isValid(i, step)) {
								return false;
							}
						}
						for(int step = 1; i + step < 8 && j + step < 8; step++) {
							if(board[i][j].isValid(i+step, j+step)) {
								return false;
							}
						}
						for(int step = 1; i - step >= 0 && j + step < 8; step++) {
							if(board[i][j].isValid(i-step, j+step)) {
								return false;
							}
						}
						for(int step = 1; i + step < 8 && j - step >= 0; step++) {
							if(board[i][j].isValid(i+step, j-step)) {
								return false;
							}
						}
						for(int step = 1; i - step >= 0 && j - step >= 0; step++) {
							if(board[i][j].isValid(i-step, j-step)) {
								return false;
							}
						}
					}
					// Check if Knights can protect the King
					if(board[i][j] instanceof Knight) {
						if(board[i][j].isValid(i+2, j-1)) {
							return false;
						}
						if(board[i][j].isValid(i+2, j+1)) {
							return false;
						}
						if(board[i][j].isValid(i-2, j-1)) {
							return false;
						}
						if(board[i][j].isValid(i-2, j+1)) {
							return false;
						}
						if(board[i][j].isValid(i-1, j+2)) {
							return false;
						}
						if(board[i][j].isValid(i+1, j+2)) {
							return false;
						}
						if(board[i][j].isValid(i-1, j-2)) {
							return false;
						}
						if(board[i][j].isValid(i+1, j-2)) {
							return false;
						}
					}
					// Check if King can escape to any spot
					if(board[i][j] instanceof King) {
						if(board[i][j].isValid(i-1, j-1)) {
							return false;
						}
						if(board[i][j].isValid(i-1, j)) {
							return false;
						}
						if(board[i][j].isValid(i-1, j+1)) {
							return false;
						}
						if(board[i][j].isValid(i, j-1)) {
							return false;
						}
						if(board[i][j].isValid(i, j+1)) {
							return false;
						}
						if(board[i][j].isValid(i+1, j-1)) {
							return false;
						}
						if(board[i][j].isValid(i+1, j)) {
							return false;
						}
						if(board[i][j].isValid(i+1, j+1)) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Prints the Chess Board.
	 * @return	The Chess Board in String format.
	 */
	public String toString() {
		String printedBoard = "";
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null) {
					printedBoard += board[i][j].getID() + " ";
				}
				else {
					if((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
						printedBoard += "## ";
					}
					else {
						printedBoard += "   ";
					}
				}
			}
			printedBoard += (8-i) + "\n";
		}
		printedBoard += " a  b  c  d  e  f  g  h";
		
		return printedBoard;
	}
	
}
