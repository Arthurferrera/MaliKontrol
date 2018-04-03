package br.com.senaijandira.malikontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView lbl_saldo, data_consulta;
    ListView list_ultimos_lancametos;
    LancamentoAdapter adapter;
    LancamentoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = LancamentoDAO.getInstance();
        Lancamento l = new Lancamento();
        l.setIdlancamento(0);

        lbl_saldo = (TextView) findViewById(R.id.lbl_saldo);
        data_consulta = (TextView) findViewById(R.id.data_consulta);
        list_ultimos_lancametos = (ListView) findViewById(R.id.list_ultimos_lancamentos);

        adapter = new LancamentoAdapter(this, new ArrayList<Lancamento>());

        list_ultimos_lancametos.setAdapter(adapter);

        /*ArrayList<Lancamento> lstLancamentos = new ArrayList<>();
        lstLancamentos.add(new Lancamento("Almoço com a mina", "R$ 44,96"));
        lstLancamentos.add(new Lancamento("Almoço com a mina", "R$ 44,96"));
        lstLancamentos.add(new Lancamento("Almoço com a mina", "R$ 44,96"));
        lstLancamentos.add(new Lancamento("Almoço com a mina", "R$ 44,96"));

        LancamentoAdapter adaptador = new LancamentoAdapter(this, lstLancamentos);
        list_ultimos_lancametos.setAdapter(adaptador);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencao = new Intent(getApplicationContext(), AdicionarLancamento.class);
                startActivity(intencao);
            }
        });


//        setando a data da consulta
        final Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        String dataAtual = String.format("%02d/%02d/%d", dia, mes+1, ano);
        data_consulta.setText(dataAtual);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Lancamento> ultimosLancamentos;

//        chamando o metodo que mostra os lancamentos
        ultimosLancamentos = dao.selecionarTodos(this);

//        limpando o adapter
        adapter.clear();

//      adicionando todos os lancamentos ao adapter
        adapter.addAll(ultimosLancamentos);

        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt","br"));
        Double saldo = dao.mostrarSaldo(this);
        lbl_saldo.setText("" + f.format(saldo) );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AbrirTela(View view) {
        Intent intencao = new Intent(this, LancamentosActivity.class);
        startActivity(intencao);
    }
}
