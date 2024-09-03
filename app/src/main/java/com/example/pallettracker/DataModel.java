package com.example.pallettracker;public class DataModel {
    private int id;
    private String company;
    private String supplier;
    private int units;
    private double cost;

    // Constructors, getters, and setters

    public DataModel() {
        // Default constructor
    }

    public DataModel(int id, String company, String supplier, int units, double cost) {
        this.id = id;
        this.company = company;
        this.supplier = supplier;
        this.units = units;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
