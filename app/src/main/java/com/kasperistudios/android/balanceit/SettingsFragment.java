package com.kasperistudios.android.balanceit;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Tämä luokka vastaa asetusnäkymän toiminnallisuudesta.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener{


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button bugReport = (Button) view.findViewById(R.id.bug_report_button);
        Button rateApp = (Button) view.findViewById(R.id.rate_app_button);

        bugReport.setOnClickListener(this);
        rateApp.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bug_report_button:
                Intent bugReport = new Intent(Intent.ACTION_SENDTO);
                bugReport.setData(Uri.parse("mailto:aavasaarikasperi@gmail.com"));
                bugReport.putExtra(Intent.EXTRA_SUBJECT, "Report a bug");
                startActivity(bugReport);
                break;
            case R.id.rate_app_button:

                Uri uri = Uri.parse("market://details?id=" + MainActivity.getAppContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.getAppContext().getPackageName())));
                }
                break;
        }
    }




    /*private void setLocale(Locale locale){
        SharedPrefUtils.saveLocale(locale); // optional - Helper method to save the selected language to SharedPreferences in case you might need to attach to activity context (you will need to code this)
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.locale=locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            MainActivity.getAppContext().createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration,displayMetrics);
        }
    }*/

}
