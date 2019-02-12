package com.kasperistudios.android.balanceit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Tämä luokka vastaa uuden tulon lisäys -näkymän toiminnallisuudesta.
 */

public class NewExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);


        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker_new_expense);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

            }
        });

        category = 0;

        final Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);

        final EditText expenseName = (EditText) findViewById(R.id.expense_name_field);
        final EditText expenseAmount = (EditText) findViewById(R.id.expense_amount_field);
        Button newExpense = (Button) findViewById(R.id.new_expense_button);


        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Money newExpense;
                try {
                    newExpense = new Money(expenseName.getText().toString(), category, Double.parseDouble(expenseAmount.getText().toString()), false, datePicker.getDayOfMonth());
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), MainActivity.getAppContext().getString(R.string.noMoneyAmount), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(expenseName.getText().toString().trim().length() == 0) {
                    Toast.makeText(v.getContext(), MainActivity.getAppContext().getString(R.string.noTextInput), Toast.LENGTH_SHORT).show();
                    return;
                }

                StorageManager storageManager = new StorageManager();
                String filename = "" + datePicker.getYear() + datePicker.getMonth();

                try {
                    MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), filename);
                    moneyStorage.addMoney(newExpense);
                    storageManager.save(MainActivity.getAppContext(), moneyStorage);
                } catch (Exception e) {
                    MoneyStorage moneyStorage = new MoneyStorage("" + datePicker.getYear(),"" + datePicker.getMonth());
                    moneyStorage.addMoney(newExpense);
                    storageManager.save(MainActivity.getAppContext(), moneyStorage);
                }


                Intent intent = new Intent(NewExpense.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.category = i + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
