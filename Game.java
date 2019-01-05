package Tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * This is the Game class. It models all the Tetris Game logic. It handles the
 * animation of the pieces moving along the board via a TimeHandler. It creates
 * and controls the game board, as well as keeps track of where pieces have
 * landed. This class also is responsible for checking move validity, line
 * clearing, and moving all squares down after a line has been cleared. This
 * class controls all key input as well, which includes
 * left/right/down/drop/rotate functions, as well as pausing. For extra credit,
 * I added a Restart Button and a Score Keeping Label. You can restart the game
 * at any time with the button. The score label displays an increased score each
 * time a line is successfully cleared.
 */
public class Game {
	private Pane _tetrisPane;
	private TetrisSquare[][] _board;
	private Piece _piece;
	private boolean _isEmptyRight;
	private boolean _isEmptyLeft;
	private boolean _isEmptyDown;
	private boolean _isEmptyRotate;
	private Timeline _timeline;
	private Label _pauseLabel;
	private Label _scoreLabel;
	private Label _gameOver;
	private int _score;
	private HBox _hbox;

	/**
	 * Constructor for the Game class. Takes in parameter of type HBox, so that it
	 * can add the Score Label and Restart Button to it (Extra Credit) The board, a
	 * 2D array of TetrisSquares, is instantiated, as well as _piece, a 1D array of
	 * TetrisSquares that models a single piece. Various private helper methods are
	 * called to set up the timeline, set up the board, the restart button, the
	 * score label, and most importantly, the method that generates the first random
	 * piece at the top of the screen is called, thus beginning the game.
	 */
	public Game(HBox hbox) {
		_tetrisPane = new Pane();
		_piece = new Piece(_tetrisPane);
		_pauseLabel = new Label("Paused");
		_board = new TetrisSquare[Constants.NUM_ROWS][Constants.NUM_COLS];
		_scoreLabel = new Label();
		_gameOver = new Label();
		KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION), new TimeHandler());
		_timeline = new Timeline(kf);
		_score = 0;
		_hbox = hbox;
		_isEmptyRight = false;
		_isEmptyLeft = false;
		_isEmptyDown = false;
		_isEmptyRotate = false;
		this.setUpTetrisPane();
		this.setUpBoard();
		this.makeRandomPiece();
		this.setUpTimeline();
		this.setUpScoreLabel();
		this.setUpRestartButton();
	}

	/**
	 * Accessor method for the _tetrisPane, so that it can be added to the root pane
	 * in PaneOrganizer.
	 */
	public Pane getGamePane() {
		return _tetrisPane;
	}

	/**
	 * private helper method to set up the _tetrisPane. This method associates the
	 * pane with its KeyHandler, and sets the focus on _tetrisPane.
	 */
	private void setUpTetrisPane() {
		_tetrisPane.setOnKeyPressed(new KeyHandler());
		_tetrisPane.requestFocus();
		_tetrisPane.setFocusTraversable(true);
	}

	/**
	 * This private helper method sets up the game board, which is a 2D array of
	 * TetrisSquares. In this setup, a border is added, filling the outer 2
	 * rows/columns withTetrisSquares, thus creating the border. These TetrisSquares
	 * are also added graphically.
	 */
	private void setUpBoard() {
		for (int row = 0; row < Constants.NUM_ROWS; row++) {
			for (int col = 0; col < Constants.NUM_COLS; col++) {
				/*
				 * Graphical placement of the TetrisSquares according to their logical locations
				 * in the array.
				 */
				int xLoc = col * Constants.SQUARE_SIZE;
				int yLoc = row * Constants.SQUARE_SIZE;
				if (row < Constants.BOARD_LOW_CUTOFF || row >= Constants.ENDING_ROW || col < Constants.BOARD_LOW_CUTOFF
						|| col >= Constants.ENDING_COL) {
					TetrisSquare borderSquare = new TetrisSquare();
					borderSquare.getTetrisSquare().setFill(Color.DARKSLATEBLUE);
					_board[row][col] = borderSquare; // logically adding the border squares.
					borderSquare.getTetrisSquare().setX(xLoc);
					borderSquare.getTetrisSquare().setY(yLoc);
					_tetrisPane.getChildren().addAll(_board[row][col].getTetrisSquare()); 															
				}
			}
		}
	}

	/**
	 * This method updates the boolean that represents if the piece can legally move
	 * down (meaning that this boolean only returns true if all 4 squares in the
	 * piece will not intersect the border/other pieces). This is checked by
	 * evaluating if the places that the piece would move down into are null or not
	 * (in the _board array).
	 */
	private void checkDown() {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			int colChecker = (int) (_piece.getPieceArray()[i].getTetrisSquare().getX() / Constants.SQUARE_SIZE);
			int rowChecker = (int) (_piece.getPieceArray()[i].getTetrisSquare().getY() / Constants.SQUARE_SIZE);
			if (_board[rowChecker + 1][colChecker] == null) {
				_isEmptyDown = true;
			} else if (_board[rowChecker + 1][colChecker] != null) {
				_isEmptyDown = false;
				break;
			}
		}
	}

	/**
	 * This method updates the boolean that represents if the piece can legally move
	 * right. This is checked by evaluating if the places that the piece would move
	 * down into are null or not (in the _board array).
	 */
	private void checkRight() {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			int colChecker = (int) (_piece.getPieceArray()[i].getTetrisSquare().getX() / Constants.SQUARE_SIZE);
			int rowChecker = (int) (_piece.getPieceArray()[i].getTetrisSquare().getY() / Constants.SQUARE_SIZE);
			if (_board[rowChecker][colChecker + 1] == null) {
				_isEmptyRight = true;
			} else if (_board[rowChecker][colChecker + 1] != null) {
				_isEmptyRight = false;
				break;
			}
		}
	}

	/**
	 * This method updates the boolean that represents if the piece can legally move
	 * left. This is checked by evaluating if the places that the piece would move
	 * down into are null or not (in the _board array).
	 */
	private void checkLeft() {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			int colChecker = (int) (_piece.getPieceArray()[i].getTetrisSquare().getX() / Constants.SQUARE_SIZE);
			int rowChecker = (int) (_piece.getPieceArray()[i].getTetrisSquare().getY() / Constants.SQUARE_SIZE);
			if (_board[rowChecker][colChecker - 1] == null) {
				_isEmptyLeft = true;
			} else if (_board[rowChecker][colChecker - 1] != null) {
				_isEmptyLeft = false;
				break;
			}
		}
	}

	/**
	 * This method updates the boolean that represents if the piece can legally
	 * rotate. This is checked by evaluating if the places that the piece would
	 * rotate into are null or not (in the _board array). If one or more of the 4
	 * squares in the piece array will rotate into a section of the board array that
	 * is not null, this boolean returns false.
	 */
	private void checkRotate() {
		for (int i = 0; i < Constants.NUM_SQUARES; i++) {
			double axisOfRotX = _piece.getPieceArray()[1].getTetrisSquare().getX();
			double axisOfRotY = _piece.getPieceArray()[1].getTetrisSquare().getY();
			int colChecker = (int) ((axisOfRotX - axisOfRotY + _piece.getPieceArray()[i].getTetrisSquare().getY())
					/ Constants.SQUARE_SIZE);
			int rowChecker = (int) ((axisOfRotY + axisOfRotX - _piece.getPieceArray()[i].getTetrisSquare().getX())
					/ Constants.SQUARE_SIZE);
			if (_board[rowChecker][colChecker] == null) {
				_isEmptyRotate = true;
			} else if (_board[rowChecker][colChecker] != null) {
				_isEmptyRotate = false;
				break;
			}
		}
	}

	/**
	 * This method generates a random piece out of seven total options. A piece is
	 * only created if all 4 spaces that it would occupy are null in the board
	 * array. (This is checking move validity prior to piece creation). Each piece
	 * checks if it will intersect non null sections based on the piece's unique
	 * configuration.
	 */
	private void makeRandomPiece() {
		int rand = (int) (Math.random() * 7);
		switch (rand) {
		case 0:
			if (this.checkGameOver(Constants.PIECE_ONE_COORDS) == false) {
				_piece.makePieceOne();
			}
			break;
		case 1:
			if (this.checkGameOver(Constants.PIECE_TWO_COORDS) == false) {
				_piece.makePieceTwo();
			}
			break;
		case 2:
			if (this.checkGameOver(Constants.PIECE_THREE_COORDS) == false) {
				_piece.makePieceThree();
			}
			break;
		case 3:
			if (this.checkGameOver(Constants.PIECE_FOUR_COORDS) == false) {
				_piece.makePieceFour();
			}
			break;
		case 4:
			if (this.checkGameOver(Constants.PIECE_FIVE_COORDS) == false) {
				_piece.makePieceFive();
			}
			break;
		case 5:
			if (this.checkGameOver(Constants.PIECE_SIX_COORDS) == false) {
				_piece.makePieceSeven();
			}
			break;
		case 6:
			if (this.checkGameOver(Constants.PIECE_SEVEN_COORDS) == false) {
				_piece.makePieceOne();
			}
			break;
		default:
			if (this.checkGameOver(Constants.PIECE_ONE_COORDS) == false) {
				_piece.makePieceOne();
			}
		}
	}

	/**
	 * This method checks if a piece has landed, and if it has, it adds it to the
	 * _board array, and generates a new random piece at the top of the screen that
	 * is now the new target of manipulation. This method decides if a piece has
	 * landed or not by checking the boolean that controls move validity for
	 * downward movement. If the boolean returns false, this means that all 4
	 * squares in the piece cannot move down. Returning false means the piece is
	 * landed and can no loner be manipulated. If the newly generated random piece
	 * cannot move down at all, a game over label is displayed and the timeline is
	 * stopped.
	 */
	private void checkLanded() {
		if (_isEmptyDown == false) { // If any of the 4 squares in a piece cannot move down, the piece is landed.
			for (int i = 0; i < Constants.NUM_SQUARES; i++) {
				int col = (int) (_piece.getPieceArray()[i].getTetrisSquare().getX() / Constants.SQUARE_SIZE);
				int row = (int) (_piece.getPieceArray()[i].getTetrisSquare().getY() / Constants.SQUARE_SIZE);
				_board[row][col] = _piece.getPieceArray()[i]; // Making the piece part of the _board array.
			}
			this.makeRandomPiece();
			this.checkDown();
			if (_isEmptyDown == false) { // if the newly generated piece cannot move down at all, game is over.
				_timeline.stop();
				this.makeEndLabel();
			}
			this.clearLines(); 
		}
	}

	/**
	 * This method clears lines that are full, and then moves all above squares
	 * down. Extra credit: the score is also incremented by 10 each time there is a
	 * full line.
	 */
	private void clearLines() {
		boolean isFull = false;
		for (int row = Constants.BOARD_LOW_CUTOFF; row < Constants.ENDING_ROW; row++) {
			for (int col = 0; col < Constants.ENDING_COL; col++) {
				if (_board[row][col] == null) {
					isFull = false; // If at least one square in the row is null, row is deemed not full
					break;
				}
				if (_board[row][col] != null) {
					isFull = true;
				}
			}
			/*
			 * When a row is full, it is cleared both graphically and logically, and the
			 * score is incremented by 10.
			 */
			if (isFull == true) {
				_score = _score + 10;
				for (int col = Constants.BOARD_LOW_CUTOFF; col < Constants.ENDING_COL; col++) {
					_tetrisPane.getChildren().remove(_board[row][col].getTetrisSquare());
					_board[row][col] = null;
				}
				/*
				 * All squares above the cleared row are moved down both graphically and
				 * logically.
				 */
				for (int r = row; r > Constants.BOARD_LOW_CUTOFF; r--) {
					for (int col = Constants.BOARD_LOW_CUTOFF; col < Constants.ENDING_COL; col++) {
						if (_board[r - 1][col] != null) {
							_board[r - 1][col].getTetrisSquare()
									.setY(_board[r - 1][col].getTetrisSquare().getY() + Constants.SQUARE_SIZE);
						}
						_board[r][col] = _board[r - 1][col];
					}
				}
			}
		}
	}

	/**
	 * This method is called when the spacebar is pressed. It moves the piece down a
	 * row until it can no longer move down anymore, meaning that at least one of
	 * the 4 squares would have intersected another if it were to keep moving.
	 */
	private void drop() {
		this.checkDown();
		while (_isEmptyDown == true) {
			for (int i = 0; i < Constants.NUM_SQUARES; i++) {
				_piece.getPieceArray()[i].getTetrisSquare()
						.setY(_piece.getPieceArray()[i].getTetrisSquare().getY() + Constants.SQUARE_SIZE);
				this.checkDown();
			}
		}
	}

	/**
	 * This method checks if the game is over or not. This method is used to foresee
	 * if the piece that is about to be newly generated is going to intersect any
	 * part of the board array. This method does so by taking in the Constant that
	 * is a 2D array of the piece's starting coordinates. This method allows you to
	 * check this for each piece's unique starting positions, before it is
	 * generated.
	 */
	private boolean checkGameOver(int[][] coord) {
		boolean isGameOver = false;
		if (_board[coord[0][1] / Constants.SQUARE_SIZE][coord[0][0] / Constants.SQUARE_SIZE] == null
				&& _board[coord[1][1] / Constants.SQUARE_SIZE][coord[1][0] / Constants.SQUARE_SIZE] == null
				&& _board[coord[2][1] / Constants.SQUARE_SIZE][coord[2][0] / Constants.SQUARE_SIZE] == null
				&& _board[coord[3][1] / Constants.SQUARE_SIZE][coord[3][0] / Constants.SQUARE_SIZE] == null) {
			isGameOver = false;
		} else {
			isGameOver = true;
		}
		return isGameOver;
	}

	/**
	 * This method creates the game over label. It is called when the newly
	 * generated piece cannot move down any longer, or the piece that is about to be
	 * generated would intersect part of the -board array.
	 */
	private void makeEndLabel() {
		_gameOver.setText("Game Over!");
		this.setUpLabels(_gameOver, Constants.GAME_OVER_X);
	}

	/**
	 * This method makes the "Paused" label. The font and size are set. This method
	 * is called when "P" is pressed and the timeline is Running.
	 */
	private void makePauseLabel() {
		_pauseLabel.setFont(Font.font("Radio Stars", Constants.LABEL_SIZE));
		this.setUpLabels(_pauseLabel, Constants.PAUSE_X);
	}

	/**
	 * This helper method allows you to set up any label. A Label is passed in,
	 * along with the desired X Location of the label. The color and font are set as
	 * well. I decided to make this method because multiple of my labels use the
	 * same color and font and locations.
	 */
	private void setUpLabels(Label label, int xLoc) {
		label.setFont(Font.font("Radio Stars", Constants.LABEL_SIZE));
		label.setTextFill(Color.INDIGO);
		label.setLayoutX(xLoc);
		label.setLayoutY(Constants.LABEL_Y);
		_tetrisPane.getChildren().addAll(label);
	}

	/**
	 * Extra Credit: This method sets up the score label and adds it to the bottom
	 * section of the root pane. The Text for this label is updated in the
	 * timehandler, so that the score increases each time a line is cleared.
	 */
	private void setUpScoreLabel() {
		_hbox.getChildren().addAll(_scoreLabel);
		_scoreLabel.setFont(Font.font("Radio Stars", Constants.SCORE_SIZE));
		_scoreLabel.setTextFill(Color.WHITE);
	}

	/**
	 * This private helper method sets up the timeline. The cycle count is set and
	 * it is instructed to play.
	 */
	private void setUpTimeline() {
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}

	/**
	 * Extra Credit: This method sets up the restart button. It is added to the
	 * bottom section of the root pane. Focus is not set on this button to ensure
	 * that the keyhandler maintains focus. The button is associated with its
	 * RestartButtonHandler.
	 */
	private void setUpRestartButton() {
		Button restartButton = new Button("Restart");
		_hbox.getChildren().addAll(restartButton);
		_hbox.setFocusTraversable(false);
		restartButton.setFocusTraversable(false);
		restartButton.setOnAction(new RestartButtonHandler()); // Button associated with ButtonHandler.
	}

	/**
	 * This private inner class controls what happens when the restart button is
	 * pressed. All fallen pieces, and currently falling pieces are removed
	 * graphically and logically, and a new piece appears at the top when the button
	 * is pressed.
	 */
	private class RestartButtonHandler implements EventHandler<ActionEvent> {
		/**
		 * This handle method is automatically called when the restartButton is clicked.
		 * It first stops the timeline, and then removes pieces graphically and
		 * logically (by removing all items in the board array that are not the border),
		 * and makes a new random piece appear at the top. The pause and game over
		 * labels are also removed, if they are present at the time of clicking this
		 * button.
		 */
		@Override
		public void handle(ActionEvent event) {
			_timeline.stop();
			for (int row = 0; row < Constants.NUM_ROWS; row++) {
				for (int col = 0; col < Constants.NUM_COLS; col++) {
					if (row >= Constants.BOARD_LOW_CUTOFF && row < Constants.ENDING_ROW
							&& col >= Constants.BOARD_LOW_CUTOFF && col < Constants.ENDING_COL
							&& _board[row][col] != null) {
						_tetrisPane.getChildren().remove(_board[row][col].getTetrisSquare());
						_board[row][col] = null;
					}
				}
			}
			for (int i = 0; i < 4; i++) {
				_tetrisPane.getChildren().remove(_piece.getPieceArray()[i].getTetrisSquare());
			}
			_tetrisPane.getChildren().remove(_gameOver);
			_tetrisPane.getChildren().remove(_pauseLabel);
			Game.this.makeRandomPiece();
			_score = 0; // The score is also reset.
			_timeline.play();
		}
	}

	/**
	 * This private inner class controls the animation. The score is continuously
	 * updated. The Timeline continuously checks if a piece has landed or not. It
	 * also is responsible for the current piece moving down the board at a constant
	 * rate.
	 */
	private class TimeHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String score = Integer.toString(_score);
			_scoreLabel.setText("Score: " + score);
			Game.this.checkDown();
			Game.this.checkLanded();
			if (_isEmptyDown == true) {
				_piece.moveDown();
			}
		}
	}

	/**
	 * This private inner class controls what happens when various keys are pressed.
	 * Up rotates the piece counter clockwise, Down moves it down a row, Right moves
	 * it one unit to the right, and Left one unit to the left. Down moves it one
	 * row down. P pauses the game and displays a paused label if the timeline is
	 * running, and it resumes the game if the timeline is paused. The keys only
	 * execute valid moves. Validity is checked through booleans. Also, the keys
	 * only execute moves if the timeline is running, so that when the game is
	 * paused or over, the piece can't be controlled anymore.
	 */
	private class KeyHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent e) {
			KeyCode keyPressed = e.getCode();
			switch (keyPressed) {
			case RIGHT:
				if (_timeline.getStatus() == Animation.Status.RUNNING) {
					Game.this.checkRight();
					if (_isEmptyRight == true && _isEmptyDown == true) {
						_piece.moveRight();
					}
				}
				break;
			case LEFT:
				if (_timeline.getStatus() == Animation.Status.RUNNING) {
					Game.this.checkLeft();
					if (_isEmptyLeft == true && _isEmptyDown == true) {
						_piece.moveLeft();
					}
				}
				break;
			case DOWN:
				if (_timeline.getStatus() == Animation.Status.RUNNING) {
					Game.this.checkDown();
					if (_isEmptyDown == true) {
						_piece.moveDown();
					}
				}
				break;
			case UP:
				if (_timeline.getStatus() == Animation.Status.RUNNING && _piece.canRotate() == true) {
					Game.this.checkRotate();
					if (_isEmptyRotate == true) {
						_piece.rotate();
					}
				}
				break;
			case SPACE:
				if (_timeline.getStatus() == Animation.Status.RUNNING) {
					Game.this.drop();
				}
				break;
			case P:
				if (_timeline.getStatus() == Animation.Status.RUNNING) {
					_timeline.pause();
					Game.this.makePauseLabel();
				} else if (_timeline.getStatus() == Animation.Status.PAUSED) {
					_timeline.play();
					_tetrisPane.getChildren().remove(_pauseLabel);
				}
				break;
			}
			e.consume();
		}
	}

}
