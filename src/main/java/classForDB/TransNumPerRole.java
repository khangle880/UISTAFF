package classForDB;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TransNumPerRole {
    private SimpleObjectProperty<TransactionRole> transRole;
    private SimpleObjectProperty<LocalDateTime> transDate;
    private SimpleLongProperty numTrans;

    TransNumPerRole() {
        this.transRole = new SimpleObjectProperty<TransactionRole>();
        this.transDate = new SimpleObjectProperty<LocalDateTime>();
        this.numTrans = new SimpleLongProperty();
    };

    public TransNumPerRole(TransactionRole transRole, LocalDateTime transDate, Long numTrans) {
        this.transRole = new SimpleObjectProperty<TransactionRole>(transRole);
        this.transDate = new SimpleObjectProperty<LocalDateTime>(transDate);
        this.numTrans = new SimpleLongProperty(numTrans);
    }

    // ---- TransactionRole getter setter
    public SimpleObjectProperty<TransactionRole> getTransRoleProperty() {
        return transRole;
    }

    public TransactionRole getTransRole() {
        return transRole.get();
    }

    public void setTransRole(TransactionRole transRole) {
        this.transRole.set(transRole);
    }

    // ---- transDate getter setter
    public SimpleObjectProperty<LocalDateTime> getTransDateProperty() {
        return transDate;
    }

    public LocalDateTime getTransDate() {
        return transDate.get();
    }

    public void setTransDate(LocalDateTime transDate) {
        this.transDate.set(transDate);
    }

    // ---- numTrans getter setter
    public SimpleLongProperty getNumTransProperty() {
        return numTrans;
    }

    public Long getNumTrans() {
        return numTrans.get();
    }

    public void setNumTrans(Long numTrans) {
        this.numTrans.set(numTrans);
    }

}
