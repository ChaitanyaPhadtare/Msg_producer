package com.kafka_producer.Model;

public class Address {

    private String address_line1;
    private String address_line2;
    private String address_city;

    public String getAddress_city() {
        return address_city;
    }

    @Override
    public String toString() {
        return "address{" +
                "address_line1='" + address_line1 + '\'' +
                ", address_line2='" + address_line2 + '\'' +
                ", address_city='" + address_city + '\'' +
                '}';
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public Address(String address_line1, String address_line_2, String address_city) {
        this.address_line1 = address_line1;
        this.address_line2 = address_line_2;
        this.address_city = address_city;
    }


}
