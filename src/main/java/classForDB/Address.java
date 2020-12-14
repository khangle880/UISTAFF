package classForDB;

import javafx.beans.property.SimpleStringProperty;

public class Address {
    private SimpleStringProperty completeStreet;
    private SimpleStringProperty ward;
    private SimpleStringProperty district;
    private SimpleStringProperty province;

    Address() {
        this.completeStreet = new SimpleStringProperty();
        this.ward = new SimpleStringProperty();
        this.district = new SimpleStringProperty();
        this.province = new SimpleStringProperty();
    };

    public Address(String completeStreet, String ward, String district, String province) {
        this.completeStreet = new SimpleStringProperty(completeStreet);
        this.ward = new SimpleStringProperty(ward);
        this.district = new SimpleStringProperty(district);
        this.province = new SimpleStringProperty(province);
    }

    public String getCompleteStreet() {
        return completeStreet.get();
    }

    public void setCompleteStreet(String completeStreet) {
        this.completeStreet.set(completeStreet);
    }

    public String getWard() {
        return ward.get();
    }

    public void setWard(String ward) {
        this.ward.set(ward);
    }

    public String getDistrict() {
        return district.get();
    }

    public void setDistrict(String district) {
        this.district.set(district);
    }

    public String getProvince() {
        return province.get();
    }

    public void setProvince(String province) {
        this.province.set(province);
    }
}
