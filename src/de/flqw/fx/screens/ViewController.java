package de.flqw.fx.screens;

import de.flqw.fx.screens.Screens.ScreenTransition;
import javafx.stage.Stage;

/**
 * Abstract superclass for FXML Controllers to be used with the Screens framework. Contains all life-cycle methods that may be
 * overridden.
 */
public abstract class ViewController {

	/**
	 * The primary stage of this screen.
	 */
	protected Stage stage;

	private Screens screens;

	/**
	 * Will be called when the initialization is done. You can access the @FXML injected values here for the first time.
	 */
	protected void init() {
	}

	/**
	 * First method to be called in the transition to this screen. This screen will appear shortly.<br>
	 * <strong>Don't forget to call transition.proceed() when the method has completed, or the transition will be stuck.</strong>
	 */
	protected void willAppear(ScreenTransition transition) {
		transition.proceed();
	}

	/**
	 * Will be called simultaneously with the disappear() method of the old controller.<br>
	 * <strong>Don't forget to call transition.proceed() when the method has completed, or the transition will be stuck.</strong>
	 */
	protected void appear(ScreenTransition transition) {
		transition.proceed();
	}

	/**
	 * This screen just appeared.<br>
	 * <strong>Don't forget to call transition.proceed() when the method has completed, or the transition will be stuck.</strong>
	 */
	protected void didAppear(ScreenTransition transition) {
		transition.proceed();
	}

	/**
	 * First method to be called in the transition. This screen will disappear shortly.<br>
	 * <strong>Don't forget to call transition.proceed() when the method has completed, or the transition will be stuck.</strong>
	 */
	protected void willDisappear(ScreenTransition transition) {
		transition.proceed();
	}

	/**
	 * Will be called simultaneously with the appear() method of the controller of the transition target.<br>
	 * <strong>Don't forget to call transition.proceed() when the method has completed, or the transition will be stuck.</strong>
	 */
	protected void disappear(ScreenTransition transition) {
		transition.proceed();
	}

	/**
	 * Will be called when the nodes of the current FXML have been removed from the tree.<br>
	 * <strong>Don't forget to call transition.proceed() when the method has completed, or the transition will be stuck.</strong>
	 */
	protected void didDisappear(ScreenTransition transition) {
		transition.proceed();
	}

	/**
	 * Transition to another screen.
	 *
	 * The view-cycle methods on both controllers will be called as follows:
	 *
	 * <ol>
	 * <li>[currentController.willDisappear()]</li>
	 * <li>newController.willAppear()</li>
	 * <li>[currentController.disappear()]<br>
	 * newController.appear()</li>
	 * <li>newController.didAppear()</li>
	 * <li>[currentController.didDisappear()]</li>
	 * </ol>
	 *
	 * <p>
	 * The steps in brackets will be skipped if the screen is the first one to be switched to.
	 * </p>
	 * <p>
	 * <strong>Don't forget to call super() when the animations in the methods have completed, or the transition will be
	 * stuck.</strong>
	 * </p>
	 *
	 * @param targetScreenId The id of the screen to be switch to.
	 */
	protected void transitionTo(String targetScreenId) {
		screens.transitionTo(targetScreenId);
	}

	/**
	 * Injector for {@link Screens}.
	 *
	 * @param stage
	 */
	void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Injector for {@link Screens}.
	 *
	 * @param screens
	 */
	void setScenes(Screens screens) {
		this.screens = screens;
	}
}
