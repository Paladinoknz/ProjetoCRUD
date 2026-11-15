/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
 *
 * @author ubuntu
 */
public class Filme {
    
    private int id_filme;
    private String titulo;
    private int ano_lancamento;
    private int duracao_min;
    private String descricao;
    private int id_genero;
    private int id_diretor;
    private int id_produtora;
    private int id_usuario;

    public int getId_filme() {
        return id_filme;
    }

    public void setId_filme(int id_filme) {
        this.id_filme = id_filme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno_lancamento() {
        return ano_lancamento;
    }

    public void setAno_lancamento(int ano_lancamento) {
        this.ano_lancamento = ano_lancamento;
    }

    public int getDuracao_min() {
        return duracao_min;
    }

    public void setDuracao_min(int duracao_min) {
        this.duracao_min = duracao_min;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public int getId_diretor() {
        return id_diretor;
    }

    public void setId_diretor(int id_diretor) {
        this.id_diretor = id_diretor;
    }

    public int getId_produtora() {
        return id_produtora;
    }

    public void setId_produtora(int id_produtora) {
        this.id_produtora = id_produtora;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    
    
}
