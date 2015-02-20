package com.myco.lcreporter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by andre on 2/20/15.
 */
public class MainFragment extends Fragment {

    private SharedPreferences mySettings;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this.mView = inflater.inflate(R.layout.page_main, container, false);
        getSettInfo();

        return this.mView;
    }

    private void getSettInfo() {
        // Getting the preferences object
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(mView.getContext());
        String name = this.mySettings.getString("pref_lider", "");

        // Putting the Settings Text in the Box
        // Leader
        EditText editText = (EditText) mView.findViewById(R.id.editText_lider);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Co Leader
        name = this.mySettings.getString("pref_colider", "");
        editText = (EditText) mView.findViewById(R.id.editText_colider);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // LT1
        name = this.mySettings.getString("pref_lt1", "");
        editText = (EditText) mView.findViewById(R.id.editText_lt1);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Host
        name = this.mySettings.getString("pref_host", "");
        editText = (EditText) mView.findViewById(R.id.editText_host);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Setting Button Text
        Button button = (Button) mView.findViewById(R.id.button_date);
        name = this.mySettings.getString("pref_date", "");
        button.setText(name);
    }

    public void setSettInfo() {
        /* Shared Preferences */
        String leaderName, coleaderName, lt1Name, hostName;

        /* Getting User Input */
        EditText editText = (EditText) mView.findViewById(R.id.editText_lider);
        leaderName = editText.getText().toString();

        editText = (EditText) mView.findViewById(R.id.editText_colider);
        coleaderName = editText.getText().toString();

        editText = (EditText) mView.findViewById(R.id.editText_lt1);
        lt1Name = editText.getText().toString();

        editText = (EditText) mView.findViewById(R.id.editText_host);
        hostName = editText.getText().toString();

        /* Changing the preferences using a SharedPreferences Object */
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(mView.getContext());

        /* Changes and apply */
        SharedPreferences.Editor editor = this.mySettings.edit();
        editor.putString("pref_lider", leaderName);
        editor.putString("pref_colider", coleaderName);
        editor.putString("pref_lt1", lt1Name);
        editor.putString("pref_host", hostName);

        // Commit
        editor.commit();
    }
}
