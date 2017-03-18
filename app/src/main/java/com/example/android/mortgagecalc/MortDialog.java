package com.example.android.mortgagecalc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Rushin Naik on 3/17/2017.
 */

public class MortDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_content)
                .setPositiveButton(R.string.edit_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User chose to edit the mortgage details


                    }
                })
                .setNegativeButton(R.string.delete_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User chose to delete the mortgage details



                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
