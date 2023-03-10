package com.example.noteapplication.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.noteapplication.CallbackInterface;
import com.example.noteapplication.DelPwdDialog;
import com.example.noteapplication.R;
import com.example.noteapplication.SetPwdDialog;
import com.example.noteapplication.network.Interface;
import com.example.noteapplication.network.RetrofitClientInstance;
import com.example.noteapplication.note.Note;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNoteFragment extends Fragment implements CallbackInterface {
    private TextView tVTitle;
    private EditText contentEditText;
    private Note note;
    Button btnDel;
    Button btnSave;
    private int id_note;
    ImageButton imageBtnLock;

    CallbackInterface callbackInterface = this;

    public EditNoteFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_note_fragment, container, false);

        // Get Note object from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            note = bundle.getParcelable("note");
        }

        // Initialize views
        tVTitle = view.findViewById(R.id.textViewTitle);
        contentEditText = view.findViewById(R.id.etex_content);

        // Set text of EditTexts to note's title and content
        tVTitle.setText(note.getTitle());
        contentEditText.setText(note.getContent());
        id_note = note.getId();

        btnDel = view.findViewById(R.id.button_delete);
        btnSave = view.findViewById(R.id.button_save);
        imageBtnLock = view.findViewById(R.id.imageButton_Lock);
        
        imageBtnLock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note.isLocked()) {
                    DelPwdDialog dialog = new DelPwdDialog(getContext(), note, callbackInterface);
                    dialog.show();

                } else {
                    SetPwdDialog dialog = new SetPwdDialog(getContext(), note, callbackInterface);
                    dialog.show();

                }


            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Retrofit client instance
                Interface apiService = RetrofitClientInstance.getRetrofitInstance().create(Interface.class);

                // Call deleteNote method with noteId parameter
                Call<Void> call = apiService.deleteNote(id_note);

                // Execute the call asynchronously
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {
                            // Delete successful, show a success message
                            Toast.makeText(getContext(), "Delete successful", Toast.LENGTH_SHORT).show();

                            // Navigate back to MainFragment
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Navigate back to MainFragment after 2 seconds
                                    Navigation.findNavController(view).navigate(R.id.action_editNoteFragment_to_mainFragment);
                                }
                            }, 500); // Delay 0.5 seconds

                        } else {
                            // Delete failed, show an error message
                            Toast.makeText(getContext(), "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Show an error message when the call fails
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            ;
        });
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString();
                String date_modify = LocalDateTime.now().toString(); // Get current date and time in string format

                Interface apiService = RetrofitClientInstance.getRetrofitInstance().create(Interface.class);

                Call<Void> call = apiService.updateNote(id_note, content, date_modify);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Update successful, show a success message
                            Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();

                            // Navigate back to MainFragment
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Navigate back to MainFragment after 2 seconds
                                    Navigation.findNavController(view).navigate(R.id.action_editNoteFragment_to_mainFragment);
                                }
                            }, 500); // Delay 0.5 seconds

                        } else {
                            // Update failed, show an error message
                            Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Show an error message when the call fails
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if(note.isLocked())
            imageBtnLock.setImageResource(R.drawable.lock);
        else
            imageBtnLock.setImageResource(R.drawable.unlock);
        return view;
    };

    @Override
    public void refreshLockStatus() {
     if(note.isLocked())
            imageBtnLock.setImageResource(R.drawable.lock);
        else
            imageBtnLock.setImageResource(R.drawable.unlock);
    }


}
