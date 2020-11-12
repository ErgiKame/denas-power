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

public class nafta_lartDaljeController implements Initializable {

	@FXML
	private JFXButton btnAnullo;

	@FXML
	private JFXTextField txtSasia;

	@FXML
	private JFXDatePicker dpData;

	@FXML
	private ComboBox<String> cmbLloji;

	@FXML
	private TextArea txtPershkrimi;

	@FXML
	private Label lblError;

	private int nafta_lartid = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Utils.make_textfield_decimal(txtSasia);
		dpData.setValue(LocalDate.now());
		cmbLloji.getItems().setAll("Betonieret", "Eskavatori (cakulli)", "Eskavatori 4pilar", "Fadroma", "Fordet gri (2 cope)", "Fordi bardhe (bajrami)", "Fordi bardhe (Marseli)", "Fordi kuq (Godina)", "Furgoni", 
	"Gjeneratori", "Gurorja", "JSB", "Kam. Astra", "Kam. Iveco", "Kamioncina", "Kompresori", "Liberi I vogel", "Liberi Madh", "Makineri per thyerje guri", "Mani bardhe", "Mani portokalli", "Nisani", "Toyota", "Toyota Lelit"
	, "Traktori", "Vinci", "xxx Luan Kapri");
		nafta_lartid = 0;
	}


	@FXML
	public void ruaj() throws SQLException {
		if (Utils.check_empty_text(txtSasia.getText())
				|| Utils.check_empty_combobox(cmbLloji))  {
			lblError.setText("Plotesoni te gjitha fushat e detyruara!*");
			return;
		}

		perdoruesit p = new perdoruesit();
		p.setUserid(Utils.idP);
		nafta_lart n = new nafta_lart();
		n.setUserid(p);
		n.setData(Date.valueOf(dpData.getValue()));
		n.setLloji_makines(cmbLloji.getValue());
		n.setPershkrimi(txtPershkrimi.getText());
		n.setNafta_larid(nafta_lartid);
		n.setSasia_dalje(Double.parseDouble(txtSasia.getText()));


		ControlDAO.getControlDao().getNafta_lartDao().daljeNafta_Lart(n);



		Utils.close_stage(btnAnullo);

	}

	@FXML
	public void anullo() {
		Utils.close_stage(btnAnullo);
	}
}
