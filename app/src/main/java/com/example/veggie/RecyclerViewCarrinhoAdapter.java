package com.example.veggie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewCarrinhoAdapter extends RecyclerView.Adapter<RecyclerViewCarrinhoAdapter.ViewHolder> {
    private Context context;
    TextView nome_field,quantidade_field;
    Button eliminar;
    ItemClickListener itemClick;
    private List<Carrinho> retrievedResponses;

    public RecyclerViewCarrinhoAdapter(List<Carrinho> carrinho_lista, ItemClickListener itemClickListener, Context context) {
        this.retrievedResponses = carrinho_lista;
        this.itemClick = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setNome(retrievedResponses.get(position).getProduto());
        holder.setQuantidade(retrievedResponses.get(position).getQuantidade());
    }

    @Override
    public int getItemCount() {
        return retrievedResponses.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nome_field = itemView.findViewById(R.id.carrinho_nome);
            quantidade_field = itemView.findViewById(R.id.carrinho_quantidade);
            eliminar = itemView.findViewById(R.id.carrinho_eliminar);

        }
        public void setNome(String nome){
            nome_field.setText(nome);
        }
        public void setQuantidade(String quantidade){
            nome_field.setText(quantidade);
        }
    }
}
