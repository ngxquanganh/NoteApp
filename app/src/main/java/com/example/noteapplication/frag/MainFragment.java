package com.example.noteapplication.frag;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.noteapplication.R;
import com.example.noteapplication.ConfirmPwdDialog;
import com.example.noteapplication.network.Interface;
import com.example.noteapplication.network.ResponseNote;
import com.example.noteapplication.network.RetrofitClientInstance;
import com.example.noteapplication.note.Note;
import com.example.noteapplication.note.NoteAdapter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment implements NoteAdapter.OnNoteClickListener {

    Button btn_logout;
    Button btn_add;
    private RecyclerView rcvNote;
    List<Note> data = null;
    public MainFragment() {
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

        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        btn_logout = view.findViewById(R.id.button_logout);
        rcvNote = view.findViewById(R.id.recyclerview);
        btn_add = view.findViewById(R.id.addnewnotebtn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvNote.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvNote.addItemDecoration(itemDecoration);

        System.out.println("MainFragment");
        callApiGetNote();
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("login_state",false);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_loginFragment);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_addNoteFragment);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(
                true // default to enabled
        ) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(
                this, // LifecycleOwner
                callback);
    }

    private void callApiGetNote(){

        Interface service = RetrofitClientInstance.getRetrofitInstance().create(Interface.class);
        SharedPreferences sharedPref = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        int id_user = sharedPref.getInt("idUser",0);
        System.out.println("id_user: "+id_user);
        Call<ResponseNote> call = service.getNotes(id_user);
        call.enqueue(new Callback<ResponseNote>() {
            @Override
            public void onResponse(Call<ResponseNote> call, Response<ResponseNote> response) {
                System.out.println(response.code());

                if (response.isSuccessful()) {

                    data = response.body().getData();
                    Collections.sort(data);
                } else {
                    System.out.println("error");
                }
                NoteAdapter adapter = new NoteAdapter(data);
                adapter.setOnNoteClickListener(MainFragment.this);
                rcvNote.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseNote> call, Throwable t) {

                Log.e("Error", t.getMessage());
//                System.out.println(t.getMessage());

            }


        });
    }

    @Override
    public void onNoteClick(Note note) {
        if(note.isLocked())
        {
            ConfirmPwdDialog dialog = new ConfirmPwdDialog(getActivity(), note.getPassword());
            dialog.setOnDialogResultListener(new ConfirmPwdDialog.OnDialogResultListener() {
                @Override
                public void onResult(boolean result) {
                    if(result)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("note", note);
                        Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_editNoteFragment, bundle);
                    }
                    else

                    {
                        Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("note", note);
        Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_editNoteFragment, bundle);
    }

}