import com.sun.deploy.security.SelectableSecurityManager;
import command.Commands;
import model.Category;
import model.Gender;
import model.Item;
import model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import storage.DataStorage;
import util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
                case IMPORT_USERS:
                    importFromXlsx();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void importFromXlsx() {
        System.out.println("Please select xlsx path");
        String xlsxPath = scanner.nextLine();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(xlsxPath);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                String surname = row.getCell(1).getStringCellValue();
                Double age = row.getCell(2).getNumericCellValue();
                Gender gender = Gender.valueOf(row.getCell(3).getStringCellValue());
                Cell phoneNumber = row.getCell(4);
                String phoneNumberStr = phoneNumber.getCellType() == CellType.NUMERIC ?
                        String.valueOf(Double.valueOf(phoneNumber.getNumericCellValue()).intValue()) : phoneNumber.getStringCellValue();
                Cell password = row.getCell(5);
                String passwordStr = password.getCellType() == CellType.NUMERIC ?
                        String.valueOf(Double.valueOf(password.getNumericCellValue()).intValue()) : password.getStringCellValue();
                User user  = new User();
                user.setName(name);
                user.setSurName(surname);
                user.setAge(age.intValue());
                user.setGender(gender);
                user.setPhonNumber(phoneNumberStr);
                user.setPassword(passwordStr);
                System.out.println(user);
                dataStorage.add(user);

            } System.out.println("Import was success!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error while importing users");
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
                case IMPORT_ITEMS:
                    importItemsFromXlsx();
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
                case EXPORT_ITEMS:
                    exportItemsFromXlsx();
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void exportItemsFromXlsx() {
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                List<Item> items = FileUtil.deserializeItemList();
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet();
                createHeader(sheet, workbook);
                int index= 1;
                for (Item item : items) {
                    Row row = sheet.createRow(index);
                    row.createCell(0).setCellValue(item.getTitle());
                    row.createCell(1).setCellValue(item.getText());
                    row.createCell(2).setCellValue(item.getPrice());
                    row.createCell(3).setCellValue(item.getCreatedDate().toString());
                    row.createCell(4).setCellValue(String.valueOf(item.getCategory()));
                    index++;
                    System.out.println(item);
                }
                final String FILE_EXPORT_FILE =
                        "C:\\Users\\Aka\\IdeaProjects\\Items\\src\\main\\resources\\itemsExport.xlsx";
                File file = new File(FILE_EXPORT_FILE);
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(FILE_EXPORT_FILE));
                    workbook.write(fileOutputStream);
                    workbook.close();
                    System.out.println("itemsExport.xlsx file was successfully exported!!");
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        };

        Thread thread = new Thread(runable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    private static void createHeader(XSSFSheet sheet, XSSFWorkbook  workbook) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue(" Item title");
        headerRow.createCell(1).setCellValue(" Item text");
        headerRow.createCell(2).setCellValue(" Item price");
        headerRow.createCell(3).setCellValue(" Item createdDate");
        headerRow.createCell(4).setCellValue(" Item Category");
    }

    private static void importItemsFromXlsx() {
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Please select xlsx path");
                String xlsxPath = scanner.nextLine();
                try {
                    XSSFWorkbook workbook = new XSSFWorkbook(xlsxPath);
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    int lastRowNum = sheet.getLastRowNum();
                    for (int i = 1; i <= lastRowNum; i++) {
                        Row row = sheet.getRow(i);
                        String title = row.getCell(0).getStringCellValue();
                        String text = row.getCell(1).getStringCellValue();
                        Double price = row.getCell(2).getNumericCellValue();
                        Date createdDate = row.getCell(3).getDateCellValue();
                        Category category = Category.valueOf(row.getCell(4).getStringCellValue().toUpperCase());

                        Item items = new Item();
                        items.setTitle(title);
                        items.setText(text);
                        items.setPrice(price);
                        items.setCreatedDate(new Date());
                        items.setCategory(category);
                        items.setUser(currentUser);
                        System.out.println(items);

                        dataStorage.add(items);
                        System.out.println("Import was success!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error while importing users");
                }
            }
        };
        Thread thread = new Thread(runable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
