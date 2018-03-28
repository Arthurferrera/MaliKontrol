package br.com.senaijandira.malikontrol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 17170075 on 21/03/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static String DB_NAME ="financas.db";

    private static int DB_VERSION = 1;

    public DbHelper(Context ctx) {super(ctx, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tbl_categoria(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL);");

        db.execSQL("CREATE TABLE tbl_lancamentos(_id INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "valor DOUBLE," +
                "data TEXT," +
                "tipoLancamento TEXT," +
                "idCategoria INTEGER," +
                "FOREIGN KEY (idCategoria) REFERENCES tbl_categoria (_id));");

        db.execSQL("INSERT INTO tbl_categoria (nome) VALUES ('Alimentação');");
        db.execSQL("INSERT INTO tbl_categoria (nome) VALUES ('Lazer');");
        db.execSQL("INSERT INTO tbl_categoria (nome) VALUES ('Moradia');");
        db.execSQL("INSERT INTO tbl_categoria (nome) VALUES ('Transporte');");
        db.execSQL("INSERT INTO tbl_categoria (nome) VALUES ('Saúde');");
        db.execSQL("INSERT INTO tbl_categoria (nome) VALUES ('Salário');");
        db.execSQL("INSERT INTO tbl_categoria (nome) VALUES ('Outros');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tbl_categoria;");
        db.execSQL("drop table tbl_lancamentos;");
        onCreate(db);
    }
}
