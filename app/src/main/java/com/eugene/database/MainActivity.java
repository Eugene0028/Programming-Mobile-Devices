package com.eugene.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.eugene.database.entity.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        dataBaseHelper = new DataBaseHelper(this);
        createStudents();
        //dataBaseHelper.deleteAllData();
        displayData(tableLayout);
    }

    private void createStudents() {
        dataBaseHelper.addStudent(new Student("Emma", 55.0f, 165.0f, 19));
        dataBaseHelper.addStudent(new Student("Michael", 80.2f, 190.0f, 22));
        dataBaseHelper.addStudent(new Student( "Sophia", 60.0f, 170.0f, 20));
        dataBaseHelper.addStudent(new Student("William", 75.8f, 175.0f, 21));
        dataBaseHelper.addStudent(new Student( "Olivia", 58.5f, 160.0f, 19));
        dataBaseHelper.addStudent(new Student( "James", 85.0f, 185.0f, 23));
        dataBaseHelper.addStudent(new Student( "Ava", 54.0f, 155.0f, 18));
        dataBaseHelper.addStudent(new Student("Benjamin", 72.0f, 180.0f, 20));
        dataBaseHelper.addStudent(new Student( "Mia", 50.5f, 160.0f, 19));
    }


    private void displayData(TableLayout tableLayout){
        Typeface typeface = ResourcesCompat.getFont(this, R.font.press_start_2p);
        Cursor cursor = dataBaseHelper.sortByAge();
        int size = 13;
        while (cursor.moveToNext())
        {
            TableRow row = new TableRow(this);
            row.addView(addData(String.valueOf(cursor.getInt(0)), typeface, size));
            row.addView(addData(String.valueOf(cursor.getString(1)), typeface, size));
            row.addView(addData(String.valueOf(cursor.getString(2)), typeface, size));
            row.addView(addData(String.valueOf(cursor.getString(3)), typeface, size));
            row.addView(addData(String.valueOf(cursor.getString(4)), typeface, size));
            tableLayout.addView(row);
        }
    }

    private TextView addData(String field, Typeface typeface, int size)
    {
        TextView data = new TextView(this);
        data.setText(field);
        data.setPadding(10, 10, 10, 10);
        data.setTextColor(Color.parseColor("#ff7700"));
        data.setTypeface(typeface);
        data.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return data;
    }

}