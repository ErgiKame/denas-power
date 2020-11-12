package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.karburanti;
import model.pastrime;
import model.perdoruesit;
import utils.Helpers;
import utils.Utils;

public class pastrimeShtoController implements Initializable {

	@FXML
	private JFXButton btnAnullo;


	@FXML
	private JFXDatePicker dpData;

	@FXML
	private ComboBox<String> cmbVendi;

	@FXML
	private Label lblError;

	@FXML
	private TextArea txtPershkrimi;

	private int pastrimeid = 0;


	@FXML
	public void ruaj() throws SQLException {
		if (cmbVendi.getValue().isEmpty()) {
			lblError.setText("Plotesoni te gjitha fushat e detyruara!*");
			return;
		}

		perdoruesit p = new perdoruesit();
		p.setUserid(Utils.idP);
		pastrime pa = new pastrime();
		pa.setUserid(p);
		pa.setData(Date.valueOf(dpData.getValue()));
		pa.setVendi(cmbVendi.getValue());
		pa.setPershkrimi(txtPershkrimi.getText());
		pa.setPastrimeid(pastrimeid);

		if(pastrimeid == 0) {
			ControlDAO.getControlDao().getPastrimeDao().addPastrime(pa);
			Helpers.insertAktiviteti("Shtim", "Pastrime");
		}
		else {
			ControlDAO.getControlDao().getPastrimeDao().updatePastrime(pa);
			Helpers.insertAktiviteti("Edit", "Pastrime");
		}
		Utils.close_stage(btnAnullo);

	}

	@FXML
	public void anullo() {
		Utils.close_stage(btnAnullo);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbVendi.getItems().setAll("Turbina 1", "Turbina 2", "Turbina 3", "Korridori 1",
				"Korridori 2", "Korridori 3", "Shkallet", "Tualetet", "Salla e Operimit", "Kuzhina", "Pista");
		
		if (pastrimeController.edit == true) {
			pastrimeid = pastrimeController.pastrimeDataHolder.getPastrimeid();
			txtPershkrimi.setText(pastrimeController.pastrimeDataHolder.getPershkrimi());
			cmbVendi.setValue(pastrimeController.pastrimeDataHolder.getVendi());
			dpData.setValue(pastrimeController.pastrimeDataHolder.getData().toLocalDate());
		}
		else {
			dpData.setValue(LocalDate.now());
			pastrimeid = 0;
		}
	}


}
