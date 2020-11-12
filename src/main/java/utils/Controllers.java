package utils;

import controller.karburantiController;
import controller.nafta_lartController;
import controller.transportiController;
import controller.pastrimeController;
import controller.furnizimeController;
import controller.perdoruesitController;
import javafx.scene.layout.VBox;

public class Controllers {

	private static transportiController transportiController;
	private static karburantiController karburantiController;
	private static pastrimeController pastrimeController;
	private static furnizimeController furnizimeController;
	private static perdoruesitController perdoruesitController;
	private static nafta_lartController nafta_lartController;
	
	private Controllers() {
	
	}

	public static void getTransporti(VBox box) {
		transportiController = transportiController == null ? new transportiController() : transportiController;
		config(box, transportiController);
	}
	
	public static void getKarburanti(VBox box) {
		karburantiController = karburantiController == null ? new karburantiController() : karburantiController;
		config(box, karburantiController);
	}
	
	public static void getPastrime(VBox box) {
		pastrimeController = pastrimeController == null ? new pastrimeController() : pastrimeController;
		config(box, pastrimeController);
	}
	
	public static void getFurnizime(VBox box) {
		furnizimeController = furnizimeController == null ? new furnizimeController() : furnizimeController;
		config(box, furnizimeController);
	}
	
	public static void getPerdoruesit(VBox box) {
		perdoruesitController = perdoruesitController == null ? new perdoruesitController() : perdoruesitController;
		config(box, perdoruesitController);
	}
	
	public static void getNafta_lart(VBox box) {
		nafta_lartController = nafta_lartController == null ? new nafta_lartController() : nafta_lartController;
		config(box, nafta_lartController);
	}
	
	public static void config(VBox box, VBox content) {
		box.getChildren().clear();
		box.getChildren().add(content);
	}
	
	

}
