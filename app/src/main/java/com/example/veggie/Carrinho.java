package com.example.veggie;

import java.util.List;

public class Carrinho {
    public Carrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    Carrinho carrinho;


    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getId_produto() {
        return id_produto;
    }

    public void setId_produto(String id_produto) {
        this.id_produto = id_produto;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public Carrinho(String produto, String id_produto, String quantidade, String preco) {
        this.produto = produto;
        this.id_produto = id_produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    private String produto,id_produto,quantidade,preco;

}
