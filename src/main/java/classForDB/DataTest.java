package classForDB;

import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataTest {
    private static ObservableList<BaseAccount> accountList = FXCollections.observableArrayList(
            new OrganizationAccount("#445", "Kristina Hoppe", "McLaughlin@gmail.com", "0975845628", "Organization",
                    new Address("Sallie Tunnel", "Eldridge Greens", "Connecticut", "Greece"),
                    LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), 10000000L, true, "lazadaPro",
                    "real nha may be \n alo\n\n\n\n\n alo", "4.9"),
            new OrganizationAccount("#4243", "Catherine Von", "Bailey@gmail.com", "0988585568", "Organization",
                    new Address("Cassin Ranch", "Dameon Extension", "Nevada", "Saint Helena"),
                    LocalDateTime.of(2019, 03, 23, 14, 33, 48, 12), 10000000L, true, "tikiPro", "real nha may be",
                    "4.9"),
            new CustomerAccount("#4243", "Willie Hilll", "Stokes@gmail.com", "0988585568", "Customer",
                    new Address("Marcella Throughway", "Boyle Parks", "Mississippi", "Saint Barthelemy"),
                    LocalDateTime.of(2019, 03, 23, 14, 33, 48, 12), 10000000L, true, "197756852", "0147852369",
                    "97041235896", "147654", "Student", LocalDateTime.of(2017, 03, 3, 14, 33, 48, 12),
                    LocalDateTime.of(2020, 03, 3, 14, 33, 48, 12)),
            new EmployeeAccount("#86567", "Miss Alexandra Adams", "Koelpin@gmail.com", "0987898882", "Employee",
                    new Address("Zetta Corner", "Orn Ridges", "Georgia", "Tonga"),
                    LocalDateTime.of(2019, 03, 16, 14, 33, 48, 12), 10000000L, true, "197756852", "0147852369",
                    "khangLe124568", "Admin"),
            new EmployeeAccount("#86567", "Miss Alexandra Adams", "Koelpin@gmail.com", "0987898882", "Employee",
                    new Address("Zetta Corner", "Orn Ridges", "Georgia", "Tonga"),
                    LocalDateTime.of(2019, 03, 16, 14, 33, 48, 12), 10000000L, true, "197756852", "0147852369",
                    "khangLe124568", "Admin"),
            new BaseAccount("#435346", "Bad Boy", "Runolfsdottir@gmail.com", "0912040325", "Base",
                    new Address("Herman Drive", "Strosin Coves", "Kentucky", "Cyprus"),
                    LocalDateTime.of(2019, 03, 12, 14, 33, 48, 12), 10000000L, true));

    private static ObservableList<Atm> atmList = FXCollections.observableArrayList(
            new Atm("ab6632", "Atm so 3", new Address("26", "dong hoa", "Di an", "Binh duong"), 2200000L, false),
            new Atm("ab6633", "Atm so 4", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6634", "Atm so 5", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6635", "Atm so 6", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6636", "Atm so 7", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true));

    private static ObservableList<TransactionRole> transRoleList = FXCollections.observableArrayList(
            new TransactionRole("Withdrawal", "Withdrawal Description"),
            new TransactionRole("Transfer", "Transfer Description"),
            new TransactionRole("Provider payment", "Provider payment Description"),
            new TransactionRole("Deposit", "Deposit Description"));

    private static ObservableList<Transaction> transactionList = FXCollections.observableArrayList(
            new Transaction("16620", atmList.get(0), accountList.get(1), accountList.get(2), transRoleList.get(0),
                    4238493708L, LocalDateTime.of(2020, 04, 1, 14, 33, 48, 12), "Lead Branding Associate"),
            new Transaction("16620", atmList.get(0), accountList.get(0), accountList.get(3), transRoleList.get(3),
                    4091327613L, LocalDateTime.of(2020, 02, 1, 14, 33, 48, 12), "Senior Factors Manager"),
            new Transaction("16620", atmList.get(0), accountList.get(2), accountList.get(4), transRoleList.get(2),
                    6744523275L, LocalDateTime.of(2020, 06, 1, 14, 33, 48, 12), "Dynamic Response Producer"),
            new Transaction("16620", atmList.get(0), accountList.get(1), accountList.get(1), transRoleList.get(1),
                    8966181469L, LocalDateTime.of(2020, 02, 1, 14, 33, 48, 12), "Human Communications Agent"),
            new Transaction("16620", atmList.get(1), accountList.get(3), accountList.get(0), transRoleList.get(3),
                    6042454004L, LocalDateTime.of(2020, 03, 7, 14, 33, 48, 12), "Legacy Integration Facilitator"),
            new Transaction("16620", atmList.get(1), accountList.get(5), accountList.get(0), transRoleList.get(1),
                    8910119391L, LocalDateTime.of(2020, 01, 8, 14, 33, 48, 12), "International Division Strategist"),
            new Transaction("16620", atmList.get(1), accountList.get(3), accountList.get(2), transRoleList.get(0),
                    6805734680L, LocalDateTime.of(2020, 03, 6, 14, 33, 48, 12), "Central Branding Manager"),
            new Transaction("16620", atmList.get(1), accountList.get(4), accountList.get(1), transRoleList.get(0),
                    7408353304L, LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12), "Customer Program Facilitator"),
            new Transaction("16620", atmList.get(2), accountList.get(2), accountList.get(5), transRoleList.get(2),
                    6758424271L, LocalDateTime.of(2020, 03, 6, 14, 33, 48, 12), "Regional Data Administrator"),
            new Transaction("16620", atmList.get(2), accountList.get(3), accountList.get(3), transRoleList.get(2),
                    30715454L, LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12), "Forward Solutions Specialist"),
            new Transaction("16620", atmList.get(2), accountList.get(5), accountList.get(2), transRoleList.get(1),
                    5212113595L, LocalDateTime.of(2020, 03, 7, 14, 33, 48, 12), "Dynamic Optimization Executive"),
            new Transaction("16620", atmList.get(2), accountList.get(1), accountList.get(4), transRoleList.get(3),
                    9668750841L, LocalDateTime.of(2020, 03, 8, 14, 33, 48, 12), "Central Usability Manager"),
            new Transaction("16620", atmList.get(3), accountList.get(0), accountList.get(0), transRoleList.get(3),
                    2041518754L, LocalDateTime.of(2020, 03, 2, 14, 33, 48, 12), "Chief Marketing Assistant"),
            new Transaction("16620", atmList.get(3), accountList.get(2), accountList.get(3), transRoleList.get(0),
                    6205396986L, LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12), "Human Optimization Facilitator"),
            new Transaction("16620", atmList.get(3), accountList.get(2), accountList.get(2), transRoleList.get(1),
                    4113214052L, LocalDateTime.of(2020, 03, 6, 14, 33, 48, 12), "Global Identity Orchestrator"),
            new Transaction("16620", atmList.get(3), accountList.get(4), accountList.get(4), transRoleList.get(1),
                    3418920931L, LocalDateTime.of(2020, 04, 5, 14, 33, 48, 12), "National Branding Engineer"),
            new Transaction("16620", atmList.get(4), accountList.get(4), accountList.get(5), transRoleList.get(3),
                    9236853744L, LocalDateTime.of(2020, 04, 2, 14, 33, 48, 12), "Customer Directives Administrator"),
            new Transaction("16620", atmList.get(4), accountList.get(2), accountList.get(0), transRoleList.get(2),
                    946815421L, LocalDateTime.of(2020, 04, 15, 14, 33, 48, 12), "Global Metrics Administrator"),
            new Transaction("16620", atmList.get(4), accountList.get(0), accountList.get(2), transRoleList.get(2),
                    464982400L, LocalDateTime.of(2020, 03, 15, 14, 33, 48, 12), "District Metrics Liaison"),
            new Transaction("16620", atmList.get(4), accountList.get(5), accountList.get(1), transRoleList.get(1),
                    5699591248L, LocalDateTime.of(2020, 03, 25, 14, 33, 48, 12), "Future Intranet Associate"));

    private static ObservableList<TransAmountPerRole> transAmountPerRoleList = FXCollections.observableArrayList(
            new TransAmountPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12), 3723260985L),
            new TransAmountPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 6, 14, 33, 48, 12), 4335115289L),
            new TransAmountPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 8, 14, 33, 48, 12), 9223683401L),
            new TransAmountPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 10, 14, 33, 48, 12), 2042716858L),
            new TransAmountPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), 230212905L),
            new TransAmountPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 2, 14, 33, 48, 12), 3343013956L),
            new TransAmountPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 8, 14, 33, 48, 12), 7736467091L),
            new TransAmountPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 9, 14, 33, 48, 12), 7409796648L),
            new TransAmountPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 22, 14, 33, 48, 12), 4481285498L),
            new TransAmountPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 15, 14, 33, 48, 12), 5691496466L),
            new TransAmountPerRole(transRoleList.get(2), LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), 7776383531L),
            new TransAmountPerRole(transRoleList.get(2), LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12), 6403432238L),
            new TransAmountPerRole(transRoleList.get(2), LocalDateTime.of(2019, 03, 6, 14, 33, 48, 12), 3226421522L),
            new TransAmountPerRole(transRoleList.get(2), LocalDateTime.of(2019, 03, 8, 14, 33, 48, 12), 7176745233L),
            new TransAmountPerRole(transRoleList.get(3), LocalDateTime.of(2019, 03, 1, 14, 33, 48, 12), 3436383712L),
            new TransAmountPerRole(transRoleList.get(3), LocalDateTime.of(2019, 03, 2, 14, 33, 48, 12), 1520445016L),
            new TransAmountPerRole(transRoleList.get(3), LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12), 439955604L),
            new TransAmountPerRole(transRoleList.get(3), LocalDateTime.of(2019, 03, 9, 14, 33, 48, 12), 8730242043L));

    private static ObservableList<TransNumPerRole> transNumPerRoleList = FXCollections.observableArrayList(
            new TransNumPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12), 16L),
            new TransNumPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 6, 14, 33, 48, 12), 62L),
            new TransNumPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 8, 14, 33, 48, 12), 61L),
            new TransNumPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 10, 14, 33, 48, 12), 31L),
            new TransNumPerRole(transRoleList.get(0), LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), 15L),
            new TransNumPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 2, 14, 33, 48, 12), 58L),
            new TransNumPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 8, 14, 33, 48, 12), 34L),
            new TransNumPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 9, 14, 33, 48, 12), 29L),
            new TransNumPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 22, 14, 33, 48, 12), 43L),
            new TransNumPerRole(transRoleList.get(1), LocalDateTime.of(2019, 03, 15, 14, 33, 48, 12), 69L),
            new TransNumPerRole(transRoleList.get(2), LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), 64L),
            new TransNumPerRole(transRoleList.get(2), LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12), 31L),
            new TransNumPerRole(transRoleList.get(2), LocalDateTime.of(2019, 03, 6, 14, 33, 48, 12), 25L),
            new TransNumPerRole(transRoleList.get(2), LocalDateTime.of(2019, 03, 8, 14, 33, 48, 12), 26L),
            new TransNumPerRole(transRoleList.get(3), LocalDateTime.of(2019, 03, 1, 14, 33, 48, 12), 62L),
            new TransNumPerRole(transRoleList.get(3), LocalDateTime.of(2019, 03, 2, 14, 33, 48, 12), 53L),
            new TransNumPerRole(transRoleList.get(3), LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12), 19L),
            new TransNumPerRole(transRoleList.get(3), LocalDateTime.of(2019, 03, 9, 14, 33, 48, 12), 15L));

    private static ObservableList<JoinTimeOfAccount> joinTimeOfAccountList = FXCollections.observableArrayList(
            new JoinTimeOfAccount(accountList.get(0), 97L), new JoinTimeOfAccount(accountList.get(0), 35L),
            new JoinTimeOfAccount(accountList.get(0), 60L), new JoinTimeOfAccount(accountList.get(0), 78L),
            new JoinTimeOfAccount(accountList.get(0), 41L), new JoinTimeOfAccount(accountList.get(1), 69L),
            new JoinTimeOfAccount(accountList.get(1), 72L), new JoinTimeOfAccount(accountList.get(1), 49L),
            new JoinTimeOfAccount(accountList.get(1), 76L), new JoinTimeOfAccount(accountList.get(1), 49L),
            new JoinTimeOfAccount(accountList.get(2), 91L), new JoinTimeOfAccount(accountList.get(2), 44L),
            new JoinTimeOfAccount(accountList.get(2), 15L), new JoinTimeOfAccount(accountList.get(2), 58L),
            new JoinTimeOfAccount(accountList.get(2), 43L), new JoinTimeOfAccount(accountList.get(3), 97L),
            new JoinTimeOfAccount(accountList.get(3), 27L), new JoinTimeOfAccount(accountList.get(3), 14L),
            new JoinTimeOfAccount(accountList.get(3), 57L), new JoinTimeOfAccount(accountList.get(3), 64L),
            new JoinTimeOfAccount(accountList.get(4), 22L), new JoinTimeOfAccount(accountList.get(4), 44L),
            new JoinTimeOfAccount(accountList.get(4), 64L), new JoinTimeOfAccount(accountList.get(4), 83L),
            new JoinTimeOfAccount(accountList.get(4), 27L));

    private static ObservableList<ConsumeOfAccount> consumeOfAccountList = FXCollections.observableArrayList(
            new ConsumeOfAccount(accountList.get(0), 44724554614L, 5466750136L),
            new ConsumeOfAccount(accountList.get(0), 20228950614L, 3549926514L),
            new ConsumeOfAccount(accountList.get(0), 61013145456L, 1364335974L),
            new ConsumeOfAccount(accountList.get(0), 14160376577L, 6056852694L),
            new ConsumeOfAccount(accountList.get(0), 74519391852L, 219569839L),
            new ConsumeOfAccount(accountList.get(1), 6557542917L, 5241020637L),
            new ConsumeOfAccount(accountList.get(1), 8549638055L, 6410263950L),
            new ConsumeOfAccount(accountList.get(1), 2559281807L, 6228451048L),
            new ConsumeOfAccount(accountList.get(1), 95577299265L, 2756493200L),
            new ConsumeOfAccount(accountList.get(1), 28353791631L, 7357080796L),
            new ConsumeOfAccount(accountList.get(2), 70165838334L, 3777815606L),
            new ConsumeOfAccount(accountList.get(2), 17436580521L, 8476741847L),
            new ConsumeOfAccount(accountList.get(2), 93065259447L, 5649943147L),
            new ConsumeOfAccount(accountList.get(2), 1124242576L, 5572522012L),
            new ConsumeOfAccount(accountList.get(2), 42649575015L, 7166998630L),
            new ConsumeOfAccount(accountList.get(3), 97047739548L, 1538514750L),
            new ConsumeOfAccount(accountList.get(3), 7526475740L, 8633347673L),
            new ConsumeOfAccount(accountList.get(3), 15390955655L, 7849860411L),
            new ConsumeOfAccount(accountList.get(3), 7347350775L, 5953695513L),
            new ConsumeOfAccount(accountList.get(3), 90578741451L, 6267642082L),
            new ConsumeOfAccount(accountList.get(4), 22558353630L, 304770951L),
            new ConsumeOfAccount(accountList.get(4), 5884574023L, 1838327993L),
            new ConsumeOfAccount(accountList.get(4), 49755615210L, 1325830595L),
            new ConsumeOfAccount(accountList.get(4), 12792577365L, 1254040508L),
            new ConsumeOfAccount(accountList.get(4), 10060957305L, 561449980L));

    private static ObservableList<Report> reportList = FXCollections.observableArrayList(
            new Report("16620", "Feeney Report", LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12),
                    LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12),
                    "1 Days", null, new TransactionReportingSubcategories(transAmountPerRoleList, transNumPerRoleList)),
            new Report("16420", "Fee Null Report", LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12),
                    LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12),
                    "1 Days", null, null),
            new Report("7684", "Adams Report", LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12),
                    LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12),
                    "8 Days", new AccountReportingSubcategories(joinTimeOfAccountList, consumeOfAccountList),
                    new TransactionReportingSubcategories(transAmountPerRoleList, transNumPerRoleList)),
            new Report("45184", "West Report", LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12),
                    LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12),
                    "5 Days", new AccountReportingSubcategories(joinTimeOfAccountList, consumeOfAccountList), null),
            new Report("42566", "Schinner Report", LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12),
                    LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12),
                    "9 Days", null, new TransactionReportingSubcategories(transAmountPerRoleList, transNumPerRoleList)),
            new Report("75212", "Muller Report", LocalDateTime.of(2019, 03, 5, 14, 33, 48, 12),
                    LocalDateTime.of(2019, 03, 4, 14, 33, 48, 12), LocalDateTime.of(2020, 03, 4, 14, 33, 48, 12),
                    "9 Days", new AccountReportingSubcategories(joinTimeOfAccountList, consumeOfAccountList), null));

    // getter
    public static ObservableList<BaseAccount> getAccountList() {
        return accountList;
    }

    public static ObservableList<Transaction> getTransactionList() {
        return transactionList;
    }

    public static ObservableList<Atm> getAtmList() {
        return atmList;
    }

    public static ObservableList<Report> getReportList() {
        return reportList;
    }

    public static ObservableList<TransactionRole> getTransRoleList() {
        return transRoleList;
    }

    public static ObservableList<TransAmountPerRole> getTransAmountPerRoleList() {
        return transAmountPerRoleList;
    }

    public static ObservableList<TransNumPerRole> getTransNumPerRoleList() {
        return transNumPerRoleList;
    }

    public static ObservableList<JoinTimeOfAccount> getJoinTimeOfAccountList() {
        return joinTimeOfAccountList;
    }

    public static ObservableList<ConsumeOfAccount> getConsumeOfAccountList() {
        return consumeOfAccountList;
    }

    // setter
    public static void setReportList(ObservableList<Report> reportList) {
        DataTest.reportList = reportList;
    }

    public static void setAtmList(ObservableList<Atm> atmList) {
        DataTest.atmList = atmList;
    }

    public static void setTransactionList(ObservableList<Transaction> transactionList) {
        DataTest.transactionList = transactionList;
    }

    public static void setAccountList(ObservableList<BaseAccount> accountList) {
        DataTest.accountList = accountList;
    }

    public static void setTransRoleList(ObservableList<TransactionRole> transRoleList) {
        DataTest.transRoleList = transRoleList;
    }

    public static void setTransAmountPerRoleList(ObservableList<TransAmountPerRole> transAmountPerRoleList) {
        DataTest.transAmountPerRoleList = transAmountPerRoleList;
    }

    public static void setTransNumPerRoleList(ObservableList<TransNumPerRole> transNumPerRoleList) {
        DataTest.transNumPerRoleList = transNumPerRoleList;
    }

    public static void setJoinTimeOfAccountList(ObservableList<JoinTimeOfAccount> joinTimeOfAccountList) {
        DataTest.joinTimeOfAccountList = joinTimeOfAccountList;
    }

    public static void setConsumeOfAccountList(ObservableList<ConsumeOfAccount> consumeOfAccountList) {
        DataTest.consumeOfAccountList = consumeOfAccountList;
    }

}
