package com.app.model;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    @Column(nullable = false)
    private String customerName;
    @Column(length = 1000)
    private String customerAddress;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private long aadhaarNumber;
    @Column(nullable = false)
    private long panNumber;
    @OneToOne
    private User user;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(long aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public long getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(long panNumber) {
        this.panNumber = panNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer() {}

    public Customer(String customerName, String customerAddress, int age, long aadhaarNumber, long panNumber) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.age = age;
        this.aadhaarNumber = aadhaarNumber;
        this.panNumber = panNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", age=" + age +
                ", aadhaarNumber=" + aadhaarNumber +
                ", panNumber=" + panNumber +
                '}';
    }
}
