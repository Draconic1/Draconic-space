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
    TextView textView3;
    EditText editText;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;


    String product = "";

    public void show_rav(SQLiteDatabase db){
        product += "id  _id1   _id2   weight  \n";
        Cursor cursor = mDb.rawQuery("SELECT * FROM coordrav", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product += cursor.getInt(0) + "   |   " + cursor.getInt(1) + "   |   " + cursor.getInt(2) + "   |   " + cursor.getInt(3) + "\n";
            cursor.moveToNext();
        }
        cursor.close();

        textView.setText(product);
    }

    public void show_dan(SQLiteDatabase db){
        product += "\n id        x               y            number  \n";
        Cursor cursor = mDb.rawQuery("SELECT * FROM coorddan", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product += cursor.getInt(0) + "   |   " + cursor.getDouble(1) + "   |   " + cursor.getDouble(2) + "   |   " + cursor.getString(3) + "\n";
            cursor.moveToNext();
        }
        cursor.close();

        textView.setText(product);
    }

    public void show_id_rav(SQLiteDatabase db, int k){
        product+="\nВывод строки 2 из таблицы связи точек\n";
        Cursor cursor = mDb.rawQuery("SELECT * FROM coordrav", null);
        cursor.moveToPosition(k);
        product += cursor.getInt(0) + " | " + cursor.getInt(1) + " | " + cursor.getInt(2) + " | " + cursor.getInt(3) + "\n";
        cursor.close();
        textView.setText(product);
    }

    public void show_number(SQLiteDatabase db, String number){
        product+="\nВывод строки по номеру аудитории\n";
          Cursor cursor = db.query("coorddan",
                  new String[] {"id", "x", "y", "number"},
                   "number = ?",
                 new String[] {number},
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product += cursor.getString(0) + " | " + cursor.getString(1) + " | " + cursor.getString(2) + " | " + cursor.getString(3) +"\n";
            cursor.moveToNext();
        }
        cursor.close();
        textView.setText(product);
    }

    public int into_id(SQLiteDatabase db, String number, int ID){
        Cursor cursor = db.query("coorddan",
                new String[] {"id", "x", "y", "number"},
                "number = ?",
                new String[] {number},
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ID = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        return ID;
    }

    public String out_id(SQLiteDatabase db, int ID){
        String number = "";
        Cursor cursor = mDb.rawQuery("SELECT * FROM coorddan", null);
        cursor.moveToPosition(ID-1);
        number = cursor.getString(3);
        cursor.close();
        return number;
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
                show_rav(mDb);
                //show_dan(mDb);
                show_id_rav(mDb,1);
                show_number(mDb,"3");

                String product1="";
                int ID=0;
                ID = into_id(mDb, "423a", ID);
                product+="\n ID = " + ID +"\n";

                String Num="";
                Num = out_id(mDb,ID);
                product+="Num = " + Num +"\n";

                //EditText Start=findViewByID(R.id.aud);
                //int Begin=Start.getText().toString();
                //String aud = editText.getText().toString();
            }
        });

    }
}