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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.transporti;
import utils.Helpers;
import utils.Utils;

public class transportiController extends VBox{

	@FXML
	private JFXTextField txtSearch;

	@FXML
	private JFXButton btnAdd, btnEdit, btnPdf, btnExcel;

	@FXML
	private TableView<transporti> tblTransporti;

	@FXML
	private TableColumn<transporti, String> tblColData, tblColPerdoruesi, tblColNisja, tblColMberritja, tblColDistanca,
	tblColKoment, tblColTransportiid;

/*	@FXML
	private TableColumn<transporti, Image> tblColFotoNisje, tblColFotoMberritje; 
*/

	@FXML 
	private TableColumn<transporti, transporti> tblColDelete;
	
	@FXML private JFXDatePicker datePickerFrom, datePickerTo;

	private ObservableList<transporti> tableViewData = FXCollections.observableArrayList();

	public static transporti transportiDataHolder = new transporti();
	public static boolean edit = false;

	public transportiController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/transporti.fxml"));

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
			loadTransporti();
			searchTableview();
//major update			clickItem(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
/*	
	public void clickItem(MouseEvent event) {
	    tblTransporti.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	 	if(tblTransporti.getSelectionModel().getSelectedCells().get(0).getColumn() == 6 && tblTransporti.getSelectionModel().getSelectedItem().getFoto_nisje() != null) {
	        			Helpers.openPhoto(tblTransporti.getSelectionModel().getSelectedItem().getFoto_nisje(), tblTransporti);
	        	 	}
	        	 	else if(tblTransporti.getSelectionModel().getSelectedCells().get(0).getColumn() == 7 && tblTransporti.getSelectionModel().getSelectedItem().getFoto_mberritje() != null) { 
	        			Helpers.openPhoto(tblTransporti.getSelectionModel().getSelectedItem().getFoto_mberritje(), tblTransporti);
	        		}
	        		else
	        			return;
	        	
	        }
	    });
	}
*/
	public void searchTableview() {
		txtSearch.textProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable o) {

	                if(txtSearch.textProperty().get().isEmpty()) {
	                	tblTransporti.setItems(tableViewData);
	                	return;
	                }

	                ObservableList<transporti> tableItems = FXCollections.observableArrayList();
	                ObservableList<TableColumn<transporti, ?>> cols = tblTransporti.getColumns();
	               
	                for(int i=0; i<tableViewData.size(); i++) {
	                	for(int j=0; j<6; j++) {
	                		TableColumn<transporti, ?> col = cols.get(j);
	                		String cellValue = col.getCellData(tableViewData.get(i)).toString();
	                		cellValue = cellValue.toLowerCase();
	                			if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
	                					tableItems.add(tableViewData.get(i));
	                					break;
	                				}                        
	                			}
	                		}

	                tblTransporti.setItems(tableItems);
				}
	     });
	}

	private void loadTransporti() throws SQLException {
		tblTransporti.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getTransportiDao().viewTransporti(datePickerFrom.getValue(), datePickerTo.getValue()));	
		tblColData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tblColPerdoruesi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<transporti, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<transporti, String> obj) {
				return new SimpleStringProperty(obj.getValue().getUserid().getUsername());
			}
		});
		tblColNisja.setCellValueFactory(new PropertyValueFactory<>("nisja"));
		tblColMberritja.setCellValueFactory(new PropertyValueFactory<>("mberritja"));
	
		tblColDistanca.setCellValueFactory(new PropertyValueFactory<>("distanca"));
		tblColKoment.setCellValueFactory(new PropertyValueFactory<>("koment"));
	/*	tblColKoment.setCellFactory(tc -> {
		    TableCell<transporti, String> cell = new TableCell<>();
		    Text text = new Text();
		    cell.setStyle("-fx-alignment:center;  -fx-text-fill: #B74636;");
		    cell.setGraphic(text);
		    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
		    text.wrappingWidthProperty().bind(tblColKoment.widthProperty());
		    text.textProperty().bind(cell.itemProperty());
		    return cell ;
		}); */
		tblColTransportiid.setCellValueFactory(new PropertyValueFactory<>("transportiid"));
