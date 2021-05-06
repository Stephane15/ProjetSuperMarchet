package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SuperMarcherController implements Initializable {

    @FXML
    private TextField txtPrix;

    @FXML
    private ComboBox<String> cboLocation;

    @FXML
    private TableColumn<SuperMarcher, Double> prixColumn;

    @FXML
    private TableColumn<SuperMarcher,String> locationColumn;

    @FXML
    private TableView<SuperMarcher> supermarcheTable;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TableColumn<SuperMarcher, String> itemColumn;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnEffacer;

    @FXML
    private TableColumn<SuperMarcher, Double> quantityColumn;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField txtItem;
    
    
    
    
    
    

	private ObservableList<String> list=(ObservableList<String>) FXCollections.observableArrayList("Loblaws", "Metro", "Longos");


	public ObservableList<SuperMarcher> supermarcherData=FXCollections.observableArrayList();

	public ObservableList<SuperMarcher> supermarcherData()
		{
			return supermarcherData;
		}

	@Override
	public void initialize(URL location, ResourceBundle resources)
		{
			cboLocation.setItems(list);

			itemColumn.setCellValueFactory(new PropertyValueFactory<>("Article"));
			quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantité"));
			prixColumn.setCellValueFactory(new PropertyValueFactory<>("Prix"));
			locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
			supermarcheTable.setItems(supermarcherData);

			btnModifier.setDisable(true);
			btnEffacer.setDisable(true);
			btnClear.setDisable(true);


			showSupermarchers(null);

			supermarcheTable.getSelectionModel().selectedItemProperty().addListener((
					observable, oldValue, newValue)-> showSupermarchers(newValue));

		}

	@FXML
	void ajouter()
		{

			if(noEmptyInput())
			{


				SuperMarcher tmp=new SuperMarcher ();
				tmp=new SuperMarcher();
				
				tmp.setArticle(txtItem.getText());
				tmp.setQuantity(Double.parseDouble(txtQuantity.getText()));
				tmp.setPrix(Double.parseDouble(txtPrix.getText()));
				tmp.setLocation(cboLocation.getValue());
				supermarcherData.add(tmp);
				clearFields();
			}

		}


	@FXML
	void clearFields()
		{
			cboLocation.setValue(null);
			txtItem.setText(" ");
			txtQuantity.setText(" ");
			txtPrix.setText(" ");
		}


	public void showSupermarchers(SuperMarcher supermarcher)
		{
			if(supermarcher !=null)
			{
				cboLocation.setValue(supermarcher.getLocation());
				txtItem.setText(supermarcher.getArticle());
				txtQuantity.setText(Double.toString(supermarcher.getQuantity()));
				txtPrix.setText(Double.toString(supermarcher.getPrix()));
				btnModifier.setDisable(false);
				btnEffacer.setDisable(false);
				btnClear.setDisable(false);



			}
			else
			{
				clearFields();
			}
		}


	@FXML
	public void updateSuperMarcher()
		{

			if(noEmptyInput())
			{

				SuperMarcher supermarcher=supermarcheTable.getSelectionModel().getSelectedItem();

				supermarcher.setArticle(txtItem.getText());
				supermarcher.setQuantity(Double.parseDouble(txtQuantity.getText()));
				supermarcher.setPrix(Double.parseDouble(txtPrix.getText()));
				supermarcher.setLocation(cboLocation.getValue());
				supermarcheTable.refresh();
			}
		}

	@FXML
	public void deleteSuperMarcher()
		{
			int selectedIndex = supermarcheTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex >=0)
			{
				Alert alert=new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Effacer");
				alert.setContentText("Confirmer la supression!");
				Optional<ButtonType> result=alert.showAndWait();
				if(result.get()==ButtonType.OK);
				supermarcheTable.getItems().remove(selectedIndex);
			}
		}


	private boolean noEmptyInput()
		{
			String errorMessage="";
			if(txtItem.getText().trim().equals(""))
			{
				errorMessage+="Le champ Article ne peut pas etre vide! \n";
			}
			if(txtQuantity.getText()==null|| txtQuantity.getText().length()==0)
			{
				errorMessage+="Le champ Quantité ne peut pas etre vide! \n";
			}
			if(txtPrix.getText()==null|| txtPrix.getText().length()==0)
			{
				errorMessage+="Le champ de prix ne peut pas etre vide! \n";
			}
			if(cboLocation.getValue()==null)
			{
				errorMessage+="Le champ de Location ne peut pas etre vide! \n";
			}

			if(errorMessage.length()==0)
			{
				return true;
			}
			else
			{
				Alert alert=new Alert(AlertType.ERROR);
				alert.setTitle("Champs manquants");
				alert.setHeaderText("Completer les champs manquants");
				alert.setContentText(errorMessage);
				alert.showAndWait();
				return false;
			}

		}





	//sauvegarde de données

	public File getSuperMarcherFilePath()
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			String filePath = prefs.get("filePath", null);

			if (filePath !=null)
			{
				return new File(filePath);
			}
			else
			{
				return null;
			}
		}
	

	public void setSuperMarcherFilePath(File file)
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			if(file != null)
			{
				prefs.put("filePath", file.getPath());
			}
			else
			{
				prefs.remove("filePath");
			}

		}


	public void loadSuperMarcherDataFromFile(File file) 
		{ 
			try {
				JAXBContext context =  JAXBContext.newInstance(SuperMarcherListWrapper.class);
				Unmarshaller um = context.createUnmarshaller();

				SuperMarcherListWrapper wrapper = (SuperMArcherListWrapper) um.unmarshal(file);
				supermarcherData.clear();
				supermarcherData.addAll(wrapper.getSuperMarchers());
				setSuperMarcherFilePath(file);

				Stage pStage=(Stage) supermarcheTable.getScene().getWindow();
				pStage.setTitle(file.getName());


			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erruer");
				alert.setTitle("les données n'ont pas été trouvées");
				alert.setContentText("Les données ne pouvaient pas etre trouvées dans le fichier : \n" + file.getPath());
				alert.showAndWait();
			}
		}
	
	public void saveSuperMarcherDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(SuperMarcherListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			SuperMarcherListWrapper wrapper = new SuperMarcherListWrapper();
			wrapper.setSuperMarcher(supermarcherData);

			m.marshal(wrapper, file);

			setSuperMarcherFilePath(file);

			Stage pStage=(Stage) supermarcheTable.getScene().getWindow();
			pStage.setTitle(file.getName());



		}catch (Exception e) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Donnés non sauvegardées");
			alert.setContentText(" Les données ne pouvaient pas etre sauvegardées dans le fichier:\n" + file.getPath());
			alert.showAndWait();


		}
}


// Start a new file
@FXML
private void handleNew() 
	{
		supermarcherData().clear();
		setSuperMarcherFilePath(null);

	}

@FXML
private void handleOpen()
	{
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter( "XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			loadSuperMarcherDataFromFile(file);

		}

	}


@FXML
private void handleSave()
	{
		File supermarcherFile = getSuperMarcherFilePath();
		if (supermarcherFile != null) {
			saveSuperMarcherDataToFile(supermarcherFile);
		} else {
			handleSaveAs();
		}
	}


@FXML
private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter( "XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml" );
			}
			saveSuperMarcherDataToFile(file);
		}

}


@FXML
public void verifNum()
	{
		txtQuantity.textProperty().addListener((observable,oldValue,newValue)->
			{
				if(!newValue.matches("^[0-9](\\.[0-9]+)?$"))
				{
					txtQuantity.setText(newValue.replaceAll("[^\\d*\\.]", ""));
				}
			});
	}



	
	
}
