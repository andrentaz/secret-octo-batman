package com.myco.lcreporter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by andre on 2/18/15.
 */
public class DeletionDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
    public interface DeletionDialogListener {
        public void onDeletionDialogPositiveClick(DialogFragment dialog, int position);
        public void onDeletionDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    private DeletionDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the DeletionDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the DeletionDialogListener so we can send events to the host
            mListener = (DeletionDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DeletionDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Get arguments
        Bundle bundle = getArguments();
        final int position = bundle.getInt(ContactListFragment.ARG_POSITION);

        /* Inflate and set the layout for the dialog
         * Pass null as the parent View because its going in the dialog layout
         */
        builder.setMessage(R.string.dialog_deletePeople_message)
                // Add the actions buttons
                .setPositiveButton(R.string.dialog_delete_people, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Send the positive button event back to the calling activity
                mListener.onDeletionDialogPositiveClick(DeletionDialogFragment.this, position);
            }
        })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDeletionDialogNegativeClick(DeletionDialogFragment.this);
                    }
                });

        return builder.create();
    }

}
