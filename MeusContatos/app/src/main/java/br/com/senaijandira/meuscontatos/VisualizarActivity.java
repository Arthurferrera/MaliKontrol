package br.com.senaijandira.meuscontatos;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisualizarActivity extends AppCompatActivity {

    TextView txt_id_contatto, txt_nome, txt_telefone, txt_email, txt_nascimento;
    Integer idContato;
    ImageView img_view_contato;
    Button btn_ligar, btn_enviar;
    ContatoDAO dao = ContatoDAO.getInstance();
    DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
    Contato c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id_contatto = (TextView) findViewById(R.id.txt_id_contato);
        txt_nome = (TextView) findViewById(R.id.txt_nome);
        txt_telefone = (TextView) findViewById(R.id.txt_telefone);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_nascimento = (TextView) findViewById(R.id.txt_nascimento);
        img_view_contato = (ImageView) findViewById(R.id.img_view_contato);
        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        btn_ligar = (Button) findViewById(R.id.btn_ligar);

//        acessar o ID  passado por Intent
        Intent intent = getIntent();
        idContato = intent.getIntExtra("idContato",0);
    }


    private void mostrarContato() {
        c = dao.selecionarUM(this, idContato);
        if(c.getFoto() != null){
            img_view_contato.setImageBitmap(c.getFoto());
        }
        txt_nome.setText(c.getNome());
        txt_telefone.setText(c.getTelefone());
        txt_email.setText(c.getEmail());
        txt_nascimento.setText(df.format(c.getDt_nascimento()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarContato();
    }

    public void ligar(View v){
        Intent intent;
        intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: " + c.getTelefone()));

        startActivity(intent);
    }

    public void mandarEmail(View view) {
        Intent intent;
        intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: " + c.getEmail()));

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_visualizar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_excluir){
//            excluir
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Excluir");
            builder.setMessage("Tem certexa que deseja excluir esse contatinho");

            final Context context = this;
            builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ContatoDAO.getInstance().remover(context, idContato);
                    finish();
                }
            });

            builder.setNegativeButton("NÂO", null);
            builder.create().show();
        }

        if(item.getItemId() == R.id.menu_editar) {
//            editar
            Intent intent = new Intent(this, CadastroActivity.class);
            intent.putExtra("idContato", idContato);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

}
