package com.redstar.infosystem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import com.redstar.infosystem.User;
import com.redstar.infosystem.UsersBase;
import com.redstar.infosystem.Worker;
import com.redstar.infosystem.WorkersBase;

import java.util.ArrayList;


public class InfoSystemDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = InfoSystemDbHelper.class.getSimpleName();

    private SQLiteDatabase db;
    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "infosystem.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 3;

    /**
     * Конструктор {@link InfoSystemDbHelper}.
     *
     * @param context Контекст приложения
     */
    public InfoSystemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_WORKERS_TABLE = "CREATE TABLE IF NOT EXISTS `" + InfoSystemContract.WorkersEntry.TABLE_NAME + "` (`"
                + InfoSystemContract.WorkersEntry._ID + "` INTEGER PRIMARY KEY AUTOINCREMENT, `"
                + InfoSystemContract.WorkersEntry.COLUMN_NAME + "` TEXT, `"
                + InfoSystemContract.WorkersEntry.COLUMN_SURNAME + "` TEXT, `"
                + InfoSystemContract.WorkersEntry.COLUMN_PATRONYMIC + "` TEXT, `"
                + InfoSystemContract.WorkersEntry.COLUMN_AGE + "` INTEGER, `"
                + InfoSystemContract.WorkersEntry.COLUMN_POST + "` TEXT, `"
                + InfoSystemContract.WorkersEntry.COLUMN_GROUP + "` INTEGER, `"
                + InfoSystemContract.WorkersEntry.COLUMN_COMPANY + "` TEXT)";


        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_WORKERS_TABLE);

        // Строка для создания таблицы
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS `" + InfoSystemContract.UsersEntry.TABLE_NAME + "` (`"
                + InfoSystemContract.UsersEntry._ID + "` INTEGER PRIMARY KEY AUTOINCREMENT, `"
                + InfoSystemContract.UsersEntry.COLUMN_LOGIN + "` TEXT, `"
                + InfoSystemContract.UsersEntry.COLUMN_PASSWORD + "` TEXT, `"
                + InfoSystemContract.UsersEntry.COLUMN_PERMISSION + "` INTEGER)";


        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    /**
     * Вызывается при обновлении схемы базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w(LOG_TAG, "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF EXISTS " + InfoSystemContract.WorkersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InfoSystemContract.UsersEntry.TABLE_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }

    public int checkUserInDatabase(String login, String password)
    {
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT `" + InfoSystemContract.UsersEntry.COLUMN_PERMISSION
                + "` FROM " + InfoSystemContract.UsersEntry.TABLE_NAME
                + " WHERE (`" + InfoSystemContract.UsersEntry.COLUMN_LOGIN +"` = \'"
                + login + "\' AND `" + InfoSystemContract.UsersEntry.COLUMN_PASSWORD + "` = \'"
                + password + "\');", null);

        if (cursor.moveToFirst())
        {
            int perm = cursor.getInt(cursor.getColumnIndex(InfoSystemContract.UsersEntry.COLUMN_PERMISSION));
            cursor.close();
            return perm;
        }
        else
        {
            cursor.close();
            return 0;
        }
    }

    public void getUsersFromDatabase(UsersBase usersBase)
    {
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + InfoSystemContract.UsersEntry.TABLE_NAME + ";", null);

        if (cursor.moveToFirst())
        {
            usersBase.clearBase();
            int id = cursor.getColumnIndex(InfoSystemContract.UsersEntry._ID);
            int loginId = cursor.getColumnIndex(InfoSystemContract.UsersEntry.COLUMN_LOGIN);
            int passwordId = cursor.getColumnIndex(InfoSystemContract.UsersEntry.COLUMN_PASSWORD);
            int permissionId = cursor.getColumnIndex(InfoSystemContract.UsersEntry.COLUMN_PERMISSION);

            do {
                usersBase.addUser(new User(cursor.getInt(id),
                        cursor.getString(loginId),
                        cursor.getString(passwordId),
                        cursor.getInt(permissionId)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
    }

    public User addUserInDb(User user){

        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(InfoSystemContract.UsersEntry.COLUMN_LOGIN, user.getLogin());
        cv.put(InfoSystemContract.UsersEntry.COLUMN_PASSWORD, user.getPassword());
        cv.put(InfoSystemContract.UsersEntry.COLUMN_PERMISSION, String.valueOf(user.getPermission()));


        long id = db.insert(InfoSystemContract.UsersEntry.TABLE_NAME, null, cv);
        if (id > 0) {

            user.setId((int) id);
            return user;
        }
        else return null;
    }

    public boolean updateUserInDb(User user){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(InfoSystemContract.UsersEntry.COLUMN_LOGIN, user.getLogin());
        cv.put(InfoSystemContract.UsersEntry.COLUMN_PASSWORD, user.getPassword());
        cv.put(InfoSystemContract.UsersEntry.COLUMN_PERMISSION, String.valueOf(user.getPermission()));

        long id = db.update(InfoSystemContract.UsersEntry.TABLE_NAME, cv, InfoSystemContract.UsersEntry._ID + "=" + String.valueOf(user.getId()), null);
        return (id > 0);

    }

    public boolean deleteUserFromDb(User user){
        db = getWritableDatabase();
        long id = db.delete(InfoSystemContract.UsersEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(user.getId())});
        return (id > 0);
    }

    public void getWorkersFromDatabase(WorkersBase workersBase)
    {
        db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + InfoSystemContract.WorkersEntry.TABLE_NAME + ";", null);

        if (cursor.moveToFirst())
        {
            workersBase.clearBase();
            int id = cursor.getColumnIndex(InfoSystemContract.WorkersEntry._ID);
            int nameId = cursor.getColumnIndex(InfoSystemContract.WorkersEntry.COLUMN_NAME);
            int surnameId = cursor.getColumnIndex(InfoSystemContract.WorkersEntry.COLUMN_SURNAME);
            int patronymicId = cursor.getColumnIndex(InfoSystemContract.WorkersEntry.COLUMN_PATRONYMIC);
            int ageId = cursor.getColumnIndex(InfoSystemContract.WorkersEntry.COLUMN_AGE);
            int postId = cursor.getColumnIndex(InfoSystemContract.WorkersEntry.COLUMN_POST);
            int groupId = cursor.getColumnIndex(InfoSystemContract.WorkersEntry.COLUMN_GROUP);
            int companyId = cursor.getColumnIndex(InfoSystemContract.WorkersEntry.COLUMN_COMPANY);

            do {
                workersBase.addWorker(new Worker(cursor.getInt(id),
                        cursor.getString(nameId),
                        cursor.getString(surnameId),
                        cursor.getString(patronymicId),
                        cursor.getInt(ageId),
                        cursor.getString(postId),
                        cursor.getString(groupId),
                        cursor.getString(companyId)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
    }

    public Worker addWorkerInDb(Worker worker){

        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_NAME, worker.getName());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_SURNAME, worker.getSurname());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_PATRONYMIC, worker.getPatronymic());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_AGE, String.valueOf(worker.getAge()));
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_POST, worker.getPost());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_GROUP, worker.getGroup());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_COMPANY, worker.getCompany());

        long id = db.insert(InfoSystemContract.WorkersEntry.TABLE_NAME, null, cv);
        if (id > 0) {

            worker.setId((int) id);
            return worker;
        }
        else return null;
    }

    public boolean updateWorkerInDb(Worker worker){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_NAME, worker.getName());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_SURNAME, worker.getSurname());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_PATRONYMIC, worker.getPatronymic());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_AGE, worker.getAge());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_POST, worker.getPost());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_GROUP, worker.getGroup());
        cv.put(InfoSystemContract.WorkersEntry.COLUMN_COMPANY, worker.getCompany());

        long id = db.update(InfoSystemContract.WorkersEntry.TABLE_NAME, cv, InfoSystemContract.WorkersEntry._ID + "=" + String.valueOf(worker.getId()), null);
        return (id > 0);
    }

    public boolean deleteWorkerFromDb(Worker worker){
        db = getWritableDatabase();
        long id = db.delete(InfoSystemContract.WorkersEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(worker.getId())});
        return (id > 0);
    }

    public void closeDatabase()
    {
        db.close();
    }
}