package classForDB;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report {
    private SimpleStringProperty reportID;
    private SimpleStringProperty title;
    private SimpleObjectProperty<LocalDateTime> createdDate;
    private SimpleObjectProperty<LocalDateTime> fromDate;
    private SimpleObjectProperty<LocalDateTime> toDate;
    private SimpleStringProperty frequency;
    private SimpleObjectProperty<AccountReportingSubcategories> accountReport;
    private SimpleObjectProperty<TransactionReportingSubcategories> transactionReport;

    public Report() {
        this.reportID = new SimpleStringProperty();
        this.title = new SimpleStringProperty();
        this.createdDate = new SimpleObjectProperty<LocalDateTime>();
        this.fromDate = new SimpleObjectProperty<LocalDateTime>();
        this.toDate = new SimpleObjectProperty<LocalDateTime>();
        this.frequency = new SimpleStringProperty();
        this.accountReport = new SimpleObjectProperty<AccountReportingSubcategories>();
        this.transactionReport = new SimpleObjectProperty<TransactionReportingSubcategories>();
    };

    public Report(String reportID, String title, LocalDateTime createdDate, LocalDateTime fromDate,
            LocalDateTime toDate, String frequency, AccountReportingSubcategories accountReport,
            TransactionReportingSubcategories transactionReport) {
        this.reportID = new SimpleStringProperty(reportID);
        this.title = new SimpleStringProperty(title);
        this.createdDate = new SimpleObjectProperty<LocalDateTime>(createdDate);
        this.fromDate = new SimpleObjectProperty<LocalDateTime>(fromDate);
        this.toDate = new SimpleObjectProperty<LocalDateTime>(toDate);
        this.frequency = new SimpleStringProperty(frequency);
        this.accountReport = new SimpleObjectProperty<AccountReportingSubcategories>(accountReport);
        this.transactionReport = new SimpleObjectProperty<TransactionReportingSubcategories>(transactionReport);
    }

    // ---- reportID getter setter
    public SimpleStringProperty getReportIDProperty() {
        return reportID;
    }

    public String getReportID() {
        return reportID.get();
    }

    public void setReportID(String reportID) {
        this.reportID.set(reportID);
    }

    // ---- title getter setter
    public SimpleStringProperty getTitleProperty() {
        return title;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    // ---- createdDate getter setter
    public SimpleObjectProperty<LocalDateTime> getCreatedDateProperty() {
        return createdDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate.get();
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate.set(createdDate);
    }

    // ---- fromDate getter setter
    public SimpleObjectProperty<LocalDateTime> getFromDateProperty() {
        return fromDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate.get();
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate.set(fromDate);
    }

    // ---- toDate getter setter
    public SimpleObjectProperty<LocalDateTime> getToDateProperty() {
        return toDate;
    }

    public LocalDateTime getToDate() {
        return toDate.get();
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate.set(toDate);
    }

    // ---- frequency getter setter
    public SimpleStringProperty getFrequencyProperty() {
        return frequency;
    }

    public String getFrequency() {
        return frequency.get();
    }

    public void setFrequency(String frequency) {
        this.frequency.set(frequency);
    }

    // ---- accountReport getter setter
    public SimpleObjectProperty<AccountReportingSubcategories> getAccountReportProperty() {
        return accountReport;
    }

    public AccountReportingSubcategories getAccountReport() {
        return accountReport.get();
    }

    public void setAccountReport(AccountReportingSubcategories accountReport) {
        this.accountReport.set(accountReport);
    }

    // ---- transactionReport getter setter
    public SimpleObjectProperty<TransactionReportingSubcategories> getTransactionReportProperty() {
        return transactionReport;
    }

    public TransactionReportingSubcategories getTransactionReport() {
        return transactionReport.get();
    }

    public void setTransactionReport(TransactionReportingSubcategories transactionReport) {
        this.transactionReport.set(transactionReport);
    }

}
