package generalInfo.showGraphInfo;

import appScreen.AppController;
import generalInfo.GeneralInfoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import templates.LoadFileError;

import javax.xml.transform.Templates;

public class ShowGraphInfoController extends mainControllers.Controllers{
    private GeneralInfoController mainController;


    @FXML
    private StackPane data_area;

    public StackPane getDataArea() {
        return data_area;
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(GeneralInfoController mainControllers) {
        this.mainController = mainControllers;
    }
}
