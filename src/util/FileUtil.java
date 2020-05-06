package util;


import model.Item;
import model.User;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileUtil {
    private static final String USER_PATH = "C:\\Users\\Aka\\IdeaProjects\\Adv_Karen\\src\\util\\user.txt";


    public static void serializeUserMap(Map<String, User> userMap) throws IOException, ClassNotFoundException {

        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream(USER_PATH));
        objectOutputStream.writeObject(userMap);
        objectOutputStream.close();


    }

    public static Map<String, User> deSerializeUserMap() throws IOException, ClassNotFoundException {
       try {
           ObjectInputStream objectInputStream =
                   new ObjectInputStream(new FileInputStream(USER_PATH));
           Object deserrialization = objectInputStream.readObject();
           Map<String, User> userMap = (Map<String, User>) deserrialization;
           objectInputStream.close();
           return userMap;
       } catch (EOFException e) {
          e.getMessage();
       }
       return null;
    }

    private static final String ITEM_PATH =
            "C:\\Users\\Aka\\IdeaProjects\\Adv_Karen\\src\\util\\item.txt";

    public static void serializeItem(List<Item> items) throws IOException {

        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream(ITEM_PATH));

        objectOutputStream.writeObject(items);
        objectOutputStream.close();

    }

    public static List<Item> deSerializeItem() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ITEM_PATH));

            Object deserialization = objectInputStream.readObject();
            List<Item> items = (List<Item>) deserialization;
            objectInputStream.close();
            return items;
        }catch (EOFException e) {
           e.getMessage();
        }
        return null;
    }
}
