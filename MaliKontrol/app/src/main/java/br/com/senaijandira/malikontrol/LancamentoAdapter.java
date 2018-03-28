package br.com.senaijandira.malikontrol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 17170075 on 14/03/2018.
 */

public class LancamentoAdapter extends ArrayAdapter<Lancamento> {

        public LancamentoAdapter(Context ctx, ArrayList<Lancamento> lstLancamentos) {
            super( ctx, 0, lstLancamentos);
            //acessando o construtor da classe pai.
            //passando o contexto, 0 como o layout e a lst de contatos
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View v = convertView;

            if(v == null){
                v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_ultimos, null);
            }

            // Pegando o contato daquela posição
            Lancamento lancamento = getItem(position);

            // Acessando os elementos visuais
            TextView lbl_titulo_lancamento = v.findViewById(R.id.lbl_titulo_lancamento);
            TextView lbl_valor_lancamento = v.findViewById(R.id.lbl_valor_lancamento);
            TextView lbl_data_lancamento = v.findViewById(R.id.lbl_data_lancamento);
            TextView lbl_titulo_categoria = v.findViewById(R.id.lbl_titulo_categoria);

//            formatando os valores
            NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt","br"));

            // Colocando os elementos na tela
            lbl_data_lancamento.setText(lancamento.getData());
            lbl_titulo_lancamento.setText(lancamento.getNome());
            lbl_titulo_categoria.setText(lancamento.getNomeCategoria());
            lbl_valor_lancamento.setText(f.format(lancamento.getValor()));

            return v;
        }


}
