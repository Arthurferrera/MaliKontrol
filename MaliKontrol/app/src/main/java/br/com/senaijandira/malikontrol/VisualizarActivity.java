package br.com.senaijandira.malikontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        btn_editar = (Button) findViewById(R.id.btn_editar);
        btn_excluir = (Button) findViewById(R.id.btn_excluir);

        Intent intent = getIntent();
        idLancamento = intent.getIntExtra("idLancamento", 0);
    }

    public void mostrarLancamento(){
        l = dao.selecionarUm(this, idLancamento);
        txt_valor.setText(f.format(l.getValor()));
        txt_descricao.setText(l.getNome());
        txt_categoria.setText(l.getNomeCategoria());
        txt_data.setText(l.getData());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarLancamento();
    }
}
