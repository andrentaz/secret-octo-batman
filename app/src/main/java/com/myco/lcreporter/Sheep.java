package com.myco.lcreporter;

/**
 * Created by Gabriel on 14/02/2015.
 */
public class Sheep {
    private final String name;
    private final String number;

    public Sheep(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        Sheep obj = (Sheep) o;

        if (this.getName() == obj.getName())
            if (this.getNumber() == obj.getNumber())
                return true;

        return false;
    }
}
