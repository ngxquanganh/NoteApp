package com.example.noteapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ConfirmPwdDialog extends Dialog {
    private EditText mEditText;
    private String password;


    public ConfirmPwdDialog(Context context, String password) {
        super(context);


        this.password = password;
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
                if (text.equals(password)) {
                    mOnDialogResultListener.onResult(true);
                    dismiss();
                } else {
                    mOnDialogResultListener.onResult(false);

                }


            }
        });
    }

    public interface OnDialogResultListener {
        void onResult(boolean result);
    }

    private OnDialogResultListener mOnDialogResultListener;

    public void setOnDialogResultListener(OnDialogResultListener onDialogResultListener) {
        mOnDialogResultListener = onDialogResultListener;
    }
}
