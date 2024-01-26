package com.example.firstapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.firstapp.model.SqlEmployee;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private Context context;

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "employee";

    // below int is our database version
    private static final int DB_VERSION = 4;

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
    private static final String EMAIL_COL="email";
    private static final String SALARY_COL="salary";

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
                + AGE_COL + " INT,"
                + SALARY_COL + " INT,"
                +EMAIL_COL+ " TEXT )";


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

    public ArrayList<SqlEmployee> readEmployees()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorEmployee = db.rawQuery("SELECT  *FROM " + TABLE_NAME, null);

        ArrayList<SqlEmployee> employeeArrayList = new ArrayList<>();

        if (cursorEmployee.moveToFirst()) {
            do {
                int id = cursorEmployee.getInt(0);
                String name = cursorEmployee.getString(1);
                String address = cursorEmployee.getString(2);
                String phone = cursorEmployee.getString(3);
                int age = cursorEmployee.getInt(4);
                int salary = cursorEmployee.getInt(5);
                String email = cursorEmployee.getString(6);

                employeeArrayList.add(new SqlEmployee(id, salary, age, name , address, email, phone));
            } while (cursorEmployee.moveToNext());
            // moving our cursor to next.
        }

        cursorEmployee.close();
        return employeeArrayList;
    }

    // this method is use to add new course to our sqlite database.
    public void addNewEmployee(String name, String address, String phoneNumber, int age, String email, int salary) {

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
        values.put(EMAIL_COL,email);
        values.put(SALARY_COL,salary);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();

        Toast.makeText(context, "Data insert successful", Toast.LENGTH_SHORT).show();
    }

    public void updateEmployee(int id, String name, String address, String phoneNumber, int age,String email, int salary) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(ADDRESS_COL, address);
        values.put(PHONE_COL, phoneNumber);
        values.put(AGE_COL, age);
        values.put(EMAIL_COL,email);
        values.put(SALARY_COL,salary);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our employee which is stored in original name variable.
        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // below is the method for deleting our employee.
    public void deleteEmployee(int id) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our employee name.
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
