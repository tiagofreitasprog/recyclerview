package com.example.veggie;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veggie.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<DataProdutos> jsonResponses;
    private RecyclerView recyclerView,recyclerViewCarrinho;
    private RecyclerViewAdapter rc;
    private Carrinho carrinho;
    private RecyclerViewCarrinhoAdapter rcCarrinho;
    private List<Carrinho> carrinho_lista;
    ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        carrinho_lista = new ArrayList<>();
        Carrinho carrinho = new Carrinho("A","A","A","A");
        carrinho_lista.add(carrinho);

        Log.d("Carrinho lista", String.valueOf(carrinho_lista.get(0)));
        recyclerView = new RecyclerView(this);
        recyclerView = findViewById(R.id.destaques);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        rc = new RecyclerViewAdapter((ArrayList<DataProdutos>) listaDefault(),itemClickListener,this);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rc);

        recyclerViewCarrinho = new RecyclerView(this);
        recyclerViewCarrinho = findViewById(R.id.rv_carrinho);
        recyclerViewCarrinho.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCarrinho.setHasFixedSize(true);

        rcCarrinho = new RecyclerViewCarrinhoAdapter(carrinho_lista,itemClickListener,this);
        recyclerViewCarrinho.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCarrinho.setAdapter(rcCarrinho);

        // Initialize listener
        itemClickListener =new ItemClickListener() {
            @Override
            public void onClick(int position, DataProdutos value) {

            }

            @Override
            public void onClickBtn(int position, DataProdutos value) {
                int pos = getCategoryPos(carrinho.getId_produto());

                carrinho.setProduto(value.getNome());
                carrinho.setPreco(value.getPreco());
                carrinho.setQuantidade(value.getQuantidade());
                carrinho_lista.add(carrinho);

                Log.d("Carrinho posicao" , carrinho_lista.toString());

                rcCarrinho.notifyDataSetChanged();

            }
        };

        volleyGet();


    }
    private int getCategoryPos(String category) {
        return carrinho_lista.indexOf(category);
    }
    public List listaDefault(){
        DataProdutos produtos;
        produtos = new DataProdutos("0","maça","teste","20","www.google.com","1");
        jsonResponses = new ArrayList<>();
        jsonResponses.add(produtos);
        return jsonResponses;
    }
    public void volleyGet() {
        String url = "http://alugamadeira.pt/api/index.php/produto/list?limit=20";
        List<DataProdutos> jsonResponses = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("produtos");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id_produtos");
                        String nome = jsonObject.getString("nome");
                        String descricao = jsonObject.getString("descricao");
                        String preco = jsonObject.getString("preco");
                        String image = jsonObject.getString("img");

                        jsonResponses.add(new DataProdutos(id, nome, descricao,preco,image ,"1"));
                        recyclerView.setAdapter(new RecyclerViewAdapter((ArrayList<DataProdutos>) jsonResponses,itemClickListener,MainActivity.this));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Erro","Falha");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Erro","erro");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}