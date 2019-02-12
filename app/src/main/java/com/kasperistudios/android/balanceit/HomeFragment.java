package com.kasperistudios.android.balanceit;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;


/**
 * Luokka vastaa kotinäkymän toiminnallisuudesta.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private int year;
    private int month;
    private TextView incomes;
    private TextView expenses;
    private TextView total;
    private TextView date;
    private Button newExpense;
    private Button newIncome;
    private Button chooseDate;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        incomes = (TextView) view.findViewById(R.id.incomes);
        expenses = (TextView) view.findViewById(R.id.expenses);
        total = (TextView) view.findViewById(R.id.total_amount);
        date = (TextView) view.findViewById(R.id.home_fragment_date);
        newExpense = (Button) view.findViewById(R.id.new_expense);
        newIncome = (Button) view.findViewById(R.id.new_income);
        chooseDate = (Button) view.findViewById(R.id.home_fragment_chooseDate);

        expenses.setTextColor(Color.RED);
        incomes.setTextColor(Color.GREEN);
        total.setTextColor(Color.BLACK);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(calendar.YEAR);
        month = calendar.get(calendar.MONTH);

        date.setText(getContext().getApplicationContext().getString(R.string.todays_date2, "" + (month + 1), "" + year));

        updateData();

        newExpense.setOnClickListener(this);
        newIncome.setOnClickListener(this);
        chooseDate.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_income:
                Intent newIncome = new Intent(getActivity(), NewIncome.class);
                startActivity(newIncome);
                break;
            case R.id.new_expense:
                Intent newExpense = new Intent(getActivity(), NewExpense.class);
                startActivity(newExpense);
                break;
            case R.id.home_fragment_chooseDate:
                Intent chooseDate = new Intent(getActivity(), ChooseDate.class);
                startActivity(chooseDate);
                break;
        }
    }

    public void updateData() {
        StorageManager storageManager = new StorageManager();

        try {
            ChosenDate chosenDate = storageManager.read(MainActivity.getAppContext(), "chosenDate", true);
            year = chosenDate.getYear();
            month = chosenDate.getMonth();
            date.setText(getContext().getApplicationContext().getString(R.string.todays_date2, "" + (month + 1), "" + year));
        } catch (Exception e) {
            e.printStackTrace();
            ChosenDate chosenDate = new ChosenDate();
            storageManager.save(MainActivity.getAppContext(), chosenDate);
            date.setText(getContext().getApplicationContext().getString(R.string.todays_date2, "" + (month + 1), "" + year));
        }

        String filename = "" + year + month;

        try {
            MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), filename);
        } catch (Exception e) {
            MoneyStorage moneyStorage = new MoneyStorage("" + year,"" + month);
            storageManager.save(MainActivity.getAppContext(), moneyStorage);
        }

        try {
            MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), filename);
            incomes.setText(getContext().getApplicationContext().getString(R.string.money, "" + moneyStorage.getIncomes()));
            incomes.setTextColor(Color.GREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), filename);
            expenses.setText(getContext().getApplicationContext().getString(R.string.money, "" + moneyStorage.getExpenses()));
            expenses.setTextColor(Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), filename);
            total.setText(getContext().getApplicationContext().getString(R.string.money, "" + moneyStorage.getTotalAmount()));
            if(moneyStorage.getTotalAmount()>=0) {
                total.setTextColor(Color.GREEN);
            } else if(moneyStorage.getTotalAmount() <= 0) {
                total.setTextColor(Color.RED);
            } else {
                total.setTextColor(Color.BLACK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
