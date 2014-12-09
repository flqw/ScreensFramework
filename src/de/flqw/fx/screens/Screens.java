package de.flqw.fx.screens;

import java.util.HashMap;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Screens extends StackPane {

	private Stage stage;

	private HashMap<String, Node> screens = new HashMap<>();
	private HashMap<String, Controller> controllers = new HashMap<>();

	private String activeScreen;

	private ScreenTransitionHandler handler;

	private ScreenTransition transition = new ScreenTransition();

	/**
	 * Constructor.
	 *
	 * @param screens Map of <id:fxmlPath> screens.
	 * @param stage The primary stage.
	 */
	public Screens(HashMap<String, String> screens, Stage stage) {
		this.stage = stage;
		// Black background looks better.
		setStyle("-fx-background-color:black");
		screens.forEach(this::load);
	}

	/**
	 * Load a screen.
	 *
	 * @param id The id of the screen (For later reference).
	 * @param fxml The location of the fxml file from the root of the classpath.
	 */
	public void load(String id, String fxml) {

		try {

			FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(fxml));

			Node screenRoot = loader.load();

			Controller controller = loader.getController();

			// Inject values.
			controller.setScenes(this);
			controller.setStage(stage);

			// Call the controller initialization.
			controller.init();

			controllers.put(id, controller);
			screens.put(id, screenRoot);

		} catch (Exception e) {
			System.err.println("The loading of the screen " + id + " with FXML " + fxml + " failed!");
			e.printStackTrace();
		}

	}

	/**
	 * For javadoc see {@link Controller#switchTo}.
	 *
	 * @param screenId
	 */
	public void transitionTo(String screenId) {

		// Two transitions at the same time are not possible.
		if (handler != null) {
			return;
		}

		if (!screens.containsKey(screenId)) {
			System.err.println("The screen with the id " + screenId + " is not loaded!");
			return;
		}

		handler = new ScreenTransitionHandler();

		handler.newScreenRoot = screens.get(screenId);
		handler.oldScreenRoot = activeScreen == null ? null : screens.get(activeScreen);

		handler.newController = controllers.get(screenId);
		handler.oldController = activeScreen == null ? null : controllers.get(activeScreen);

		// Start the transition.
		handler.proceed();

		activeScreen = screenId;

	}

	/**
	 * Call this method to proceed the transition.
	 */
	public void proceedTransition() {
		if (handler != null) {
			handler.proceed();
		}
	}

	private static enum ScreenTransitionState {
		OLD_WILL_DISAPPEAR, NEW_WILL_APPEAR, OLD_DISAPPEAR, NEW_APPEAR, NEW_DID_APPEAR, OLD_DID_DISAPPEAR, END
	}

	private class ScreenTransitionHandler {

		ScreenTransitionState state = ScreenTransitionState.OLD_WILL_DISAPPEAR;

		Controller oldController;
		Controller newController;

		Node newScreenRoot;
		Node oldScreenRoot;

		/**
		 * This does all totally make sense. Might be prettier with different syntax.
		 */
		void proceed() {
			switch (state) {
			case OLD_WILL_DISAPPEAR:
				state = ScreenTransitionState.NEW_WILL_APPEAR;
				if (oldController != null) {
					oldController.willDisappear(transition);
				} else {
					proceed(); // Skip this step if there is no old screen.
				}
				break;
			case NEW_WILL_APPEAR:
				state = ScreenTransitionState.OLD_DISAPPEAR;
				newController.willAppear(transition);
				break;
			case OLD_DISAPPEAR:
				state = ScreenTransitionState.NEW_APPEAR;
				getChildren().add(newScreenRoot);
				newScreenRoot.toBack();
				newController.appear(transition);
				if (oldController != null) {
					oldController.disappear(transition);
				} else {
					proceed(); // Skip this step if there is no old screen.
				}
				break;
			case NEW_APPEAR: // Just wait for both disappear() and appear() to have called proceed().
				state = ScreenTransitionState.NEW_DID_APPEAR;
				break;
			case NEW_DID_APPEAR:
				state = ScreenTransitionState.OLD_DID_DISAPPEAR;
				newController.didAppear(transition);
				break;
			case OLD_DID_DISAPPEAR:
				state = ScreenTransitionState.END;
				if (oldScreenRoot != null) {
					getChildren().remove(oldScreenRoot);
				}
				if (oldController != null) {
					oldController.didDisappear(transition);
				} else {
					proceed();
				}
				break;
			case END:
				handler = null; // Self destruction ... beep ... beep ... beep.
				break;
			}
		}

	}

	/**
	 * Simple class to simplify the transition process.
	 */
	public class ScreenTransition {

		/**
		 * Proceed the transition now.
		 */
		public void proceed() {
			proceedTransition();
		}

		/**
		 * Proceed the transition.
		 *
		 * @param event Will be thrown away, is just here to allow nice Java 8 method reference syntax: transition::proceed
		 */
		public void proceed(Event event) {
			proceed();
		}

	}

}
