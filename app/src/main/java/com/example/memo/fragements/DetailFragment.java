package com.example.memo.fragements;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memo.DetailActivity;
import com.example.memo.R;

public class DetailFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_PARAMETRE = "EXTRA_PARAMETRE";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null && getView() != null){
            String argument = getArguments().getString(EXTRA_PARAMETRE);
            TextView textView = getView().findViewById(R.id.fragementMemo);
            textView.setText(argument);
            Toast.makeText(getContext(), argument,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        FragmentActivity activity = getActivity();
        if(activity instanceof DetailActivity){

        }
    }
}
