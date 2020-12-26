package classForDB;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class JoinTimeOfAccount {
    private SimpleObjectProperty<BaseAccount> account;
    private SimpleLongProperty numDayJoined;

    JoinTimeOfAccount() {
        this.account = new SimpleObjectProperty<BaseAccount>();
        this.numDayJoined = new SimpleLongProperty();
    };

    public JoinTimeOfAccount(BaseAccount account, Long numDayJoined) {
        this.account = new SimpleObjectProperty<BaseAccount>(account);
        this.numDayJoined = new SimpleLongProperty(numDayJoined);
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

    // ---- numDayJoined getter setter
    public SimpleLongProperty getNumDayJoinedProperty() {
        return numDayJoined;
    }

    public Long getNumDayJoined() {
        return numDayJoined.get();
    }

    public void setNumDayJoined(Long numDayJoined) {
        this.numDayJoined.set(numDayJoined);
    }

}
