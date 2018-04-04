package br.com.senaijandira.malikontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class LancamentosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView list_todos_lancamentos;
    LancamentoAdapter adapter;
    LancamentoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = LancamentoDAO.getInstance();
        Lancamento l = new Lancamento();
        l.setIdlancamento(0);

        list_todos_lancamentos = (ListView) findViewById(R.id.list_todos_lancamentos);
        adapter = new LancamentoAdapter(this, new ArrayList<Lancamento>());
        list_todos_lancamentos.setAdapter(adapter);

        list_todos_lancamentos.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Lancamento> todosLancamentos;
//        chamando o metodo que mostra os lancamentos
        todosLancamentos = dao.selecionarTodos(this);
//        limpando o adapter
        adapter.clear();
//      adicionando todos os lancamentos ao adapter
        adapter.addAll(todosLancamentos);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Lancamento item  = adapter.getItem(i);

        Intent intent = new Intent(this, VisualizarActivity.class);

        intent.putExtra("idLancamento", item.getIdlancamento());

        startActivity(intent);

    }
}
