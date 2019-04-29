package com.example.mastermind.contacts_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="CONTACTS.DB";
    private static final String TABLE_NAME="CONTACTS";
    private static final String TABLE_NAME1="FAVCONTACTS";
    private static final String COL_ID="_ID";
    private static final String COL_NAME="NAME";
    private static final String COL_PHONE="PHONE";
    private static final String COL_EMAIL="E_MAIL";
    private static final String COL_IMG="PROFILEPIC";
    private static final String COL_NAMEF="NAME";
    private static final String COL_PHONEF="PHONE";
    private static final String COL_EMAILF="E_MAIL";
    private static final String COL_IMGF="PROFILEPIC";

    MyDatabase(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL("CREATE TABLE "+TABLE_NAME +" (" +COL_ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            COL_NAME+
            " TEXT NOT NULL , "+
            COL_PHONE+
            " TEXT NOT NULL UNIQUE, "+
            COL_EMAIL+
            " TEXT  , "+
            COL_IMG+
            " TEXT  )"
                );
        db.execSQL("CREATE TABLE "+TABLE_NAME1 +" (" +COL_ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                COL_NAMEF+
                " TEXT NOT NULL , "+
                COL_PHONEF+
                " TEXT NOT NULL UNIQUE, "+
                COL_EMAILF+
                " TEXT  , "+
                COL_IMGF+
                " TEXT  )"
        );



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);

        onCreate(db);
    }
    public boolean addContacts(String name,String phone,String email,String imguri)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COL_NAME,name);
        cv.put(COL_PHONE,phone);
        cv.put(COL_EMAIL,email);
        cv.put(COL_IMG,imguri);
        long result=db.insert(TABLE_NAME,null,cv);
        if(result==-1)
        {
            return false;
        }
        return true;
    }
public Cursor getContacts()
{
    SQLiteDatabase database=getWritableDatabase();
    Cursor cursor=database.rawQuery("SELECT * FROM "+TABLE_NAME +" ORDER BY "+COL_NAME,null);
    return  cursor;

}
public boolean updateContacts(String id,String name,String phone,String email,String imgpath)
{
    SQLiteDatabase database=getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put(COL_ID,id);
    contentValues.put(COL_NAME,name);
    contentValues.put(COL_PHONE,phone);
    contentValues.put(COL_EMAIL,email);
    contentValues.put(COL_IMG,imgpath);
    database.update(TABLE_NAME,contentValues,COL_ID+" = ?",new String[]{id});
    return true;
}
public boolean deleteContact(String id)
{

    SQLiteDatabase database=getWritableDatabase();
    int result=database.delete(TABLE_NAME,COL_ID+" = ?",new String[]{id});
    if(result>0)
    {
        return  true;
    }
    return false;
}

public Cursor getsearchData(String name)
{
    SQLiteDatabase database=getWritableDatabase();
    Cursor cursor=database.rawQuery("SELECT * FROM "+TABLE_NAME +" where " + COL_NAME + " like '%" + name
            + "%'", null);

return cursor;
}

public boolean addtoFav(String name,String phone,String email,String imgid)
{
    SQLiteDatabase db=this.getWritableDatabase();
    ContentValues cv=new ContentValues();

    cv.put(COL_NAME,name);
    cv.put(COL_PHONE,phone);
    cv.put(COL_EMAIL,email);
    cv.put(COL_IMG,imgid);
    long result=db.insert(TABLE_NAME1,null,cv);
    if(result==-1)
    {
        return false;
    }
    return true;

}
public Cursor gettingFav()
{
    SQLiteDatabase database=getWritableDatabase();
    Cursor cursor=database.rawQuery("SELECT DISTINCT * FROM "+TABLE_NAME1+" ORDER BY "+COL_NAME  ,null);
    return cursor;
}



}




