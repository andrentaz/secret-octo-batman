package com.myco.lcreporter;

/**
 * Created by Gabriel on 14/02/2015.
 */
public class Contact {
    private final String name;
    private final String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
