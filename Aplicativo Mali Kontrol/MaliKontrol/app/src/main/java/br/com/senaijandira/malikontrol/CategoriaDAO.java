package br.com.senaijandira.malikontrol;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by 17170075 on 21/03/2018.
 */

public class CategoriaDAO {

    private static CategoriaDAO instance;

    public static CategoriaDAO getInstance(){
        if(instance == null){
            instance = new CategoriaDAO();
        }
        return instance;
    }

    public ArrayList<Categoria> selecionarTodas(Context context){

        ArrayList<Categoria> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM tbl_categoria";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Categoria c = new Categoria();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));

            retorno.add(c);
        }
        return retorno;
    }

}
