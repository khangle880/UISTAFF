package classForDB;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Atm {
    private SimpleStringProperty code;
    private SimpleStringProperty name;
    private SimpleObjectProperty<Address> address;
    private SimpleLongProperty moneyStorage;
    private SimpleBooleanProperty status;

    public Atm() {
        this.code = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleObjectProperty<Address>();
        this.moneyStorage = new SimpleLongProperty();
        this.status = new SimpleBooleanProperty();
    };

    public Atm(String code, String name, Address address, Long moneyStorage, Boolean status) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleObjectProperty<Address>(address);
        this.moneyStorage = new SimpleLongProperty(moneyStorage);
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

    public SimpleLongProperty getMoneyStorageProperty() {
        return moneyStorage;
    }

    public Long getMoneyStorage() {
        return moneyStorage.get();
    }

    public void setMoneyStorage(Long moneyStorage) {
        this.moneyStorage.set(moneyStorage);
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

    public SimpleStringProperty getCodeProperty() {
        return code;
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

}
