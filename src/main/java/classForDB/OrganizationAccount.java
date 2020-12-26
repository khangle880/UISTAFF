package classForDB;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;

public class OrganizationAccount extends BaseAccount {
    private SimpleStringProperty password;
    private SimpleStringProperty description;
    private SimpleStringProperty rating;

    public OrganizationAccount() {
        super();
        this.password = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.rating = new SimpleStringProperty();
    };

    public OrganizationAccount(String ID, String name, String email, String phoneNumber, String typeAccount,
            Address address, LocalDateTime joinTime, Long amountBalance, Boolean status, String password,
            String description, String rating) {
        super(ID, name, email, phoneNumber, typeAccount, address, joinTime, amountBalance, status);
        this.password = new SimpleStringProperty(password);
        this.description = new SimpleStringProperty(description);
        this.rating = new SimpleStringProperty(rating);
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

    // ---- Description getter setter
    public SimpleStringProperty getDescriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    // ---- rating getter setter
    public SimpleStringProperty getRatingProperty() {
        return rating;
    }

    public String getRating() {
        return rating.get();
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

}
