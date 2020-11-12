package preloader;

import java.io.IOException;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

public class MyPreloader extends Preloader {

	private Stage preloaderStage;
	private Scene scene;


	public MyPreloader() {
	}

	@Override
	public void init() throws Exception {
		Platform.runLater(() -> {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/splashScreen.fxml")); 
			Parent root = null;
			try {
				root = (Parent)loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			scene = new Scene(root);
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.preloaderStage = primaryStage;
		scene.setFill(Color.TRANSPARENT);
		preloaderStage.initStyle(StageStyle.TRANSPARENT); 
		preloaderStage.setMaximized(false);
		preloaderStage.setResizable(false);
		preloaderStage.getIcons().add(new Image("/images/water.png"));
		preloaderStage.setScene(scene);
		preloaderStage.show();
	}

	@Override
	public void handleApplicationNotification(PreloaderNotification info) {

	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {
		StateChangeNotification.Type type = info.getType();
		switch (type) {
		case BEFORE_LOAD:
			break;
		case BEFORE_INIT:
			break;
		case BEFORE_START:
			preloaderStage.hide();
			break;
		}
	}
}
