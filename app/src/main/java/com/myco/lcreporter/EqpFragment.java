package com.myco.lcreporter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by andre on 2/20/15.
 */
public class EqpFragment extends Fragment {

    private SharedPreferences mySettings;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this.mView = inflater.inflate(R.layout.page_eqp, container, false);
        getSettInfo();

        return this.mView;
    }

    private void getSettInfo() {
        // Getting the preferences object
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(mView.getContext());
        String name = this.mySettings.getString("pref_eqp", "");

        // Putting the Settings Text in the Box
        // Equipe
        EditText editText = (EditText) mView.findViewById(R.id.editText_eqp);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Master
        name = this.mySettings.getString("pref_master", "");
        editText = (EditText) mView.findViewById(R.id.editText_master);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Rede
        name = this.mySettings.getString("pref_rede", "");
        editText = (EditText) mView.findViewById(R.id.editText_rede);
        editText.setText(name, TextView.BufferType.EDITABLE);
    }

    public void setSettInfo() {
        /* Shared Preferences */
        String eqpName, masterName, redeName;

        /* Getting User Input */
        EditText editText = (EditText) mView.findViewById(R.id.editText_eqp);
        eqpName = editText.getText().toString();

        editText = (EditText) mView.findViewById(R.id.editText_master);
        masterName = editText.getText().toString();

        editText = (EditText) mView.findViewById(R.id.editText_rede);
        redeName = editText.getText().toString();

        /* Changing the preferences using a SharedPreferences Object */
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(mView.getContext());

        /* Changes and apply */
        SharedPreferences.Editor editor = this.mySettings.edit();
        editor.putString("pref_eqp", eqpName);
        editor.putString("pref_master", masterName);
        editor.putString("pref_rede", redeName);

        // Commit
        editor.commit();
    }
}
