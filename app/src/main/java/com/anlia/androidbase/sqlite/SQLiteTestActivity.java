package com.anlia.androidbase.sqlite;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.anlia.androidbase.R;

import static com.anlia.androidbase.sqlite.MySQLiteOpenHelper.FILE_DIR;

public class SQLiteTestActivity extends AppCompatActivity {
    private SQLiteDatabase database;

    public static final String DATABASE_NAME = FILE_DIR + "test.db";
    private static final int CODE_PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请写入权限
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, CODE_PERMISSION_REQUEST);
        } else {
            createDB();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case CODE_PERMISSION_REQUEST:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createDB();
                } else{
                }
                break;
            default:
                break;
        }
    }

    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                if(database!=null){
                    ContentValues values = new ContentValues();
                    values.put("id","1");
                    values.put("name","name1");
                    database.insert("test",null,values);

                    database.execSQL("insert into test(id, name) values(2, 'name2')");
                    database.close();
                }
                break;
            case R.id.btn_delete:
                if(database!=null){
                    //添加多条数据用来测试
                    database.execSQL("insert into test(id, name) values(3, 'name3')");
                    database.execSQL("insert into test(id, name) values(4, 'name4')");
                    database.execSQL("insert into test(id, name) values(5, 'name5')");

                    String whereClause = "id=?";
                    String[] whereArgs = {"3"};
                    database.delete("test",whereClause,whereArgs);

                    database.execSQL("delete from test where name = 'name4'");

                    database.close();
                }
                break;
            case R.id.btn_update:
                if (database!=null){
                    ContentValues values = new ContentValues();
                    values.put("name","update2");
                    String whereClause = "id=?";
                    String[] whereArgs={"2"};
                    database.update("test",values,whereClause,whereArgs);

                    database.execSQL("update test set name = 'update5' where id = 5");
                    database.close();
                }
                break;
            case R.id.btn_query:
//                if(database!=null){
//                    String selection = "id=? or name=?";
//                    String[] selectionArgs = {"1","update2"};
//                    Cursor cursor = database.query ("test",null,selection,selectionArgs,null,null,null);
//                    while (cursor.moveToNext()){
//                        String id = cursor.getString(0);
//                        String name=cursor.getString(1);
//                        Log.e("SQLiteTest query","id:"+id+" name:"+name);
//                    }
//                    database.close();
//                }

                if(database!=null){
                    Cursor cursor = database.rawQuery("SELECT * FROM test WHERE id=? or name=?", new String[]{"1","update2"});
                    while (cursor.moveToNext()){
                        String id = cursor.getString(0);
                        String name=cursor.getString(1);
                        Log.e("SQLiteTest query","id:"+id+" name:"+name);
                    }
                    database.close();
                }

//                if(database!=null){
//                    Cursor cursor = database.rawQuery("SELECT * FROM test", null);
//                    while (cursor.moveToNext()){
//                        String id = cursor.getString(0);
//                        String name=cursor.getString(1);
//                        Log.e("SQLiteTest query","id:"+id+" name:"+name);
//                    }
//                    database.close();
//                }
                break;
        }
    }

    private void createDB(){
//        File dir = new File(FILE_DIR);
//        if (!dir.exists()) {
//            dir.mkdir();
//        }

//        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
//        database.execSQL("create table if not exists " + "test" +
//                "(id text primary key,name text)");

//        SQLiteDatabase database = this.openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
//        database.execSQL("create table if not exists " + "test" +
//                "(id text primary key,name text)");

        MySQLiteOpenHelper sqLiteOpenHelper = new MySQLiteOpenHelper(this, DATABASE_NAME);
        database = sqLiteOpenHelper.getWritableDatabase();
    }
}
