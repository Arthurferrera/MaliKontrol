package br.com.senaijandira.malikontrol;

/**
 * Created by 17170075 on 21/03/2018.
 */

public class Categoria {

    private int id;
    private String nome;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }


    @Override
    public String toString() {
        return nome;
    }
}
