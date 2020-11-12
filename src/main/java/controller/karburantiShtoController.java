package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import main.global;
import model.karburanti;
import model.perdoruesit;
import utils.Helpers;
import utils.Utils;

public class karburantiShtoController implements Initializable {

	@FXML
	private JFXButton btnAnullo;

	@FXML
	private JFXTextField txtCmimi, txtSasia;

	@FXML
	private JFXDatePicker dpData;

	@FXML
	private ComboBox<String> cmbLloji;
	
	@FXML
	private ImageView imgFatura;

	@FXML
	private Label lblError;

	private int karburantiid = 0;
	
	private String imazhiFatura = null;
	
	private File selectedFileFatura = null;
	
	public static boolean isAnullo = false;


	@FXML
	public void ruaj() throws SQLException {
		isAnullo = false;
		if (Utils.check_empty_text(txtSasia.getText(),txtCmimi.getText()) 
				|| Utils.check_empty_combobox(cmbLloji)) {
			lblError.setText("Plotesoni te gjitha fushat e detyruara!*");
			return;
		}

		perdoruesit p = new perdoruesit();
		p.setUserid(Utils.idP);
		karburanti k = new karburanti();
		k.setUserid(p);
		k.setData(Date.valueOf(dpData.getValue()));
		k.setCmimi(Double.parseDouble(txtCmimi.getText()));
		k.setSasia(Double.parseDouble(txtSasia.getText()));
		k.setLloji(cmbLloji.getValue());
		k.setKarburantiid(karburantiid);
		
		k.setFoto_fature(imazhiFatura);
		k.setPhoto_fature(new ImageView(global.TO_FILE + global.INSTALL_DIR_IMAGES + imazhiFatura));
		
		if(karburantiid == 0) {
			ControlDAO.getControlDao().getKarburantiDao().addKarburanti(k);
			Helpers.insertAktiviteti("Shtim", "Karburanti");
		}
		else {
			ControlDAO.getControlDao().getKarburantiDao().updateKarburanti(k);
			Helpers.insertAktiviteti("Edit", "Karburanti");
		}
		Utils.close_stage(btnAnullo);

	}

	@FXML
	public void anullo() {
		isAnullo = true;
		Utils.close_stage(btnAnullo);
	}
	
	@FXML
	private void ngarkoFaturen() throws IOException {
		global.getFtpClient();
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter extFilters = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png" , "*.jpeg", "*.bmp");
        fc.getExtensionFilters().add(extFilters);
	    fc.setTitle("Zgjidh Foton");
	    selectedFileFatura = fc.showOpenDialog(null);
		
		if(selectedFileFatura != null) {
			Path from = Paths.get(selectedFileFatura.toURI());
			FileInputStream fis = new FileInputStream(selectedFileFatura);
	        Image image = new Image(fis);
	        imgFatura.setImage(image);
	        String firstRemoteFile = selectedFileFatura.getName();
			InputStream inputStream = new FileInputStream(from.toString());
            boolean done = global.ftpClient.storeFile(firstRemoteFile, inputStream);
            System.out.println(done);
            inputStream.close();
			Path to = Paths.get(global.INSTALL_DIR_IMAGES + selectedFileFatura.getName());
			File toFile = new File(to.toString());
            if(!toFile.exists()) 
            	 Files.copy(from, to);
        
            imazhiFatura = firstRemoteFile;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Utils.make_textfield_decimal(txtCmimi);
		Utils.make_textfield_decimal(txtSasia);
		cmbLloji.getItems().setAll("Nafte", "Benzine", "Gaz");
		if(karburantiController.edit == true) {
			karburantiid = karburantiController.karburantiDataHolder.getKarburantiid();
			dpData.setValue(karburantiController.karburantiDataHolder.getData().toLocalDate());
			txtSasia.setText(karburantiController.karburantiDataHolder.getSasia() + "");
			txtCmimi.setText(karburantiController.karburantiDataHolder.getVlera()+ "");
			cmbLloji.setValue(karburantiController.karburantiDataHolder.getLloji());
			File fotoFature= new File(global.INSTALL_DIR_IMAGES + karburantiController.karburantiDataHolder.getFoto_fature());
			if(fotoFature.exists()) {
	    		imgFatura.setImage(new Image(global.TO_FILE + global.INSTALL_DIR_IMAGES + karburantiController.karburantiDataHolder.getFoto_fature()));
	    		imazhiFatura = karburantiController.karburantiDataHolder.getFoto_fature();
	    	}
		}
		else {
			dpData.setValue(LocalDate.now());
			karburantiid = 0;
		}
	}
}
