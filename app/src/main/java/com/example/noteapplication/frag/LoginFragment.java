package com.example.noteapplication.frag;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapplication.R;
import com.example.noteapplication.User;
import com.example.noteapplication.network.Interface;
import com.example.noteapplication.network.ResponseJson;
import com.example.noteapplication.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {


    EditText et_username;
    EditText et_password;

    Button btn_login;
    Button btn_register;

    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login = view.findViewById(R.id.button_login);
        btn_register = view.findViewById(R.id.button_to_register);
        et_password = view.findViewById(R.id.ET_password);
        et_username = view.findViewById(R.id.ET_username);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                Interface Interface = RetrofitClientInstance.getRetrofitInstance().create(Interface.class);
                Call<ResponseJson> user = Interface.getUser(et_username.getText().toString(),et_password.getText().toString());
                user.enqueue(new Callback<ResponseJson>() {
                    @Override
                    public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                        if (response.body().status.equals("success")) {
                            SharedPreferences sharedPref = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("name",response.body().data.name);
                            editor.putInt("idUser", response.body().data.idUser);
                            editor.putString("password", response.body().data.password);
                            editor.putString("full_name", response.body().data.full_name);
                            editor.putInt("gender", response.body().data.gender);
                            editor.putBoolean("login_state", true);
                            editor.apply();
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment);

                        }
                        else
                        {
                            Toast.makeText(getContext(), "Login fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseJson> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }}
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
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

}