package com.example.noteapplication;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.noteapplication.network.Interface;
import com.example.noteapplication.network.RetrofitClientInstance;
import com.example.noteapplication.note.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPwdDialog extends Dialog {

    CallbackInterface cb;


    public SetPwdDialog(@NonNull Context context, Note note, CallbackInterface cb) {
        super(context);
        this.cb = cb;
        setContentView(R.layout.set_pwd_note_dialog);

        EditText password = findViewById(R.id.editText_password_note);
        EditText retype_password = findViewById(R.id.editText_password_note_retype);
        Button btnCancel = findViewById(R.id.button_cancel);
        Button btnOk = findViewById(R.id.button_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = password.getText().toString();
                if (text.equals(retype_password.getText().toString())
                        && text.length() > 0) {
                    Interface service = RetrofitClientInstance.getRetrofitInstance().create(Interface.class);
                    Call<Void> call = service.setPass( note.getId(), text);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Password set", Toast.LENGTH_SHORT).show();
                                note.setIsLocked(1);
                                note.setPassword(text);
                                cb.refreshLockStatus();
                            }
                            else
                                Toast.makeText(getContext(), "Password not set", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                    dismiss();
                }
                // Do something with the text
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
