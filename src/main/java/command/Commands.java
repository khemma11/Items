package command;

public interface Commands {

    //main commands
    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;
    int IMPORT_USERS = 3;

    //item commands
    int LOGOUT = 0;
    int ADD_NEW_ITEMS =1;
    int PRINT_MY_ALL_ITEMS = 2;
    int PRINT_ALL_ITEMS = 3;
    int PRINT_ITEM_BY_CATEGORY = 4;
    int PRINT_ALL_ITEMS_BY_TITLE_SORT = 5;
    int PRINT_ALL_ITEMS_BY_DATE_SORT = 6;
    int DELETE_MY_ALL_ITEMS = 7;
    int DELETE_ITEMS_BY_ID = 8;

    static void printMainCommands() {
        System.out.println("Please input " + EXIT + " for exit");
        System.out.println("Please input " + LOGIN + " for login");
        System.out.println("Please input " + REGISTER + " for register");
        System.out.println("Please input " + IMPORT_USERS + " for import users");


    }

    static void printItemCommands() {
        System.out.println("Please input " + LOGOUT + " for logout");
        System.out.println("Please input " + ADD_NEW_ITEMS + " for ADD_NEW_AD");
        System.out.println("Please input " + PRINT_MY_ALL_ITEMS + " for PRINT_MY_ALL_ADS");
        System.out.println("Please input " + PRINT_ALL_ITEMS + " for PRINT_ALL_ADS ");
        System.out.println("Please input " + PRINT_ITEM_BY_CATEGORY + " for PRINT_AD_BY_CATEGORY ");
        System.out.println("Please input " + PRINT_ALL_ITEMS_BY_TITLE_SORT + " for PRINT_ALL_ADS_BY_TITLE_SORT ");
        System.out.println("Please input " + PRINT_ALL_ITEMS_BY_DATE_SORT + " for PRINT_ALL_ADS_BY_DATE_SORT ");
        System.out.println("Please input " + DELETE_MY_ALL_ITEMS + " for DELETE_MY_ALL_ADS ");
        System.out.println("Please input " + DELETE_ITEMS_BY_ID + " for DELETE_AD_BY_TITLE ");
    }

}
