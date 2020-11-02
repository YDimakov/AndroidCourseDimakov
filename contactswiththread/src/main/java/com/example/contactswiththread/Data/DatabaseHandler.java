// Работа моего модуля при помощи SQLiteOpenHelper полностью рабочий!!!!





//package com.example.contactswiththread.Data;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import Utils.Util;
//
//public class DatabaseHandler extends SQLiteOpenHelper {
//    private int id;
//    private SQLiteDatabase sql;
//    private ContentValues contentValues;
//    private Cursor cursor;
//    private static String createContactsTable = null;
//    private Contact contact;
//    private String countQuery;
//    private String selectAllContact;
//    private List<Contact> contactList;
//
//    public DatabaseHandler(Context context) {
//        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        createContactsTable = "CREATE TABLE " + Util.TABLE_NAME + "("
//                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
//                + Util.KEY_NAME + " TEXT,"
//                + Util.KEY_INFORMATION + " TEXT" + ")";
//        db.execSQL(createContactsTable);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
//        onCreate(db);
//    }
//
//    public long insertContact(String name, String info) {
//
//        sql = this.getWritableDatabase();
//        contentValues = new ContentValues();
//        contentValues.put(Util.KEY_NAME, name);
//        contentValues.put(Util.KEY_INFORMATION, info);
//        id = (int) sql.insert(Util.TABLE_NAME, null, contentValues);
//        sql.close();
//        return id;
//    }
//
//    public Contact getContact(int id) {
//        sql = this.getReadableDatabase();
//        cursor = sql.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_NAME,
//                        Util.KEY_INFORMATION}, Util.KEY_ID + "=?", new String[]{String.valueOf(id)},
//                null, null,
//                null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        contact = new Contact((int) Long.parseLong(
//                cursor.getString(0)),
//                cursor.getString(1),
//                cursor.getString(2));
//        cursor.close();
//        return contact;
//    }
//
//    public List<Contact> getAllContacts() {
//        sql = this.getReadableDatabase();
//        contactList = new ArrayList<>();
//        selectAllContact = "SELECT * FROM " + Util.TABLE_NAME;
//        cursor = sql.rawQuery(selectAllContact, null);
//        if (cursor.moveToFirst()) {
//            do {
//                contact = new Contact();
//                contact.setId(Integer.parseInt(cursor.getString(0)));
//                contact.setName(cursor.getString(1));
//                contact.setInformation(cursor.getString(2));
//                contactList.add(contact);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return contactList;
//    }
//
//    public int updateContact(Contact contact) {
//        sql = this.getWritableDatabase();
//        contentValues = new ContentValues();
//        contentValues.put(Util.KEY_NAME, contact.getName());
//        contentValues.put(Util.KEY_INFORMATION, contact.getInformation());
//        return sql.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
//    }
//
//    public void deleteContact(Contact contact) {
//        sql = this.getWritableDatabase();
//        sql.delete(Util.TABLE_NAME, Util.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
//        sql.close();
//    }
//
//    public int getContactCount() {
//        sql = this.getReadableDatabase();
//        countQuery = "SELECT * FROM " + Util.TABLE_NAME;
//        cursor = sql.rawQuery(countQuery, null);
//        return cursor.getCount();
//    }
//}
