package Items;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem {
    private final SimpleStringProperty itemName;
    private final SimpleStringProperty itemType;
    private final SimpleIntegerProperty quantity;
    private final String iconPath; // Path to the icon image

    public InventoryItem(String itemName, String itemType, int quantity, String iconPath) {
        this.itemName = new SimpleStringProperty(itemName);
        this.itemType = new SimpleStringProperty(itemType);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.iconPath = iconPath;
    }

    // Getters
    public String getItemName() { return itemName.get(); }
    public String getItemType() { return itemType.get(); }
    public int getQuantity() { return quantity.get(); }
    public String getIconPath() { return iconPath; }

    // Property getters for TableView
    public SimpleStringProperty itemNameProperty() { return itemName; }
    public SimpleStringProperty itemTypeProperty() { return itemType; }
    public SimpleIntegerProperty quantityProperty() { return quantity; }
}