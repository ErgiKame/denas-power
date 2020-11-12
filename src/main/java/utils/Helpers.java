package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.global;
import model.aktiviteti;
import model.perdoruesit;

public class Helpers {

	public static void make_jfxtextfield_integer(JFXTextField txt) {
		txt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,12}?")) {
					txt.setText(oldValue);
				}
			}
		});
	}
	
	public static void fileChooser(File selectedFile,ImageView name, String imgName) throws IOException {
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter extFilters = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png" , "*.jpeg", "*.bmp");
        fc.getExtensionFilters().add(extFilters);
	    fc.setTitle("Zgjidh Foton");
	    selectedFile = fc.showOpenDialog(null);
		
		if(selectedFile != null) {
			Path from = Paths.get(selectedFile.toURI());
			FileInputStream fis = new FileInputStream(selectedFile);
	        Image image = new Image(fis);
	        name.setImage(image);
	        Path to = Paths.get(global.INSTALL_DIR_IMAGES + selectedFile.getName());
            File toFile = new File(to.toString());
   
            Path to1 = Paths.get(selectedFile.getName());
            if(!toFile.exists()) 
            	 Files.copy(from, to);
        
            imgName = to1.toString();
		}
	}
	
	private static int i = 0;
	public static void convertDatepicker(JFXDatePicker... datePicker) {

		for(i=0;i<datePicker.length;i++) {
			datePicker[i].setConverter(new StringConverter<LocalDate>() {
				String pattern = "dd/MM/yyyy";
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
				{
					datePicker[i].setPromptText(pattern.toLowerCase());
				}

				@Override public String toString(LocalDate date) {
					if (date != null) {
						return dateFormatter.format(date);
					} else {
						return "";
					}
				}

				@Override public LocalDate fromString(String string) {
					if (string != null && !string.isEmpty()) {
						return LocalDate.parse(string, dateFormatter);
					} else {
						return null;
					}
				}
			});
		}
	}
	
	public static void build_change_password(VBox root, JFXButton btn) {
		root.setEffect(new BoxBlur(5, 5, 5));
		JFXAlert alert = new JFXAlert((Stage) btn.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Ndryshimi i passwordit ("+Utils.username+")"));
        VBox txtfields = new VBox();
        
        Label lblError = new Label();
        //hiqi kto setStyle veji tek css
        lblError.setStyle("-fx-text-fill : #B74636");
        txtfields.setMargin(lblError, new Insets(30, 0, 0, 0));
        
        JFXTextField existing_password = new JFXTextField();
        existing_password.setPromptText("Passwordi aktual: *");
        txtfields.setMargin(existing_password, new Insets(30, 0, 0, 0));
        existing_password.setLabelFloat(true);
        
        JFXTextField new_password = new JFXTextField();
        new_password.setPromptText("Passwordi i ri: *");
        txtfields.setMargin(new_password, new Insets(50, 0, 50, 0));
        new_password.setLabelFloat(true);
        
        JFXTextField repeat_new_password = new JFXTextField();
        repeat_new_password.setPromptText("Perserit passwordin: *");
        repeat_new_password.setLabelFloat(true);
        
        JFXButton change_pass = new JFXButton("Konfirmo");
        change_pass.setStyle("-fx-background-color: #4186CE; -fx-text-fill: white;-fx-cursor: hand;");
        change_pass.setDefaultButton(true);
        
        JFXButton goBack = new JFXButton("Anullo");
        goBack.setStyle("-fx-background-color: #B74636;-fx-text-fill: white;-fx-cursor: hand;");
        
        txtfields.getChildren().addAll(existing_password,new_password,repeat_new_password, goBack, change_pass, lblError);
       
        layout.setBody(txtfields);
        change_pass.setOnAction(e -> {
        	 if(Utils.check_empty_text(existing_password.getText().toString(),new_password.getText().toString(),repeat_new_password.getText().toString())) {
             	lblError.setText("Ju lutem plotesoni te gjitha fushat!*");
             	return;
             }
        	 try {
				if(!ControlDAO.getControlDao().getLoginDao().get_pass().equals(existing_password.getText().toString())) {
					lblError.setText("Passwordi i vjetër nuk është i saktë!"); 
					return;
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
     		
     		if (!new_password.getText().toString().equals(repeat_new_password.getText().toString())) {
     			lblError.setText("Passwordi i ri nuk është njësoj!"); 
     			return;
     		}
     		
     		perdoruesit p = new perdoruesit();
     		p.setPassword(new_password.getText().toString());
     		try {
				ControlDAO.getControlDao().getLoginDao().update_password(p);
				Helpers.insertAktiviteti("Ndryshim Fjalekalimi", "Info");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
     		
        	alert.hideWithAnimation();
        	root.setEffect(null);
        });
        
        goBack.setOnAction(e -> {
        	alert.hideWithAnimation();
        	root.setEffect(null);
        });
        layout.setActions(change_pass, goBack);
        alert.setContent(layout);
        alert.show();
	}
	
	public static ImageView setPhoto(ImageView img ,String record) {
    	File f = new File(global.INSTALL_DIR_IMAGES + record);
    	if(f.exists())
			  img = new ImageView(global.TO_FILE + global.INSTALL_DIR_IMAGES + record);
    	img.setFitWidth(40);
		img.setFitHeight(40);
		return img;
    }
	
	public static void insertAktiviteti(String emri_aktiviteti, String moduli) {
		perdoruesit p = new perdoruesit();
    	p.setUserid(Utils.idP);
    	
    	aktiviteti a = new aktiviteti();
    	a.setEmri_aktiviteti(emri_aktiviteti);
    	a.setUserid(p);
    	a.setModuli(moduli);
    	try {
			ControlDAO.getControlDao().getAktivitetiDao().insertAktivitet(a);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void openPhoto(String foto, TableView<?> tblView) {
		JFXAlert alert = new JFXAlert((Stage) tblView.getScene().getWindow());
    	Utils.alert_fshirje(alert,"", null, null, true, global.TO_FILE + global.INSTALL_DIR_IMAGES + foto);
	}
	
}
