package com.ksn.amatorfutboltv.ui.prelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.ksn.amatorfutboltv.R;
import com.ksn.amatorfutboltv.base.Consts;
import com.ksn.amatorfutboltv.modal.City;
import com.ksn.amatorfutboltv.modal.Club;
import com.ksn.amatorfutboltv.modal.Country;
import com.ksn.amatorfutboltv.modal.UserType;
import com.ksn.amatorfutboltv.ui.BaseActivity;
import com.ksn.amatorfutboltv.ui.browsing.MainActivity;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prelogin_activity_splash);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        Thread thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(3000);
                    }
                }
                catch(InterruptedException ex){
                }

                //loadCityCountry();

            }
        };

        thread.start();

        writeClubs();

    }

    private void writeClubs(){
        for (int i =0;i< Consts.clubs.length;i++){
            Club club = new Club();
            club.setClubCity(34);
            club.setClubCountry(90);
            club.setClubId(Integer.valueOf(Consts.clubs[i][2]));
            club.setClubName(Consts.clubs[i][3]);
            try {
                club.save();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCityCountry() {
        final ParseQuery<City> cityQuery = ParseQuery.getQuery(City.class);
        final ParseQuery<Country> countryQuery = ParseQuery.getQuery(Country.class);
        final ParseQuery<UserType> typeQuery = ParseQuery.getQuery(UserType.class);
        cityQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        countryQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        typeQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

        cityQuery.findInBackground(new FindCallback<City>() {
            @Override
            public void done(List<City> objects, ParseException e) {
                getApp().getModal().setCity(objects);
                countryQuery.findInBackground(new FindCallback<Country>() {
                    @Override
                    public void done(List<Country> objects, ParseException e) {
                        getApp().getModal().setCountry(objects);
                        typeQuery.findInBackground(new FindCallback<UserType>() {
                            @Override
                            public void done(List<UserType> objects, ParseException e) {
                                getApp().getModal().setUserTypes(objects);
                                gotoActivity();
                            }
                        });
                    }
                });
            }
        });
    }

    private void gotoActivity(){
        if(ParseUser.getCurrentUser() == null){
            Snackbar.make(getWindow().getDecorView(), "User not found", Snackbar.LENGTH_SHORT).show();
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }


}
