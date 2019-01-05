package Tetris;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * PaneOrganizer is responsible for organizing the panes. In this case, they are
 * a main game pane and an HBox at the bottom that contains the buttons and
 * score label. Also, this class has a private inner class ((QuitButtonHandler)
 * that handles the button's function.
 */
public class PaneOrganizer {
	private BorderPane _root;
	private HBox _hbox;
	private Game _game;
	private ImageView _imageViewerLogo;
	private ImageView _imageViewerHeart;
	private ImageView _imageViewerHeartTwo;

	/**
	 * PaneOrganizer's constructor. Instantiates Game, along with the root pane. the
	 * HBox that contains the label and buttons is set to the bottom of the root
	 * pane. The quit button is set up Extra Credit: Three image Viewers are
	 * initialized. Private helper methods that set up and place these 3 images are
	 * called. Also, a background is created/added.
	 */
	public PaneOrganizer() {
		_hbox = new HBox();
		this.setUpQuitButton();
		_game = new Game(_hbox);
		_root = new BorderPane();
		_root.setCenter(_game.getGamePane());
		_root.setBottom(_hbox);
		_imageViewerLogo = new ImageView();
		_imageViewerHeart = new ImageView();
		_imageViewerHeartTwo = new ImageView();
		this.makeBackground();
		this.makeLogo();
		this.makeHearts();
	}

	/**
	 * Accessor method for the root pane so that is can be referenced in App.
	 */
	public BorderPane getRoot() {
		return _root;
	}

	/**
	 * This private helper method creates the background for the App. It starts by
	 * making an instance of BackgroundImage and passing in the correct image's
	 * string. The latter 4 arguments are null because when left as null, they
	 * default to setting the background to repeat in the X and Y directions, and
	 * uses a default setting for the background's position and size.
	 */
	private void makeBackground() {
		BackgroundImage background = new BackgroundImage(new Image("/Tetris_Background.png"), null, null, null, null);
		_root.setBackground(new Background(background));
	}

	/**
	 * This private helper method sets up the image of the tetris logo. It places it
	 * on the screen and sets up its properties.
	 */
	private void makeLogo() {
		Image tetrisLogo = new Image("TETRISLOGO.png");
		this.makeImage(_imageViewerLogo, tetrisLogo, Constants.LOGO_WIDTH, Constants.LOGO_X, Constants.LOGO_Y);
	}

	/**
	 * This private helper method sets up the image of the 2 heart images next to
	 * the Logo. It places them on the screen and sets up them properties.
	 */
	private void makeHearts() {
		Image heart = new Image("HEART.png");
		this.makeImage(_imageViewerHeart, heart, Constants.HEART_WIDTH, Constants.HEART_X_ONE, Constants.HEART_Y);
		this.makeImage(_imageViewerHeartTwo, heart, Constants.HEART_WIDTH, Constants.HEART_X_TWO, Constants.HEART_Y);
	}

	/**
	 * This private helper method was created to generically set up any image. It
	 * takes in as parameters: the image, its unique image viewer, the desired
	 * width, x, and y positions.
	 */
	private void makeImage(ImageView imageview, Image image, int width, int x, int y) {
		imageview.setImage(image);
		imageview.setFitWidth(width);
		imageview.setPreserveRatio(true);
		imageview.setSmooth(true);
		imageview.setCache(true);
		imageview.setX(x);
		imageview.setY(y);
		_game.getGamePane().getChildren().addAll(imageview);
	}

	/**
	 * private helper method to set up the quit button. it is added to the bottom
	 * section of the root pane. Focus is not set on this button. It is associated
	 * with its QuitButtonHandler.
	 */
	private void setUpQuitButton() {
		Button quitButton = new Button("Quit");
		_hbox.getChildren().addAll(quitButton);
		_hbox.setFocusTraversable(false);
		_hbox.setSpacing(Constants.HBOX_SPACING);
		quitButton.setFocusTraversable(false);
		quitButton.setOnAction(new QuitButtonHandler()); // Button associated with ButtonHandler.
	}

	/**
	 * Private inner class that handles what happens when the quit button is
	 * clicked.
	 */
	private class QuitButtonHandler implements EventHandler<ActionEvent> {
		/**
		 * This handle method is automatically called when the quitButton is clicked. It
		 * calls a method that closes the program.
		 */
		@Override
		public void handle(ActionEvent event) {
			System.exit(0);
		}
	}

}
