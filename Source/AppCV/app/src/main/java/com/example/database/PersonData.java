package com.example.database;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

public class PersonData {


    public static boolean destroy(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            dataBase.queryData("DROP TABLE IF EXISTS Person");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static boolean create(Context context,
                                 String fullName,
                                 String CMND,
                                 String BHYT,
                                 String NgaySinh,
                                 String GioiTinh,
                                 String QuocTich,
                                 String DiaChi,
                                 String SDT,
                                 String Email
    ) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            dataBase.queryData("DROP TABLE IF EXISTS Person");
            dataBase.queryData("CREATE TABLE " +
                    "Person(" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "FullName VARCHAR(200)," +
                    "CMND VARCHAR(200)," +
                    "BHYT VARCHAR(200)," +
                    "NgaySinh VARCHAR(200)," + //định dạng DDMMYYYY
                    "GioiTinh VARCHAR(200)," +
                    "QuocTich VARCHAR(200)," +
                    "DiaChi VARCHAR(200)," +
                    "SDT VARCHAR(200)," +
                    "Email VARCHAR(200)" +
                    ")");
            dataBase.queryData("INSERT INTO Person VALUES(null, '" + fullName +
                    "', '" + CMND +
                    "', '" + BHYT +
                    "', '" + NgaySinh +
                    "', '" + GioiTinh +
                    "', '" + QuocTich +
                    "', '" + DiaChi +
                    "', '" + SDT +
                    "', '" + Email +
                    "')");
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getFullName(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(1);
            }
            throw new Exception("MISSING FULLNAME DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getCMND(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(2);
            }
            throw new Exception("MISSING CMND DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getBHYT(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(3);
            }
            throw new Exception("MISSING BHYT DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getNgaySinh(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(4);
            }
            throw new Exception("MISSING NgaySinh DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getGioiTinh(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(5);
            }
            throw new Exception("MISSING GioiTinh DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getQuocTich(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(6);
            }
            throw new Exception("MISSING QuocTich DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getDiaChi(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(7);
            }
            throw new Exception("MISSING DiaChi DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getSDT(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(8);
            }
            throw new Exception("MISSING SDT DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

    public static String getEmail(Context context) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(context, "applicationcv.sqlite", null, 1);
            Cursor data = dataBase.getData("SELECT * FROM Person");
            if (data.moveToNext()) {
                return data.getString(9);
            }
            throw new Exception("MISSING Email DATA");
        } catch (Exception ex) {
            return null;
        } finally {
            if (dataBase != null)
                dataBase.close();
        }
    }

}
