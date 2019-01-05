package Tetris;

/**
 * This class models constants for Tetris. They include the APP dimensions, the
 * board's number of columns and rows, and the starting coordinates of each of
 * the 7 types of tetris pieces. Constants for all imported images are also in
 * this class.
 */
public class Constants {

	public static final int APP_WIDTH = 600;
	public static final int APP_HEIGHT = 1046;

	/*
	 * Constants for the piece array.
	 */
	public static final int SQUARE_SIZE = 30;
	public static final int NUM_SQUARES = 4;
	public static final double STROKE_WIDTH = 2.2;

	/*
	 * Constants for the board 2D array.
	 */
	public static final int NUM_ROWS = 34;
	public static final int NUM_COLS = 20;
	public static final int BOARD_LOW_CUTOFF = 2;
	public static final int ENDING_ROW = 32;
	public static final int ENDING_COL = 18;

	public static final double DURATION = 0.5;
	public static final int HBOX_SPACING = 14;

	/*
	 * 2D arrays of coordinates modeling the starting positions of each tetris
	 * piece.
	 */
	public static final int[][] PIECE_ONE_COORDS = { { 300, 60 }, { 300, 60 + Constants.SQUARE_SIZE },
			{ 300, 60 + (2 * Constants.SQUARE_SIZE) }, { 300, 60 + (3 * Constants.SQUARE_SIZE) } };
	public static final int[][] PIECE_TWO_COORDS = { { 300, 60 }, { 300 + Constants.SQUARE_SIZE, 60 },
			{ 300, 60 + Constants.SQUARE_SIZE }, { 300 + Constants.SQUARE_SIZE, 60 + Constants.SQUARE_SIZE } };
	public static final int[][] PIECE_THREE_COORDS = { { 300, 60 }, { 300, 60 + Constants.SQUARE_SIZE },
			{ 300, 60 + (2 * Constants.SQUARE_SIZE) }, { 300 + Constants.SQUARE_SIZE, 60 + Constants.SQUARE_SIZE } };
	public static final int[][] PIECE_FOUR_COORDS = { { 300, 60 }, { 300, 60 + Constants.SQUARE_SIZE },
			{ 300, 60 + (2 * Constants.SQUARE_SIZE) }, { 300 + Constants.SQUARE_SIZE, 60 } };
	public static final int[][] PIECE_FIVE_COORDS = { { 300, 60 }, { 300, 60 + Constants.SQUARE_SIZE },
			{ 300, 60 + (2 * Constants.SQUARE_SIZE) }, { 300 - Constants.SQUARE_SIZE, 60 } };
	public static final int[][] PIECE_SIX_COORDS = { { 300, 60 }, { 300, 60 + Constants.SQUARE_SIZE },
			{ 300 + Constants.SQUARE_SIZE, 60 + Constants.SQUARE_SIZE },
			{ 300 + Constants.SQUARE_SIZE, 60 + (2 * Constants.SQUARE_SIZE) } };
	public static final int[][] PIECE_SEVEN_COORDS = { { 300, 60 }, { 300, 60 + Constants.SQUARE_SIZE },
			{ 300 - Constants.SQUARE_SIZE, 60 + Constants.SQUARE_SIZE },
			{ 300 - Constants.SQUARE_SIZE, 60 + (2 * Constants.SQUARE_SIZE) } };

	/*
	 * Constants for the Image of the tetris logo.
	 */
	public static final int LOGO_WIDTH = 157;
	public static final int LOGO_X = 222;
	public static final int LOGO_Y = 0;

	/*
	 * Constants for the two images of hearts.
	 */
	public static final int HEART_WIDTH = 87;
	public static final int HEART_Y = 0;
	public static final int HEART_X_ONE = 135;
	public static final int HEART_X_TWO = 379;

	/*
	 * Constants for the labels.
	 */
	public static final int GAME_OVER_X = 150;
	public static final int PAUSE_X = 210;
	public static final int SCORE_SIZE = 24;
	public static final int LABEL_SIZE = 48;
	public static final int LABEL_Y = 400;

}
