package Widgets;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;

public class GroupedComboBoxWidget extends HBox {
    private ComboBox<String> mainComboBox;
    private ComboBox<String> subComboBox;
    private Map<String, ObservableList<String>> optionsMap;

    public GroupedComboBoxWidget() {
        this.setSpacing(20);
        this.setPadding(new Insets(10, 10, 10, 10));

        mainComboBox = new ComboBox<>();
        subComboBox = new ComboBox<>();
        optionsMap = new HashMap<>();

        // Labels
        mainComboBox.setPromptText("Select an action type");
        subComboBox.setPromptText("Select an action");

        mainComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && optionsMap.containsKey(newValue)) {
                subComboBox.setItems(optionsMap.get(newValue));
            }
        });

        this.getChildren().addAll(mainComboBox, subComboBox);
    }

    public void setMainOptions(ObservableList<String> mainOptions) {
        mainComboBox.setItems(mainOptions);
    }

    public void addSubOptions(String mainOption, ObservableList<String> subOptions) {
        optionsMap.put(mainOption, subOptions);
    }
}
