package com.ksn.amatorfutboltv.modal;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by talhakosen on 28/10/15.
 */
@ParseClassName("UserType")
public class UserType extends ParseObject {

    public Number getTypeId() {
        return getNumber("TypeId");
    }

    public void setTypeId(int typeId) {
        put("TypeId", typeId);
    }

    public String getTypeName() {
        return getString("TypeName");
    }

    public void setTypeName(int typeName) {
        put("TypeName", typeName);
    }
}
