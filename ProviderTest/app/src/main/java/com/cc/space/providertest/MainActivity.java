package com.cc.space.providertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private String newId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButOnClickListener(R.id.addData);
        setButOnClickListener(R.id.delData);
        setButOnClickListener(R.id.updateData);
        setButOnClickListener(R.id.queryData);

    }

    private void setButOnClickListener(int butId) {
        Button addBut=findViewById(butId);
        addBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addData:
                Uri uri= Uri.parse("content://cc.com.databasetest.provider/book");
                ContentValues values=new ContentValues();
                values.put("name","A Calsh of JKings");
                values.put("author","george Martin");
                values.put("pages","1040");
                values.put("price","22.85");
                Uri newUri=getContentResolver().insert(uri,values);
                newId=newUri.getPathSegments().get(1);
                toastSimple("addData succ newId="+newId);
                break;
            case R.id.updateData:
                Uri updateUri= Uri.parse("content://cc.com.databasetest.provider/book/"+newId);
                ContentValues updateParam=new ContentValues();
                updateParam.put("name","A storm of swords");
                updateParam.put("pages","1216");
                updateParam.put("price","24.05");
                int updateCou= getContentResolver().update(updateUri,updateParam,null,null);
                 Log.d(TAG,"updateCou="+updateCou);
                toastSimple("updateData succ updateCou="+updateCou);

                break;
            case R.id.delData:
                Uri delUri= Uri.parse("content://cc.com.databasetest.provider/book/"+newId);
                int delCou= getContentResolver().delete(delUri,null,null);
                Log.d(TAG,"delCou="+delCou);
                toastSimple("delData succ delCou="+delCou);

                break;
            case R.id.queryData:
                Uri queryUri= Uri.parse("content://cc.com.databasetest.provider/book");
                Cursor cursor=getContentResolver().query(queryUri,null,null,null,
                        null,null);
                if(cursor!=null){
                    while (cursor.moveToNext()){
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG,"book nam is"+name);
                        Log.d(TAG,"book author is"+author);
                        Log.d(TAG,"book pages is"+pages);
                        Log.d(TAG,"book price is"+price);
                    }
                    cursor.close();
                    toastSimple("queryData succ ");

                }
                break;
        }
    }

    void toastSimple(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
