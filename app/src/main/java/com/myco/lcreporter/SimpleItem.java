package com.myco.lcreporter;

import java.io.Serializable;

/**
 * Created by andre on 4/4/15.
 */
public class SimpleItem implements Serializable {
    private String name, number;

    public SimpleItem(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
