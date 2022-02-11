package components.generalComponents;

import components.appScreen.AppController;

public class workerGeneralComponent {
    protected static AppController appController;

    public AppController getAppController() {
        return appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
