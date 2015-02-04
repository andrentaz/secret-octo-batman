package com.myco.lcreporter;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by andre on 2/3/15.
 */
public class DatePickerActivity extends Activity {

    /* Creates the DatePicker using the fragment */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Getting the Fragment */
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new DatePickerFragment()).commit();
    }
}
