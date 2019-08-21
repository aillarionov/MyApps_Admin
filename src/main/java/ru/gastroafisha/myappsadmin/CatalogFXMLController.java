package ru.gastroafisha.myappsadmin;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.gastroafisha.myappsadmin.model.CatalogProxy;
import ru.gastroafisha.myappsadmin.model.CatalogSource;
import ru.gastroafisha.myappsadmin.model.CatalogType;

/**
 * FXML Controller class
 *
 * @author alex
 */
public class CatalogFXMLController implements Initializable {

    @FXML
    private ComboBox<CatalogType> typeComboBox;
    @FXML
    private ComboBox<CatalogSource> sourceComboBox;

    @FXML
    private TextField sourceIdTextField;
    @FXML
    private TextField albumIdTextField;

    @FXML
    private TextField filterTextField;
    @FXML
    private TextField visitorFilterTextField;
    @FXML
    private TextField presenterFilterTextField;

    @FXML
    private CheckBox fixedPostCheckBox;
    @FXML
    private CheckBox visitorVisibleCheckBox;
    @FXML
    private CheckBox presenterVisibleCheckBox;

    private final ObservableList<CatalogType> types = FXCollections.observableArrayList();
    private final ObservableList<CatalogSource> sources = FXCollections.observableArrayList();

    private CatalogProxy catalog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareControls();

        types.setAll(CatalogType.values());
        sources.setAll(CatalogSource.values());
    }

    private void prepareControls() {
        albumIdTextField.disableProperty().set(fixedPostCheckBox.selectedProperty().get());
        fixedPostCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                albumIdTextField.textProperty().set(null);
                albumIdTextField.disableProperty().set(true);
            } else {
                albumIdTextField.disableProperty().set(false);
            }
        });

        typeComboBox.setItems(types);
        sourceComboBox.setItems(sources);
    }

    public void showCatalog(CatalogProxy catalog) {

        this.catalog = catalog;

        sourceIdTextField.textProperty().set(catalog.getSourceOwner());
        if ("fixedPost".equals(catalog.getSourceAlbum())) {
            albumIdTextField.textProperty().set(null);
            fixedPostCheckBox.selectedProperty().set(true);
        } else {
            albumIdTextField.textProperty().set(catalog.getSourceAlbum());
            fixedPostCheckBox.selectedProperty().set(false);
        }

        visitorVisibleCheckBox.selectedProperty().set(catalog.getVisitorVisible() != null ? catalog.getVisitorVisible() : true);
        presenterVisibleCheckBox.selectedProperty().set(catalog.getPresenterVisible() != null ? catalog.getPresenterVisible() : true);

        typeComboBox.getSelectionModel().select(catalog.getType());
        sourceComboBox.getSelectionModel().select(catalog.getSource());

        visitorFilterTextField.textProperty().set(catalog.getVisitorNotificationFilter());
        presenterFilterTextField.textProperty().set(catalog.getPresenterNotificationFilter());

        if (catalog.getParams() == null) {
            catalog.setParams(new HashMap<>());
        }
        
        if (catalog.getParams().containsKey("filter")) {
            filterTextField.textProperty().set(catalog.getParams().get("filter"));
        } else {
            filterTextField.textProperty().set(null);
        }
    }

    private void saveCatalog() {
        if (catalog != null) {
            catalog.setType(typeComboBox.getSelectionModel().getSelectedItem());
            catalog.setSource(sourceComboBox.getSelectionModel().getSelectedItem());

            catalog.setVisitorNotificationFilter(visitorFilterTextField.textProperty().get());
            catalog.setPresenterNotificationFilter(presenterFilterTextField.textProperty().get());

            catalog.setVisitorVisible(visitorVisibleCheckBox.selectedProperty().get());
            catalog.setPresenterVisible(presenterVisibleCheckBox.selectedProperty().get());

            catalog.setSourceOwner(sourceIdTextField.textProperty().get());
            catalog.setSourceAlbum(fixedPostCheckBox.selectedProperty().get() ? "fixedPost" : albumIdTextField.textProperty().get());

            String filter = filterTextField.textProperty().get();
            if (filter == null || filter.isEmpty()) {
                if (catalog.getParams().containsKey("filter")) {
                    catalog.getParams().remove("filter");
                }
            } else {
                catalog.getParams().put("filter", filter);
            }

            if (catalog.getId() == null) {
                catalog.setId(0);
            }
        }
    }

    @FXML
    protected void handleSave() {
        saveCatalog();

        Stage stage = (Stage) typeComboBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleCancel() {
        Stage stage = (Stage) typeComboBox.getScene().getWindow();
        stage.close();
    }
}
