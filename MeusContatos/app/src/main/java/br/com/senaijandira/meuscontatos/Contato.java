package br.com.senaijandira.meuscontatos;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Contato {

    private String nome;
    private String telefone;
    private String email;
    private Date dt_nascimento;
    private Bitmap foto; // Objeto de umagem
    private Integer id;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(Date dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) { this.foto = foto; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }
}
