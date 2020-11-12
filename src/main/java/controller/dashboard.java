package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Controllers;
import utils.Helpers;
import utils.Utils;

public class dashboard implements Initializable {

	public Parent root; 
	@FXML public VBox mainVbox, btnHolders;
	@FXML private JFXButton btnPerdoruesit, btnTransporti, btnKarburanti,
	btnPastrime, btnFurnizime, btnNaftaLart, btnNdryshoPasswordin, btnDil;
	@FXML private ImageView imgLogo;
	@FXML private Label lblWelcome;

	@FXML
	public void transporti() {
		Controllers.getTransporti(mainVbox);
	}

	@FXML
	public void karburanti() {
		Controllers.getKarburanti(mainVbox);
	}

	@FXML
	public void pastrime() {
		Controllers.getPastrime(mainVbox);
	}

	@FXML
	public void furnizime() {
		Controllers.getFurnizime(mainVbox);
	}

	@FXML
	public void perdoruesit() {
		Controllers.getPerdoruesit(mainVbox);
	}

	@FXML
	public void ndryshoPasswordin() {
		Helpers.build_change_password(mainVbox,btnTransporti);
	}
	
	@FXML
	public void nafta_lart() {
		Controllers.getNafta_lart(mainVbox);
	}

	@FXML
	public void dil() throws IOException {
		Platform.exit();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblWelcome.setText("Pershendetje " + Utils.name + "! " + DateTimeFormatter.ofPattern("dd.MM.YYYY").format(LocalDate.now()));
		if(Utils.rights.contentEquals("User")) {
			btnHolders.getChildren().remove(btnPerdoruesit);
			btnHolders.getChildren().remove(btnNaftaLart);
		}
	}

}
