package Widgets;

import Items.InventoryItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class InventoryPanel {

    public InventoryPanel()
    {

    }

    public ScrollPane createPanel(Scene scene) {
        TableView<InventoryItem> inventoryTable = buildInventoryLine();

        // Add items to the inventory
        ObservableList<InventoryItem> items = FXCollections.observableArrayList(
                new InventoryItem("Epee", "Arme",
                        1, "Assets/ICONS/base/tile049.png"),
                new InventoryItem("Bouclier", "Defense",
                        2, "Assets/ICONS/base/tile064.png"),
                new InventoryItem("Potion", "Consommable",
                        5, "Assets/ICONS/base/tile114.png")
        );
        inventoryTable.setItems(items);

        // Context menu setup omitted for brevity
        setupContextMenu(inventoryTable);

        ScrollPane scrollPane = new ScrollPane(inventoryTable);
        configureScrollPane(scrollPane, scene);
        return scrollPane;
    }

    private TableView<InventoryItem> buildInventoryLine() {
        TableView<InventoryItem> inventoryTable = new TableView<>();

        // Column for item icons
        TableColumn<InventoryItem, ImageView> iconColumn = new TableColumn<>("Icon");

        iconColumn.setCellValueFactory(cellData -> {
            String iconPath = cellData.getValue().getIconPath();
            ImageView imageView = null;
            if (iconPath != null && !iconPath.isEmpty()) {
                try {
                    Image image = new Image(iconPath, true); // true for background loading
                    imageView = new ImageView(image);
                    imageView.setFitHeight(32);
                    imageView.setFitWidth(32);
                } catch (IllegalArgumentException e) {
                    // Handle the case where the icon path is invalid
                }
            }
            return new SimpleObjectProperty<>(imageView);
        });

        iconColumn.setCellFactory(col -> {
            // TableCell customization
            TableCell<InventoryItem, ImageView> cell = new TableCell<InventoryItem, ImageView>() {
                @Override
                protected void updateItem(ImageView item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(item); // Wrap the ImageView in an HBox
                        hbox.setAlignment(Pos.CENTER_LEFT); // Center-left alignment for the icon
                        hbox.setPadding(new Insets(0, 0, 0, 5)); // Add padding on the left
                        setGraphic(hbox);
                    }
                }
            };
            return cell;
        });


        // Item Name
        TableColumn<InventoryItem, String> itemColumn = new TableColumn<>("Item");
        itemColumn.setCellFactory((Callback)generalCellFactory);
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        // Item Type
        TableColumn<InventoryItem, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellFactory((Callback)generalCellFactory);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));

        // Quantity
        TableColumn<InventoryItem, Integer> quantityColumn = new TableColumn<>("Quantité");
        quantityColumn.setCellFactory((Callback)generalCellFactory);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        inventoryTable.getColumns().addAll(iconColumn, itemColumn, typeColumn, quantityColumn);
        return inventoryTable;
    }

    /* Custom table factory */
    Callback<TableColumn<InventoryItem, Object>, TableCell<InventoryItem, Object>> generalCellFactory = column -> new TableCell<InventoryItem, Object>() {
        @Override
        protected void updateItem(Object item, boolean empty) {
            Font gameFont12 = Font.loadFont(getClass()
                    .getResourceAsStream("/Assets/FONT/alagard/alagard.ttf"), 16);
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
            } else {
                setText(item.toString());
                setFont(gameFont12);
                setStyle("-fx-alignment: CENTER;");
            }
        }
    };

    private void setupContextMenu(TableView<InventoryItem> inventoryTable) {
        ContextMenu contextMenu = new ContextMenu();
        inventoryTable.setRowFactory(tv -> {
            TableRow<InventoryItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                    InventoryItem item = row.getItem();
                    configureContextMenuForRow(contextMenu, item);
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    private void configureContextMenuForRow(ContextMenu contextMenu, InventoryItem item) {
        contextMenu.getItems().clear(); // Clear previous items
        // Add menu items based on the item type
        switch (item.getItemType()) {
            case "Arme":
                contextMenu.getItems().addAll(new MenuItem("Utiliser"), new MenuItem("Jeter"));
                break;
            case "Defense":
                contextMenu.getItems().addAll(new MenuItem("Equiper"), new MenuItem("Jeter"));
                break;
            case "Consommable":
                contextMenu.getItems().addAll(new MenuItem("Consommer"), new MenuItem("Jeter"));
                break;
        }
    }

    private void configureScrollPane(ScrollPane scrollPane, Scene scene) {
        scrollPane.setPrefViewportWidth(scene.getWidth() / 5);
        scrollPane.setPrefViewportHeight(scene.getHeight() / 3);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #222; -fx-border-color: #AAA;");
    }
}
