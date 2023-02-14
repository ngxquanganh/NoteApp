package com.example.noteapplication.frag;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapplication.R;
import com.example.noteapplication.network.Interface;
import com.example.noteapplication.network.ResponseNote;
import com.example.noteapplication.network.RetrofitClientInstance;
import com.example.noteapplication.note.Note;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;


public class EditNoteFragment extends Fragment {

     EditText et_title;
        EditText et_content;
        Button btn_save;
        Button btn_cancel;
    public EditNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        et_title = view.findViewById(R.id.ET_title);
        et_content = view.findViewById(R.id.ET_content);
        btn_save = view.findViewById(R.id.button_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String content = et_content.getText().toString();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", getContext().MODE_PRIVATE);
                int userId = sharedPreferences.getInt("idUser", 0);

                if(title.isEmpty() || content.isEmpty())
                {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
//
                    Interface service = RetrofitClientInstance.getRetrofitInstance().create(Interface.class);
                    System.out.println("userId: " + userId);
                    Call<Note> call = service.createNote(userId,title, content, LocalDateTime.now().toString(),LocalDateTime.now().toString());
                    call.enqueue(new Callback<Note>() {
                        @Override
                        public void onResponse(Call<Note> call, retrofit2.Response<Note> response) {
                            Toast.makeText(getContext(), "Note created successfully", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(v).navigate(R.id.action_editNoteFragment_to_mainFragment);
                        }

                        @Override
                        public void onFailure(Call<Note> call, Throwable t) {
                            Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}