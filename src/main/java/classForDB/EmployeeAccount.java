package classForDB;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeAccount extends PersonalAccount {
    private SimpleStringProperty password;
    private SimpleStringProperty position;

    public EmployeeAccount() {
        super();
        this.password = new SimpleStringProperty();
        this.position = new SimpleStringProperty();
    };

    public EmployeeAccount(String ID, String name, String email, String phoneNumber, String typeAccount,
            Address address, LocalDateTime joinTime, Long amountBalance, Boolean status, String identifyID,
            String mobilePhoneNumber, String password, String position) {
        super(ID, name, email, phoneNumber, typeAccount, address, joinTime, amountBalance, status, identifyID,
                mobilePhoneNumber);
        this.password = new SimpleStringProperty(password);
        this.position = new SimpleStringProperty(position);
    }

    // ---- Password getter setter
    public SimpleStringProperty getPasswordProperty() {
        return password;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    // ---- Position getter setter
    public SimpleStringProperty getPositionProperty() {
        return position;
    }

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

}
