package com.kasperistudios.android.balanceit;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;


/**
 * Tämä luokka vastaa tilastonäkymän toiminnallisuudesta.
 */
public class StatsFragment extends Fragment {

    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        TextView date = (TextView) view.findViewById(R.id.stats_fragment_chosenDate);
        TextView housingCategory = (TextView) view.findViewById(R.id.housing_category_text);
        TextView foodCategory = (TextView) view.findViewById(R.id.food_category_text);
        TextView entertainmentCategory = (TextView) view.findViewById(R.id.entertainment_category_text);
        TextView otherCategory = (TextView) view.findViewById(R.id.other_category_text);

        StorageManager storageManager = new StorageManager();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);

        try{
            ChosenDate chosenDate = storageManager.read(getActivity().getApplicationContext(), "chosenDate", true);
            year = chosenDate.getYear();
            month = chosenDate.getMonth();
        } catch (Exception e) {
            e.printStackTrace();
        }

        date.setText(getContext().getApplicationContext().getString(R.string.todays_date2, "" + (month + 1),"" + year ));
        String filename = "" + year + month;

        Context context = MainActivity.getAppContext();

        updateData(housingCategory, foodCategory, entertainmentCategory, otherCategory, year, month);

        MoneyStorage moneyStorage2 = storageManager.read(context,filename);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new StatsAdapter(moneyStorage2, view.getContext());
        recyclerView.setAdapter(mAdapter);




        return view;
    }

    private void updateData(TextView housingCategory, TextView foodCategory, TextView entertainmentCategory, TextView otherCategory, int year, int month) {
        StorageManager storageManager = new StorageManager();
        try {
            MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), "" + year + month);
            housingCategory.setText(getContext().getApplicationContext().getString(R.string.housingCategory, "" + moneyStorage.getCategoryMoney(1)));
        } catch (Exception e) {
            housingCategory.setText(getContext().getApplicationContext().getString(R.string.housingCategory, "0.0"));
        }

        try {
            MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), "" + year + month);
            foodCategory.setText(getContext().getApplicationContext().getString(R.string.foodCategory, "" + moneyStorage.getCategoryMoney(2)));
        } catch (Exception e) {
            foodCategory.setText(getContext().getApplicationContext().getString(R.string.foodCategory, "0.0"));
        }

        try {
            MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), "" + year + month);
            entertainmentCategory.setText(getContext().getApplicationContext().getString(R.string.entertainmentCategory, "" + moneyStorage.getCategoryMoney(3)));
        } catch (Exception e) {
            entertainmentCategory.setText(getContext().getApplicationContext().getString(R.string.entertainmentCategory, "0.0"));
        }

        try {
            MoneyStorage moneyStorage = storageManager.read(MainActivity.getAppContext(), "" + year + month);
            otherCategory.setText(getContext().getApplicationContext().getString(R.string.otherCategory, "" + moneyStorage.getCategoryMoney(4)));
        } catch (Exception e) {
            otherCategory.setText(getContext().getApplicationContext().getString(R.string.otherCategory, "0.0"));
        }
    }

}
