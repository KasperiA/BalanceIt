package com.kasperistudios.android.balanceit;

import java.io.Serializable;

/**
 * Tulot ja menot tallennetaan tästä luokasta tehtyihin olioihin.
 */

public class Money implements Serializable{
    private String name;
    private double amount;
    private boolean isIncome;
    private int day;
    private int category;


    public Money(String name, int category, double amount, boolean isIncome, int day) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.isIncome = isIncome;
        this.day = day;
    }

    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean isIncome() {
        return this.isIncome;
    }

    public int getDay() { return this.day;}

    public int getCategory() { return this.category;}

}
