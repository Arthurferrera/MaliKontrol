package br.com.senaijandira.meuscontatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 17170075 on 07/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

//    nome banco
    private static String DB_NAME = "contatos.db";
//    versão banco
    private static int DB_VERSION = 1;

//    construtor da classe para criaco do banco
    public DbHelper(Context ctx){
        super(ctx, DB_NAME, null, DB_VERSION);
    }

//    codigo de criacao do banco, aqui vai ser criada as tabelas , ou qualquer estrutura inicial
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table tbl_contatos(" +
                "_id INTEGER primary key autoincrement," +
                "nome TEXT," +
                "telefone TEXT," +
                "email TEXT," +
                "dt_nascimento INTEGER," +
                "foto BLOOB);";

        db.execSQL(sql);

    }

//    nesse método será feito os upgrades do banco
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tbl_contatos;");
        onCreate(db);
    }
}