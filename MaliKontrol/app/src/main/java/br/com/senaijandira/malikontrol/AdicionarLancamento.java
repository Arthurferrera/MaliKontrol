package br.com.senaijandira.malikontrol;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AdicionarLancamento extends AppCompatActivity {

    RadioButton rd_receita, rd_despesa;
    RadioGroup rd_group_tipo;
    EditText txt_nome_lancamento, txt_valor_lancamento, txt_data_lancamento;
    Spinner sp_categoria;

    CategoriaDAO dao = CategoriaDAO.getInstance();

    Boolean modoEdicao = false;
    Lancamento lancamento;

    // private static String[] categorias = new String[]{"Lazer", "Transporte", "Saúde", "Moradia", "Salário", "Outros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lancamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rd_receita = (RadioButton) findViewById(R.id.rd_receita);
        rd_despesa = (RadioButton) findViewById(R.id.rd_despesa);
        txt_nome_lancamento = (EditText) findViewById(R.id.txt_nome_lancamento);
        txt_valor_lancamento = (EditText) findViewById(R.id.txt_valor_lancamento);
        txt_data_lancamento= (EditText) findViewById(R.id.txt_data_lancamento);
        sp_categoria  = (Spinner) findViewById(R.id.sp_categoria);
        rd_group_tipo = (RadioGroup) findViewById(R.id.rd_group_tipo);

//        colocando no EditText da data do lancamento, o dia atual, para facilitar
        final Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        String dataAtual = String.format("%02d/%02d/%d", dia, mes+1, ano);
        txt_data_lancamento.setText(dataAtual);


        ArrayList<Categoria> categorias = dao.selecionarTodas(this);

        ArrayAdapter adapter = new ArrayAdapter<Categoria>(this, R.layout.support_simple_spinner_dropdown_item, categorias);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_categoria.setAdapter(adapter);
    }

    public void salvar(View view){

        Lancamento lanc;

        if(modoEdicao){
            lanc = lancamento;
        } else {
            lanc = new Lancamento();

        }
//      verificando se campo está vazio
        if(txt_nome_lancamento.getText().toString().isEmpty()){
            txt_nome_lancamento.setError("Preencha a descrição");
            return;
        }
//      verificando se campo está vazio
        if(txt_valor_lancamento.getText().toString().isEmpty()){
            txt_valor_lancamento.setError("Preencha o valor");
            return;
        }
//      verificando se campo está vazio
        if(txt_data_lancamento.getText().toString().isEmpty()){
            txt_data_lancamento.setError("Preencha a data");
            return;
        }
//      verificando se os radios estão selecionados
        if(rd_group_tipo.getCheckedRadioButtonId() == -1){
            rd_despesa.setError("Selecione uma das opções");
            rd_receita.setError("Selecione uma das opções");
            return;
        }
//        verificando qual radio está selecionado para aplicar a lógica de como salvar o valor

        Double valor  = Double.parseDouble(txt_valor_lancamento.getText().toString());
        if (rd_receita.isChecked()){
            lanc.setTipoLancamento("R");
            lanc.setValor(valor);
        } else {
            valor = valor * -1;
            lanc.setValor(valor);
            lanc.setTipoLancamento("D");
        }

        lanc.setNome(txt_nome_lancamento.getText().toString());
        lanc.setData(txt_data_lancamento.getText().toString());

//        pegando o id da categoria no spinner
        Categoria categoria = (Categoria) sp_categoria.getSelectedItem();
        lanc.setIdCategoria(categoria.getId());

        LancamentoDAO.getInstance().inserir(this, lanc);
        Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

        finish();

    }
}
