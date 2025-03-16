package com.bright.lynx.modules;

import android.content.Context;

import com.lynx.jsbridge.LynxMethod;
import com.lynx.jsbridge.LynxModule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NativeSessionStorageModule extends LynxModule {
    private static final Map<String, String> MAP = new ConcurrentHashMap<>();

    public NativeSessionStorageModule(Context context) {
        super(context);
    }

    @LynxMethod
    public void setItem(String key, String value) {
        MAP.put(key, value);
    }

    @LynxMethod
    public String getItem(String key) {
        return MAP.get(key);
    }

    @LynxMethod
    public boolean hasItem(String key) {
        return MAP.containsKey(key);
    }

    @LynxMethod
    public String[] keys() {
        return MAP.keySet().toArray(new String[0]);
    }

    @LynxMethod
    public void removeItem(String key) {
        MAP.remove(key);
    }

    @LynxMethod
    public void clear() {
        MAP.clear();
    }
}
