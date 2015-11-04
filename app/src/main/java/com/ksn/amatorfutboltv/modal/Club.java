package com.ksn.amatorfutboltv.modal;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by talhakosen on 28/10/15.
 */
@ParseClassName("Club")
public class Club extends ParseObject {
    public Number getClubId() {
        return getNumber("ClubId");
    }

    public void setClubId(Number clubId) {
        put("ClubId", clubId);
    }

    public Number getClubCity() {
        return getNumber("ClubCity");
    }

    public void setClubCity(Number clubCity) {
        put("ClubCity", clubCity);
    }

    public Number getClubCountry() {
        return getNumber("ClubCountry");
    }

    public void setClubCountry(int clubCountry) {
        put("ClubCountry", clubCountry);
    }

    public String getClubImage() {
        return getString("ClubImage");
    }

    public void setClubImage(String clubImage) {
        put("ClubImage", clubImage);
    }

    public String getClubName() {
        return getString("ClubName");
    }

    public void setClubName(String clubName) {
        put("ClubName", clubName);
    }
}
