package classForDB;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerAccount extends PersonalAccount {
    private SimpleStringProperty cardID;
    private SimpleStringProperty PINNumber;
    private SimpleStringProperty typeCard;
    private SimpleObjectProperty<LocalDateTime> creationDate;
    private SimpleObjectProperty<LocalDateTime> expiryDate;

    public CustomerAccount() {
        super();
        this.cardID = new SimpleStringProperty();
        this.PINNumber = new SimpleStringProperty();
        this.typeCard = new SimpleStringProperty();
        this.creationDate = new SimpleObjectProperty<LocalDateTime>();
        this.expiryDate = new SimpleObjectProperty<LocalDateTime>();
    };

    public CustomerAccount(String ID, String name, String email, String phoneNumber, String typeAccount,
            Address address, LocalDateTime joinTime, Long moneyBalance, Boolean status, String identifyID,
            String mobilePhoneNumber, String cardID, String PINNumber, String typeCard, LocalDateTime creationDate,
            LocalDateTime expiryDate) {
        super(ID, name, email, phoneNumber, typeAccount, address, joinTime, moneyBalance, status, identifyID,
                mobilePhoneNumber);
        this.cardID = new SimpleStringProperty(cardID);
        this.PINNumber = new SimpleStringProperty(PINNumber);
        this.typeCard = new SimpleStringProperty(typeCard);
        this.creationDate = new SimpleObjectProperty<LocalDateTime>(creationDate);
        this.expiryDate = new SimpleObjectProperty<LocalDateTime>(expiryDate);
    }

    // ---- CardID getter setter
    public SimpleStringProperty getCardIDProperty() {
        return cardID;
    }

    public String getCardID() {
        return cardID.get();
    }

    public void setCardID(String cardID) {
        this.cardID.set(cardID);
    }

    // ---- PINNumber getter setter
    public SimpleStringProperty getPINNumberProperty() {
        return PINNumber;
    }

    public String getPINNumber() {
        return PINNumber.get();
    }

    public void setPINNumber(String PINNumber) {
        this.PINNumber.set(PINNumber);
    }

    // ---- TypeCard getter setter
    public SimpleStringProperty getTypeCardProperty() {
        return typeCard;
    }

    public String getTypeCard() {
        return typeCard.get();
    }

    public void setTypeCard(String typeCard) {
        this.typeCard.set(typeCard);
    }

    // ---- CreationDate getter setter
    public SimpleObjectProperty<LocalDateTime> getCreationDateProperty() {
        return creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate.get();
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate.set(creationDate);
    }

    // ---- ExpiryDate getter setter
    public SimpleObjectProperty<LocalDateTime> getExpiryDateProperty() {
        return expiryDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate.get();
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate.set(expiryDate);
    }

}
