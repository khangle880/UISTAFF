package classForDB;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TransAmountPerRole {
    private SimpleObjectProperty<TransactionRole> transRole;
    private SimpleObjectProperty<LocalDateTime> transDate;
    private SimpleLongProperty amountTrans;

    TransAmountPerRole() {
        this.transRole = new SimpleObjectProperty<TransactionRole>();
        this.transDate = new SimpleObjectProperty<LocalDateTime>();
        this.amountTrans = new SimpleLongProperty();
    };

    public TransAmountPerRole(TransactionRole transRole, LocalDateTime transDate, Long amountTrans) {
        this.transRole = new SimpleObjectProperty<TransactionRole>(transRole);
        this.transDate = new SimpleObjectProperty<LocalDateTime>(transDate);
        this.amountTrans = new SimpleLongProperty(amountTrans);
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

    // ---- AmountTrans getter setter
    public SimpleLongProperty getAmountTransProperty() {
        return amountTrans;
    }

    public Long getAmountTrans() {
        return amountTrans.get();
    }

    public void setAmountTrans(Long amountTrans) {
        this.amountTrans.set(amountTrans);
    }

}
