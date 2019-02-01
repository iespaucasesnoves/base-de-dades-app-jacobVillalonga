package com.jacob.basedades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataSourceVi {
    private SQLiteDatabase database;
    private HelperVi dbAjuda; //CLASSE AJUDA
    private String[] allColumnsVi = {HelperVi.COLUMN_ID, HelperVi.COLUMN_NOMVI, HelperVi.COLUMN_ANADA,
            HelperVi.COLUMN_LLOC, HelperVi.COLUMN_GRADUACIO, HelperVi.COLUMN_DATA,
            HelperVi.COLUMN_COMENTARI, HelperVi.COLUMN_IDBODEGA,
            HelperVi.COLUMN_IDDENOMINACIO, HelperVi.COLUMN_PREU,
            HelperVi.COLUMN_VALOLFATIVA, HelperVi.COLUMN_VALGUSTATIVA,
            HelperVi.COLUMN_VALVISUAL, HelperVi.COLUMN_NOTA, HelperVi.COLUMN_FOTO,
            HelperVi.COLUMN_TIPUS};
    private String[] allDenominacio = {HelperVi.COLUMN_NOMDENOMINACIO};
    private String[] allBodega = {HelperVi.COLUMN_NOMBODEGA};
    private String[] allColumnsDenominacio = {HelperVi.COLUMN__IDDENOMINACIO, HelperVi.COLUMN_NOMDENOMINACIO};
    private String[] allColumnsBodega = {HelperVi.COLUMN__IDBODEGA, HelperVi.COLUMN_NOMBODEGA};
    private String[] allColumnsTipus = {HelperVi.COLUMN_TIPUS};

    public DataSourceVi(Context context) { //CONSTRUCTOR
        dbAjuda = new HelperVi(context);
    }

    public void open() throws SQLException {
        database = dbAjuda.getWritableDatabase();
    }

    public void close() {
        dbAjuda.close();
    }

    public Vi createVi(Vi vi) {
        // insert d'un nou vi
        ContentValues values = new ContentValues();
        values.put(HelperVi.COLUMN_NOMVI, vi.getNomVi());
        values.put(HelperVi.COLUMN_ANADA, vi.getAnada());
        values.put(HelperVi.COLUMN_TIPUS, vi.getTipus());
        values.put(HelperVi.COLUMN_LLOC, vi.getLloc());
        values.put(HelperVi.COLUMN_GRADUACIO, vi.getGraduacio());
        values.put(HelperVi.COLUMN_DATA, String.valueOf(vi.getData()));
        values.put(HelperVi.COLUMN_COMENTARI, vi.getComentari());
        values.put(HelperVi.COLUMN_IDBODEGA, vi.getIdBodega());
        values.put(HelperVi.COLUMN_IDDENOMINACIO, vi.getIdDenominacio());
        values.put(HelperVi.COLUMN_PREU, vi.getPreu());
        values.put(HelperVi.COLUMN_VALOLFATIVA, vi.getValOlfativa());
        values.put(HelperVi.COLUMN_VALGUSTATIVA, vi.getValGustativa());
        values.put(HelperVi.COLUMN_VALVISUAL, vi.getValVisual());
        values.put(HelperVi.COLUMN_NOTA, vi.getNota());
        values.put(HelperVi.COLUMN_FOTO, vi.getFoto());
        long insertId = database.insert(HelperVi.TABLE_VI, null, values);
        vi.setId(insertId);
        return vi;
    }

    public boolean updateVi(Vi vi) {
        // update vi
        ContentValues values = new ContentValues();
        long id = vi.getId();
        values.put(HelperVi.COLUMN_NOMVI, vi.getNomVi());
        values.put(HelperVi.COLUMN_ANADA, vi.getAnada());
        values.put(HelperVi.COLUMN_LLOC, vi.getLloc());
        values.put(HelperVi.COLUMN_TIPUS, vi.getTipus());
        values.put(HelperVi.COLUMN_GRADUACIO, vi.getGraduacio());
        values.put(HelperVi.COLUMN_DATA, String.valueOf(vi.getData()));
        values.put(HelperVi.COLUMN_COMENTARI, vi.getComentari());
        values.put(HelperVi.COLUMN_IDBODEGA, vi.getIdBodega());
        values.put(HelperVi.COLUMN_IDDENOMINACIO, vi.getIdDenominacio());
        values.put(HelperVi.COLUMN_PREU, vi.getPreu());
        values.put(HelperVi.COLUMN_VALOLFATIVA, vi.getValOlfativa());
        values.put(HelperVi.COLUMN_VALGUSTATIVA, vi.getValGustativa());
        values.put(HelperVi.COLUMN_VALVISUAL, vi.getValVisual());
        values.put(HelperVi.COLUMN_NOTA, vi.getNota());
        values.put(HelperVi.COLUMN_FOTO, vi.getFoto());
        return database.update(HelperVi.TABLE_VI, values, HelperVi.COLUMN_ID + "=" + id, null) > 0;
    }

    public void deleteVi(Vi vi) {
        long id = vi.getId();
        database.delete(HelperVi.TABLE_VI, HelperVi.COLUMN_ID + " = " + id, null);
    }

    public Vi getVi(long id) {
        Vi vi;
        Cursor cursor = database.query(HelperVi.TABLE_VI,
                allColumnsVi, HelperVi.COLUMN_ID + " = " + id, null,
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            vi = cursorToVi(cursor);
        } else {
            vi = new Vi();
        } // id=-1 no trobat
        cursor.close();
        return vi;
    }

    public List<Vi> getAllVi() {
        List<Vi> vins = new ArrayList<Vi>();
        Cursor cursor = database.query(HelperVi.TABLE_VI, allColumnsVi, null, null, null, null,
                HelperVi.COLUMN_DATA + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Vi vi = cursorToVi(cursor);
            vins.add(vi);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return vins;
    }

    private Vi cursorToVi(Cursor cursor) {
        Vi v = new Vi();
        v.setId(cursor.getLong(0));
        v.setNomVi(cursor.getString(1));
        v.setAnada(cursor.getString(2));
        v.setLloc(cursor.getString(3));
        v.setGraduacio(cursor.getString(4));
        v.setData(cursor.getString(5));
        v.setComentari(cursor.getString(6));
        v.setIdBodega(cursor.getLong(7));
        v.setIdDenominacio(cursor.getLong(8));
        v.setPreu(cursor.getFloat(9));
        v.setValOlfativa(cursor.getString(10));
        v.setValGustativa(cursor.getString(11));
        v.setValVisual(cursor.getString(12));
        v.setNota(cursor.getInt(13));
        v.setFoto(cursor.getString(14));
        v.setTipus(cursor.getString(15));
        return v;
    }
//CREAREM EL MÈTODES QUE ENS FACIN FALTA EN FUNCIÓ DE LA NOSTRA BASE DE DADES
    //bodega
    public Bodega createBodega(Bodega bodega) {
        // insert d'una nova bodega
        ContentValues values = new ContentValues();
        values.put(HelperVi.COLUMN_NOMBODEGA, bodega.getNomBodega());
        values.put(HelperVi.COLUMN_IDBODEGA, bodega.getIdBodega());
        long insertId = database.insert(HelperVi.TABLE_BODEGA, null, values);
        bodega.setIdBodega(insertId);
        return bodega;
    }
    public List<String> getLlistaBodegues(){
        List<String> llista = new ArrayList<String>();
        Cursor cursor = database.query(HelperVi.TABLE_BODEGA, allColumnsBodega, null, null, null, null,
                null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String bodega = cursorToBodega(cursor).getNomBodega();
            llista.add(bodega);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return llista;
    }
    public String getNomBodega(long id){
        String nom;
        Cursor cursor = database.query(HelperVi.TABLE_BODEGA, allColumnsBodega, HelperVi.COLUMN__IDBODEGA + " = " + id, null,
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            nom = cursorToBodega(cursor).getNomBodega();
        } else {
            nom = new Bodega().getNomBodega();
        } // id=-1 no trobat
        cursor.close();
        return nom;
    }
    private Bodega cursorToBodega(Cursor cursor) {
        Bodega v = new Bodega();
        v.setIdBodega(cursor.getLong(0));
        v.setNomBodega(cursor.getString(1));
        return v;
    }

    //denominacio
    public Denominacio createDenominacio(Denominacio denominacio) {
        // insert d'una nova bodega
        ContentValues values = new ContentValues();
        values.put(HelperVi.COLUMN_NOMDENOMINACIO, denominacio.getNomDenominacio());
        values.put(HelperVi.COLUMN_IDDENOMINACIO, denominacio.getIdDenominacio());
        long insertId = database.insert(HelperVi.TABLE_DENOMINACIO, null, values);
        denominacio.setIdDenominacio(insertId);
        return denominacio;
    }
    public List<String> getLlistaDenominacions(){
        List<String> llista = new ArrayList<String>();
        Cursor cursor = database.query(HelperVi.TABLE_DENOMINACIO, allDenominacio, null, null, null, null,
                null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String denominacio = cursorToDenominacio(cursor).getNomDenominacio();
            llista.add(denominacio);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return llista;
    }
    public String getNomDenominacio(long id){
        String nom;
        Cursor cursor = database.query(HelperVi.TABLE_BODEGA, allColumnsDenominacio, HelperVi.COLUMN__IDDENOMINACIO + " = " + id, null,
                null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            nom = cursorToDenominacio(cursor).getNomDenominacio();
        } else {
            nom = new Denominacio().getNomDenominacio();
        } // id=-1 no trobat
        cursor.close();
        return nom;
    }
    private Denominacio cursorToDenominacio(Cursor cursor) {
        Denominacio v = new Denominacio();
        v.setIdDenominacio(cursor.getLong(0));
        v.setNomDenominacio(cursor.getString(1));
        return v;
    }
    //tipus

    public List<String> getAllTipus() {
        List<String> listTipus = new ArrayList<String>();
        Cursor cursor = database.query(HelperVi.TABLE_TIPUS, allColumnsTipus, null, null, null, null,
                null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tipus = cursorToTipus(cursor);
            listTipus.add(tipus);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return listTipus;
    }
    private String cursorToTipus(Cursor cursor) {
        String v = cursor.getString(0);
        return v;
    }

}