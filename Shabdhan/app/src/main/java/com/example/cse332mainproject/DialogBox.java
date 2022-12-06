package com.example.cse332mainproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBox extends AppCompatDialogFragment{

    private EditText editTextName;
    private EditText editTextNumber;
    private DialogBoxListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Add Recipient")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editTextName.getText().toString();
                        String number = editTextNumber.getText().toString();
                        if(!name.equals("") && !number.equals("")) {
                            listener.applyTexts(name, number);
                        }
                        else{
                            Toast.makeText(((Dialog) dialogInterface).getContext(), "Please fill up all fields!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        editTextName = view.findViewById(R.id.edit_name);
        editTextNumber = view.findViewById(R.id.edit_number);


        return builder.create();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogBoxListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement DialogBox Listener");
        }

    }

    public interface DialogBoxListener
    {
        void applyTexts(String username, String password);
    }

}

