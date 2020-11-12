package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.perdoruesit;
import utils.Helpers;
import utils.Utils;

public class perdoruesitShtoController implements Initializable {

	@FXML
	private JFXTextField txtEmri, txtMbiemri, txtUsername, txtTelefon, txtEmail;
	
	@FXML
	private ComboBox<String> cmbTeDrejtat;
	
	@FXML
	private Label lblError;
	
	@FXML
	private JFXButton btnAnullo;
	
	
	private int userid = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbTeDrejtat.getItems().setAll("Admin", "User");
		
		if(perdoruesitController.edit == true) {
			userid = perdoruesitController.perdoruesitDataHolder.getUserid();
			txtUsername.setText(perdoruesitController.perdoruesitDataHolder.getUsername());
			txtEmri.setText(perdoruesitController.perdoruesitDataHolder.getName());
			txtMbiemri.setText(perdoruesitController.perdoruesitDataHolder.getSurname());
			txtTelefon.setText(perdoruesitController.perdoruesitDataHolder.getTelefon());
			txtEmail.setText(perdoruesitController.perdoruesitDataHolder.getEmail()+ "");
			cmbTeDrejtat.setValue(perdoruesitController.perdoruesitDataHolder.getAccess());

		}
		else {
			userid = 0;
		}
		
	}

	@FXML
	public void ruaj() throws SQLException {
		
		if (Utils.check_empty_text(txtUsername.getText(), cmbTeDrejtat.getValue())) {
			lblError.setText("Plotesoni te gjitha fushat e detyruara!*");
			return;
		}
		perdoruesit p = new perdoruesit();
		p.setUsername(txtUsername.getText());
		p.setUserid(Utils.idP);
		p.setPassword(txtUsername.getText());
		p.setName(txtEmri.getText());
		p.setSurname(txtMbiemri.getText());
		p.setEmail(txtEmail.getText());
		p.setTelefon(txtTelefon.getText());
		p.setAccess(cmbTeDrejtat.getValue());
		p.setUserid(userid);
		
		if(userid == 0) {
			ControlDAO.getControlDao().getPerdoruesitDao().addPerdorues(p);
			Helpers.insertAktiviteti("Shtim", "Perdoruesit");
		}
		else {
			ControlDAO.getControlDao().getPerdoruesitDao().updatePerdorues(p);
			Helpers.insertAktiviteti("Edit", "Perdoruesit");
		}

		
		Utils.close_stage(btnAnullo);
		
	}
	
	@FXML
	public void anullo() {
		Utils.close_stage(btnAnullo);
	}
}
