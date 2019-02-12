package com.kasperistudios.android.balanceit;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tästä luokasta tehtyihin olioihin tallennetaan Money-oliot.
 */

public class MoneyStorage implements Serializable{

    private String year;
    private String month;
    private ArrayList<Money> moneyArrayList;
    private Double[] categoryMoney;
    private double totalAmount;
    private double incomes;
    private double expenses;


    public MoneyStorage(String year, String month) {
        this.year = year;
        this.month = month;
        this.moneyArrayList = new ArrayList<>();
        this.categoryMoney = new Double[5];
        initializeCategoryData();
        this.totalAmount = 0.0;
        this.incomes = 0.0;
        this.expenses = 0.0;
    }

    public MoneyStorage(String year, String month, ArrayList<Money> money, Double[] categoryMoney, double totalAmount, double incomes, double expenses) {
        this.year = year;
        this.month = month;
        this.moneyArrayList = money;
        this.totalAmount = totalAmount;
        this.categoryMoney = categoryMoney;
        this.incomes = incomes;
        this.expenses = expenses;
    }

    private void initializeCategoryData() {
        for (int i = 0; i < 5; i++) {
            this.categoryMoney[i] = 0.0;
        }
    }

    public String getYear() {
        return this.year;
    }


    public String getMonth() {
        return this.month;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public double getIncomes() {
        return this.incomes;
    }

    public double getExpenses() {
        return this.expenses;
    }

    public ArrayList<Money> getMoneyArrayList() {
        return this.moneyArrayList;
    }

    public Double[] getCategoryMoney() { return this.categoryMoney;}

    public Double getCategoryMoney(int position) {return this.categoryMoney[position];}

    public void addMoney(Money money) {

        if (money.isIncome()) {
            this.incomes += money.getAmount();
        } else {
            this.expenses += money.getAmount();
        }
        int category = money.getCategory();
        this.categoryMoney[category] = (this.categoryMoney[category] + money.getAmount());
        this.totalAmount = this.incomes - this.expenses;

        this.moneyArrayList.add(money);
    }

    public int getItemNumber() {
        return this.moneyArrayList.size();
    }

    public Money getMoney(int position) {
        return moneyArrayList.get(position);
    }

    public void removeItem(int position) {
        Money money = this.moneyArrayList.get(position);
        if(money.isIncome()) {
            this.incomes -= money.getAmount();
        } else {
            this.expenses -= money.getAmount();
        }
        this.categoryMoney[money.getCategory()] = this.categoryMoney[money.getCategory()] - money.getAmount();
        this.totalAmount = this.incomes - this.expenses;
        this.moneyArrayList.remove(position);
    }
}
