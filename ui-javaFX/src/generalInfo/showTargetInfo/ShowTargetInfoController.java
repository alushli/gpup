package generalInfo.showTargetInfo;

import appScreen.AppController;
import enums.FxmlPath;
import generalInfo.GeneralInfoController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.net.URL;

public class ShowTargetInfoController extends mainControllers.Controllers{
    private GeneralInfoController mainController;


    @FXML
    private Label lable;

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(GeneralInfoController mainControllers) {
        this.mainController = mainControllers;
    }

    public void setTable(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("../../" + FxmlPath.TARGET_TABEL);
            fxmlLoader.setLocation(url);
            this.appController.setArea(fxmlLoader.load(url.openStream()));
        }catch (Exception e){
            System.out.println("problem with table");
        }

    }
}
