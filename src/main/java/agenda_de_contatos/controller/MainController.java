package agenda_de_contatos.controller;

import agenda_de_contatos.MainApplication;
import agenda_de_contatos.model.Contato;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private ChoiceBox<String> themeChoiceBox;


    private String currentTheme = "dark-theme.css";
    @FXML
    public void initialize() {

        themeChoiceBox.setItems(FXCollections.observableArrayList("Escuro", "Claro", "Macho Alfa"));

        if ("light-theme.css".equals(currentTheme)) {
            themeChoiceBox.setValue("Claro");
        } else if ("barbie-theme.css".equals(currentTheme)) {
            themeChoiceBox.setValue("Macho Alfa");
        } else {
            themeChoiceBox.setValue("Escuro");
        }

        themeChoiceBox.setOnAction(event -> handleApplyTheme());

        handleShowContatoList();
    }


    @FXML
    private void handleShowContatoList() {
        loadView("contato-list-view.fxml", null);
    }

    @FXML
    private void handleShowContatoForm() {
        loadView("contato-form-view.fxml", null);
    }
    @FXML
    private BorderPane mainPane;

    public void showEditForm(Contato contato) {
        loadView("contato-form-view.fxml", contato);
    }

    @FXML
    private void handleShowConfig() {
        loadView("config-view.fxml", null);
    }

    @FXML
    private void handleApplyTheme() {
        String selectedTheme = themeChoiceBox.getValue();
        if (selectedTheme == null) return;

        if ("Claro".equals(selectedTheme)) {
            currentTheme = "light-theme.css";
        } else if ("Macho Alfa".equals(selectedTheme)) {
            currentTheme = "barbie-theme.css";
        } else {
            currentTheme = "dark-theme.css";
        }


        Scene scene = mainPane.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(MainApplication.class.getResource(currentTheme).toExternalForm());
        }
    }
    private void loadView(String fxmlFile, Contato contato) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
            Node view = loader.load();

            Object controller = loader.getController();

            if (controller instanceof ContatoListController) {
                ((ContatoListController) controller).setMainController(this);
            } else if (controller instanceof ContatoFormController) {
                ((ContatoFormController) controller).setMainController(this);
                ((ContatoFormController) controller).setContato(contato);
            } else if (controller instanceof ConfigController) {
                ((ConfigController) controller).setMainController(this);
            }

            contentPane.getChildren().setAll(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);


            Scene scene = contentPane.getScene();
            if (scene != null) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(MainApplication.class.getResource(currentTheme).toExternalForm());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getCurrentTheme() {
        return currentTheme;
    }
}
