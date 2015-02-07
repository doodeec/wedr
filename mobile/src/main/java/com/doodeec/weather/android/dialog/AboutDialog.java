package com.doodeec.weather.android.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.doodeec.weather.android.R;

/**
 * About dialog
 * available in action bar menu
 *
 * @author Dusan Bartos
 */
public class AboutDialog extends DialogFragment {

    private static final String ABOUT_DIALOG_TAG = "aboutDialog";

    public static AboutDialog showDialog(FragmentManager fragmentManager) {
        AboutDialog dialog = new AboutDialog();
        dialog.show(fragmentManager, ABOUT_DIALOG_TAG);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // dismiss dialog only
                            }
                        }
                )
                .create();
    }
}
