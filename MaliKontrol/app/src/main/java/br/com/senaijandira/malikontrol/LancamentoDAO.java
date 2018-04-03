package br.com.senaijandira.malikontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 17170075 on 21/03/2018.
 */

public class LancamentoDAO {

    private static LancamentoDAO instance;

    public static LancamentoDAO getInstance(){
        if(instance == null){
            instance = new LancamentoDAO();
        }
        return instance;
    }

    public Boolean inserir(Context context, Lancamento l){

//        acessar o banco em modo escrita
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", l.getNome());
        valores.put("valor", l.getValor());
        valores.put("data", l.getData());
        valores.put("tipoLancamento", l.getTipoLancamento());
        valores.put("idCategoria", l.getIdCategoria());

        Long id = db.insert("tbl_lancamentos", null, valores);

        if(id != -1){
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<Lancamento> selecionarTodos (Context context){
        ArrayList<Lancamento> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM tbl_lancamentos l INNER JOIN tbl_categoria c ON l.idCategoria = c._id";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Lancamento l = new Lancamento();
            l.setIdlancamento(cursor.getInt(0));
            l.setNome(cursor.getString(1));
            l.setValor(cursor.getDouble(2));
            l.setData(cursor.getString(3));
            l.setTipoLancamento(cursor.getString(4));
            l.setIdCategoria(cursor.getInt(5));
            l.setNomeCategoria(cursor.getString(7));

            retorno.add(l);
        }
        return retorno;
    }

    public Lancamento selecionarUm(Context context, int id){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM tbl_lancamentos l INNER JOIN tbl_categoria c ON l.idCategoria = c._id AND L._id =" + id +";";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            Lancamento l = new Lancamento();
            l.setIdlancamento(cursor.getInt(0));
            l.setNome(cursor.getString(1));
            l.setTipoLancamento(cursor.getString(2));
            l.setValor(cursor.getDouble(3));
            l.setData(cursor.getString(4));
            l.setIdCategoria(cursor.getInt(5));
            l.setNomeCategoria(cursor.getString(7));

            cursor.close();
            return l;
        }
        return null;
    }


    public Double mostrarSaldo(Context context) {

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT SUM(valor) FROM tbl_lancamentos;";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        return cursor.getDouble(0);
    }
}
