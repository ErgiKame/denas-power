package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.furnizime;
import model.karburanti;
import model.pastrime;
import model.transporti;
import utils.Helpers;
import utils.Utils;

public class karburantiController extends VBox {
	
	@FXML
	private JFXButton btnAdd, btnEdit, btnPdf, btnExcel;
	
	@FXML
	private TableView<karburanti> tblKarburanti;
	
	@FXML
	private TableColumn<karburanti, String> tblColData, tblColPerdoruesi, tblColLloji, tblColKarburantiid;
	
	@FXML 
	private TableColumn<karburanti, karburanti> tblColDelete;
	
	@FXML
	private TableColumn<karburanti, Image> tblColFotoFature;
	
	@FXML
	private TableColumn<karburanti, Double> tblColSasia, tblColCmimi, tblColVlera;
	
	@FXML
	private JFXTextField txtSearch;
	
	@FXML private JFXDatePicker datePickerFrom, datePickerTo;
	
	private ObservableList<karburanti> tableViewData = FXCollections.observableArrayList();
	
	public static karburanti karburantiDataHolder = new karburanti();
	
	public static boolean edit = false;
	
	public karburantiController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/karburanti.fxml"));

		fxml.setRoot(this);
		fxml.setController(this);
		try {
			fxml.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		
		Helpers.convertDatepicker(datePickerFrom,datePickerTo);
		datePickerTo.setValue(LocalDate.now());
		datePickerFrom.setValue(datePickerTo.getValue().minusMonths(3));
		try {
			loadKarburanti();
			searchTableview();
			clickItem(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clickItem(MouseEvent event) {
	    tblKarburanti.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	 	if(tblKarburanti.getSelectionModel().getSelectedCells().get(0).getColumn() == 6 && tblKarburanti.getSelectionModel().getSelectedItem().getFoto_fature() != null) { 
	        			Helpers.openPhoto(tblKarburanti.getSelectionModel().getSelectedItem().getFoto_fature(), tblKarburanti);
	        	 	}
	        	 	else
	        	 		return;
	        	
	        }
	    });
	}
	
	public void searchTableview() {
		txtSearch.textProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable o) {

	                if(txtSearch.textProperty().get().isEmpty()) {
	                	tblKarburanti.setItems(tableViewData);
	                	return;
	                }

	                ObservableList<karburanti> tableItems = FXCollections.observableArrayList();
	                ObservableList<TableColumn<karburanti, ?>> cols = tblKarburanti.getColumns();
	               
	                for(int i=0; i<tableViewData.size(); i++) {
	                	for(int j=0; j<6; j++) {
	                		TableColumn<karburanti, ?> col = cols.get(j);
	                		String cellValue = col.getCellData(tableViewData.get(i)).toString();
	                		cellValue = cellValue.toLowerCase();
	                			if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
	                					tableItems.add(tableViewData.get(i));
	                					break;
	                				}                        
	                			}
	                		}

