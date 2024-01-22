package com.example.firstapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    private Context context;

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "employee";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "employee_details";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String ADDRESS_COL = "address";

    // below variable for our course description column.
    private static final String PHONE_COL = "phone";

    // below variable is for our course tracks column.
    private static final String AGE_COL = "age";

    // creating a constructor for our database handler.
    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + ADDRESS_COL + " CHAR(100),"
                + PHONE_COL + " TEXT,"
                + AGE_COL + " INT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // this method is use to add new course to our sqlite database.
    public void addNewData(String name, String address, String phoneNumber, int age) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(ADDRESS_COL, address);
        values.put(PHONE_COL, phoneNumber);
        values.put(AGE_COL, age);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();

        Toast.makeText(context, "Data insert successful", Toast.LENGTH_SHORT).show();
    }
}
