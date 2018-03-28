package br.com.senaijandira.meuscontatos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {

    EditText txt_nome, txt_telefone, txt_dtNasc, txt_email;
    ImageView img_contato;
    Button btn_salvar;
    ContatoDAO dao = ContatoDAO.getInstance();
    Bitmap foto;

    int COD_GALERIA = 1;

    Boolean modoEdicao = false;
    Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_nome = (EditText) findViewById(R.id.txt_nome);
        txt_telefone = (EditText) findViewById(R.id.txt_telefone);
        txt_dtNasc = (EditText) findViewById(R.id.txt_dtNasc);
        txt_email = (EditText) findViewById(R.id.txt_email);
        img_contato = (ImageView) findViewById(R.id.img_contato);
        btn_salvar = (Button) findViewById(R.id.btn_salvar);

        Integer idContato = getIntent().getIntExtra("idContato", 0);
        if(idContato != 0){
//            edição
            modoEdicao = true;
            contato = ContatoDAO.getInstance().selecionarUM(this, idContato);
            txt_nome.setText(contato.getNome());
            txt_email.setText(contato.getEmail());
            txt_telefone.setText(contato.getTelefone());
            txt_dtNasc.setText(new SimpleDateFormat("dd/mm/yyyy").format(contato.getDt_nascimento()));
            if (contato.getFoto() != null ){
                img_contato.setImageBitmap(contato.getFoto());
            }
        } else {
//            inserir
            modoEdicao = false;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void salvar(View v) {


        Contato c;
        if(modoEdicao){
            c = contato;
        } else {
            c = new Contato();
        }

//        verificar se caixa está vazia e avisar o usuario
        if(txt_nome.getText().toString().isEmpty()){
            txt_nome.setError("Preencha o nome");
            return;
        } else {
            c.setNome(txt_nome.getText().toString());
        }
        if(txt_telefone.getText().toString().isEmpty()) {
            txt_telefone.setError("Preencha o telefone");
            return;
        } else {
            c.setTelefone(txt_telefone.getText().toString());
        }
        if(txt_email.getText().toString().isEmpty()){
            txt_email.setError("Preencha o email");
            return;
        } else {
            c.setEmail(txt_email.getText().toString());
        }

        String dt_nascimento = txt_dtNasc.getText().toString();
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        try {
//            formatar a data
            Date dt = df.parse(dt_nascimento);

//            definindo a data de nascimento
            c.setDt_nascimento(dt);
        } catch (ParseException e) {
            e.printStackTrace();
            txt_dtNasc.setError("Preencha uma data correta!");
            return;
        }

        if(foto != null){
            c.setFoto(foto);
        }

        if(modoEdicao){
//            atualizando o contato
            ContatoDAO.getInstance().atualizar(this, c);
            Toast.makeText(getBaseContext(), "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        } else{
//            inserir no banco de dados
            ContatoDAO.getInstance().inserir(this, c);
            Toast.makeText(getBaseContext(), "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
        }

//            finalizar a tela
        finish();
    }

//    abrir galeria de imagens atraves da ImageView
    public void abrirGaleria(View v){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, COD_GALERIA);
    }

//    aqui volta o resultado da chamada a galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == COD_GALERIA){
            if(resultCode == Activity.RESULT_OK){
//                selecionou alguma coisa
                try {
//                    pegando a imagem em binario
                    InputStream inp = getContentResolver()
                            .openInputStream(data.getData());

//                    transformando o strean en bitmap (binario em imagem)
                    foto = BitmapFactory.decodeStream(inp);

//                    colocando a foto no ImageView
                    img_contato.setImageBitmap(foto);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
