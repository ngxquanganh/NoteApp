package com.example.noteapplication;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class dialog extends Dialog {
    private EditText mEditText;

    public dialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_layout);
        mEditText = findViewById(R.id.edit_text);
        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnOk = findViewById(R.id.btn_ok);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                // Do something with the text
                dismiss();
            }
        });
    }
}
