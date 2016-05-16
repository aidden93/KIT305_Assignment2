package au.edu.utas.kit305_assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import au.edu.utas.kit305_assignment2.Pojo.PastData;

/**
 * Created by adarshan on 5/13/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private List<PastData> listFoods = new ArrayList<PastData>();
    public static final String DATABASE_NAME = "logfood.db";
    public static final String TABLE_NAME = "mymeal";
    public static final String FOOD_GROUP = "food_group";
    public static final String FOOD_TYPE = "food_type";
    public static final String QUANTITY = "quantity";
    public static final String DATE = "date";
    public static final String TIME = "time";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
            + "food_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FOOD_GROUP + " TEXT,"
            + FOOD_TYPE + " TEXT,"
            + QUANTITY + " TEXT,"
            + DATE + " TEXT,"
            + TIME + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String food_group, String food_type, String quantity, String date,
                          String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("food_id", (byte[]) null);
        contentValues.put(FOOD_GROUP, food_group);
        contentValues.put(FOOD_TYPE, food_type);
        contentValues.put(QUANTITY, quantity);
        contentValues.put(DATE, date);
        contentValues.put(TIME, time);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean removeEntry(int index) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "food_id="+index, null) != -1;
    }

    public List<PastData> getListFoods(int page, String startDate, String endDate) {
        int itemPerPage = 4;
        int offset = (page - 1) * itemPerPage;
        int i = 0;
        String query = ("SELECT * FROM " + TABLE_NAME + " WHERE date BETWEEN ? AND ? "+" LIMIT " + offset + "," + itemPerPage );
        Log.i("startDate", startDate);
        Log.i("endDate", endDate);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{endDate, startDate});

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            PastData food = new PastData();
            food.setFoodGroup(cursor.getString(cursor.getColumnIndex(FOOD_GROUP)));
            food.setFoodType(cursor.getString(cursor.getColumnIndex(FOOD_TYPE)));
            food.setServing(cursor.getString(cursor.getColumnIndex(QUANTITY)));
            food.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
            food.setTime(cursor.getString(cursor.getColumnIndex(TIME)));
            listFoods.add(food);

        }
        return listFoods;
    }
}
