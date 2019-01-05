package Tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This wrapper class models a TetrisSquare, which will be used to make pieces
 * and the board's border.
 */
public class TetrisSquare {
	private Rectangle _rectangle;

	/**
	 * Creates the tetris square, sets its stroke, and sets its size.
	 */
	public TetrisSquare() {
		_rectangle = new Rectangle(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
		_rectangle.setStroke(Color.WHITE);
		_rectangle.setStrokeWidth(Constants.STROKE_WIDTH);
	}

	/**
	 * Accessor method for the tetris square.
	 */
	public Rectangle getTetrisSquare() {
		return _rectangle;
	}

}
