package com.ksn.amatorfutboltv.modal;

import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by talhakosen on 28/10/15.
 */
@ParseClassName("City")
public class City extends ParseObject {

    public Number getCityId() {
        return getNumber("CityId");
    }

    public void setCityId(int countryId) {
        put("CityId", countryId);
    }

    public String getCityName() {
        return getString("CityName");
    }

    public void setCityName(int countryName) {
        put("CityName", countryName);
    }
}
