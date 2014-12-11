package de.flqw.fx.screens;

import de.flqw.fx.screens.ViewController;
import de.flqw.fx.screens.Screens.ScreenTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Screen1Controller extends ViewController {

	private @FXML Button transitionButton;
	private @FXML Button exitButton;

	@FXML
	public void exit() {
		stage.close();
	}

	@FXML
	public void transition() {
		transitionTo("screen2");
	}

	@Override
	protected void init() {
		System.out.println("Custom data: " + data.get("test"));
	}

	@Override
	protected void disappear(ScreenTransition transition) {

		double outside = stage.getHeight();
		translateTo(exitButton, Duration.seconds(.2), outside);
		translateTo(transitionButton, Duration.seconds(.4), outside, transition::proceed);

	}

	@Override
	protected void willAppear(ScreenTransition transition) {

		// Move the buttons out of the view area.
		double outside = stage.getHeight();

		transitionButton.setTranslateY(outside);
		exitButton.setTranslateY(outside);

		transition.proceed();

	}

	@Override
	protected void appear(ScreenTransition transition) {

		translateTo(transitionButton, Duration.seconds(.2), 0);
		translateTo(exitButton, Duration.seconds(.4), 0, transition::proceed);

	}

	private void translateTo(Node node, Duration delay, double y) {
		translateTo(node, delay, y, null);
	}

	private void translateTo(Node node, Duration delay, double y, EventHandler<ActionEvent> onFinished) {

		TranslateTransition transition = new TranslateTransition(Duration.seconds(1), node);
		transition.setOnFinished(onFinished);
		transition.setDelay(delay);
		transition.setToY(y);
		transition.play();

	}


}
