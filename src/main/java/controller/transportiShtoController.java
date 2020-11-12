package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import main.global;
import model.perdoruesit;
import model.transporti;
import utils.Helpers;
import utils.Utils;

public class transportiShtoController implements Initializable {

	@FXML
	private JFXButton btnAnullo;

	@FXML
	private JFXDatePicker dpData;

	@FXML
	private JFXTextField txtNisja, txtMberritja;

	@FXML
	private TextArea txtAreaKoment;

/*	@FXML
	private ImageView imgNisje, imgMberritje;
*/ //major update
	
	@FXML
	private Label lblError;

	private int transportid = 0;
	private String imazhiNisja = null, imazhiMberritja = null;
	private File selectedFileNisja = null;
	private File selectedFileMberritja = null;
	public static boolean isAnullo = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Utils.make_jfxtextfield_integer(txtNisja);
		Utils.make_jfxtextfield_integer(txtMberritja);
		
		if(transportiController.edit == true) {
			transportid = transportiController.transportiDataHolder.getTransportiid();
			txtNisja.setText(transportiController.transportiDataHolder.getNisja() + "");
			txtMberritja.setText(transportiController.transportiDataHolder.getMberritja() + "");
			txtAreaKoment.setText(transportiController.transportiDataHolder.getKoment());
			dpData.setValue(transportiController.transportiDataHolder.getData().toLocalDate());
/*			File fotoNisje = new File(global.INSTALL_DIR_IMAGES + transportiController.transportiDataHolder.getFoto_nisje());
	    	if(fotoNisje.exists()) {
	    		imgNisje.setImage(new Image(global.TO_FILE + global.INSTALL_DIR_IMAGES + transportiController.transportiDataHolder.getFoto_nisje()));
	    		imazhiNisja = transportiController.transportiDataHolder.getFoto_nisje();
	    	}
	    		
	    	File fotoMberritje = new File(global.INSTALL_DIR_IMAGES + transportiController.transportiDataHolder.getFoto_mberritje());
	    	if(fotoMberritje.exists()) {
	    		imgMberritje.setImage(new Image(global.TO_FILE + global.INSTALL_DIR_IMAGES + transportiController.transportiDataHolder.getFoto_mberritje()));
	    		imazhiMberritja = transportiController.transportiDataHolder.getFoto_mberritje();
	    	}
*/	   //major update  		
		}
		else {
			transportid = 0;
			dpData.setValue(LocalDate.now());
		}
	}

	@FXML
	private void ruaj() throws SQLException {
		isAnullo = false;
		if (Utils.check_empty_text(txtNisja.getText(),txtMberritja.getText())) {
			lblError.setText("Plotesoni te gjitha fushat e detyruara!*");
			return;
		}
		
		perdoruesit p = new perdoruesit();
		p.setUserid(Utils.idP);
		transporti t = new transporti();
		t.setUserid(p);
		t.setData(Date.valueOf(dpData.getValue()));
		t.setKoment(txtAreaKoment.getText());
		t.setNisja(Integer.parseInt(txtNisja.getText()));
		t.setMberritja(Integer.parseInt(txtMberritja.getText()));
		t.setTransportiid(transportid);
		
/*		t.setFoto_nisje(imazhiNisja);
		t.setPhoto_nisje(new ImageView(global.TO_FILE + global.INSTALL_DIR_IMAGES + imazhiNisja));
		
		t.setFoto_mberritje(imazhiMberritja);
		t.setPhoto_mberritje(new ImageView(global.TO_FILE + global.INSTALL_DIR_IMAGES + imazhiMberritja));
*/ //major update		
		
		if(transportid == 0) {
			ControlDAO.getControlDao().getTransportiDao().addTransporti(t);
			Helpers.insertAktiviteti("Shtim", "Transporti");
		}
		else {
			ControlDAO.getControlDao().getTransportiDao().updateTransporti(t);
			Helpers.insertAktiviteti("Edit", "Transporti");
		}
		
		Utils.close_stage(btnAnullo);
	}

	@FXML
	private void anullo() {
		Utils.close_stage(btnAnullo);
	}

/*	@FXML
	private void ngarkoNisje() throws IOException {
		global.getFtpClient();
		//Helpers.fileChooser(imgFatura);
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter extFilters = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png" , "*.jpeg", "*.bmp");
        fc.getExtensionFilters().add(extFilters);
	    fc.setTitle("Zgjidh Foton");
	    selectedFileNisja = fc.showOpenDialog(null);
		
		if(selectedFileNisja != null) {
			Path from = Paths.get(selectedFileNisja.toURI());
			FileInputStream fis = new FileInputStream(selectedFileNisja);
	        Image image = new Image(fis);
	        imgNisje.setImage(image);
	        String firstRemoteFile = selectedFileNisja.getName();
			InputStream inputStream = new FileInputStream(from.toString());
            boolean done = global.ftpClient.storeFile(firstRemoteFile, inputStream);
            System.out.println(done);
            inputStream.close();
			Path to = Paths.get(global.INSTALL_DIR_IMAGES + selectedFileNisja.getName());
			File toFile = new File(to.toString());
            if(!toFile.exists()) 
            	 Files.copy(from, to);
        
            imazhiNisja = firstRemoteFile;
		}
	}

*/  //major update
	
/*	@FXML
	private void ngarkoMberritje() throws IOException {
		global.getFtpClient();
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter extFilters = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png" , "*.jpeg", "*.bmp");
        fc.getExtensionFilters().add(extFilters);
	    fc.setTitle("Zgjidh Foton");
	    selectedFileMberritja = fc.showOpenDialog(null);
		
		if(selectedFileMberritja != null) {
			Path from = Paths.get(selectedFileMberritja.toURI());
			FileInputStream fis = new FileInputStream(selectedFileMberritja);
	        Image image = new Image(fis);
	        imgMberritje.setImage(image);
	        String firstRemoteFile = selectedFileMberritja.getName();
			InputStream inputStream = new FileInputStream(from.toString());
            boolean done = global.ftpClient.storeFile(firstRemoteFile, inputStream);
            System.out.println(done);
            inputStream.close();
			Path to = Paths.get(global.INSTALL_DIR_IMAGES + selectedFileMberritja.getName());
			File toFile = new File(to.toString());
            if(!toFile.exists()) 
            	 Files.copy(from, to);
        
            imazhiMberritja = firstRemoteFile;
		}
	}
*/ //major update	
}
