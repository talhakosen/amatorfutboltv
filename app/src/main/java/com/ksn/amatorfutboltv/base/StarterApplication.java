/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.ksn.amatorfutboltv.base;

import android.app.Application;

import com.ksn.amatorfutboltv.modal.City;
import com.ksn.amatorfutboltv.modal.Club;
import com.ksn.amatorfutboltv.modal.Country;
import com.ksn.amatorfutboltv.modal.Modal;
import com.ksn.amatorfutboltv.modal.UserType;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class StarterApplication extends Application {
    private Modal modal;

    public StarterApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);

        // Objects
        ParseObject.registerSubclass(Country.class);
        ParseObject.registerSubclass(City.class);
        ParseObject.registerSubclass(UserType.class);
        ParseObject.registerSubclass(Club.class);

        // Add your initialization code here
        Parse.initialize(this);
        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // Optionally enable public read access.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        modal = new Modal();

    }

    public StarterApplication(Modal modal) {
        this.modal = modal;
    }

    public Modal getModal() {
        return modal;
    }

    public void setModal(Modal modal) {
        this.modal = modal;
    }

    /* String[][] str2 = {{"Adana", "1"},
            {"Adıyaman", "2"},
            {"Afyonkarahisar", "3"},
            {"Ağrı", "4"},
            {"Amasya", "5"},
            {"Ankara", "6"},
            {"Antalya", "7"},
            {"Artvin", "8"},
            {"Aydın", "9"},
            {"Balıkesir", "10"},
            {"Bilecik", "11"},
            {"Bingöl", "12"},
            {"Bitlis", "13"},
            {"Bolu", "14"},
            {"Burdur", "15"},
            {"Bursa", "16"},
            {"Çanakkale", "17"},
            {"Çankırı", "18"},
            {"Çorum", "19"},
            {"Denizli", "20"},
            {"Diyarbakır", "21"},
            {"Edirne", "22"},
            {"Gölcük(Elazığ)", "23"},
            {"Elazığ", "23"},
            {"Erzincan", "24"},
            {"Erzurum", "25"},
            {"Eskişehir", "26"},
            {"Gaziantep", "27"},
            {"Kozdere(Gaziantep)", "27"},
            {"Giresun", "28"},
            {"Gümüşhane", "29"},
            {"Hakkari", "30"},
            {"Hatay", "31"},
            {"Isparta", "32"},
            {"Mersin", "33"},
            {"İstanbul", "34"},
            {"İzmir", "35"},
            {"Kars", "36"},
            {"Kastamonu", "37"},
            {"Kayseri", "38"},
            {"Kırklareli", "39"},
            {"Kırşehir", "40"},
            {"Kocaeli", "41"},
            {"Konya", "42"},
            {"Kütahya", "43"},
            {"Malatya", "44"},
            {"Manisa", "45"},
            {"Kahramanmaraş", "46"},
            {"Mardin", "47"},
            {"Muğla", "48"},
            {"Muş", "49"},
            {"Nevşehir", "50"},
            {"Niğde", "51"},
            {"Ordu", "52"},
            {"Rize", "53"},
            {"Sakarya", "54"},
            {"Samsun", "55"},
            {"Siirt", "56"},
            {"Sinop", "57"},
            {"Sivas", "58"},
            {"Tekirdağ", "59"},
            {"Tokat", "60"},
            {"Trabzon", "61"},
            {"Tunceli", "62"},
            {"Şanlıurfa", "63"},
            {"Uşak", "64"},
            {"Van", "65"},
            {"Yozgat", "66"},
            {"Zonguldak", "67"},
            {"Aksaray", "68"},
            {"Bayburt", "69"},
            {"Karaman", "70"},
            {"Kırıkkale", "71"},
            {"Batman", "72"},
            {"Şırnak", "73"},
            {"Bartın", "74"},
            {"Ardahan", "75"},
            {"Iğdır", "76"},
            {"Yalova", "77"},
            {"Karabük", "78"},
            {"Kilis", "79"},
            {"Osmaniye", "80"},
            {"Düzce", "81"}};*/
}
