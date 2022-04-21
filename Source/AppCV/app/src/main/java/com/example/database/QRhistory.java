package com.example.database;

import android.content.Context;
import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QRhistory {


    public static boolean destroy(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            dataBase.queryData("DROP TABLE IF EXISTS QRhistory");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static boolean create(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            dataBase.queryData("DROP TABLE IF EXISTS QRhistory");
            dataBase.queryData("CREATE TABLE " +
                    "QRhistory(" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Time VARCHAR(200)," +
                    "QRcodeScan VARCHAR(200)" +
                    ")");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static boolean insert(Context context, String time, String qrcodeScan) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            dataBase.queryData("INSERT INTO QRhistory VALUES(null, '" + time +
                    "', '" + qrcodeScan +
                    "')");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String time(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM QRhistory");
            if (data.moveToNext()) {
                return data.getString(1);
            }
            throw new Exception("MISSING TIME DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String qrcodeScan(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM QRhistory");
            if (data.moveToNext()) {
                return data.getString(2);
            }
            throw new Exception("MISSING QR DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }


    public static List<String> getAllTime(Context context) {
        List<String> list = new ArrayList<String>();
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM QRhistory");

            while (!data.isAfterLast()) {
                list.add(data.getString(1));
                data.moveToNext();
            }
            if (list.size() > 0) {
                return list;
            }
            throw new Exception("MISSING time DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static List<String> getAllQR(Context context) {
        List<String> list = new ArrayList<String>();
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM QRhistory");
            if (data.moveToFirst()) {
                while (!data.isAfterLast()) {
                    list.add(data.getString(2));
                    data.moveToNext();
                }
            }
//            if (data.moveToFirst()) {
//                while (!data.isAfterLast()) {
//                    list.add(data.getString(2));
//
//                }
//            }
            if (list.size() > 0) {
                return list;
            }
            throw new Exception("MISSING QR DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    private static boolean updateSetting(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            String setting = null;
            Cursor settings = dataBase.getData("SELECT * FROM QRhistory");
//            if (settings.moveToNext()) {
//                setting = Cryptography.decrypt(settings.getString(1));
//            }
            if (setting == null) {
                return false;
            }
//            JSONObject jsonObj = new JSONObject(setting);
//            setting = Cryptography.encrypt(update.getUpdate(jsonObj).toString());

            dataBase.queryData("UPDATE Settings SET Setting='" + setting + "';");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

}
