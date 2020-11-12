package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.aktiviteti;
import model.perdoruesit;
import utils.Helpers;
import utils.Utils;

public class login implements Initializable {

	@FXML private JFXTextField txtUsername;
	@FXML private JFXPasswordField txtPassword;
	@FXML private Label lblError;
	@FXML private Button btnLogin;
	

	public void initialize(URL location, ResourceBundle resources) {
		try {
			ControlDAO.getControlDao().getLoginDao().flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	private void checkLogin() throws IOException, SQLException {
		String user = txtUsername.getText();
		String passwd = Utils.encrypt(Utils.key, Utils.initVector, txtPassword.getText());

		if(user.isEmpty() || txtPassword.getText().isEmpty() ) {
			lblError.setText("Ju lutem plotesoni fushat!");	
			return;
		}

	if(ControlDAO.getControlDao().getLoginDao().check_user_and_pass(user,passwd)) {
			
			Stage stage = (Stage) btnLogin.getScene().getWindow();
			stage.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard-admin.fxml"));
			Parent root = loader.load();
	    	Scene scene = new Scene(root);
	    	Stage primaryStage = new Stage();
	    	primaryStage.setMaximized(true);
	    	primaryStage.setResizable(true);
	    	primaryStage.setScene(scene);
	    	primaryStage.centerOnScreen();
	    	primaryStage.setTitle("Denas Power Management");
	    	primaryStage.getIcons().add(new Image("/images/water.png"));
	    	primaryStage.show();
	    	primaryStage.setOnCloseRequest( e-> System.exit(0));   
	    	
	    	Helpers.insertAktiviteti("Hyrje ne Sistem", "Info");
		}
		else {
			lblError.setText("Username ose Passwordi gabim!");	
			return;
		}
		
	}

	
}
