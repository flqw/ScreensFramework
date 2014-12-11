package de.flqw.fx.screens;

import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int HEIGHT = 550;
	private static final int WIDTH = 600;

	private static final HashMap<String, String> SCREENS = new HashMap<>();
	private static final String START_SCREEN = "screen1";

	static {
		SCREENS.put("screen1", "de/flqw/fx/screens/Screen1.fxml");
		SCREENS.put("screen2", "de/flqw/fx/screens/Screen2.fxml");
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		HashMap<String, Object> data = new HashMap<>();
		data.put("test", "HALLO");

		Screens screens = new Screens(SCREENS, stage, data);

		stage.setMinHeight(HEIGHT);
		stage.setMinWidth(WIDTH);

		// Group root = new Group(screenController);
		Scene scene = new Scene(screens);

		stage.setScene(scene);

		stage.show();

		// Do this here so all animations are started after the screen is shown.
		screens.transitionTo(START_SCREEN);

	}

}
