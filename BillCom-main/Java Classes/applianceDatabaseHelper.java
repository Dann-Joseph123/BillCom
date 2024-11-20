package com.secondapplication.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

    public class applianceDatabaseHelper extends SQLiteOpenHelper {

        //static variables to avoid errors since we'd be using them more than once.
        //region staticVariables
        public static final String APPLIANCE_TABLE = "APPLIANCE_TABLE";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_APPLIANCE_NAME = "APPLIANCE_NAME";
        public static final String COLUMN_ELECTRIC_CAPACITY = "ELECTRIC_CAPACITY";
        public static final String COLUMN_HOURS_USED = "HOURS_USED";
        public static final String COLUMN_DAYS_USED = "DAYS_USED";
        public static final String COLUMN_KWHPERDAY = "KWH_PER_DAY";    //energy per day
        public static final String COLUMN_ELECTRICITY_COST_PERDAY = "ELECTRICITY_COST_PERDAY";  //energy cost per day
        public static final String COLUMN_ELECTRICITY_COST_PERWEEK = "ELECTRICITY_COST_PERWEEK";    //energy cost per week

        //endregion
        public applianceDatabaseHelper(@Nullable Context context) {
            super(context, "appliance.db", null, 1);
        }

        //first time called the first time a database is accessed
        @Override
        public void onCreate(SQLiteDatabase db) {

            //creates this string statement so that you don't have to type this exact statement completely.
            //region createTableStatement
            String createTableStatement = "CREATE TABLE " + APPLIANCE_TABLE +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_APPLIANCE_NAME + " TEXT, " +
                    COLUMN_ELECTRIC_CAPACITY + " FLOAT, " +
                    COLUMN_HOURS_USED + " FLOAT, " +
                    COLUMN_DAYS_USED + " FLOAT, " +
                    COLUMN_KWHPERDAY + " FLOAT, " +
                    COLUMN_ELECTRICITY_COST_PERDAY + " FLOAT, " +
                    COLUMN_ELECTRICITY_COST_PERWEEK + " FLOAT)";
            //endregion

            db.execSQL(createTableStatement);
        }

        //called if the db version number changed.
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }


        public boolean addOne(Appliance appliance){
            SQLiteDatabase db = this.getWritableDatabase();     //gets a writable db
            ContentValues cv = new ContentValues();             //content values acts like a hashmap

            cv.put(COLUMN_APPLIANCE_NAME, appliance.getApplianceName());
            cv.put(COLUMN_ELECTRIC_CAPACITY, appliance.getElectricCapacity());
            cv.put(COLUMN_HOURS_USED, appliance.getHoursUsed());
            cv.put(COLUMN_DAYS_USED, appliance.getDaysUsed());
            cv.put(COLUMN_KWHPERDAY, appliance.energyConsumptionCalculationperDay()); //energy per day
            cv.put(COLUMN_ELECTRICITY_COST_PERDAY, appliance.electricityCostCalculationperDay());   //energy cost per day
            cv.put(COLUMN_ELECTRICITY_COST_PERWEEK, appliance.electricityCostCalculationWeek());    //energy cost per week

            long insert = db.insert(APPLIANCE_TABLE, null, cv);

            if (insert == -1) {
                return false;
            }else {
                return true;
            }
        }
        public ArrayList<Appliance> getAllAppliances(){ //method to get a list of all appliance objects from appliance.db

            ArrayList<Appliance> returnList = new ArrayList<>();
            String queryString = "SELECT * FROM " + APPLIANCE_TABLE;    //get data from appliance.db
            SQLiteDatabase db = this.getReadableDatabase(); //gets a read only database
            Cursor cursor = db.rawQuery(queryString, null);

            if(cursor.moveToFirst()){
                do{
                    int applianceID= cursor.getInt(0);
                    String applianceName = cursor.getString(1);
                    float applianceElectricityConsumption = cursor.getFloat(2);
                    float applianceHoursUsed = cursor.getFloat(3);
                    float applianceDaysUsed = cursor.getFloat(4);

                    Appliance newAppliance = new Appliance(applianceID, applianceName, applianceElectricityConsumption, applianceHoursUsed, applianceDaysUsed);
                    returnList.add(newAppliance);
                }while(cursor.moveToNext());

            }else{
                //Do nothing
            }

            //close cursor and db
            cursor.close();
            db.close();
            return returnList;
        }
        public boolean deleteOne(Appliance appliance){
            //find appliance in appliance.db, if found, return true. else false.

            SQLiteDatabase db = this.getWritableDatabase();
            String queryString = "DELETE FROM " + APPLIANCE_TABLE + " WHERE " + COLUMN_ID + " = " + appliance.getId();

            Cursor cursor = db.rawQuery(queryString, null);
            if (cursor.moveToFirst()){
                return true;
            }else{
                return false;
            }
        }

        public boolean updateAppliance(Appliance applianceToUpdate){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            String idToEdit = String.valueOf(applianceToUpdate.getId());

            cv.put(COLUMN_APPLIANCE_NAME, applianceToUpdate.getApplianceName());
            cv.put(COLUMN_ELECTRIC_CAPACITY, applianceToUpdate.getElectricCapacity());
            cv.put(COLUMN_HOURS_USED, applianceToUpdate.getHoursUsed());
            cv.put(COLUMN_DAYS_USED, applianceToUpdate.getDaysUsed());
            cv.put(COLUMN_KWHPERDAY, applianceToUpdate.energyConsumptionCalculationperDay()); //energy per day
            cv.put(COLUMN_ELECTRICITY_COST_PERDAY, applianceToUpdate.electricityCostCalculationperDay());   //energy cost per day
            cv.put(COLUMN_ELECTRICITY_COST_PERWEEK, applianceToUpdate.electricityCostCalculationWeek());    //energy cost per week
            Cursor cursor = db.rawQuery("Select * from " + APPLIANCE_TABLE + " where " + COLUMN_ID + " = ?", new String[]{idToEdit});

            if(cursor.getCount()>0){
                long result = db.update(APPLIANCE_TABLE, cv, COLUMN_ID + "=?", new String[]{idToEdit});
                if(result == -1){
                    return false;
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }
    }
