package ru.gastroafisha.myappsadmin;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.gastroafisha.myappsadmin.cell.CatalogCell;
import ru.gastroafisha.myappsadmin.cell.FormCell;
import ru.gastroafisha.myappsadmin.cell.IconsEnumCell;
import ru.gastroafisha.myappsadmin.cell.TupleCell;
import ru.gastroafisha.myappsadmin.model.CatalogProxy;
import ru.gastroafisha.myappsadmin.model.FormProxy;
import ru.gastroafisha.myappsadmin.model.IconsEnum;
import ru.gastroafisha.myappsadmin.model.MenuItemCls;
import ru.gastroafisha.myappsadmin.model.MenuItemProxy;
import ru.gastroafisha.myappsadmin.model.MenuItemType;

/**
 * FXML Controller class
 *
 * @author alex
 */
public class MenuItemFXMLController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<MenuItemType> typeComboBox;
    @FXML
    private ComboBox<IconsEnum> iconComboBox;

    @FXML
    private ComboBox<CatalogProxy> catalogSelectComboBox;

    @FXML
    private ComboBox<FormProxy> formSelectComboBox;

    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab standartTab;
    @FXML
    private Tab formTab;
    @FXML
    private Tab catalogTab;

    @FXML
    private ComboBox<MenuItemCls> clsSelectComboBox;

    @FXML
    private TabPane standartTabPane;
    @FXML
    private Tab aboutTab;
    @FXML
    private Tab mapTab;
    @FXML
    private Tab searchTab;
    @FXML
    private Tab favoritesTab;
    @FXML
    private Tab planTab;
    @FXML
    private Tab ticketTab;
    @FXML
    private Tab urlTab;

    @FXML
    private ComboBox<CatalogProxy> aboutCatalogSelectComboBox;

    @FXML
    private TextField latTextField;
    @FXML
    private TextField lonTextField;
    @FXML
    private TextField zoomTextField;
    @FXML
    private TextField mapCaptionTextField;

    @FXML
    private TextField urlTextField;

    @FXML
    private TextField newCaptionTextField;
    @FXML
    private TextField newUrlTextField;

    @FXML
    private ListView<Tuple<String, String>> plansListView;

    private final ObservableList<MenuItemType> types = FXCollections.observableArrayList();
    private final ObservableList<IconsEnum> icons = FXCollections.observableArrayList();
    private final ObservableList<CatalogProxy> catalogs = FXCollections.observableArrayList();
    private final ObservableList<FormProxy> forms = FXCollections.observableArrayList();
    private final ObservableList<MenuItemCls> cls = FXCollections.observableArrayList();
    private final ObservableList<Tuple<String, String>> plans = FXCollections.observableArrayList();

    private MenuItemProxy menuItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareControls();

        types.setAll(MenuItemType.values());
        icons.setAll(IconsEnum.values());
        cls.setAll(MenuItemCls.values());
    }

    private void prepareControls() {
        typeComboBox.setItems(types);

        iconComboBox.setItems(icons);
        iconComboBox.setButtonCell(new IconsEnumCell());
        iconComboBox.setCellFactory(param -> new IconsEnumCell());

        catalogSelectComboBox.setItems(catalogs);
        catalogSelectComboBox.setButtonCell(new CatalogCell());
        catalogSelectComboBox.setCellFactory(param -> new CatalogCell());

        formSelectComboBox.setItems(forms);
        formSelectComboBox.setButtonCell(new FormCell());
        formSelectComboBox.setCellFactory(param -> new FormCell());

        clsSelectComboBox.setItems(cls);

        aboutCatalogSelectComboBox.setItems(catalogs);
        aboutCatalogSelectComboBox.setButtonCell(new CatalogCell());
        aboutCatalogSelectComboBox.setCellFactory(param -> new CatalogCell());

        plansListView.setCellFactory(param -> new TupleCell());
        plansListView.setItems(plans);

        plansListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tuple<String, String>> observable, Tuple<String, String> oldValue, Tuple<String, String> newValue) -> {
            if (newValue != null) {
                newCaptionTextField.textProperty().set(newValue.getX());
                newUrlTextField.textProperty().set(newValue.getY());
            }
        });
    }

    public void showMenuItem(MenuItemProxy menuItem, List<CatalogProxy> catalogs, List<FormProxy> forms) {
        this.menuItem = menuItem;
        this.catalogs.setAll(catalogs);
        this.forms.setAll(forms);

        nameTextField.textProperty().set(menuItem.getName());
        iconComboBox.getSelectionModel().select(menuItem.getIcon());

        typeComboBox.getSelectionModel().select(menuItem.getType());
        handleTypeChange();

        if (menuItem.getCatalog() != null) {
            List<CatalogProxy> cp = this.catalogs.filtered((t) -> t.getId().equals(menuItem.getCatalog()));
            if (!cp.isEmpty()) {
                catalogSelectComboBox.getSelectionModel().select(cp.get(0));
                aboutCatalogSelectComboBox.getSelectionModel().select(cp.get(0));
            }
        }

        if (menuItem.getForm() != null) {
            List<FormProxy> cp = this.forms.filtered((t) -> t.getId().equals(menuItem.getForm()));
            if (!cp.isEmpty()) {
                formSelectComboBox.getSelectionModel().select(cp.get(0));
            }
        }

        if (menuItem.getParams() == null) {
            menuItem.setParams(new HashMap<>());
        }

        if (menuItem.getParams().containsKey("cls")) {
            clsSelectComboBox.getSelectionModel().select(MenuItemCls.getByName(menuItem.getParams().get("cls")));
        } else {
            clsSelectComboBox.getSelectionModel().select(null);
        }
        handleClsChange();

        // Map
        if (menuItem.getParams().containsKey("lat")) {
            latTextField.textProperty().set(menuItem.getParams().get("lat"));
        }
        if (menuItem.getParams().containsKey("lon")) {
            lonTextField.textProperty().set(menuItem.getParams().get("lon"));
        }
        if (menuItem.getParams().containsKey("zoom")) {
            zoomTextField.textProperty().set(menuItem.getParams().get("zoom"));
        }
        if (menuItem.getParams().containsKey("text")) {
            mapCaptionTextField.textProperty().set(menuItem.getParams().get("text"));
        }

        // Url
        if (menuItem.getParams().containsKey("url")) {
            urlTextField.textProperty().set(menuItem.getParams().get("url"));
        }

        // Plan
        plans.clear();
        if (menuItem.getParams().containsKey("plans")) {
            String sPlans = menuItem.getParams().get("plans");

            for (String plan : sPlans.split("\\|")) {
                String[] p = plan.split("=");
                plans.add(new Tuple<>(p[0], p[1]));
            }
        }

    }

    private void saveMenuItem() {
        menuItem.setName(nameTextField.textProperty().get());
        menuItem.setType(typeComboBox.getSelectionModel().getSelectedItem());
        menuItem.setIcon(iconComboBox.getSelectionModel().getSelectedItem());

        switch (typeComboBox.getSelectionModel().getSelectedItem()) {
            case Catalog:
                CatalogProxy cp = catalogSelectComboBox.getSelectionModel().getSelectedItem();
                if (cp != null) {
                    menuItem.setCatalog(cp.getId());
                }
                break;

            case Form:
                FormProxy fp = formSelectComboBox.getSelectionModel().getSelectedItem();
                if (fp != null) {
                    menuItem.setForm(fp.getId());
                }
                break;

            case Standart:
                MenuItemCls c = clsSelectComboBox.getSelectionModel().getSelectedItem();
                if (c != null) {
                    menuItem.getParams().put("cls", c.name().toLowerCase());
                    saveStandart();
                }

                break;
        }

        if (menuItem.getId() == null) {
            menuItem.setId(0);
        }
    }

    protected void saveStandart() {
        MenuItemCls c = clsSelectComboBox.getSelectionModel().getSelectedItem();
        if (c == null) {
            return;
        }

        switch (c) {
            case About:
                CatalogProxy cp = aboutCatalogSelectComboBox.getSelectionModel().getSelectedItem();
                if (cp != null) {
                    menuItem.setCatalog(cp.getId());
                }
                break;

            case Map:
                menuItem.getParams().put("lat", latTextField.textProperty().get());
                menuItem.getParams().put("lon", lonTextField.textProperty().get());
                menuItem.getParams().put("zoom", zoomTextField.textProperty().get());
                menuItem.getParams().put("text", mapCaptionTextField.textProperty().get());
                break;

            case Search:
                break;

            case Favorites:
                break;

            case Plan:

                String sPlans = null;
                if (!plans.isEmpty()) {
                    sPlans = plans.stream().map(t -> t.getX() + "=" + t.getY()).collect(Collectors.joining("|"));
                }

                menuItem.getParams().put("plans", sPlans);
                
                break;

            case Ticket:
                break;

            case Url:
                menuItem.getParams().put("url", urlTextField.textProperty().get());
                break;
        }
    }

    @FXML
    protected void handleTypeChange() {
        if (typeComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        
        switch (typeComboBox.getSelectionModel().getSelectedItem()) {
            case Standart:
                mainTabPane.getSelectionModel().select(standartTab);
                break;

            case Form:
                mainTabPane.getSelectionModel().select(formTab);
                break;

            case Catalog:
                mainTabPane.getSelectionModel().select(catalogTab);
                break;
        }
    }

    @FXML
    protected void handleClsChange() {
        if (clsSelectComboBox.getSelectionModel().isEmpty()) {
            standartTabPane.getSelectionModel().select(0);
            return;
        }

        switch (clsSelectComboBox.getSelectionModel().getSelectedItem()) {
            case About:
                standartTabPane.getSelectionModel().select(aboutTab);
                break;

            case Map:
                standartTabPane.getSelectionModel().select(mapTab);
                break;

            case Search:
                standartTabPane.getSelectionModel().select(searchTab);
                break;

            case Favorites:
                standartTabPane.getSelectionModel().select(favoritesTab);
                break;

            case Plan:
                standartTabPane.getSelectionModel().select(planTab);
                break;

            case Ticket:
                standartTabPane.getSelectionModel().select(ticketTab);
                break;

            case Url:
                standartTabPane.getSelectionModel().select(urlTab);
                break;
        }
    }

    @FXML
    protected void handlePlanAdd() {
        plans.add(new Tuple<>(newCaptionTextField.textProperty().get(), newUrlTextField.textProperty().get()));
    }

    @FXML
    protected void handlePlanRemove() {
        if (plansListView.getSelectionModel().getSelectedItem() != null) {
            plans.remove(plansListView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    protected void handlePlanChange() {
        if (plansListView.getSelectionModel().getSelectedItem() != null) {
            Tuple<String, String> t = plansListView.getSelectionModel().getSelectedItem();
            t.setX(newCaptionTextField.textProperty().get());
            t.setY(newUrlTextField.textProperty().get());
            plansListView.refresh();
        }
    }

    @FXML
    protected void handleSave() {
        saveMenuItem();

        Stage stage = (Stage) typeComboBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleCancel() {
        Stage stage = (Stage) typeComboBox.getScene().getWindow();
        stage.close();
    }
}
