package com.example.database;

import android.content.Context;
import android.database.Cursor;

public class LoginData {


    public static boolean destroy(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            dataBase.queryData("DROP TABLE IF EXISTS Login");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static boolean create(Context context, String pass, String email ) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
//            String passWord = Cryptography.encrypt(pass);
            dataBase.queryData("DROP TABLE IF EXISTS Login");
            dataBase.queryData("CREATE TABLE " +
                    "Login(" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "PassWord VARCHAR(8192)," +
                    "Email VARCHAR(200)" +
                    ")");
            dataBase.queryData("INSERT INTO Login VALUES(null, '" + pass + "', '" + email + "')");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String Email(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Login");
            if (data.moveToNext()) {
                return data.getString(2);
            }
            throw new Exception("MISSING FULLNAME DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getPassWord(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Login");
            if (data.moveToNext()) {
//                return Cryptography.decrypt(data.getString(1));
                return data.getString(1);
            }
            throw new Exception("MISSING PASSWORD");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

}
