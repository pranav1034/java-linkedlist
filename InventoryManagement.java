class Item {
    String itemName;
    int itemID;
    int quantity;
    double price;
    Item next;

    public Item(int itemID, String itemName, int quantity, double price) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.next = null;
    }
}

public class InventoryManagement {
    private Item head;

    public InventoryManagement() {
        this.head = null;
    }

    // Add an item at the beginning
    public void addAtBeginning(int itemID, String itemName, int quantity, double price) {
        Item newItem = new Item(itemID, itemName, quantity, price);
        newItem.next = head;
        head = newItem;
    }

    // Add an item at the end
    public void addAtEnd(int itemID, String itemName, int quantity, double price) {
        Item newItem = new Item(itemID, itemName, quantity, price);
        if (head == null) {
            head = newItem;
            return;
        }
        Item current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newItem;
    }

    // Add an item at a specific position
    public void addAtPosition(int itemID, String itemName, int quantity, double price, int position) {
        Item newItem = new Item(itemID, itemName, quantity, price);
        if (position == 0) {
            newItem.next = head;
            head = newItem;
            return;
        }
        Item current = head;
        for (int i = 0; i < position - 2 && current != null; i++) {
            current = current.next;
        }
        if (current != null) {
            newItem.next = current.next;
            current.next = newItem;
        }
    }

    // Remove an item based on Item ID
    public void removeItem(int itemID) {
        if (head == null) return;
        if (head.itemID == itemID) {
            head = head.next;
            return;
        }
        Item current = head;
        while (current.next != null && current.next.itemID != itemID) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    // Update the quantity of an item by Item ID
    public void updateQuantity(int itemID, int newQuantity) {
        Item item = searchByItemID(itemID);
        if (item != null) {
            item.quantity = newQuantity;
        }
    }

    // Search for an item by Item ID
    public Item searchByItemID(int itemID) {
        Item current = head;
        while (current != null) {
            if (current.itemID == itemID) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Search for an item by Item Name
    public Item searchByItemName(String itemName) {
        Item current = head;
        while (current != null) {
            if (current.itemName.equalsIgnoreCase(itemName)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Calculate and display total value of inventory
    public double calculateTotalValue() {
        double totalValue = 0;
        Item current = head;
        while (current != null) {
            totalValue += current.quantity * current.price;
            current = current.next;
        }
        return totalValue;
    }

    // Sort inventory by Item Name (Bubble Sort)
    public void sortByItemName() {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            swapped = false;
            Item current = head;
            while (current.next != null) {
                if (current.itemName.compareToIgnoreCase(current.next.itemName) > 0) {
                    // Swap data
                    swap(current, current.next);
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    // Sort inventory by Price (Bubble Sort)
    public void sortByPrice() {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            swapped = false;
            Item current = head;
            while (current.next != null) {
                if (current.price > current.next.price) {
                    // Swap data
                    swap(current, current.next);
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    // Helper function to swap two nodes' data
    private void swap(Item a, Item b) {
        int tempID = a.itemID;
        String tempName = a.itemName;
        int tempQuantity = a.quantity;
        double tempPrice = a.price;

        a.itemID = b.itemID;
        a.itemName = b.itemName;
        a.quantity = b.quantity;
        a.price = b.price;

        b.itemID = tempID;
        b.itemName = tempName;
        b.quantity = tempQuantity;
        b.price = tempPrice;
    }

    // Display all inventory items
    public void displayAll() {
        Item current = head;
        while (current != null) {
            System.out.println("ID: " + current.itemID + ", Name: " + current.itemName + ", Quantity: " + current.quantity + ", Price: $" + current.price);
            current = current.next;
        }
    }

    public static void main(String[] args) {
        InventoryManagement inventory = new InventoryManagement();
        // Adding 3 items
        inventory.addAtEnd(1, "Laptop", 10, 80.0);
        inventory.addAtEnd(2, "Mobile Phone", 50, 30.0);
        inventory.addAtEnd(3, "IPad", 30, 50.0);

        System.out.println("\nInitial Inventory:");
        inventory.displayAll();

        // Updating quantity of Mouse
        inventory.updateQuantity(2, 40);
        System.out.println("\nAfter Updating Quantity of Mobile Phone:");
        inventory.displayAll();

        // Searching for an item
        Item foundItem = inventory.searchByItemID(3);
        if (foundItem != null) {
            System.out.println("\nItem Found: " + foundItem.itemName + ", Price: $" + foundItem.price);
        }

        // Removing an item (Laptop)
        inventory.removeItem(1);
        System.out.println("\nAfter Removing Laptop:");
        inventory.displayAll();

        // Calculating total value of inventory
        System.out.println("\nTotal Inventory Value: $" + inventory.calculateTotalValue());
    }
}
