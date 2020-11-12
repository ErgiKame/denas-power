package main;

import java.io.File;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.global;
import preloader.MyPreloader;
import utils.ftpDownload;

public class Main extends Application {

	private static final int COUNT_LIMIT = 1;

	private static int stepCount = 1;


	private Stage applicationStage;

	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);
	}


	@Override
	public void init() throws Exception {
		
		File img_folder = new File(global.INSTALL_DIR_IMAGES);
		System.out.println(img_folder.mkdirs());
		new ftpDownload().downlaodFiles();

		for (int i = 1; i < COUNT_LIMIT; i++) {
			double progress = (100 * i) / COUNT_LIMIT;
			LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		applicationStage = primaryStage;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
		Parent root=(Parent)loader.load();

		Scene scene = new Scene(root);
		applicationStage.setResizable(false);
		applicationStage.setScene(scene);
		applicationStage.centerOnScreen();
		applicationStage.setTitle("Denas Power Management");
		applicationStage.getIcons().add(new Image("/images/water.png"));
		applicationStage.show();
	}

}
