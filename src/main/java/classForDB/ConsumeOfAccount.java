package classForDB;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ConsumeOfAccount {
    private SimpleObjectProperty<BaseAccount> account;
    private SimpleLongProperty sendAmount;
    private SimpleLongProperty receiveAmount;

    ConsumeOfAccount() {
        this.account = new SimpleObjectProperty<BaseAccount>();
        this.sendAmount = new SimpleLongProperty();
        this.receiveAmount = new SimpleLongProperty();
    };

    public ConsumeOfAccount(BaseAccount account, Long sendAmount, Long receiveAmount) {
        this.account = new SimpleObjectProperty<BaseAccount>(account);
        this.sendAmount = new SimpleLongProperty(sendAmount);
        this.receiveAmount = new SimpleLongProperty(receiveAmount);
    }

    // ---- account getter setter
    public SimpleObjectProperty<BaseAccount> getAccountProperty() {
        return account;
    }

    public BaseAccount getAccount() {
        return account.get();
    }

    public void setAccount(BaseAccount account) {
        this.account.set(account);
    }

    // ---- sendAmount getter setter
    public SimpleLongProperty getSendAmountProperty() {
        return sendAmount;
    }

    public Long getSendAmount() {
        return sendAmount.get();
    }

    public void setSendAmount(Long sendAmount) {
        this.sendAmount.set(sendAmount);
    }

    // ---- receiveAmount getter setter
    public SimpleLongProperty getReceiveAmountProperty() {
        return receiveAmount;
    }

    public Long getReceiveAmount() {
        return receiveAmount.get();
    }

    public void setReceiveAmount(Long receiveAmount) {
        this.receiveAmount.set(receiveAmount);
    }

}
