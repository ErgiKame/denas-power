package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import main.global;
import model.perdoruesit;
import model.furnizime;
import utils.Helpers;
import utils.Utils;

public class furnizimeShtoController implements Initializable {

	@FXML
	private JFXButton btnAnullo;
	
	@FXML
	private JFXTextField txtCmimi;
	
	@FXML
	private JFXDatePicker dpData;
	
	@FXML
	private ImageView imgFatura;
	
	
	@FXML
	private Label lblError;
	
	@FXML
	private TextArea txtPershkrimi;

	private int furnzimeid = 0;
	
	private String imazhiFatura = null;
	
	private File selectedFileFatura = null;
	
	public static boolean isAnullo = false;
	
	@FXML
	public void ruaj() throws SQLException {
		isAnullo = false;
		if (Utils.check_empty_text(txtCmimi.getText(),txtPershkrimi.getText())) {
			lblError.setText("Plotesoni te gjitha fushat e detyruara!*");
			return;
		}
		
		perdoruesit p = new perdoruesit();
		p.setUserid(Utils.idP);
		furnizime f = new furnizime();
		f.setUserid(p);
		f.setData(Date.valueOf(dpData.getValue()));
		f.setVlera(Double.parseDouble(txtCmimi.getText()));
		f.setPershkrimi(txtPershkrimi.getText());
		f.setFurnizimeid(furnzimeid);
		
		f.setFoto_fature(imazhiFatura);
		f.setPhoto_fature(new ImageView(global.TO_FILE + global.INSTALL_DIR_IMAGES + imazhiFatura));
		
		if(furnzimeid == 0) {
			ControlDAO.getControlDao().getFurnizimeDao().addFurnizime(f);
			Helpers.insertAktiviteti("Shtim", "Furnizime");
		}
		else {
			ControlDAO.getControlDao().getFurnizimeDao().updateFurnizime(f);
			Helpers.insertAktiviteti("Edit", "Furnizime");
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
		//Helpers.fileChooser(imgFatura);
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
		
		if(furnizimeController.edit == true) {
			furnzimeid = furnizimeController.furnizimeDataHolder.getFurnizimeid();
			txtCmimi.setText(furnizimeController.furnizimeDataHolder.getVlera() + "");
			txtPershkrimi.setText(furnizimeController.furnizimeDataHolder.getPershkrimi());
			dpData.setValue(furnizimeController.furnizimeDataHolder.getData().toLocalDate());
			File fotoFature= new File(global.INSTALL_DIR_IMAGES + furnizimeController.furnizimeDataHolder.getFoto_fature());
	    	if(fotoFature.exists()) {
	    		imgFatura.setImage(new Image(global.TO_FILE + global.INSTALL_DIR_IMAGES + furnizimeController.furnizimeDataHolder.getFoto_fature()));
	    		imazhiFatura = furnizimeController.furnizimeDataHolder.getFoto_fature();
	    	}
	    		    		
		}
		
		else {
			dpData.setValue(LocalDate.now());
			furnzimeid = 0;
		}
	}
	
	
}
