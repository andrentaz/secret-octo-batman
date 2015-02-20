package com.myco.lcreporter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nakagaki on 20/02/2015.
 */
public class ScreenFragment extends Fragment {

    private int mResourse;

    /**
     * Set the arguments when created by the Activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mResourse = getArguments().getInt("layoutId");
    }

    /**
     * Inflates the Layout passed as argument
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(this.mResourse, container, false);
    }

    /**
     * Returns an Object of this Class
     * @param layoutId
     * @return
     */
    public static Fragment init(int layoutId) {
        ScreenFragment newFrag = new ScreenFragment();
        Bundle args = new Bundle();

        // Select the correct layout
        switch (layoutId) {
            case 0: // Main
                args.putInt("layoutId", R.layout.page_main);
                break;
            case 1: // Equipe
                args.putInt("layoutId", R.layout.page_eqp);
                break;
            case 2: // Sheep
                args.putInt("layoutId", R.layout.page_sheep);
            break;
        }

        newFrag.setArguments(args);
        return newFrag;
    }
}
