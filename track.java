import java.util.*;
import java.io.*;

public class track {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        String itemName = "";
        int itemQuantity = 0;

        System.out.println("Offline Inventory tracker");
        System.out.println("Open new store or no (y/n)");
        String openStore = sc.nextLine();

        ArrayList<String> items = new ArrayList<>();

        // LOAD FILE INTO ARRAYLIST
        File file = new File("store.txt");
        if (file.exists()) {
            Scanner readFile = new Scanner(file);
            while (readFile.hasNextLine()) {
                items.add(readFile.nextLine());
            }
            readFile.close();
        }

        // 🔹 SHOW ITEMS ALWAYS
        for (String item : items) {
            System.out.println(item);
            String[] parts = item.split(",");
            int qty = Integer.parseInt(parts[1].replace("Item quantity:", "").trim());
            if (qty <= 1) {
                System.out.println("⚠ " + parts[0].replace("Item name:", "").trim() + " is low in stock");
            }
        }

        if (openStore.equals("y")) {

            System.out.println("Enter how much items you have");
            int itemNums = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < itemNums; i++) {
                System.out.println("Enter " + (i + 1) + " item name");
                itemName = sc.nextLine();

                System.out.println("Enter " + (i + 1) + " item quantity");
                itemQuantity = sc.nextInt();
                sc.nextLine();

                addItems(items, itemName, itemQuantity);
            }

            // 🔹 Show items after adding
            for (String item : items) {
                System.out.println(item);
                String[] parts = item.split(",");
                int qty = Integer.parseInt(parts[1].replace("Item quantity:", "").trim());
                if (qty <= 1) {
                    System.out.println("⚠ " + parts[0].replace("Item name:", "").trim() + " is low in stock");
                }
            }

            FileWriter writer = new FileWriter("store.txt", false);
            for (String item : items) {
                writer.write(item + "\n");
            }
            writer.close();

        }

        System.out.println("Do you wanna remove an item (y/n)");
        String r = sc.nextLine();

        if (r.equals("y")) {
            System.out.println("Item name:");
            itemName = sc.nextLine();

            System.out.println("Item Quantity:");
            itemQuantity = sc.nextInt();
            sc.nextLine();

            removeItem(items, itemName, itemQuantity);

            // 🔹 Show items after removing
            for (String item : items) {
                System.out.println(item);
                String[] parts = item.split(",");
                int qty = Integer.parseInt(parts[1].replace("Item quantity:", "").trim());
                if (qty <= 1) {
                    System.out.println("⚠ " + parts[0].replace("Item name:", "").trim() + " is low in stock");
                }
            }

            FileWriter writer = new FileWriter("store.txt", false);
            for (String item : items) {
                writer.write(item + "\n");
            }
            writer.close();

        } else {
            System.out.println("Do you wanna edit (y/n): ");
            String e = sc.nextLine();

            if (e.equals("y")) {
                System.out.println("Whats the item you want to edit: ");
                itemName = sc.nextLine();

                System.out.println("Enter new quantity: ");
                int newQuantity = sc.nextInt();
                sc.nextLine();

                updateQuantity(items, itemName, newQuantity);

                // 🔹 Show items after editing
                for (String item : items) {
                    System.out.println(item);
                    String[] parts = item.split(",");
                    int qty = Integer.parseInt(parts[1].replace("Item quantity:", "").trim());
                    if (qty <= 1) {
                        System.out.println("⚠ " + parts[0].replace("Item name:", "").trim() + " is low in stock");
                    }
                }

                FileWriter writer = new FileWriter("store.txt", false);
                for (String item : items) {
                    writer.write(item + "\n");
                }
                writer.close();
            }
        }

        sc.close();
    }

    public static void addItems(ArrayList<String> items, String itemName, int itemQuantity) {
        items.add("Item name: " + itemName + ",  Item quantity: " + itemQuantity);
    }

    public static void removeItem(ArrayList<String> items, String itemName, int itemQuantity) {
        Iterator<String> it = items.iterator();
        while (it.hasNext()) {
            String item = it.next();
            if (item.contains("Item name: " + itemName)
                    && item.contains("Item quantity: " + itemQuantity)) {
                it.remove();
                System.out.println("Item removed");
                return;
            }
        }
        System.out.println("Item not found");
    }

    public static void updateQuantity(ArrayList<String> items, String itemName, int newQuantity) {
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            if (item.contains("Item name: " + itemName)) {
                items.set(i,
                        "Item name: " + itemName + ",  Item quantity: " + newQuantity);
                return;
            }
        }
        System.out.println("Item not found");
    }
}