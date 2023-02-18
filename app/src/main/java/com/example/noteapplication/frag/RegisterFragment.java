package com.example.noteapplication.frag;

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
import com.example.noteapplication.network.ResponseJson;
import com.example.noteapplication.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {


    EditText et_username;
    EditText et_password;
    EditText et_fullname;
    Button btn_register;


    public RegisterFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        et_fullname = view.findViewById(R.id.ET_fullname);
        et_username = view.findViewById(R.id.ET_username);
        et_password = view.findViewById(R.id.ET_password);
        btn_register = view.findViewById(R.id.button_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String fullname = et_fullname.getText().toString();
                if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()){
                    return;
                }

                Interface service = RetrofitClientInstance.getRetrofitInstance().create(Interface.class);
                Call<ResponseJson> call = service.registerUser(username,password,1,fullname);
                call.enqueue(new Callback<ResponseJson>() {
                    @Override
                    public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                        System.out.println(response.body());
                        if (response.body().status.equals("success") ){
                            Toast.makeText( getContext(), "Register success", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
                        }
                        else if(response.body().status.equals("fail")){
                            Toast.makeText( getContext(), "User existed\nRegister fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseJson> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

        return view;
    }
}