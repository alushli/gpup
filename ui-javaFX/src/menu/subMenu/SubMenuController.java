package menu.subMenu;

import appScreen.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import menu.MenuController;

public class SubMenuController extends  mainControllers.Controllers{
    private MenuController mainController;

    @FXML
    private VBox buttons_vbox;

    @FXML
    private Label sub_header_label;

    @FXML
    private Button back_btn;


    @FXML
    void clickBack(ActionEvent event) {
        this.appController.initialize();
    }

    public void setActionButtons(){
        this.sub_header_label.setText("Actions");
        Button showCirclesBtn = new Button("Show circles");
        Button showPathBtn = new Button("Show path between targets");
        designBtn(showCirclesBtn);
        designBtn(showPathBtn);
        buttons_vbox.getChildren().add(showCirclesBtn);
        buttons_vbox.getChildren().add(showPathBtn);
        showCirclesBtn.setOnAction(e -> {
            this.mainController.getActionController().setShowCirclesControllers();
            this.appController.setArea(this.mainController.getActionController().getShowCirclesParent());
        });
        showPathBtn.setOnAction(e -> {
            this.mainController.getActionController().setShowPathsControllers();
            this.appController.setArea(this.mainController.getActionController().getShowPathsParent());
        });
    }

    public void setGeneralInfoButtons(){
        this.sub_header_label.setText("General Information");
        Button targetInfoBtn = new Button("Show target information");
        Button graphInfoBtn = new Button("Show graph information");
        designBtn(targetInfoBtn);
        designBtn(graphInfoBtn);
        buttons_vbox.getChildren().add(targetInfoBtn);
        buttons_vbox.getChildren().add(graphInfoBtn);
        targetInfoBtn.setOnAction(e -> {
            this.mainController.getGeneralInfoController().setTargetInfoControllers();
            this.appController.setArea(this.mainController.getGeneralInfoController().getShowTargetInfoParent());

        });
        graphInfoBtn.setOnAction(e -> {
            this.mainController.getGeneralInfoController().setGraphInfoControllers();
            this.appController.setArea(this.mainController.getGeneralInfoController().getShowGraphInfoParent());
        });
    }

    public void setTasksButtons(){
        this.sub_header_label.setText("Tasks");
        Button simulationBtn = new Button("Simulation Task");
        Button compilerBtn = new Button("Java Compiler Task");
        designBtn(simulationBtn);
        designBtn(compilerBtn);
        buttons_vbox.getChildren().add(simulationBtn);
        buttons_vbox.getChildren().add(compilerBtn);
        simulationBtn.setOnAction(e -> {
            this.mainController.getTasksController().setSimulationControllers();
            this.appController.setArea(this.mainController.getTasksController().getSimulationTaskParent());
        });
        compilerBtn.setOnAction(e -> {
            this.mainController.getTasksController().setCompilerControllers();
            this.appController.setArea(this.mainController.getTasksController().getCompilerTaskParent());
        });
    }

    @Override
    public void setAppController(AppController mainControllers) {
        this.appController = mainControllers;
    }

    public void setMainController(MenuController mainControllers) {
        this.mainController = mainControllers;
    }

    void designBtn(Button btn){
        btn.getStyleClass().add("menu-btn");
        btn.setMaxWidth(1.7976931348623157E308);
        btn.setAlignment(Pos.TOP_LEFT);
        btn.setMinHeight(40);
        btn.setMnemonicParsing(false);
    }
}
