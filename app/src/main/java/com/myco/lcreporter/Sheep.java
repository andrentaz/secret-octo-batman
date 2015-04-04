package com.myco.lcreporter;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Andre on 14/02/2015.
 */
public class Sheep {
    private final String name;
    private final String number;
    private final Uri contactUri;
    private final Bitmap thumb;

    public Sheep(String name, String number) {
        this.name = name;
        this.number = number;
        this.contactUri = null;
        this.thumb = null;
    }

    public Sheep(String name, String number, Uri contactUri, Bitmap thumb) {
        this.name = name;
        this.number = number;
        this.contactUri = contactUri;
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public Uri getContactUri() {
        return contactUri;
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public SimpleItem toSimpleItem() {
        return new SimpleItem(name, number);
    }
}
