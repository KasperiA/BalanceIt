package com.kasperistudios.android.balanceit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Tämä luokka vastaa uuden menon lisäys -näkymän toiminnallisuudesta.
 */

public class NewIncome extends AppCompatActivity {
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);
        calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker_new_income);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

            }
        });

        final EditText incomeName = (EditText) findViewById(R.id.income_name_field);
        final EditText incomeAmount = (EditText) findViewById(R.id.income_amount_field);
        Button newIncome = (Button) findViewById(R.id.new_income_button);

        newIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Money newIncome;
                try {
                    newIncome = new Money(incomeName.getText().toString(), 0, Double.parseDouble(incomeAmount.getText().toString()), true, datePicker.getDayOfMonth());
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), MainActivity.getAppContext().getString(R.string.noMoneyAmount), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(incomeName.getText().toString().trim().length() == 0) {
                    Toast.makeText(v.getContext(), MainActivity.getAppContext().getString(R.string.noTextInput), Toast.LENGTH_SHORT).show();
                    return;
                }

                StorageManager storageManager = new StorageManager();
                String filename = "" + datePicker.getYear() + datePicker.getMonth();

                try {
                    MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), filename);
                    moneyStorage.addMoney(newIncome);
                    storageManager.save(getApplicationContext(), moneyStorage);
                } catch (Exception e) {
                    MoneyStorage moneyStorage = new MoneyStorage("" + datePicker.getYear(),"" + datePicker.getMonth());
                    moneyStorage.addMoney(newIncome);
                    storageManager.save(MainActivity.getAppContext(), moneyStorage);
                }

                Intent intent = new Intent(NewIncome.this, MainActivity.class);
                startActivity(intent);
            }
            });
    }




}
