package br.com.senaijandira.meuscontatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView list_view_contatos;
    ContatoAdapter adapter;
    ContatoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      pegando a instancia do DAO
        dao = ContatoDAO.getInstance();
        Contato c = new Contato();
        c.setId(0);

//      findviews
        list_view_contatos = (ListView) findViewById(R.id.lis_view_contatos);

//      criando o adaptador
        adapter = new ContatoAdapter(this, new ArrayList<Contato>());

//      plugando(conectando) o adaptador na lista
        list_view_contatos.setAdapter(adapter);

//      Clicl do botao flutuante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* è tipo um alert
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                abrir rerla de cadastro
*/
                Intent intent = new Intent(getApplicationContext(),CadastroActivity.class);
                startActivity(intent);

            }
        });

//        listener de click da lista
        list_view_contatos.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

//      pegando os contatos do "banco"
        ArrayList<Contato> contatoCadastrados;
        contatoCadastrados = dao.selecionarTodos(this);

//      limpar conteudo do adapter
        adapter.clear();

//      preenchendo o adaptador - para aparecer o conteudo novo e não duplicar o antigo
        adapter.addAll(contatoCadastrados);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//        pegando o contato da posição i da lista
        Contato item = adapter.getItem(i);

//        criando o objeto de intenção
        Intent intent = new Intent(this, VisualizarActivity.class);
//        passando o id do contato
        intent.putExtra("idContato", item.getId());
//        abrindo a tela de visualizar
        startActivity(intent);

//        Toast.makeText(this, "cliquei na lista", Toast.LENGTH_SHORT).show();

    }
}