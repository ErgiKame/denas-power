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
import model.nafta_lart;
import model.perdoruesit;
import utils.Utils;

public class nafta_lartLlogaritController implements Initializable {

	@FXML
	private JFXButton btnDil;

	@FXML
	private JFXDatePicker dpFrom, dpTo;


	@FXML
	private Label lblError;

	@FXML
	private ComboBox<String> cmbLloji;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cmbLloji.getItems().setAll("Betonieret", "Eskavatori (cakulli)", "Eskavatori 4pilar", "Fadroma", "Fordet gri (2 cope)", "Fordi bardhe (bajrami)", "Fordi bardhe (Marseli)", "Fordi kuq (Godina)", "Furgoni", 
				"Gjeneratori", "Gurorja", "JSB", "Kam. Astra", "Kam. Iveco", "Kamioncina", "Kompresori", "Liberi I vogel", "Liberi Madh", "Makineri per thyerje guri", "Mani bardhe", "Mani portokalli", "Nisani", "Toyota", "Toyota Lelit"
				, "Traktori", "Vinci", "xxx Luan Kapri");
	
	}


	@FXML
	public void llogarit() throws SQLException {
		
		if (Utils.check_datePickers(dpFrom, dpTo) || cmbLloji.getValue().isEmpty())  {
			lblError.setText("Ploteso fushat e detyruara!*");
			return;
		}

		lblError.setText(ControlDAO.getControlDao().getNafta_lartDao().getLlogaritje(cmbLloji.getValue(), dpFrom.getValue(), dpTo.getValue()) + " Litra");


	}

	@FXML
	public void dil() {
		Utils.close_stage(btnDil);
	}
}
