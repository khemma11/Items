package main;

import command.Commands;
import model.Category;
import model.Gender;
import model.Item;
import model.User;
import storage.DataStorage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class AdvertisementMain  implements Commands {


    private static Scanner scanner = new Scanner(System.in);
    private static DataStorage dataStorage = new DataStorage();
    private static User currentUser = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        dataStorage.initData();
        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void registerUser() {
        System.out.println("Please input user data: "
                + "name, surName, gender(MALE,FEMALE), age, phonNumber, password");
        try {
            String userDataStr = scanner.nextLine();
            String[] userDatAr = userDataStr.split(",");
            User userFormatStorage = dataStorage.getUser(userDatAr[4]);

            if (userFormatStorage == null) {
                User user = new User();
                user.setName(userDatAr[0]);
                user.setSurName(userDatAr[1]);
                user.setGender(Gender.valueOf(userDatAr[2].toUpperCase()));
                user.setAge(Integer.parseInt(userDatAr[3]));
                user.setPhonNumber(userDatAr[4]);
                user.setPassword(userDatAr[5]);
                dataStorage.add(user);
                System.out.println("User was successfully added");
            } else {
                System.out.println("User already exist!");
            }
        } catch (Exception e) {
            System.out.println("Wrong data!");
        }
    }

    private static void loginUser() {
        System.out.println("Please input phonNumber and password");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArr = loginStr.split(",");
            User user = dataStorage.getUser(loginArr[0]);
            if (user != null & user.getPassword().equals(loginArr[1])) {
                currentUser = user;
                loginSuccess();
            } else {
                System.out.println("Wrong phonNumber or password ");
            }
        } catch (IndexOutOfBoundsException | IOException | ClassNotFoundException e) {
            System.out.println("Wrong data!");
        }
    }

    private static void loginSuccess() throws IOException, ClassNotFoundException {
        System.out.println("Welcome " + currentUser.getName() + "!");

        boolean isRun = true;
        while (isRun) {
            Commands.printItemCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_ITEMS:
                    addedNewItem();
                    break;
                case PRINT_MY_ALL_ITEMS:
                    dataStorage.printItemsByUser(currentUser);
                    break;
                case PRINT_ALL_ITEMS:
                    dataStorage.printItems();
                    break;
                case PRINT_ITEM_BY_CATEGORY:
                    printByCategory();
                    break;
                case PRINT_ALL_ITEMS_BY_TITLE_SORT:
                    dataStorage.printItemsOrderByTitle();
                    break;
                case PRINT_ALL_ITEMS_BY_DATE_SORT:
                    dataStorage.printItemsOrderByDate();
                    break;
                case DELETE_MY_ALL_ITEMS:
                    dataStorage.deleteItemsByUser(currentUser);
                    break;
                case DELETE_ITEMS_BY_ID:
                    deletById();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void deletById() throws IOException {
        System.out.println("please choose id from list");
        dataStorage.printItemsByUser(currentUser);
        long id = Long.parseLong(scanner.nextLine());
        Item itemById = dataStorage.getItemById(id);
        if (itemById != null && itemById.getUser().equals(currentUser)) {
            dataStorage.deleteItemsById(id);
        } else {
            System.out.println("Wrong id!");
        }

    }

    private static void addedNewItem() {
        System.out.println("Please input item data title,text,price,category");
        System.out.println("Please choose category name from list: " + Arrays.toString(Category.values()));
        try {
            String itemDataStr = scanner.nextLine();
            String[] itemDataArr = itemDataStr.split(",");
            Item item = new Item(itemDataArr[0], itemDataArr[1], Double.parseDouble(itemDataArr[2])
                    , currentUser, Category.valueOf(itemDataArr[3]), new Date());
            dataStorage.add(item);
            System.out.println("Item was successfully added");
        } catch (Exception e) {
            System.out.println("Wrong Data!");
        }
    }

    private static void printByCategory() {
        System.out.println("Please choose category name from list: " + Arrays.toString(Category.values()));
        try {
            String categoryStr = scanner.nextLine();
            Category category = Category.valueOf(categoryStr);
            dataStorage.printItemsByCategory(category);
        } catch (Exception e) {
            System.out.println("Wrong Category!");
        }
    }

}
