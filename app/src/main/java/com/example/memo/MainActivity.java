package com.example.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memo.database.AppDatabaseHelper;
import com.example.memo.liste.ItemTouchHelperCallback;
import com.example.memo.liste.ListAdapter;
import com.example.memo.database.ListeDTO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListAdapter listAdapter;
    private List<ListeDTO> listMemos = new ArrayList<>();
    private EditText ajoutMemo;
    private Button btnAjoutMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = findViewById(R.id.liste_memos);

        listMemos = AppDatabaseHelper.getDatabase(this)
                .listeDAO()
                .getListeMemos();

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        int valeur = preferences.getInt("positionMemo", -1) + 1;
        if ( valeur > -1) {
            Toast.makeText(this, "La derniere valeur cliquée est " + valeur, Toast.LENGTH_SHORT ).show();
        }

        listAdapter = new ListAdapter(listMemos, this);
        recyclerView.setAdapter(listAdapter);

        btnAjoutMemo = findViewById(R.id.addMemo);
        ajoutMemo = findViewById(R.id.libelle_new_memo);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelperCallback(listAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        btnAjoutMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListeDTO newMemo = new ListeDTO(ajoutMemo.getText().toString());
                AppDatabaseHelper.getDatabase(v.getContext()).listeDAO().insert(newMemo);
                AppDatabaseHelper.getDatabase(v.getContext())
                        .listeDAO()
                        .getListeMemos();
                listAdapter.notifyItemInserted(listMemos.size());
                listMemos.add(newMemo);
                ajoutMemo.setText("");
            }
        });
    }
}
