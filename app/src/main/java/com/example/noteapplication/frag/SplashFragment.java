package com.example.noteapplication.frag;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteapplication.R;

public class SplashFragment extends Fragment {

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                boolean login_state  = sharedPref.getBoolean("login_state", false);
                if(login_state)
                {
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_mainFragment);
                }
                else
                {
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
                }
            }
        }, 1000);
        return view;
    }
}