/*		tblColFotoNisje.setCellValueFactory(new PropertyValueFactory<>("photo_nisje"));
		tblColFotoMberritje.setCellValueFactory(new PropertyValueFactory<>("photo_mberritje"));
*/ //major update		
		//tblColData.setCellValueFactory(new PropertyValueFactory<>("created_date"));

		tblColDelete.setStyle("-fx-alignment:center;");
		tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tblColDelete.setCellFactory(param -> new TableCell<transporti, transporti>() {

			Button delete = new Button("");
			protected void updateItem(transporti t, boolean empty) {
				super.updateItem(t, empty);

				if (t == null) {
					setGraphic(null);
					return;
				}

				if (!t.getUserid().getUsername().equals(Utils.username)) {
					delete.setDisable(true);
				}
				else {
					delete.setDisable(false);
				}

				setGraphic(delete);
				Utils.style_delete_button(delete);
				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblTransporti.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #B74636; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #4186CE; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert,"transportin?", konfirmo, anullo, false, "");
					//konfirmo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());
					//anullo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());

					//konfirmo.getStyleClass().addAll("btnConfirm","btn","btnBlurred");
					//anullo.getStyleClass().addAll("btnLogout" , "btn", "btnBlurred");

					konfirmo.setOnAction(e-> {
						delete(t.getTransportiid());
						alert.close();
					}); 
					anullo.setOnAction( e1 -> {
						alert.close();
					});
					Utils.refresh_focus_table(tblTransporti);
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
		tblTransporti.setItems(tableViewData);
	}

	@FXML
	private void add() throws IOException, SQLException {
		edit = false;
		new Utils().open_edit_scene("transportiShto", "transporti");
		if(!transportiShtoController.isAnullo)
			loadTransporti();
	}

	@FXML
	private void edit() throws IOException, SQLException {
		edit = true;
		if(tblTransporti.getSelectionModel().getSelectedItem() != null) 
			getData();

		else
			Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", AlertType.WARNING);
	}

	private void getData() throws IOException, SQLException {
		transporti transport = tblTransporti.getSelectionModel().getSelectedItem();
		transportiDataHolder.setTransportiid(transport.getTransportiid());
		transportiDataHolder.setData(transport.getData());
		transportiDataHolder.setNisja(transport.getNisja());
		transportiDataHolder.setMberritja(transport.getMberritja());
		transportiDataHolder.setKoment(transport.getKoment());
		transportiDataHolder.setDistanca(transport.getDistanca());
		
/*		transportiDataHolder.setFoto_nisje(transport.getFoto_nisje());
		transportiDataHolder.setFoto_mberritje(transport.getFoto_mberritje());
*/		//major update	
		new Utils().open_edit_scene("transportiShto", "transporti");
		if(!transportiShtoController.isAnullo)
			loadTransporti();
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
			String header = "Nr" + "," + "Data" + "," + "Perdoruesi" + "," + "Nisja"  + "," + "Mberritja" + "," + "Distanca" + "," + "Koment" +"\n" ;

			fileWriter.write(header);
			for(int i=0; i<tableViewData.size(); i++){

				text = i+1 + "," + tableViewData.get(i).getData()+ "," + tableViewData.get(i).getUserid().getUsername() + "," + tableViewData.get(i).getNisja()+ "," 
				+ tableViewData.get(i).getMberritja() + "," + tableViewData.get(i).getDistanca() + "," + tableViewData.get(i).getKoment()+"\n";
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

			Paragraph p3 = new Paragraph("Transporti",FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new BaseColor(183, 70, 54)));
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
			Phrase phrase3 = new Phrase("Nisja", bold);
			PdfPCell c3 = new PdfPCell(phrase3);
			c3.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c3);
			Phrase phrase4 = new Phrase("Mberritja", bold);
			PdfPCell c4 = new PdfPCell(phrase4);
			c4.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c4);
			Phrase phrase5 = new Phrase("Distanca", bold);
			PdfPCell c5 = new PdfPCell(phrase5);
			c5.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c5);
			Phrase phrase6 = new Phrase("Koment", bold);
			PdfPCell c6 = new PdfPCell(phrase6);
			c6.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c6);

			for(transporti table_pdf : tableViewData){
				t.addCell(table_pdf.getData().toString());
				t.addCell(table_pdf.getUserid().getUsername());
				t.addCell(table_pdf.getNisja() + "");
				t.addCell(table_pdf.getMberritja() + "");
				t.addCell(table_pdf.getDistanca() + "");
				t.addCell(table_pdf.getKoment());

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


	private void delete(int transportiid) {
		try {
			ControlDAO.getControlDao().getTransportiDao().deleteTransporti(transportiid);
			loadTransporti();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void filter_date() throws SQLException {
		loadTransporti();
		searchTableview();
	}

}
