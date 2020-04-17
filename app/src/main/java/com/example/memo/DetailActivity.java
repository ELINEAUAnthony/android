package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.memo.fragements.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String memo = getIntent().getStringExtra("memo");

        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DetailFragment.EXTRA_PARAMETRE, memo);
        fragment.setArguments(bundle);

// fragment manager :
        FragmentManager fragmentManager = getSupportFragmentManager();
// transaction :
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.conteneur_detail, fragment, "detail");
        fragmentTransaction.commit();
    }
}
