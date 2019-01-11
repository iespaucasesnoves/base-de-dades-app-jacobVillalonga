package com.jacob.basedades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    private String[] allColumnsDenominacio = {HelperVi.COLUMN_IDDENOMINACIO, HelperVi.COLUMN_NOMDENOMINACIO};
    private String[] allColumnsBodega = {HelperVi.COLUMN_IDBODEGA, HelperVi.COLUMN_NOMBODEGA};

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
        v.setIdBodega(cursor.getLong(8));
        v.setPreu(cursor.getFloat(9));
        v.setValOlfativa(cursor.getString(10));
        v.setValGustativa(cursor.getString(11));
        v.setValVisual(cursor.getString(12));
        v.setNota(cursor.getInt(13));
        v.setFoto(cursor.getString(14));
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

    public boolean updateBodega(Bodega bodega) {
        // update vi
        ContentValues values = new ContentValues();
        long idBodega = bodega.getIdBodega();
        values.put(HelperVi.COLUMN_NOMBODEGA, bodega.getNomBodega());
        return database.update(HelperVi.TABLE_BODEGA, values, HelperVi.COLUMN_IDBODEGA + "=" + idBodega, null) > 0;
    }

    public void deleteBodega(Bodega bodega) {
        long idBodega = bodega.getIdBodega();
        database.delete(HelperVi.TABLE_BODEGA, HelperVi.COLUMN_IDBODEGA + " = " + idBodega, null);
    }

    public List<Bodega> getAllBodega() {
        List<Bodega> bod = new ArrayList<Bodega>();
        Cursor cursor = database.query(HelperVi.TABLE_BODEGA, allColumnsBodega, null, null, null, null,
                null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Bodega bodega = cursorToBodega(cursor);
            bod.add(bodega);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return bod;
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
    public boolean updateDenominacio(Denominacio denominacio) {
        // update den
        ContentValues values = new ContentValues();
        long idDenominacio = denominacio.getIdDenominacio();
        values.put(HelperVi.COLUMN_NOMDENOMINACIO, denominacio.getNomDenominacio());
        return database.update(HelperVi.TABLE_DENOMINACIO, values, HelperVi.COLUMN_IDDENOMINACIO + "=" + idDenominacio, null) > 0;
    }

    public void deleteDenominacio(Denominacio denominacio) {
        long idDenominacio = denominacio.getIdDenominacio();
        database.delete(HelperVi.TABLE_DENOMINACIO, HelperVi.COLUMN_IDDENOMINACIO + " = " + idDenominacio, null);
    }
    public List<Denominacio> getAllDenominacio() {
        List<Denominacio> denoms = new ArrayList<Denominacio>();
        Cursor cursor = database.query(HelperVi.TABLE_DENOMINACIO, allColumnsDenominacio, null, null, null, null,
                null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Denominacio denominacio = cursorToDenominacio(cursor);
            denoms.add(denominacio);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return denoms;
    }
    private Denominacio cursorToDenominacio(Cursor cursor) {
        Denominacio v = new Denominacio();
        v.setIdDenominacio(cursor.getLong(0));
        v.setNomDenominacio(cursor.getString(1));
        return v;
    }

}