package engine.support;// package

import javafx.stage.Stage;
import tic.App;

public class FXApplication extends javafx.application.Application {
		
	private static FXFrontEnd fx;
	

	public static void begin(FXFrontEnd fx) {
		FXApplication.fx = fx;
		launch();
	}
	
	@Override
	public final void start(Stage stage) throws Exception {
		fx.init(stage);
	}

}
