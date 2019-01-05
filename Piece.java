package Tetris;

import java.util.Arrays;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This class models a piece. It also has methods that control the piece's
 * movements. This class also has private helper methods that create each new
 * piece and place it at the top of the screen.
 */
public class Piece {
	private TetrisSquare[] _piece;
	private Pane _pane;
	private boolean _canRotate;

	/**
	 * Constructor for Piece. Takes in a parameter of type pane to associate this
	 * class with Game, and so that the pieces can be added to the tetris pane. A 1D
	 * array of 4 tetris squares is created.
	 */
	Piece(Pane pane) {
		_pane = pane;
		_piece = new TetrisSquare[Constants.NUM_SQUARES];
		_canRotate = true;
	}

	/**
	 * This method creates, places, and sets the color of the first piece type.
	 */
	public void makePieceOne() {
		this.makeNewPiece(Color.LAVENDER, true);
		this.placePiece(Constants.PIECE_ONE_COORDS);
	}

	/**
	 * This method creates, places, and sets the color of the second piece type.
	 */
	public void makePieceTwo() {
		this.makeNewPiece(Color.PURPLE, false);
		this.placePiece(Constants.PIECE_TWO_COORDS);
	}

	/**
	 * This method creates, places, and sets the color of the third piece type.
	 */
	public void makePieceThree() {
		this.makeNewPiece(Color.MEDIUMPURPLE, true);
		this.placePiece(Constants.PIECE_THREE_COORDS);
	}

	/**
	 * This method creates, places, and sets the color of the fourth piece type.
	 */
	public void makePieceFour() {
		this.makeNewPiece(Color.PLUM, true);
		this.placePiece(Constants.PIECE_FOUR_COORDS);
	}

	/**
	 * This method creates, places, and sets the color of the fifth piece type.
	 */
	public void makePieceFive() {
		this.makeNewPiece(Color.DARKORCHID, true);
		this.placePiece(Constants.PIECE_FIVE_COORDS);
	}

	/**
	 * This method creates, places, and sets the color of the sixth piece type.
	 */
	public void makePieceSix() {
		this.makeNewPiece(Color.BLUEVIOLET, true);
		this.placePiece(Constants.PIECE_SIX_COORDS);
	}

	/**
	 * This method creates, places, and sets the color of the seventh piece type.
	 */
	public void makePieceSeven() {
		this.makeNewPiece(Color.MEDIUMORCHID, true);
		this.placePiece(Constants.PIECE_SEVEN_COORDS);
	}

	/**
	 * This method places any piece at the top of the board according to each
	 * piece's unique 2D array of coordinates, which it takes in as an argument.
	 */
	private void placePiece(int[][] coordinates) {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			_piece[i].getTetrisSquare().setX(coordinates[i][0]);
		}
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			_piece[i].getTetrisSquare().setY(coordinates[i][1]);
		}
	}

	/**
	 * Makes a new piece by making 4 new TetrisSquares and filling the 1D array that
	 * represents a piece with them. This method takes in parameters of type color
	 * and boolean so each time a piece is made, a unique color can be set as well
	 * as if it can or can't rotate. The squares are also graphically added.
	 */
	private void makeNewPiece(Color color, boolean canRotate) {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			TetrisSquare square = new TetrisSquare();
			_piece[i] = square;
		}
		for (TetrisSquare square : _piece) {
			square.getTetrisSquare().setFill(color);
		}
		_canRotate = canRotate;
		_pane.getChildren().addAll(Arrays.asList(_piece[0].getTetrisSquare(), _piece[1].getTetrisSquare(),
				_piece[2].getTetrisSquare(), _piece[3].getTetrisSquare()));

	}

	/**
	 * Accessor method for the rotation boolean so that the KeyHandler in game knows
	 * whether to allow rotation.
	 */
	public boolean canRotate() {
		return _canRotate;
	}

	/**
	 * Moves the entire piece right by one column.
	 */
	public void moveRight() {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			_piece[i].getTetrisSquare().setX(_piece[i].getTetrisSquare().getX() + Constants.SQUARE_SIZE);
		}
	}

	/**
	 * Moves the entire piece to the left by one column.
	 */
	public void moveLeft() {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			_piece[i].getTetrisSquare().setX(_piece[i].getTetrisSquare().getX() - Constants.SQUARE_SIZE);
		}
	}

	/**
	 * Moves the entire piece down a row.
	 */
	public void moveDown() {
		for (TetrisSquare square : _piece) {
			square.getTetrisSquare().setY(square.getTetrisSquare().getY() + Constants.SQUARE_SIZE);
		}
	}

	/**
	 * Rotates the piece counter clockwise about a center of rotation which is the X
	 * and Y values of the second block of the piece.
	 */
	public void rotate() {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			int newX = (int) (_piece[1].getTetrisSquare().getX() - _piece[1].getTetrisSquare().getY()
					+ _piece[i].getTetrisSquare().getY());
			int newY = (int) (_piece[1].getTetrisSquare().getY() + _piece[1].getTetrisSquare().getX()
					- _piece[i].getTetrisSquare().getX());
			_piece[i].getTetrisSquare().setX(newX);
			_piece[i].getTetrisSquare().setY(newY);
		}
	}

	/**
	 * Accessor method for the piece array so that it can be referenced in Game.
	 */
	public TetrisSquare[] getPieceArray() {
		return _piece;
	}

}
