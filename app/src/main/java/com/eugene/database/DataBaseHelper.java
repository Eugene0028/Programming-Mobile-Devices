package com.eugene.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.eugene.database.entity.Student;

public class DataBaseHelper extends SQLiteOpenHelper
{
    private Context context;

    private static final String DATABASE_NAME = "Students.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "students_table";

    private static final String COLUMN_ID = "_id";

    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_WEIGHT = "WEIGHT";

    private static final String COLUMN_HEIGHT = "HEIGHT";
    private static final String COLUMN_AGE = "AGE";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        @SuppressLint("DefaultLocale")
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s FLOAT, %s FLOAT, %s INTEGER);",
                TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_WEIGHT, COLUMN_HEIGHT, COLUMN_AGE);
        Log.i(query, "MEEESSSAAAGEE");
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    void addStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, student.getName());
        cv.put(COLUMN_WEIGHT, student.getWeight());
        cv.put(COLUMN_HEIGHT, student.getHeight());
        cv.put(COLUMN_AGE, student.getAge());
        if (db.insert(TABLE_NAME, null, cv) == -1) Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
    }


    Cursor readAllData(){
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    void deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public Cursor sortByAge() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_AGE, null);
    }

    public Cursor sortByAgeDescending() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_AGE + " DESC", null);
    }

}
