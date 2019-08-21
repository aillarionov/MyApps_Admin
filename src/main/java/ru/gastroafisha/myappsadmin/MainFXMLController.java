package ru.gastroafisha.myappsadmin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.disposables.CompositeDisposable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import retrofit2.HttpException;
import ru.gastroafisha.myappsadmin.cell.CatalogCell;
import ru.gastroafisha.myappsadmin.cell.CityCell;
import ru.gastroafisha.myappsadmin.cell.FormCell;
import ru.gastroafisha.myappsadmin.cell.MenuItemCell;
import ru.gastroafisha.myappsadmin.cell.SimpleOrgCell;
import ru.gastroafisha.myappsadmin.model.CatalogProxy;
import ru.gastroafisha.myappsadmin.model.CityProxy;
import ru.gastroafisha.myappsadmin.model.FormProxy;
import ru.gastroafisha.myappsadmin.model.MenuItemProxy;
import ru.gastroafisha.myappsadmin.model.OrgProxy;
import ru.gastroafisha.myappsadmin.model.SimpleOrgProxy;
import ru.gastroafisha.myappsadmin.model.TicketProxy;
import ru.gastroafisha.myappsadmin.model.TicketType;
import ru.gastroafisha.myappsadmin.rest.RESTDataProvider;

/**
 * FXML Controller class
 *
 * @author alex
 */
public class MainFXMLController implements Initializable {

    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private ListView<SimpleOrgProxy> groupsList;
    @FXML
    private ListView<MenuItemProxy> menuItemsList;
    @FXML
    private ListView<FormProxy> formsList;
    @FXML
    private ListView<CatalogProxy> catalogsList;

    @FXML
    private TextField idField;
    @FXML
    private CheckBox suspendCheckBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField logoField;
    @FXML
    private ComboBox<CityProxy> cityComboBox;

    @FXML
    private ComboBox<TicketType> ticketTypeComboBox;
    @FXML
    private TextField ticketSource;
    @FXML
    private TextField ticketButton;
    @FXML
    private TextArea ticketText;

    private CompositeDisposable disp = new CompositeDisposable();

    private final ObservableList<SimpleOrgProxy> groups = FXCollections.observableArrayList();
    private final ObservableList<MenuItemProxy> menuItems = FXCollections.observableArrayList();
    private final ObservableList<FormProxy> forms = FXCollections.observableArrayList();
    private final ObservableList<CatalogProxy> catalogs = FXCollections.observableArrayList();
    private final ObservableList<TicketType> ticketTypes = FXCollections.observableArrayList();
    private final ObservableList<CityProxy> cities = FXCollections.observableArrayList();

    private Integer minId = -1;

    private OrgProxy org;

    RESTDataProvider rest = new RESTDataProvider();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareControls();

        ticketTypes.addAll(TicketType.values());

