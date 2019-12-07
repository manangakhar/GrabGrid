package com.example.grabgrid.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grabgrid.model.Transaction;
import com.example.grabgrid.model.User;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GrabGridDB";

    private static final String USER_TABLE_NAME = "Users";
    private static final String USER_KEY_ID = "userId";
    private static final String USER_KEY_USERNAME = "username";
    private static final String USER_KEY_PASSWORD = "password";
    private static final String USER_KEY_STEPS_REMAINING = "stepsRemaining";
    private static final String[] COLUMNS_USER = {USER_KEY_ID, USER_KEY_USERNAME, USER_KEY_PASSWORD, USER_KEY_STEPS_REMAINING};

    private static final String TXN_TABLE_NAME = "Transactions";
    private static final String TXN_KEY_ID = "txnId";
    private static final String TXN_KEY_USER_ID = "userId";
    private static final String TXN_KEY_SERVICE = "service";
    private static final String TXN_KEY_AMOUNT = "amount";
    private static final String[] COLUMNS_TXN = {TXN_KEY_ID, TXN_KEY_USER_ID, TXN_KEY_SERVICE, TXN_KEY_AMOUNT};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATION_TABLE = "CREATE TABLE if not exists Users ( "
                + "userId INTEGER PRIMARY KEY AUTOINCREMENT, " + "username TEXT, "
                + "password TEXT, " + "stepsRemaining INTEGER)";

        db.execSQL(CREATION_TABLE);

        CREATION_TABLE = "CREATE TABLE if not exists Transactions ( "
                + "txnId INTEGER PRIMARY KEY AUTOINCREMENT, " + "userId TEXT, "
                + "service TEXT, " + "amount INTEGER)";

        db.execSQL(CREATION_TABLE);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        /*if (newVersion > oldVersion) {
            db.execSQL("Alter TABLE Users add column stepsRemaining INTEGER");
        }*/
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TXN_TABLE_NAME);
        this.onCreate(db);
        db.close();
    }

    public void deleteOneUser(User user) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE_NAME, "userId = ?", new String[] { String.valueOf(user.getUserId()) });
        db.close();
    }

    public void deleteOneTxn(Transaction txn) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TXN_TABLE_NAME, "txnId = ?", new String[] { String.valueOf(txn.getTxnId()) });
        db.close();
    }


    public Transaction getTxn(int txnId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TXN_TABLE_NAME, // a. table
                COLUMNS_TXN, // b. column names
                " txnId = ?", // c. selections
                new String[] { String.valueOf(txnId) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        if(cursor.getCount()==0){
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setTxnId(Integer.parseInt(cursor.getString(0)));
        transaction.setUserId(Integer.parseInt(cursor.getString(1)));
        transaction.setService(cursor.getString(2));
        transaction.setAmount(Integer.parseInt(cursor.getString(3)));
        db.close();
        return transaction;
    }

    public User getUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USER_TABLE_NAME, // a. table
                COLUMNS_USER, // b. column names
                " userId = ?", // c. selections
                new String[] { String.valueOf(userId) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        if(cursor.getCount()==0){
            return null;
        }

        User user = new User();
        user.setUserId(Integer.parseInt(cursor.getString(0)));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setStepsRemaining(Integer.parseInt(cursor.getString(3)));
        db.close();
        return user;
    }

    public List<User> allUsers() {

        List<User> users = new LinkedList<>();
        String query = "SELECT  * FROM " + USER_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = null;

        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setUserId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setStepsRemaining(Integer.parseInt(cursor.getString(3)));
                users.add(user);
            } while (cursor.moveToNext());
        }
        db.close();
        return users;
    }

    public List<Transaction> allTransactions(int userId) {

        List<Transaction> transactions = new LinkedList<>();
        String query = "SELECT  * FROM " + TXN_TABLE_NAME + "where userId=" + userId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Transaction transaction = null;

        if (cursor.moveToFirst()) {
            do {
                transaction = new Transaction();
                transaction.setTxnId(Integer.parseInt(cursor.getString(0)));
                transaction.setUserId(Integer.parseInt(cursor.getString(1)));
                transaction.setService(cursor.getString(2));
                transaction.setAmount(Integer.parseInt(cursor.getString(3)));
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }
        db.close();
        return transactions;
    }

    public void addTransaction(Transaction txn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TXN_KEY_USER_ID, txn.getUserId());
        values.put(TXN_KEY_SERVICE, txn.getService());
        values.put(TXN_KEY_AMOUNT, txn.getAmount());
        // insert
        db.insert(TXN_TABLE_NAME,null, values);
        db.close();
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_KEY_USERNAME, user.getUsername());
        values.put(USER_KEY_PASSWORD, user.getPassword());
        values.put(USER_KEY_STEPS_REMAINING, user.getStepsRemaining());
        // insert
        db.insert(USER_TABLE_NAME,null, values);
        db.close();
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_KEY_USERNAME, user.getUsername());
        values.put(USER_KEY_PASSWORD, user.getPassword());
        values.put(USER_KEY_STEPS_REMAINING, user.getStepsRemaining());

        int i = db.update(USER_TABLE_NAME, // table
                values, // column/value
                "userId = ?", // selections
                new String[] { String.valueOf(user.getUserId()) });

        db.close();

        return i;
    }

    public int updateTransaction(Transaction txn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TXN_KEY_USER_ID, txn.getUserId());
        values.put(TXN_KEY_SERVICE, txn.getService());
        values.put(TXN_KEY_AMOUNT, txn.getAmount());

        int i = db.update(TXN_TABLE_NAME, // table
                values, // column/value
                "txnId = ?", // selections
                new String[] { String.valueOf(txn.getTxnId()) });

        db.close();

        return i;
    }

    public Boolean validateUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USER_TABLE_NAME, // a. table
                COLUMNS_USER, // b. column names
                " username = ? and password = ?", // c. selections
                new String[] { String.valueOf(user.getUsername()), String.valueOf(user.getPassword()) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();
        else
            return false;

        if(cursor.getCount()==0){
            return false;
        }

        user.setUserId(Integer.parseInt(cursor.getString(0)));
        db.close();
        return true;
    }

}

