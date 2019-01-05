package Tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * It's time for Tetris! This is the main class to get things started.
 *
 * The main method of this application calls the start method. You will need to
 * fill in the start method to instantiate your game.
 *
 * This is the App class. Creates the top level object and sets up the scene.
 * This class triggers the program to begin. The root pane is passed into the
 * scene's constructor.
 */
public class App extends Application {

	/**
	 * The top level object is created here, the scene is set up with reference to
	 * the root pane, and the stage is shown.
	 */
	@Override
	public void start(Stage stage) {
		PaneOrganizer organizer = new PaneOrganizer();
		Scene scene = new Scene(organizer.getRoot(), Constants.APP_WIDTH, Constants.APP_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("Super Awesome Tetris!");
		stage.show();
	}

	/*
	 * Here is the mainline! No need to change this.
	 */
	public static void main(String[] argv) {
		// launch is a method inherited from Application
		launch(argv);
	}
}
