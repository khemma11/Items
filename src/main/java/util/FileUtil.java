package util;


import model.Item;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
    private static final String USER_MAP_PATH = "src\\main\\resources\\user.txt";
    private static final String ITEM_LIST_PATH = "src\\main\\resources\\item.txt";

    public static void serializeUserMap(Map<String, User> userMap) {
        File userMapFile = new File(USER_MAP_PATH);
        try {
            if (!userMapFile.exists()) {
                userMapFile.createNewFile();
            }
            try (ObjectOutputStream obOutStream = new ObjectOutputStream(new FileOutputStream(USER_MAP_PATH))) {
                obOutStream.writeObject(userMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serializeItemList(List<Item> items) {
        File itemListFile = new File(ITEM_LIST_PATH);
        try {
            if (!itemListFile.exists()) {
                itemListFile.createNewFile();
            }
            try (ObjectOutputStream obOutStream = new ObjectOutputStream(new FileOutputStream(ITEM_LIST_PATH))) {
                obOutStream.writeObject(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> deserializeItemList() {
        List<Item> result = new ArrayList<>();
        File itemListFile = new File(ITEM_LIST_PATH);
        if (itemListFile.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ITEM_LIST_PATH))) {
                Object o = objectInputStream.readObject();
                return (List<Item>) o;
            } catch (EOFException e){
                e.getMessage();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static Map<String, User> deserializeUserMap() {
        Map<String, User> result = new HashMap<>();
        File userMapFile = new File(USER_MAP_PATH);
        if (userMapFile.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USER_MAP_PATH))) {
                Object o = objectInputStream.readObject();
                return (Map<String, User>) o;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}

