package br.com.senaijandira.malikontrol;

import java.util.Date;

/**
 * Created by 17170075 on 14/03/2018.
 */

public class Lancamento {

    private int idlancamento;
    private String nome;
    private String tipoLancamento;
    private Double valor;
    private String data;
    private int idCategoria;
    private  String nomeCategoria;

    public Lancamento() {
            this.nome = nome;
            this.valor = valor;
    }

    public int getIdlancamento() {
        return idlancamento;
    }

    public void setIdlancamento(int idlancamento) {
        this.idlancamento = idlancamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() { return nomeCategoria; }

    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }

}
