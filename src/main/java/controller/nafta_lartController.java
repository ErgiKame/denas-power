package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.nafta_lart;
import model.pastrime;
import utils.Helpers;
import utils.Utils;

public class nafta_lartController extends VBox {

	@FXML
	private TableView<nafta_lart> tblNaftaLart;
	
	@FXML 
	private TableColumn<nafta_lart, nafta_lart> tblColDelete;
	
	@FXML
	private JFXButton btnHyrje, btnDalje, btnPdf, btnLlogarit;
	
	@FXML
	private TableColumn<nafta_lart, String> tblColData, tblColMakina, tblColSasia, tblColPershkrimi, tblColNaftaLartId;
	
	@FXML
	private JFXTextField txtSearch;
	
	@FXML 
	private JFXDatePicker datePickerFrom, datePickerTo;
	
	@FXML
	private Label lblGjendja;

	private ObservableList<nafta_lart> tableViewData = FXCollections.observableArrayList();
	private ObservableList<nafta_lart> tableViewDataPdf = FXCollections.observableArrayList();

	public static nafta_lart nafta_lartDataHolder = new nafta_lart();
	
	public static boolean edit = false;
	
	public nafta_lartController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/nafta_lart.fxml"));

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
			loadNaftaLart();
			searchTableview();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void searchTableview() {
		txtSearch.textProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable o) {

	                if(txtSearch.textProperty().get().isEmpty()) {
	                	tblNaftaLart.setItems(tableViewData);
	                	return;
	                }

	                ObservableList<nafta_lart> tableItems = FXCollections.observableArrayList();
	                ObservableList<TableColumn<nafta_lart, ?>> cols = tblNaftaLart.getColumns();
	               
	                for(int i=0; i<tableViewData.size(); i++) {
	                	for(int j=0; j<4; j++) {
	                		TableColumn<nafta_lart, ?> col = cols.get(j);
	                		String cellValue = col.getCellData(tableViewData.get(i)).toString();
	                		cellValue = cellValue.toLowerCase();
	                			if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
	                					tableItems.add(tableViewData.get(i));
	                					break;
	                				}                        
	                			}
	                		}

	                tblNaftaLart.setItems(tableItems);
				}
	     });
	}
	
	private void loadNaftaLart() throws SQLException {
		
		lblGjendja.setText(ControlDAO.getControlDao().getNafta_lartDao().getGjendja() + "");
		tblNaftaLart.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getNafta_lartDao().viewNafta_lart(datePickerFrom.getValue(), datePickerTo.getValue()));
		tblColData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tblColPershkrimi.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));
		tblColMakina.setCellValueFactory(new PropertyValueFactory<>("lloji_makines"));
		tblColSasia.setCellValueFactory(new PropertyValueFactory<>("sasia_dalje"));
		tblColNaftaLartId.setCellValueFactory(new PropertyValueFactory<>("pershkrimi"));
		tblColDelete.setStyle("-fx-alignment:center;");
		tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tblColDelete.setCellFactory(param -> new TableCell<nafta_lart, nafta_lart>() {

			Button delete = new Button("");
			protected void updateItem(nafta_lart na, boolean empty) {
				super.updateItem(na, empty);

				if (na == null) {
					setGraphic(null);
					return;
				}

				
				setGraphic(delete);
				Utils.style_delete_button(delete);
				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblNaftaLart.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #B74636; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #4186CE; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert,"rreshtin?", konfirmo, anullo, false, "");
					//konfirmo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());
					//anullo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());

					//konfirmo.getStyleClass().addAll("btnConfirm","btn","btnBlurred");
					//anullo.getStyleClass().addAll("btnLogout" , "btn", "btnBlurred");
					
					konfirmo.setOnAction(e-> {
						delete(na.getNafta_larid());
						alert.close();
					}); 
					anullo.setOnAction( e1 -> {
						alert.close();
					});
					
					Utils.refresh_focus_table(tblNaftaLart);
				});
			}
		});
		

		if(Utils.name.contentEquals("Jola") || Utils.name.contentEquals("HeadAdmin")) {
			btnHyrje.setVisible(true);
			btnDalje.setVisible(true);
			btnPdf.setVisible(true);
			btnLlogarit.setVisible(true);
			lblGjendja.setVisible(true);
			tblColDelete.setVisible(true);
		}
		else {
			btnHyrje.setVisible(false);
			btnDalje.setVisible(false);
			btnPdf.setVisible(true);
			btnLlogarit.setVisible(true);
			lblGjendja.setVisible(true);
			tblColDelete.setVisible(false);
		}

		tblNaftaLart.setItems(tableViewData); 
	}
	
	
	@FXML
	private void hyrje() throws IOException, SQLException {
		new Utils().open_edit_scene("nafta_lartHyrje", "fuel");
		loadNaftaLart();
	}

	@FXML
	private void dalje() throws IOException, SQLException {
		new Utils().open_edit_scene("nafta_lartDalje", "fuel");
		loadNaftaLart();
		}
	
	@FXML
	private void llogarit() throws IOException, SQLException {
		new Utils().open_edit_scene("nafta_lartLlogarit", "calc");
		loadNaftaLart();
	}



	@FXML
	private void pdf() {
		try {
			tableViewDataPdf.addAll(ControlDAO.getControlDao().getNafta_lartDao().getSasiaSipasLlojit(datePickerFrom.getValue(), datePickerTo.getValue()));
			Stage stage = (Stage)btnHyrje.getScene().getWindow();

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

			Paragraph p3 = new Paragraph("Konsumi sipas makinerive ne datat e zgjedhura",FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new BaseColor(183, 70, 54)));
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setSpacingBefore(25);		
			document.add(p3);

			PdfPTable t = new PdfPTable(2);
			t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			t.setSpacingBefore(30);
			t.setWidthPercentage(95);
			t.setSpacingAfter(5);
			Font bold = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

			Phrase phrase1 = new Phrase("Makina", bold);
			PdfPCell c1 = new PdfPCell(phrase1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c1);
			Phrase phrase2 = new Phrase("Sasia", bold);
			PdfPCell c2 = new PdfPCell(phrase2);
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c2);
			
			double sum = 0;
			
			for(nafta_lart table_pdf : tableViewDataPdf){
				t.addCell(table_pdf.getLloji_makines());
				t.addCell(String.valueOf(table_pdf.getSasia_dalje()));
				sum+=table_pdf.getSasia_dalje();

			}
			document.add(t);

			DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			Paragraph p4 = new Paragraph("Totali i konsumuar nga data " + datePickerFrom.getValue().format(customFormatter) + " deri ne daten " + datePickerTo.getValue().format(customFormatter) + " eshte:  " + sum,FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new BaseColor(183, 70, 54)));
			p4.setAlignment(Paragraph.ALIGN_CENTER);
			p4.setSpacingBefore(25);		
			document.add(p4);
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(95);
			table.setSpacingBefore(50);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			document.add(table);
			document.close();
			tableViewDataPdf.clear();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void delete(int nafta_lartid) {
		try {
			ControlDAO.getControlDao().getNafta_lartDao().deleteNafta_lart(nafta_lartid);
			loadNaftaLart();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		
	@FXML
	private void filter_date() throws SQLException {
		loadNaftaLart();
		searchTableview();
	}
	
}
	
