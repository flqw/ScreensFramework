#ScreensFramework

Small JavaFX 8 framework to provide easy yet powerful transitioning between FXML files with a 6-step transition lifecycle.

##Transition Lifecycle

`source` = FXML Controller triggering the transition<br>
`target` = Controller of the target FXML

1. `source.willDisappear(ScreenTransition transition)`
2. `target.willAppear(ScreenTransition transition)`
3. `source.disappear(ScreenTransition transition)` <br>
   `target.appear(ScreenTransition transition)`
4. `target.didAppear(ScreenTransition transition)`
5. `source.didDisappear(ScreenTransition transition)`

When a step is complete the transition must be proceeded with `transition.proceed()`

##Usage / Example
For a usage example see the [test package](test/de/flqw/fx/screens).
