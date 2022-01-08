package enums;

public enum StyleSheetsPath {
    APP_SCREEN_LIGHT("/appScreen/AppScreenLight.css"),
    APP_SCREEN_DARK("/appScreen/AppScreenDark.css"),
    APP_SCREEN_PRINCESS("/appScreen/AppScreenPrincess.css"),
    MENU_LIGHT("/menu/MenuLight.css"),
    MENU_DARK("/menu/MenuDark.css"),
    MENU_PRINCESS("/menu/MenuPrincess.css"),
    MAIN_CSS_LIGHT("/resources/mainCssLight.css"),
    MAIN_CSS_DARK("/resources/mainCssDark.css"),
    MAIN_CSS_PRINCESS("/resources/mainCssPrincess.css"),
    LOAD_FILE_LIGHT("/loadFile/LoadFileLight.css"),
    LOAD_FILE_DARK("/loadFile/LoadFileDark.css"),
    LOAD_FILE_PRINCESS("/loadFile/LoadFilePrincess.css"),
    GENERAL_INFO_LIGHT("/generalInfo/GeneralInfoLight.css"),
    GENERAL_INFO_DARK("/generalInfo/GeneralInfoDark.css"),
    GENERAL_INFO_PRINCESS("/generalInfo/GeneralInfoPrincess.css"),
    ACTIONS_LIGHT("/actions/ActionsLight.css"),
    ACTIONS_DARK("/actions/ActionsDark.css"),
    ACTIONS_PRINCESS("/actions/ActionsPrincess.css"),
    TARGETS_TABLE_LIGHT("/generalComponents/targetsTable/TargetsTableLight.css"),
    TARGETS_TABLE_DARK("/generalComponents/targetsTable/TargetsTableDark.css"),
    TARGETS_TABLE_PRINCESS("/generalComponents/targetsTable/TargetsTablePrincess.css"),
    SERIAL_SET_TABLE_LIGHT("/generalComponents/serialSetTable/SerialSetTableLight.css"),
    SERIAL_SET_TABLE_DARK("/generalComponents/serialSetTable/SerialSetTableDark.css"),
    SERIAL_SET_TABLE_PRINCESS("/generalComponents/serialSetTable/SerialSetTablePrincess.css"),
    TASK_LIGHT("/tasks/TasksLight.css"),
    TASK_DARK("/tasks/TasksDark.css"),
    TASK_PRINCESS("/tasks/TasksPrincess.css");

    private String name;

    /* constructor */
    StyleSheetsPath(String name){
        this.name = name;
    }

    @Override
    /* the function return toString of the enum */
    public String toString() {
        return name;
    }
}
