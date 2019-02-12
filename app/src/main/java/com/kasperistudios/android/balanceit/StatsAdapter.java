package com.kasperistudios.android.balanceit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Tämä luokka vastaa tilastonäkymässä olevan RecyclerView:n toiminnasta.
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.MyViewHolder> {

    private MoneyStorage moneyStorage;
    private Context context;
    private String[] categories = MainActivity.getAppContext().getResources().getStringArray(R.array.categories);

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, amount, date, type, category;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            amount = (TextView) view.findViewById(R.id.amount);
            date = (TextView) view.findViewById(R.id.date);
            type = (TextView) view.findViewById(R.id.type);
            category = (TextView) view.findViewById(R.id.category);
            view.setOnClickListener(this);
            }

        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(MainActivity.getAppContext().getString(R.string.deleteConfirmation))
                    .setMessage(MainActivity.getAppContext().getString(R.string.noUndone))
                    .setPositiveButton(MainActivity.getAppContext().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItem(getAdapterPosition());
                        }
                    })
                    .setNegativeButton(MainActivity.getAppContext().getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }


    }

    public StatsAdapter(MoneyStorage moneyStorage, Context context) {
        this.moneyStorage = moneyStorage;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stats_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Money money = this.moneyStorage.getMoney(position);
        holder.name.setText(money.getName());
        holder.amount.setText("" + money.getAmount());
        if(money.isIncome()) {
            holder.amount.setTextColor(Color.GREEN);
            holder.type.setText(MainActivity.getAppContext().getString(R.string.income));
        } else {
            holder.amount.setTextColor(Color.RED);
            holder.type.setText(MainActivity.getAppContext().getString(R.string.expense, this.categories[money.getCategory() - 1]));
        }
        holder.date.setText(getContext().getApplicationContext().getString(R.string.todays_date3, "" + money.getDay(),"" + (Integer.parseInt(this.moneyStorage.getMonth()) + 1), this.moneyStorage.getYear()));


    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return this.moneyStorage == null ? 0 : this.moneyStorage.getItemNumber();
    }

    public void deleteItem(int position) {
        StorageManager storageManager = new StorageManager();
        this.moneyStorage.removeItem(position);
        MoneyStorage newMoneyStorage = new MoneyStorage(this.moneyStorage.getYear(), this.moneyStorage.getMonth(), this.moneyStorage.getMoneyArrayList(), this.moneyStorage.getCategoryMoney(), this.moneyStorage.getTotalAmount(), this.moneyStorage.getIncomes(), this.moneyStorage.getExpenses());
        MainActivity.getAppContext().deleteFile("" + this.moneyStorage.getYear() + this.moneyStorage.getMonth());
        storageManager.save(MainActivity.getAppContext(), newMoneyStorage);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position, moneyStorage.getItemNumber());
    }

}
