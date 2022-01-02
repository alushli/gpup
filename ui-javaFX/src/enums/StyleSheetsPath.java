package enums;

public enum StyleSheetsPath {
    APP_SCREEN_LIGHT("/appScreen/AppScreenLight.css"),
    APP_SCREEN_DARK("/appScreen/AppScreenDark.css"),
    MENU_LIGHT("/menu/MenuLight.css"),
    MENU_DARK("/menu/MenuDark.css"),
    MAIN_CSS_LIGHT("/resources/mainCssLight.css"),
    MAIN_CSS_DARK("/resources/mainCssDark.css"),
    LOAD_FILE_LIGHT("/loadFile/LoadFileLight.css"),
    LOAD_FILE_DARK("/loadFile/LoadFileDark.css"),
    GENERAL_INFO_LIGHT("/generalInfo/GeneralInfoLight.css"),
    GENERAL_INFO_DARK("/generalInfo/GeneralInfoDark.css"),
    ACTIONS_LIGHT("/actions/ActionsLight.css"),
    ACTIONS_DARK("/actions/ActionsDark.css"),
    TARGETS_TABLE_LIGHT("/generalComponents/targetsTable/TargetsTableLight.css"),
    TARGETS_TABLE_DARK("/generalComponents/targetsTable/TargetsTableDark.css"),
    SERIAL_SET_TABLE_LIGHT("/generalComponents/serialSetTable/SerialSetTableLight.css"),
    SERIAL_SET_TABLE_DARK("/generalComponents/serialSetTable/SerialSetTableDark.css");

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
