package br.com.senaijandira.malikontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class VisualizarActivity extends AppCompatActivity {

    TextView txt_valor, txt_descricao, txt_categoria, txt_data;
    Button btn_editar, btn_excluir;
    Integer idLancamento;
    Lancamento l;
    LancamentoDAO dao = LancamentoDAO.getInstance();
    NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt","br"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_valor = (TextView) findViewById(R.id.txt_valor);
        txt_descricao = (TextView) findViewById(R.id.txt_descricao);
        txt_categoria= (TextView) findViewById(R.id.txt_categoria);
        txt_data= (TextView) findViewById(R.id.txt_data);

        Intent intent = getIntent();
        idLancamento = intent.getIntExtra("idLancamento", 0);
    }

    public void mostrarLancamento(){
        l = dao.selecionarUm(this, idLancamento);
        double valor = l.getValor();
        txt_valor.setText(f.format(valor));
        if (valor < 0) {
            txt_valor.setTextColor(getResources().getColor(R.color.vermelho));
        } else {
            txt_valor.setTextColor(getResources().getColor(R.color.verde));
        }
        txt_descricao.setText(l.getNome());
        txt_categoria.setText(l.getNomeCategoria());
        txt_data.setText(l.getData());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarLancamento();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_visualizar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_excluir){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Excluir");
            builder.setMessage("Tem certeza que deseja excluir este lançamento?");

            final Context context = this;
            builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    LancamentoDAO.getInstance().excluir(context, idLancamento);
                    finish();
                }
            });

            builder.setNegativeButton("NÂO", null);
            builder.create().show();
        }

        if(item.getItemId() == R.id.menu_editar){
            Intent intent = new Intent(this, AdicionarLancamento.class);
            intent.putExtra("idLancamento", idLancamento);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}