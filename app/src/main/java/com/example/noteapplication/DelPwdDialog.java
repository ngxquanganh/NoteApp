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

public class DelPwdDialog extends Dialog {

    CallbackInterface cb;
    public DelPwdDialog(@NonNull Context context, Note note, CallbackInterface cb) {
        super(context);
        this.cb = cb;

        setContentView(R.layout.del_pwd_note_dialog);
        EditText et_password = findViewById(R.id.editText_password_note_delete);
        Button btn_ok = findViewById(R.id.button_ok);
        Button btn_cancel = findViewById(R.id.button_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                if (password.equals(note.getPassword())) {
                    Interface service = RetrofitClientInstance.getRetrofitInstance().create(Interface.class);
                    Call<Void> call = service.deletePass(note.getId());
                    call.enqueue(new retrofit2.Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Password deleted", Toast.LENGTH_SHORT).show();

                                note.setIsLocked(0);
                                note.setPassword(null);
                                cb.refreshLockStatus();
                            } else {
                                Toast.makeText(getContext(), "Password not deleted, error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Password not deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });

    }
}
