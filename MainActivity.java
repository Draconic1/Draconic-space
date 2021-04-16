package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //Объявим переменные компонентов
    Button button;
    TextView textView;
    TextView textView2;
    EditText editText;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;


    String product = "number   x     y \n";

    public void show_all(SQLiteDatabase db){
      //  product = "number   x     y \n";
        Cursor cursor = mDb.rawQuery("SELECT * FROM coord", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product += cursor.getString(0) + " | " + cursor.getString(1) + " | " + cursor.getString(2) + "\n";
            cursor.moveToNext();
        }
        cursor.close();

        textView.setText(product);
    }

    public void show_id(SQLiteDatabase db, int k){
      //  String product = "number   x     y \n";
        product+="\nВывод строки 2\n";
      //  Cursor cursor = db.query("coord",
      //          new String[] {"number", "x", "y"},
     //           "number = ?",
       //         new String[] {"number"},
        //        null, null, null);
        Cursor cursor = mDb.rawQuery("SELECT * FROM coord", null);
        cursor.moveToPosition(k);
        product += cursor.getString(0) + " | " + cursor.getString(1) + " | " + cursor.getString(2) + "\n";
        cursor.close();
        textView.setText(product);
    }

    public void show_number(SQLiteDatabase db, String number){
        product+="\nВывод строки по номеру аудитории\n";
          Cursor cursor = db.query("coord",
                  new String[] {"number", "x", "y"},
                   "number = ?",
                 new String[] {number},
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product += cursor.getString(0) + " | " + cursor.getString(1) + " | " + cursor.getString(2) + "\n";
            cursor.moveToNext();
        }
        cursor.close();
        textView.setText(product);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        //Найдем компоненты в XML разметке
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        //Пропишем обработчик клика кнопки
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_all(mDb);
                show_id(mDb,1);
                show_number(mDb,"422");


                //String aud = editText.getText().toString();


            }
        });
        
    }
}