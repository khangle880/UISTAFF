package classForDB;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class BaseAccount {
    private SimpleStringProperty ID;
    private SimpleStringProperty name;
    private SimpleStringProperty email;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty typeAccount;
    private SimpleObjectProperty<Address> address;
    private SimpleObjectProperty<LocalDateTime> JoinTime;
    private SimpleLongProperty moneyBalance;
    private SimpleBooleanProperty status;

    public BaseAccount() {
        this.ID = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.typeAccount = new SimpleStringProperty();
        this.address = new SimpleObjectProperty<Address>();
        this.JoinTime = new SimpleObjectProperty<LocalDateTime>();
        this.moneyBalance = new SimpleLongProperty();
        this.status = new SimpleBooleanProperty();
    };

    public BaseAccount(String ID, String name, String email, String phoneNumber, String typeAccount, Address address,
            LocalDateTime joinTime, Long moneyBalance, Boolean status) {
        this.ID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.typeAccount = new SimpleStringProperty(typeAccount);
        this.address = new SimpleObjectProperty<Address>(address);
        this.JoinTime = new SimpleObjectProperty<LocalDateTime>(joinTime);
        this.moneyBalance = new SimpleLongProperty(moneyBalance);
        this.status = new SimpleBooleanProperty(status);
    }

    // ---- ID getter setter
    public SimpleStringProperty getIDProperty() {
        return ID;
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    // ---- NAME getter setter
    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    // ---- EMAIL getter setter
    public SimpleStringProperty getEmailProperty() {
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // ---- phoneNumber getter setter
    public SimpleStringProperty getPhoneNumberProperty() {
        return phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    // ---- typeAccount getter setter
    public SimpleStringProperty getTypeAccountProperty() {
        return typeAccount;
    }

    public String getTypeAccount() {
        return typeAccount.get();
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount.set(typeAccount);
    }

    // ---- Address getter setter
    public SimpleObjectProperty<Address> getAddressProperty() {
        return address;
    }

    public Address getAddress() {
        return address.get();
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }

    // ---- JoinTime getter setter
    public SimpleObjectProperty<LocalDateTime> getJoinTimeProperty() {
        return JoinTime;
    }

    public LocalDateTime getJoinTime() {
        return JoinTime.get();
    }

    public void setJoinTime(LocalDateTime joinTime) {
        this.JoinTime.set(joinTime);
    }

    // ---- MoneyBalance getter setter
    public SimpleLongProperty getMoneyBalanceProperty() {
        return moneyBalance;
    }

    public Long getMoneyBalance() {
        return moneyBalance.get();
    }

    public void setMoneyBalance(Long moneyBalance) {
        this.moneyBalance.set(moneyBalance);
    }

    // ---- Status getter setter
    public SimpleBooleanProperty getStatusProperty() {
        return status;
    }

    public Boolean getStatus() {
        return status.get();
    }

    public void setStatus(Boolean status) {
        this.status.set(status);
    }

}
