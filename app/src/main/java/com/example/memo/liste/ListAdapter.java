package com.example.memo.liste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memo.DetailActivity;
import com.example.memo.R;
import com.example.memo.database.ListeDTO;
import com.example.memo.fragements.DetailFragment;
import com.example.memo.webservice.RetourMemo;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>
{
    private static final String TAG = "ListAdapter";

    private List<ListeDTO> listeDTOMemos;
    private AppCompatActivity activity;

    public ListAdapter(List<ListeDTO> listeDTOMemos, AppCompatActivity activity)
    {
        this.listeDTOMemos = listeDTOMemos;
        this.activity = activity;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View viewMemo = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liste_memo, parent, false);
        return new ListViewHolder(viewMemo);
    }
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position)
    {
        holder.textViewLibelleCourse.setText(listeDTOMemos.get(position).intitule);
    }
    @Override
    public int getItemCount()
    {
        
        return listeDTOMemos.size();
    }
    public boolean onItemMove(int positionDebut, int positionFin)
    {

        Collections.swap(listeDTOMemos, positionDebut, positionFin);
        notifyItemMoved(positionDebut, positionFin);
        return true;
    }
    // Appelé une fois à la suppression.
    public void onItemDismiss(int position)
    {
        if (position > -1)
        {
            listeDTOMemos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder
    {
        // TextView intitulé course :
        public TextView textViewLibelleCourse;
        // Constructeur :
        public ListViewHolder(final View itemView)
        {
            super(itemView);
            textViewLibelleCourse = itemView.findViewById(R.id.libelle_memo);
            textViewLibelleCourse.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View view)
                {
                    ListeDTO memo = listeDTOMemos.get(getAdapterPosition());
                    // client HTTP :
                    AsyncHttpClient client = new AsyncHttpClient();
// paramètres :
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("memo", memo.intitule);

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("positionMemo", getAdapterPosition());
                    editor.apply();
// appel :
                    client.post("http://httpbin.org/post", requestParams, new AsyncHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response)
                        {
// retour du webservice :
                            String retour = new String(response);

                            Gson gson = new Gson();
                            RetourMemo retourWS = gson.fromJson(retour, RetourMemo.class);

                            Toast.makeText(view.getContext(), retourWS.form.memo, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              byte[] errorResponse, Throwable e)
                        {
                            Log.e(TAG, e.toString());
                        }
                    });

                    if(activity.findViewById(R.id.fragmentDetailMemo) == null){
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("memo", memo.intitule);
                        view.getContext().startActivity(intent);
                    }else{
                        DetailFragment fragment = new DetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(DetailFragment.EXTRA_PARAMETRE, memo.intitule);
                        fragment.setArguments(bundle);

// fragment manager :
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
// transaction :
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentDetailMemo, fragment, "detail");
                        fragmentTransaction.commit();
                    }

                }
            });
        }
    }
}
