package com.bright.lynx.modules;

import android.content.Context;
import android.util.Log;

import com.lynx.jsbridge.LynxMethod;
import com.lynx.jsbridge.LynxModule;
import com.lynx.react.bridge.JavaOnlyArray;
import com.lynx.react.bridge.WritableArray;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NativeSessionStorageModule extends LynxModule {
    private static final String TAG = "SessionStorage";
    private static final Map<String, String> MAP = new ConcurrentHashMap<>();

    public NativeSessionStorageModule(Context context) {
        super(context);
    }

    @LynxMethod
    public void setItem(String key, String value) {
        MAP.put(key, value);
        Log.d(TAG, "setItem: key = " + key + "value = " + value);
    }

    @LynxMethod
    public String getItem(String key) {
        Log.d(TAG, "getItem: key = " + key);
        return MAP.get(key);
    }

    @LynxMethod
    public boolean hasItem(String key) {
        Log.d(TAG, "hasItem: key = " + key);
        return MAP.containsKey(key);
    }

    @LynxMethod
    public WritableArray keys() {
        Log.d(TAG, "keys()");
        String[] allKeys = MAP.keySet().toArray(new String[0]);
        JavaOnlyArray array = new JavaOnlyArray();
        if (allKeys.length > 0) {
            array.addAll(Arrays.asList(allKeys));
        }
        return array;
    }

    @LynxMethod
    public void removeItem(String key) {
        Log.d(TAG, "removeItem: key = " + key);
        MAP.remove(key);
    }

    @LynxMethod
    public void clear() {
        Log.d(TAG, "clear()");
        MAP.clear();
    }
}
