package classForDB;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;

public class PersonalAccount extends BaseAccount {
    private SimpleStringProperty identifyID;
    private SimpleStringProperty mobilePhoneNumber;

    public PersonalAccount() {
        super();
        this.identifyID = new SimpleStringProperty();
        this.mobilePhoneNumber = new SimpleStringProperty();
    };

    public PersonalAccount(String ID, String name, String email, String phoneNumber, String typeAccount,
            Address address, LocalDateTime joinTime, Long moneyBalance, Boolean status, String identifyID,
            String mobilePhoneNumber) {
        super(ID, name, email, phoneNumber, typeAccount, address, joinTime, moneyBalance, status);
        this.identifyID = new SimpleStringProperty(identifyID);
        this.mobilePhoneNumber = new SimpleStringProperty(mobilePhoneNumber);
    }

    // ---- Identify ID getter setter
    public SimpleStringProperty getIdentifyIDProperty() {
        return identifyID;
    }

    public String getIdentifyID() {
        return identifyID.get();
    }

    public void setIdentifyID(String identifyID) {
        this.identifyID.set(identifyID);
    }

    // ---- MOBILE PHONE NUMBER getter setter
    public SimpleStringProperty getMobilePhoneNumberProperty() {
        return mobilePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber.get();
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber.set(mobilePhoneNumber);
    }

}
