package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SuperMarcherController {

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
    

}
