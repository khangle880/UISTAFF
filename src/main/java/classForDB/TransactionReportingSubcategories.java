package classForDB;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class TransactionReportingSubcategories {
    private SimpleListProperty<TransAmountPerRole> transAmountStatistic;
    private SimpleListProperty<TransNumPerRole> transNumStatistic;

    public TransactionReportingSubcategories() {
        this.transAmountStatistic = new SimpleListProperty<TransAmountPerRole>();
        this.transNumStatistic = new SimpleListProperty<TransNumPerRole>();
    };

    public TransactionReportingSubcategories(ObservableList<TransAmountPerRole> transAmountStatistic,
            ObservableList<TransNumPerRole> transNumStatistic) {
        this.transAmountStatistic = new SimpleListProperty<TransAmountPerRole>(transAmountStatistic);
        this.transNumStatistic = new SimpleListProperty<TransNumPerRole>(transNumStatistic);
    }

    // ---- transAmountStatistic getter setter
    public SimpleListProperty<TransAmountPerRole> getTransAmountStatisticProperty() {
        return transAmountStatistic;
    }

    public ObservableList<TransAmountPerRole> getTransAmountStatistic() {
        return transAmountStatistic.get();
    }

    public void setTransAmountStatistic(ObservableList<TransAmountPerRole> transAmountStatistic) {
        this.transAmountStatistic.set(transAmountStatistic);
    }

    // ---- transNumStatistic getter setter
    public SimpleListProperty<TransNumPerRole> getTransNumStatisticProperty() {
        return transNumStatistic;
    }

    public ObservableList<TransNumPerRole> getTransNumStatistic() {
        return transNumStatistic.get();
    }

    public void setTransNumStatistic(ObservableList<TransNumPerRole> transNumStatistic) {
        this.transNumStatistic.set(transNumStatistic);
    }

}
