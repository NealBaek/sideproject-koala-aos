package com.ksdigtalnomad.koala.helpers.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ksdigtalnomad.koala.BuildConfig;
import com.ksdigtalnomad.koala.ui.base.BaseApplication;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("unchecked")
public class PreferenceGenericHelper<T extends Serializable> {

    private static PreferenceGenericHelper instance = null;
    private PreferenceGenericHelper(){}
    public static PreferenceGenericHelper getInstance(){
        if(instance == null){
            instance = new PreferenceGenericHelper();
        }
        return instance;
    }


    private SharedPreferences.Editor getEditPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences(BuildConfig.PREF_FILE_NAME, Activity.MODE_PRIVATE);
        return pref.edit();
    }
    private SharedPreferences getReadPreference() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        return context.getSharedPreferences(BuildConfig.PREF_FILE_NAME, Activity.MODE_PRIVATE);
    }



    public enum Key {
        design_calendar
    }

    /** SetValue */
    public void setValue(Key key, T value) {
        if(value instanceof String){
            getEditPreference().putString(key.name(), ((String) value) ).apply();
        }else if(value instanceof Integer){
            getEditPreference().putInt(key.name(), ((Integer) value)).apply();
        }else if(value instanceof Float){
            getEditPreference().putFloat(key.name(), ((Float) value)).apply();
        }else if(value instanceof Boolean){
            getEditPreference().putBoolean(key.name(), ((Boolean) value)).apply();
        }else if(value instanceof Long){
            getEditPreference().putLong(key.name(), ((Long) value)).apply();
        }else if(value instanceof Set){
            getEditPreference().putStringSet(key.name(), ((Set<String>) value)).apply();
        }else{
            FBEventLogHelper.onError(new Exception("No such type"));
            Log.d("ABC", "Error: No value Type");
        }
    }
    public void setValue(String key, T value) {
        if(value instanceof String){
            getEditPreference().putString(key, ((String) value)).apply();
        }else if(value instanceof Integer){
            getEditPreference().putInt(key, ((Integer) value)).apply();
        }else if(value instanceof Float){
            getEditPreference().putFloat(key, ((Float) value)).apply();
        }else if(value instanceof Boolean){
            getEditPreference().putBoolean(key, ((Boolean) value)).apply();
        }else if(value instanceof Long){
            getEditPreference().putLong(key, ((Long) value)).apply();
        }else if(value instanceof Set){
            getEditPreference().putStringSet(key, ((Set<String>) value)).apply();
        }else{
            FBEventLogHelper.onError(new Exception("No such type"));
            Log.d("ABC", "Error: No value Type");
        }
    }


    /** GetValue */
    public T getValue(Key key, T defalutValue) {
        try{
            if(defalutValue instanceof String){
                return (T) (Object) getReadPreference().getString(key.name(), ((String)defalutValue));
            }else if(defalutValue instanceof Integer){
                return (T) (Object) getReadPreference().getInt(key.name(), ((Integer)defalutValue));
            }else if(defalutValue instanceof Float){
                return (T) (Object) getReadPreference().getFloat(key.name(), ((Float)defalutValue));
            }else if(defalutValue instanceof Boolean){
                return (T) (Object) getReadPreference().getBoolean(key.name(), ((Boolean)defalutValue));
            }else if(defalutValue instanceof Long){
                return (T) (Object) getReadPreference().getLong(key.name(), ((Long)defalutValue));
            }else if(defalutValue instanceof Set){
                return (T) getReadPreference().getStringSet(key.name(), ((Set)defalutValue));
            }else{
                throw new Exception("No such type");
            }
        }catch (Exception e){
            FBEventLogHelper.onError(e);
            return defalutValue;
        }
    }

    public T getValue(String key, T defalutValue) {
        try{
            if(defalutValue instanceof String){
                return (T) getReadPreference().getString(key, ((String)defalutValue));
            }else if(defalutValue instanceof Integer){
                return (T) ((Object) getReadPreference().getInt(key, ((Integer)defalutValue)));
            }else if(defalutValue instanceof Float){
                return (T) ((Object) getReadPreference().getFloat(key, ((Float)defalutValue)));
            }else if(defalutValue instanceof Boolean){
                return (T) ((Object) getReadPreference().getBoolean(key, ((Boolean)defalutValue)));
            }else if(defalutValue instanceof Long){
                return (T) ((Object) getReadPreference().getLong(key, ((Long)defalutValue)));
            }else if(defalutValue instanceof Set){
                return (T) getReadPreference().getStringSet(key, ((Set)defalutValue));
            }else{
                throw new Exception("No such type");
            }
        }catch (Exception e){
            FBEventLogHelper.onError(e);
            return defalutValue;
        }
    }
}
