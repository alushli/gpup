package templates;

import appScreen.AppController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoadFileError {
    public static void setLoadFileError(StackPane data_area, AppController appController){
        VBox vBox = new VBox();
        Label label = new Label();
        label.setText("Please upload valid xml file first");
        Button loadFile = new Button("Go to Load File screen");
        designBtn(vBox,label,loadFile);
        vBox.getChildren().add(label);
        vBox.getChildren().add(loadFile);
        data_area.getChildren().add(0,vBox);
        loadFile.setOnAction(e -> {
            appController.getMenuComponentController().clickLoadFile(e);
            appController.initialize();
        });
    }

    static void designBtn(VBox vBox, Label label, Button btn){
        vBox.setSpacing(10);
        btn.getStyleClass().add("load_btn");
        btn.setMaxWidth(1.7976931348623157E308);
        label.getStyleClass().add("load_error_label");

    }
}
