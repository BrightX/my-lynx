package com.bright.lynx.modules;

import android.content.Context;
import android.util.Log;

import com.lynx.jsbridge.LynxMethod;
import com.lynx.jsbridge.LynxModule;
import com.lynx.react.bridge.JavaOnlyArray;
import com.lynx.react.bridge.WritableArray;
import com.tencent.mmkv.MMKV;

import java.util.Arrays;

public class NativeLocalStorageModule extends LynxModule {
    private static final String TAG = "mmkvLocalStorage";
    private static final String PREF_NAME = "LocalStorage";
    private final MMKV mmkv;

    public NativeLocalStorageModule(Context context) {
        super(context);
        mmkv = MMKV.mmkvWithID(PREF_NAME, MMKV.MULTI_PROCESS_MODE);
    }

    @LynxMethod
    public void setItem(String key, String value) {
        mmkv.putString(key, value);
        Log.d(TAG, "setItem: key = " + key + ", value = " + value);
    }

    @LynxMethod
    public String getItem(String key) {
        Log.d(TAG, "getItem: key = " + key);
        return mmkv.getString(key, null);
    }

    @LynxMethod
    public boolean hasItem(String key) {
        Log.d(TAG, "hasItem: key = " + key);
        return mmkv.containsKey(key);
    }

    @LynxMethod
    public WritableArray keys() {
        Log.d(TAG, "keys()");
        String[] allKeys = mmkv.allKeys();
        JavaOnlyArray array = new JavaOnlyArray();
        if (allKeys != null && allKeys.length > 0) {
            array.addAll(Arrays.asList(allKeys));
        }
        return array;
    }

    @LynxMethod
    public void removeItem(String key) {
        Log.d(TAG, "removeItem: key = " + key);
        mmkv.removeValueForKey(key);
    }

    @LynxMethod
    public void clear() {
        Log.d(TAG, "clear()");
        mmkv.clearAll();
    }

    @Override
    public void destroy() {
        super.destroy();
        Log.d(TAG, "destroy() - mmkv.async()");
        mmkv.async();
    }
}
