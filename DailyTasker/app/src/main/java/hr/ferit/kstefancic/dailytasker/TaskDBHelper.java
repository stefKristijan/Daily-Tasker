package hr.ferit.kstefancic.dailytasker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kristijan on 11.4.2017..
 */

public class TaskDBHelper extends SQLiteOpenHelper {

    static final String CREATE_TABLE_TASKS = "CREATE TABLE " + Schema.TABLE_TASK +
            " (" + Schema.TITLE + " VARCHAR(100)," + Schema.DESCRIPTION+ " TEXT," +Schema.DATE + " VARCHAR(30)," + Schema.CATEGORY + " VARCHAR(30)," +Schema.PRIORITY+" INT );";
    static final String DROP_TABLE_TASK = "DROP TABLE IF EXISTS " +Schema.TABLE_TASK;
    static final String SELECT_ALL_BOOKS = "SELECT * FROM "+ Schema.TABLE_TASK;
    static final String SELECT_CATEGORIES = "SELECT "+Schema.CATEGORY+" FROM "+Schema.TABLE_TASK;


    private static TaskDBHelper mTaskHelper = null;

    private TaskDBHelper (Context context){
        super(context.getApplicationContext(),Schema.DATABASE_NAME,null,Schema.SCHEMA_VER);
    }

    public static synchronized  TaskDBHelper getInstance(Context context){
        if(mTaskHelper==null){
            mTaskHelper = new TaskDBHelper(context);
        }
        return mTaskHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_TASK);
        this.onCreate(db);
    }

    public ArrayList<String> getCategories(){
        ArrayList<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_CATEGORIES,null);
        if(cursor.moveToFirst()){
            do{
                String category = cursor.getString(0);
                if(!categories.contains(category)) {
                    categories.add(category);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public void insertTask(Task task){
        ContentValues contVal = new ContentValues();
        contVal.put(Schema.TITLE, task.getTitle());
        contVal.put(Schema.DESCRIPTION, task.getDescription());
        contVal.put(Schema.DATE,task.getDate());
        contVal.put(Schema.CATEGORY,task.getCategory());
        contVal.put(Schema.PRIORITY,task.getPriority());
        SQLiteDatabase tasksDB = this.getWritableDatabase();
        tasksDB.insert(Schema.TABLE_TASK, Schema.TITLE, contVal);
        tasksDB.close();
    }

    public ArrayList<Task> getAllTasks(){
        SQLiteDatabase tasksDB = this.getWritableDatabase();
        Cursor cursor = tasksDB.rawQuery(SELECT_ALL_BOOKS,null);
        ArrayList<Task> tasks = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(0);
                String description = cursor.getString(1);
                String date = cursor.getString(2);
                String category = cursor.getString(3);
                int priority = cursor.getInt(4);
                tasks.add(new Task(title,description,date, category,priority));
            }while(cursor.moveToNext());
        }
        cursor.close();
        tasksDB.close();
        return tasks;
    }

    public static class Schema {
        private static final int SCHEMA_VER = 1;
        private static final String DATABASE_NAME = "tasks.db";

        static final String TABLE_TASK = "task";
        static final String TITLE = "title";
        static final String DESCRIPTION = "description";
        static final String CATEGORY = "category";
        static final String PRIORITY = "priority";
        static final String DATE = "date";
    }
}
