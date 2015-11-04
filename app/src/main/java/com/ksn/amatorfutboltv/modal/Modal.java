package com.ksn.amatorfutboltv.modal;

import java.util.List;

/**
 * Created by talhakosen on 28/10/15.
 */
public class Modal {
    private List<City> city;
    private List<Country> country;
    private List<UserType> userTypes;
    private String[] str;

    public Modal() {
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public List<UserType> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(List<UserType> userTypes) {
        this.userTypes = userTypes;
    }

    public String[] getUserTypeAsArray(){
        if(str != null)
            return str;

        if(getUserTypes()==null)
            return null;

        str = new String[getUserTypes().size()];
        for(int i =0;i<getUserTypes().size();i++)
            str[i] = getUserTypes().get(i).getTypeName();

        return str;
    }
}
