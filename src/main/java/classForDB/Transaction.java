package classForDB;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {
    private SimpleStringProperty transID;
    private SimpleObjectProperty<Atm> atm;
    private SimpleObjectProperty<BaseAccount> senderAccount;
    private SimpleObjectProperty<BaseAccount> receiveAccount;
    private SimpleObjectProperty<TransactionRole> transRole;
    private SimpleLongProperty cashAmount;
    private SimpleObjectProperty<LocalDateTime> executionDate;
    private SimpleStringProperty content;

    public Transaction() {
        this.transID = new SimpleStringProperty();
        this.atm = new SimpleObjectProperty<Atm>();
        this.senderAccount = new SimpleObjectProperty<BaseAccount>();
        this.receiveAccount = new SimpleObjectProperty<BaseAccount>();
        this.transRole = new SimpleObjectProperty<TransactionRole>();
        this.cashAmount = new SimpleLongProperty();
        this.executionDate = new SimpleObjectProperty<LocalDateTime>();
        this.content = new SimpleStringProperty();
    };

    public Transaction(String transID, Atm atm, BaseAccount senderAccount, BaseAccount receiveAccount,
            TransactionRole transRole, Long cashAmount, LocalDateTime executionDate, String content) {
        this.transID = new SimpleStringProperty(transID);
        this.atm = new SimpleObjectProperty<Atm>(atm);
        this.senderAccount = new SimpleObjectProperty<BaseAccount>(senderAccount);
        this.receiveAccount = new SimpleObjectProperty<BaseAccount>(receiveAccount);
        this.transRole = new SimpleObjectProperty<TransactionRole>(transRole);
        this.cashAmount = new SimpleLongProperty(cashAmount);
        this.executionDate = new SimpleObjectProperty<LocalDateTime>(executionDate);
        this.content = new SimpleStringProperty(content);
    }

    // ---- transID getter setter
    public SimpleStringProperty getTransIDProperty() {
        return transID;
    }

    public String getTransID() {
        return transID.get();
    }

    public void setTransID(String transID) {
        this.transID.set(transID);
    }

    // ---- atm getter setter
    public SimpleObjectProperty<Atm> getAtmProperty() {
        return atm;
    }

    public Atm getAtm() {
        return atm.get();
    }

    public void setAtm(Atm atm) {
        this.atm.set(atm);
    }

    // ---- senderAccount getter setter
    public SimpleObjectProperty<BaseAccount> getSenderAccountProperty() {
        return senderAccount;
    }

    public BaseAccount getSenderAccount() {
        return senderAccount.get();
    }

    public void setSenderAccount(BaseAccount senderAccount) {
        this.senderAccount.set(senderAccount);
    }

    // ---- receiveAccount getter setter
    public SimpleObjectProperty<BaseAccount> getReceiveAccountProperty() {
        return receiveAccount;
    }

    public BaseAccount getReceiveAccount() {
        return receiveAccount.get();
    }

    public void setReceiveAccount(BaseAccount receiveAccount) {
        this.receiveAccount.set(receiveAccount);
    }

    // ---- transRole getter setter
    public SimpleObjectProperty<TransactionRole> getTransRoleProperty() {
        return transRole;
    }

    public TransactionRole getTransRole() {
        return transRole.get();
    }

    public void setTransRole(TransactionRole transRole) {
        this.transRole.set(transRole);
    }

    // ---- cashAmount getter setter
    public SimpleLongProperty getCashAmountProperty() {
        return cashAmount;
    }

    public Long getCashAmount() {
        return cashAmount.get();
    }

    public void setCashAmount(Long cashAmount) {
        this.cashAmount.set(cashAmount);
    }

    // ---- executionDate getter setter
    public SimpleObjectProperty<LocalDateTime> getExecutionDateProperty() {
        return executionDate;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate.get();
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate.set(executionDate);
    }

    // ---- content getter setter
    public SimpleStringProperty getContentProperty() {
        return content;
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

}
