package com.myco.lcreporter;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Andre on 14/02/2015.
 */
public class Sheep implements Serializable {
    private final String name;
    private final String number;
    private final Bitmap thumb;

    public Sheep(String name, String number) {
        this.name = name;
        this.number = number;
        this.thumb = null;
    }

    public Sheep(String name, String number, Bitmap thumb) {
        this.name = name;
        this.number = number;
        this.thumb = thumb;
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

    public Bitmap getThumb() {
        return thumb;
    }
}
