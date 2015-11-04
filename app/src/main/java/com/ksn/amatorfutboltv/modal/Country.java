package com.ksn.amatorfutboltv.modal;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by talhakosen on 28/10/15.
 */
@ParseClassName("Country")
public class Country extends ParseObject {

    public Number getCountryId() {
        return getNumber("CountryId");
    }

    public void setCountryId(int countryId) {
        put("CountryId", countryId);
    }

    public String getCountryName() {
        return getString("CountryName");
    }

    public void setCountryName(int countryName) {
        put("CountryName", countryName);
    }
}