	                tblKarburanti.setItems(tableItems);
				}
	     });
	}
	
	private void loadKarburanti() throws SQLException {
		tblKarburanti.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getKarburantiDao().viewKarburanti(datePickerFrom.getValue(), datePickerTo.getValue()));
		tblColData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tblColPerdoruesi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<karburanti, String>, ObservableValue<String>>() {
	           @Override
	            public ObservableValue<String> call(TableColumn.CellDataFeatures<karburanti, String> obj) {
	                return new SimpleStringProperty(obj.getValue().getUserid().getUsername());
	            }
	        });
		tblColLloji.setCellValueFactory(new PropertyValueFactory<>("lloji"));
		tblColSasia.setCellValueFactory(new PropertyValueFactory<>("sasia"));
		tblColCmimi.setCellValueFactory(new PropertyValueFactory<>("cmimi"));
		tblColVlera.setCellValueFactory(new PropertyValueFactory<>("vlera"));
		tblColKarburantiid.setCellValueFactory(new PropertyValueFactory<>("karburantiid"));
		tblColFotoFature.setCellValueFactory(new PropertyValueFactory<>("photo_fature"));
		
		tblColDelete.setStyle("-fx-alignment:center;");
		tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tblColDelete.setCellFactory(param -> new TableCell<karburanti, karburanti>() {

			Button delete = new Button("");
			protected void updateItem(karburanti k, boolean empty) {
				super.updateItem(k, empty);

				if (k == null) {
					setGraphic(null);
					return;
				}

				if (!k.getUserid().getUsername().equals(Utils.username)) {
					delete.setDisable(true);
				}
				else {
					delete.setDisable(false);
				}

				setGraphic(delete);
				Utils.style_delete_button(delete);
				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblKarburanti.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #B74636; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #4186CE; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert,"karburantin?", konfirmo, anullo, false, "");
					//konfirmo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());
					//anullo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());

					//konfirmo.getStyleClass().addAll("btnConfirm","btn","btnBlurred");
					//anullo.getStyleClass().addAll("btnLogout" , "btn", "btnBlurred");
					
					konfirmo.setOnAction(e-> {
						delete(k.getKarburantiid());
						alert.close();
					}); 
					anullo.setOnAction( e1 -> {
						alert.close();
					});
					
					Utils.refresh_focus_table(tblKarburanti);
				});
			}
		});
		

		if(Utils.rights.contentEquals("User")) {
			btnPdf.setVisible(false);
			btnExcel.setVisible(false);
			tblColDelete.setVisible(true);
		//	tblColPerdoruesi.setVisible(false);
		}
		else {
			btnAdd.setVisible(false);
			btnEdit.setVisible(false);
			tblColDelete.setVisible(false);
		//	tblColPerdoruesi.setVisible(true);
		}

		tblKarburanti.setItems(tableViewData);
	}
	
	
	@FXML
	private void add() throws IOException, SQLException {
		edit = false;
		new Utils().open_edit_scene("karburantiShto", "fuel");
		if(!karburantiShtoController.isAnullo)
			loadKarburanti();
	}

	@FXML
	private void edit() throws IOException, SQLException {
		edit = true;
		if(tblKarburanti.getSelectionModel().getSelectedItem() != null) 
			getData();
		
		else
			Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", AlertType.WARNING);
	}
		
	private void getData() throws IOException, SQLException {
		karburanti karburanti = tblKarburanti.getSelectionModel().getSelectedItem();
		karburantiDataHolder.setData(karburanti.getData());
		karburantiDataHolder.setLloji(karburanti.getLloji());
		karburantiDataHolder.setSasia(karburanti.getSasia());
		karburantiDataHolder.setCmimi(karburanti.getCmimi());
		karburantiDataHolder.setVlera(karburanti.getVlera());
		karburantiDataHolder.setKarburantiid(karburanti.getKarburantiid());
		karburantiDataHolder.setFoto_fature(karburanti.getFoto_fature());
		
		new Utils().open_edit_scene("karburantiShto", "fuel");
		if(!karburantiShtoController.isAnullo)
			loadKarburanti();
	}

	@FXML
	private void excel() {
		try {

			Stage stage = (Stage)btnExcel.getScene().getWindow();

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Comma Delimited (*.csv)", "*.csv");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(stage);
			FileWriter fileWriter = new FileWriter(file);

			String text = "";
			String header = "Nr" + "," + "Data" + ","  + "Perdoruesi" + "," + "Lloji" + "," + "Sasia"  + "," + "Cmimi" + "," + "Vlera" +"\n" ;

			fileWriter.write(header);
			for(int i=0; i<tableViewData.size(); i++){

				text = i+1 + "," + tableViewData.get(i).getData()+ "," + tableViewData.get(i).getUserid().getUsername() + "," + tableViewData.get(i).getLloji() + "," 
				+ tableViewData.get(i).getSasia() + "," + tableViewData.get(i).getCmimi() + "," + tableViewData.get(i).getVlera()+"\n";
				fileWriter.write(text);
			}

			fileWriter.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@FXML
	private void pdf() {
		try {

			Stage stage = (Stage)btnExcel.getScene().getWindow();

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf","*.PDF");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(stage);

			Document document = new Document(PageSize.A4.rotate(), 5f, 5f, 5f, 5f);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(file.getAbsolutePath()));

			document.open();

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			Anchor anchorTarget = new Anchor("Data "+dateFormat.format(date) + " Ora " + sdf.format(cal.getTime()));

			Paragraph paragraph1 = new Paragraph();
			paragraph1.setAlignment(Paragraph.ALIGN_RIGHT);
			paragraph1.setSpacingBefore(15);
			paragraph1.setSpacingAfter(15);

			paragraph1.add(anchorTarget);
			document.add(paragraph1);

			Paragraph p2 = new Paragraph("Denas Power Management",FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, new CMYKColor(169,169,169,0)));
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setSpacingBefore(20);		
			document.add(p2);

			Paragraph p3 = new Paragraph("Karburanti",FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new BaseColor(183, 70, 54)));
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setSpacingBefore(25);		
			document.add(p3);

			PdfPTable t = new PdfPTable(6);
			t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			t.setSpacingBefore(30);
			t.setWidthPercentage(95);
			t.setSpacingAfter(5);
			Font bold = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

			Phrase phrase1 = new Phrase("Data", bold);
			PdfPCell c1 = new PdfPCell(phrase1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c1);
			Phrase phrase2 = new Phrase("Perdoruesi", bold);
			PdfPCell c2 = new PdfPCell(phrase2);
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c2);
			Phrase phrase3 = new Phrase("Lloji", bold);
			PdfPCell c3 = new PdfPCell(phrase3);
			c3.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c3);
			Phrase phrase4 = new Phrase("Sasia", bold);
			PdfPCell c4 = new PdfPCell(phrase4);
			c4.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c4);
			Phrase phrase5 = new Phrase("Cmimi", bold);
			PdfPCell c5 = new PdfPCell(phrase5);
			c5.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c5);
			Phrase phrase6 = new Phrase("Vlera", bold);
			PdfPCell c6 = new PdfPCell(phrase6);
			c6.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c6);

			for(karburanti table_pdf : tableViewData){
				t.addCell(table_pdf.getData().toString());
				t.addCell(table_pdf.getUserid().getUsername());
				t.addCell(table_pdf.getLloji());
				t.addCell(table_pdf.getSasia() + "");
				t.addCell(table_pdf.getCmimi() + "");
				t.addCell(table_pdf.getVlera() + "");

			}
			document.add(t);

			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(95);
			table.setSpacingBefore(50);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			document.add(table);
			document.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void delete(int karburantiid) {
		try {
			ControlDAO.getControlDao().getKarburantiDao().deleteKarburanti(karburantiid);
			loadKarburanti();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	@FXML
	private void filter_date() throws SQLException {
		loadKarburanti();
		searchTableview();
	}
}
