package com.kasperistudios.android.balanceit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Luokka vastaa päivänvalinta-activityn toiminnasta
 */

public class ChooseDate extends AppCompatActivity {

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);

        calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);

        final Button chooseDate = (Button) findViewById(R.id.choose_date_button);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker_choose_date);

        chooseDate.setText(getApplicationContext().getString(R.string.choose_date, "" + (month + 1), "" + year));


        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                chooseDate.setText(getApplicationContext().getString(R.string.choose_date, "" + (i1 + 1), "" + i));
            }
        });



        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StorageManager storageManager = new StorageManager();
                ChosenDate chosenDate = storageManager.read(MainActivity.getAppContext(), "chosenDate", true);
                chosenDate.setMonth(datePicker.getMonth());
                chosenDate.setYear(datePicker.getYear());
                storageManager.save(MainActivity.getAppContext(), chosenDate);

                Intent intent = new Intent(ChooseDate.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
