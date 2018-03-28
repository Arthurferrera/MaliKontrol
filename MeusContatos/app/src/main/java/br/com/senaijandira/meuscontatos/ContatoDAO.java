package br.com.senaijandira.meuscontatos;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 17170075 on 21/02/2018.
 */

public class ContatoDAO {

    private Integer idContato = 0;

//  ArrayList para guardar os contatos, tipo um banco fake
    ArrayList<Contato> lstContato = new ArrayList<>();

//  Esquema de classes Singelton
    private static ContatoDAO instance;

    public static ContatoDAO getInstance(){
        if(instance == null){
            instance = new ContatoDAO();
        }
        return instance;
    }

    public Boolean inserir(Context context, Contato c){

//        acessar o banco em modo escrita
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", c.getNome());
        valores.put("email", c.getEmail());
        valores.put("telefone", c.getTelefone());
//        TODO: Arrumar a data de nascimento
//        valores.put("dt_nascimento", c.getDt_nascimento());

        if(c.getFoto() != null) {
            valores.put("foto", transformarParaBytes(c.getFoto()));
        }

        Long id = db.insert("tbl_contatos", null, valores);

        if(id != -1){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Contato> selecionarTodos(Context context){

        ArrayList<Contato> retorno = new ArrayList<>();

//        banco de dados de leitura
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
//        comando sql
        String sql = "Select * from tbl_contatos;";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Contato c = new Contato();
            c.setId(cursor.getInt(0)); //acessando a coluna do id
            c.setNome(cursor.getString(1));
            c.setTelefone(cursor.getString(2));
            c.setEmail(cursor.getString(3));

//          TODO: arrumar dt nascimento
            c.setDt_nascimento(new Date());

            byte[] fotoBanco = cursor.getBlob(5);

            if(fotoBanco != null && fotoBanco.length > 0){
                c.setFoto(transformarParaBitmap(fotoBanco));
            }

            retorno.add(c);
        }

        return retorno;
    }

    public Contato selecionarUM(Context context, int id){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "select * from tbl_contatos where _id = " + id +";";

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            Contato c = new Contato();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setTelefone(cursor.getString(2));
            c.setEmail(cursor.getString(3));
            c.setDt_nascimento(new Date()); // arrumar a data

            byte[] fotoBanco = cursor.getBlob(5);

            if(fotoBanco != null && fotoBanco.length > 0){
                c.setFoto(transformarParaBitmap(fotoBanco));
            }

            cursor.close();
            return c;
        }

        return null;
    }

    public Boolean remover(Context context, Integer id){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        db.delete("tbl_contatos", "_id = ?", new String[]{ id.toString() });

        return true;
    }

    public boolean atualizar(Context context, Contato c){

    SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", c.getNome());
        valores.put("telefone", c.getTelefone());
        valores.put("email", c.getEmail());

//        TODO: arrumar dt nascimento

        if(c.getFoto() != null) {
            valores.put("foto", transformarParaBytes(c.getFoto()));
        }

        db.update("tbl_contatos", valores, "_id = ?", new String[]{ c.getId().toString() } );

        return true;
    }


//    Função que transforma um Bitmap para u, array de bytes
//    é necessário porque no banco de dados é gravado um
//    array getDe de bytes
    private byte[] transformarParaBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

//    Função que transforma um array de bytes em bitmap.
//    é necssário porque quando volta do banco é
//    precisso tranformar em bitmap novamente
    private Bitmap transformarParaBitmap(byte[] img){
        return BitmapFactory.decodeByteArray(img, 0, img.length);

    }

}
