package generalComponents;

import appScreen.AppController;

public class GeneralComponent {
    protected static AppController appController;

    public AppController getAppController() {
        return appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