        disp.add(rest.getAdminService().getCitiesList()
                .subscribe(_cities -> this.cities.setAll(_cities), error -> {
                    String text = error.getMessage();
                    if (error instanceof HttpException) {
                        text = ((HttpException) error).response().errorBody().string();
                    }

                    MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Не удалось получить список городов с сервера", text);
                })
        );
    }

    @Override
    protected void finalize() throws Throwable {
        disp.clear();

        super.finalize();
    }

    private void prepareControls() {
        loginTextField.textProperty().addListener((observable, oldValue, newValue) -> this.rest.setLogin(newValue));
        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> this.rest.setPassword(newValue));

        groupsList.setItems(groups);
        groupsList.setCellFactory(param -> new SimpleOrgCell());

        menuItemsList.setItems(menuItems);
        menuItemsList.setCellFactory(param -> new MenuItemCell());

        formsList.setItems(forms);
        formsList.setCellFactory(param -> new FormCell());

        catalogsList.setItems(catalogs);
        catalogsList.setCellFactory(param -> new CatalogCell());

        catalogsList.setItems(catalogs);

        ticketTypeComboBox.setItems(ticketTypes);

        cityComboBox.setItems(cities);
        cityComboBox.setButtonCell(new CityCell());
        cityComboBox.setCellFactory(param -> new CityCell());
    }

    protected void loadData(OrgProxy org) {
        this.org = org;

        idField.textProperty().set(org.getId().toString());
        nameField.textProperty().set(org.getName());
        logoField.textProperty().set(org.getLogo());
        suspendCheckBox.selectedProperty().set(org.getSuspend());

        List<CityProxy> selected = this.cities.filtered((city) -> city.getId() == org.getCity().getId());
        if (!selected.isEmpty()) {
            cityComboBox.getSelectionModel().select(selected.get(0));
        }

        if (org.getTicket() != null) {
            ticketTypeComboBox.getSelectionModel().select(org.getTicket().getType());
            ticketSource.textProperty().set(org.getTicket().getSource());
            ticketButton.textProperty().set(org.getTicket().getButton());
            ticketText.textProperty().set(org.getTicket().getText());
        } else {
            ticketTypeComboBox.getSelectionModel().select(null);
            ticketSource.textProperty().set(null);
            ticketButton.textProperty().set(null);
            ticketText.textProperty().set(null);
        }

        // lists
        menuItems.setAll(org.getMenuItems());
        menuItems.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));

        catalogs.setAll(org.getCatalogs());
        catalogs.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

        forms.setAll(org.getForms());
        forms.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

        // calc minId
        org.getMenuItems().forEach(item -> {
            minId = Integer.min(minId, item.getId());
        });
        org.getCatalogs().forEach(item -> {
            minId = Integer.min(minId, item.getId());
        });
        org.getForms().forEach(item -> {
            minId = Integer.min(minId, item.getId());
        });
    }

    protected void saveData() {
        if (org != null) {
            org.setName(nameField.textProperty().get());
            org.setLogo(logoField.textProperty().get());
            org.setCity(cityComboBox.getSelectionModel().getSelectedItem());
            org.setSuspend(suspendCheckBox.selectedProperty().get());

            if ((ticketSource.textProperty().get() != null && !ticketSource.textProperty().get().isEmpty())
                    || (ticketButton.textProperty().get() != null && !ticketButton.textProperty().get().isEmpty())
                    || (ticketText.textProperty().get() != null  && !ticketText.textProperty().get().isEmpty())) {
                
                TicketProxy ticket = org.getTicket();

                if (ticket == null) {
                    ticket = new TicketProxy();
                    ticket.setId(--minId);
                    org.setTicket(ticket);
                }

                ticket.setSource(ticketSource.textProperty().get());
                ticket.setButton(ticketButton.textProperty().get());
                ticket.setText(ticketText.textProperty().get());
                ticket.setType(ticketTypeComboBox.getSelectionModel().getSelectedItem());

            }

            org.setMenuItems(menuItems);
            org.setCatalogs(catalogs);
            org.setForms(forms);
        }
    }

    @FXML
    protected void handleServerRefreshClick() {
        disp.add(rest.getAdminService().getOrgsList()
                .subscribe(orgs -> this.groups.setAll(orgs), error -> {
                    String text = error.getMessage();
                    if (error instanceof HttpException) {
                        text = ((HttpException) error).response().errorBody().string();
                    }

                    MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Не удалось получить список групп с сервера", text);
                })
        );
    }

    @FXML
    protected void handleServerLoadClick() {
        if (!groupsList.getSelectionModel().isEmpty()) {
            SimpleOrgProxy sOrg = (SimpleOrgProxy) groupsList.getSelectionModel().getSelectedItem();
            disp.add(rest.getAdminService().getOrg(sOrg.getId())
                    .subscribe(org -> loadData(org), error -> {
                        String text = error.getMessage();
                        if (error instanceof HttpException) {
                            text = ((HttpException) error).response().errorBody().string();
                        }
                        MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Не удалось получить группу с сервера", text);
                    }));
        }
    }

    @FXML
    protected void handleServerSaveClick() {
        saveData();

        disp.add(rest.getAdminService().updateOrg(org)
                .subscribe(org -> {
                    loadData(org);
                    MessageDialog.show(Alert.AlertType.INFORMATION, "Успешно", "Группа успешно сохранена", "");
                }, error -> {
                    String text = error.getMessage();
                    if (error instanceof HttpException) {
                        text = ((HttpException) error).response().errorBody().string();
                    }

                    MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Не удалось сохранить группу на сервер", text);
                })
        );
    }

    @FXML
    protected void handleServerCreateClick() {
        if (MessageDialog.ask("Подтверждение", "Внимание! На сервере будет создана новая группа", "Будет создана новая группа и заполнена указанными данными")) {
            
            org = new OrgProxy();
            
            saveData();
            
            disp.add(rest.getAdminService().newOrg(org)
                    .subscribe(org -> {
                        loadData(org);
                        MessageDialog.show(Alert.AlertType.INFORMATION, "Успешно", "Группа успешно создана", "");
                    }, error -> {

                        String text = error.getMessage();
                        if (error instanceof HttpException) {
                            text = ((HttpException) error).response().errorBody().string();
                        }

                        MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Не удалось создать группу на сервере", text);
                    })
            );
        }

    }

    @FXML
    protected void handleFileLoadClick() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Загрузка из файла");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json", "*.json"));

        File selectedFile = chooser.showOpenDialog(null);

        try {
            Reader reader = new FileReader(selectedFile);
            Gson gson = new GsonBuilder().create();
            OrgProxy o = gson.fromJson(reader, OrgProxy.class);
            if (o != null) {
                loadData(o);
            }
            reader.close();
        } catch (Exception e) {
            MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Не удалось загрузить и файла", e.getMessage());
        }
    }

    @FXML
    protected void handleFileSaveClick() {
        saveData();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Сохранение в файл");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json", "*.json"));

        File selectedFile = chooser.showSaveDialog(null);

        try {
            Writer writer = new FileWriter(selectedFile);
            Gson gson = new GsonBuilder().create();
            gson.toJson(org, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Не удалось сохранить в файл", e.getMessage());
        }
    }

    @FXML
    protected void handleMenuItemAdd() {
        try {
            MenuItemProxy menuItem = new MenuItemProxy();
            openMenuItemForm(menuItem, "Добавление элемента меню");
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void handleMenuItemChange() {
        try {
            if (menuItemsList.getSelectionModel().getSelectedItem() != null) {
                openMenuItemForm(menuItemsList.getSelectionModel().getSelectedItem(), "Редактирование элемента меню");
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void handleMenuItemDelete() {
        if (menuItemsList.getSelectionModel().getSelectedIndex() >= 0) {
            menuItemsList.getItems().remove(menuItemsList.getSelectionModel().getSelectedIndex());
            for (int i = 0; i < menuItems.size(); i++) {
                menuItems.get(i).setOrder(i + 1);
            }
        }
    }

    @FXML
    protected void handleCatalogAdd() {
        try {
            CatalogProxy catalog = new CatalogProxy();
            openCatalogForm(catalog, "Добавление каталога");
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void handleCatalogChange() {
        try {
            if (catalogsList.getSelectionModel().getSelectedItem() != null) {
                openCatalogForm(catalogsList.getSelectionModel().getSelectedItem(), "Редактирование каталога");
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void handleCatalogDelete() {
        if (catalogsList.getSelectionModel().getSelectedItems() != null) {
            CatalogProxy catalog = catalogsList.getSelectionModel().getSelectedItems().get(0);
            List<MenuItemProxy> items = menuItems.filtered((t) -> catalog.getId().equals(t.getCatalog()));

            if (items.isEmpty()) {
                catalogsList.getItems().remove(catalogsList.getSelectionModel().getSelectedIndex());
            } else {
                MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Каталог используется в элементе меню", items.get(0).getName());
            }
        }
    }

    @FXML
    protected void handleFormAdd() {
        try {
            FormProxy form = new FormProxy();
            openFormForm(form, "Добавление формы");
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void handleFormChange() {
        try {
            if (formsList.getSelectionModel().getSelectedItem() != null) {
                openFormForm(formsList.getSelectionModel().getSelectedItem(), "Редактирование формы");
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void handleFormDelete() {
        if (formsList.getSelectionModel().getSelectedIndex() >= 0) {
            FormProxy form = formsList.getSelectionModel().getSelectedItems().get(0);
            List<MenuItemProxy> items = menuItems.filtered((t) -> form.getId().equals(t.getForm()));

            if (items.isEmpty()) {
                formsList.getItems().remove(formsList.getSelectionModel().getSelectedIndex());
            } else {
                MessageDialog.show(Alert.AlertType.ERROR, "Ошибка", "Форма используется в элементе меню", items.get(0).getName());
            }
        }
    }

    private void openCatalogForm(CatalogProxy catalog, String title) throws IOException {
        Stage stage = new Stage();
        String fxmlFile = "/fxml/CatalogFXML.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        if (catalog != null) {
            CatalogFXMLController controller = loader.<CatalogFXMLController>getController();
            controller.showCatalog(catalog);
        }

        stage.setScene(new Scene(rootNode));
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        if (0 == catalog.getId()) {
            catalog.setId(--minId);
            catalogs.add(catalog);
        }

        catalogsList.refresh();
    }

    private void openFormForm(FormProxy form, String title) throws IOException {
        Stage stage = new Stage();
        String fxmlFile = "/fxml/FormFXML.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        if (form != null) {
            FormFXMLController controller = loader.<FormFXMLController>getController();
            controller.showForm(form);
        }

        stage.setScene(new Scene(rootNode));
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        if (0 == form.getId()) {
            form.setId(--minId);
            forms.add(form);
        }

        formsList.refresh();
    }

    private void openMenuItemForm(MenuItemProxy menuItem, String title) throws IOException {
        Stage stage = new Stage();
        String fxmlFile = "/fxml/MenuItemFXML.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        if (menuItem != null) {
            MenuItemFXMLController controller = loader.<MenuItemFXMLController>getController();
            controller.showMenuItem(menuItem, catalogs, forms);
        }

        stage.setScene(new Scene(rootNode));
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        if (0 == menuItem.getId()) {
            menuItem.setId(--minId);
            menuItems.add(menuItem);
            for (int i = 0; i < menuItems.size(); i++) {
                menuItems.get(i).setOrder(i + 1);
            }
        }

        menuItemsList.refresh();
    }

}
