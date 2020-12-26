package classForDB;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class AccountReportingSubcategories {
    private SimpleListProperty<JoinTimeOfAccount> joinedTimeStatistic;
    private SimpleListProperty<ConsumeOfAccount> consumeStatistic;

    public AccountReportingSubcategories() {
        this.joinedTimeStatistic = new SimpleListProperty<JoinTimeOfAccount>();
        this.consumeStatistic = new SimpleListProperty<ConsumeOfAccount>();
    };

    public AccountReportingSubcategories(ObservableList<JoinTimeOfAccount> joinedTimeStatistic,
            ObservableList<ConsumeOfAccount> consumeStatistic) {
        this.joinedTimeStatistic = new SimpleListProperty<JoinTimeOfAccount>(joinedTimeStatistic);
        this.consumeStatistic = new SimpleListProperty<ConsumeOfAccount>(consumeStatistic);
    }

    // ---- joinedTimeStatistic getter setter
    public SimpleListProperty<JoinTimeOfAccount> getJoinedTimeStatisticProperty() {
        return joinedTimeStatistic;
    }

    public ObservableList<JoinTimeOfAccount> getJoinedTimeStatistic() {
        return joinedTimeStatistic.get();
    }

    public void setJoinedTimeStatistic(ObservableList<JoinTimeOfAccount> joinedTimeStatistic) {
        this.joinedTimeStatistic.set(joinedTimeStatistic);
    }

    // ---- consumeStatistic getter setter
    public SimpleListProperty<ConsumeOfAccount> getConsumeStatisticProperty() {
        return consumeStatistic;
    }

    public ObservableList<ConsumeOfAccount> getConsumeStatistic() {
        return consumeStatistic.get();
    }

    public void setConsumeStatistic(ObservableList<ConsumeOfAccount> consumeStatistic) {
        this.consumeStatistic.set(consumeStatistic);
    }

}
