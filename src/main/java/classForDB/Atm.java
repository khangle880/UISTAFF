package classForDB;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Atm {
    private SimpleStringProperty ID;
    private SimpleStringProperty name;
    private SimpleObjectProperty<Address> address;
    private SimpleLongProperty amountStorage;
    private SimpleBooleanProperty status;

    public Atm() {
        this.ID = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleObjectProperty<Address>();
        this.amountStorage = new SimpleLongProperty();
        this.status = new SimpleBooleanProperty();
    };

    public Atm(String ID, String name, Address address, Long amountStorage, Boolean status) {
        this.ID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleObjectProperty<Address>(address);
        this.amountStorage = new SimpleLongProperty(amountStorage);
        this.status = new SimpleBooleanProperty(status);
    }

    public SimpleBooleanProperty getStatusProperty() {
        return status;
    }

    public Boolean getStatus() {
        return status.get();
    }

    public void setStatus(Boolean status) {
        this.status.set(status);
    }

    public SimpleLongProperty getAmountStorageProperty() {
        return amountStorage;
    }

    public Long getAmountStorage() {
        return amountStorage.get();
    }

    public void setAmountStorage(Long amountStorage) {
        this.amountStorage.set(amountStorage);
    }
    
    public SimpleObjectProperty<Address> getAddressProperty() {
        return address;
    }

    public Address getAddress() {
        return address.get();
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }
    
    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty getIDProperty() {
        return ID;
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

}
