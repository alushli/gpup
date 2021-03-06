package menu.subMenu;

import appScreen.AppController;
import enums.StyleSheetsPath;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import menu.MenuController;

public class SubMenuController extends  mainControllers.Controllers{
    private MenuController mainController;
    private StringProperty skin;
    private Image backDarkImg;
    private Image backLightImg;

    @FXML
    private ImageView back_img;

    @FXML
    private GridPane main_screen;

    @FXML
    private VBox buttons_vbox;

    @FXML
    private Label sub_header_label;

    @FXML
    private Button back_btn;

    @FXML
    public void initialize() {
        this.backDarkImg = new Image("/menu/subMenu/back_arrow_dark.png");
        this.backLightImg = new Image("/menu/subMenu/back_arrow_light.png");
        this.skin = new SimpleStringProperty("Light");
        this.skin.addListener((a,b,c)->{
            if(skin.getValue().equals("Light")){
                this.back_img.setImage(this.backLightImg);
            }else if (skin.getValue().equals("Dark")){
                this.back_img.setImage(this.backDarkImg);
            } else{
                this.back_img.setImage(this.backLightImg);
            }
        });
    }

    public void skinListener(){
        this.mainController.skinListener(this.skin, this.main_screen);
    }

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
        });
        showPathBtn.setOnAction(e -> {
            this.mainController.getActionController().setShowPathsControllers();
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
        });
        graphInfoBtn.setOnAction(e -> {
            this.mainController.getGeneralInfoController().setGraphInfoControllers();
        });
    }

    public StringProperty skinProperty() {
        return skin;
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
