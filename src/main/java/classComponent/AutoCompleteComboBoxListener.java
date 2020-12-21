package classComponent;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.skins.JFXComboBoxListViewSkin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private JFXComboBox comboBox;
    private StringBuilder sb;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;
    private JFXComboBoxListViewSkin cbSkin;

    public AutoCompleteComboBoxListener(final JFXComboBox comboBox) {
        this.comboBox = comboBox;
        sb = new StringBuilder();
        data = comboBox.getItems();
        cbSkin = new JFXComboBoxListViewSkin(comboBox);
        comboBox.setSkin(cbSkin);
        cbSkin.getPopupContent().addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);

        this.comboBox.focusedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && !data.isEmpty() && comboBox.getItems().size() == 0) {
                    updateSelectComboBox(newValue);
                }
                updateSelectComboBox(newValue);

            }
        });

    }

    public void updateSelectComboBox(Boolean newValue) {
        if (!newValue) {
            if (!data.isEmpty()) {
                if (comboBox.getItems().size() == 0) {
                    comboBox.setValue("");
                    char c = data.get(0).toString().charAt(0);
                    handle(new KeyEvent(KeyEvent.KEY_PRESSED, "character", "text",
                            KeyCode.getKeyCode(String.valueOf(Character.toUpperCase(c))), false, false, false, false));
                }
            } else {
                comboBox.setValue("Not available");
            }

        }
    }

    @Override
    public void handle(KeyEvent event) {
        // caret: position of point writer
        if (event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.DOWN) {
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if (event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.isControlDown()
                || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
                || event.getCode() == KeyCode.TAB) {
            return;
        }

        ObservableList list = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).toString().toLowerCase()
                    .startsWith(AutoCompleteComboBoxListener.this.comboBox.getEditor().getText().toLowerCase())) {
                list.add(data.get(i));
            }
        }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if (!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if (!list.isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if (caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

}