package classForDB;

import javafx.beans.property.SimpleStringProperty;

public class TransactionRole {
    private SimpleStringProperty name;
    private SimpleStringProperty description;

    TransactionRole() {
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    };

    public TransactionRole(String name, String description) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
    }

    // ---- name getter setter
    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    // ---- description getter setter
    public SimpleStringProperty getDescriptionProperty() {
        return description;
    }

    public String Description() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

}
