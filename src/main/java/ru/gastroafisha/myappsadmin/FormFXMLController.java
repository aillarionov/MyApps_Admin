package ru.gastroafisha.myappsadmin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.gastroafisha.myappsadmin.cell.FormItemCell;
import ru.gastroafisha.myappsadmin.model.FormItemProxy;
import ru.gastroafisha.myappsadmin.model.FormItemType;
import ru.gastroafisha.myappsadmin.model.FormProxy;

/**
 * FXML Controller class
 *
 * @author alex
 */
public class FormFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private ListView<FormItemProxy> itemsListView;

    @FXML
    private TextField codeTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField defaultTextField;
    @FXML
    private ComboBox<FormItemType> typeComboBox;
    @FXML
    private CheckBox requiredCheckBox;
    
    @FXML
    private TextField dataEmailTextField;

    private final ObservableList<FormItemProxy> items = FXCollections.observableArrayList();
    private final ObservableList<FormItemType> types = FXCollections.observableArrayList();

    private FormProxy form;

    private Integer minId = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareControls();

        types.addAll(FormItemType.values());
        itemsListView.setItems(items);
    }

    private void prepareControls() {
        typeComboBox.setItems(types);

        itemsListView.setCellFactory(param -> new FormItemCell());

        itemsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends FormItemProxy> observable, FormItemProxy oldValue, FormItemProxy newValue) -> {
            if (newValue != null) {
                codeTextField.textProperty().set(newValue.getName());
                titleTextField.textProperty().set(newValue.getTitle());
                defaultTextField.textProperty().set(newValue.getDefaultValue());
                typeComboBox.getSelectionModel().select(newValue.getType());
                requiredCheckBox.selectedProperty().set(newValue.getRequired());
            }
        });
    }

    public void showForm(FormProxy form) {
        this.form = form;

        nameTextField.textProperty().set(form.getName());
        dataEmailTextField.textProperty().set(form.getDataEmail());
        
        if (form.getFormItems() != null) {
            items.setAll(form.getFormItems());
            items.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
        } else {
            items.clear();
        }
        items.forEach(item -> {
            minId = Integer.min(minId, item.getId());
        });
    }

    private void saveFormItem() {
        if (form != null) {

            form.setName(nameTextField.textProperty().get());
            form.setDataEmail(dataEmailTextField.textProperty().get());
            
            for (int i = 0; i < items.size(); i++) {
                items.get(i).setOrder(i + 1);
            }
            form.setFormItems(items);

            if (form.getId() == null) {
                form.setId(0);
            }

        }
    }

    @FXML
    protected void handleAddItem() {
        FormItemProxy item = new FormItemProxy();

        item.setId(--minId);
        item.setName(codeTextField.textProperty().get());
        item.setTitle(titleTextField.textProperty().get());
        item.setDefaultValue(defaultTextField.textProperty().get());
        item.setType(typeComboBox.getSelectionModel().getSelectedItem());
        item.setRequired(requiredCheckBox.selectedProperty().get());

        items.add(item);
    }

    @FXML
    protected void handleRemoveItem() {
        if (itemsListView.getSelectionModel().getSelectedItem() != null) {
            items.remove(itemsListView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    protected void handleChangeItem() {
        if (itemsListView.getSelectionModel().getSelectedItem() != null) {
            FormItemProxy item = itemsListView.getSelectionModel().getSelectedItem();

            item.setName(codeTextField.textProperty().get());
            item.setTitle(titleTextField.textProperty().get());
            item.setDefaultValue(defaultTextField.textProperty().get());
            item.setType(typeComboBox.getSelectionModel().getSelectedItem());
            item.setRequired(requiredCheckBox.selectedProperty().get());

            itemsListView.refresh();
        }
    }

    @FXML
    protected void handleSave() {
        saveFormItem();

        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleCancel() {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

}
