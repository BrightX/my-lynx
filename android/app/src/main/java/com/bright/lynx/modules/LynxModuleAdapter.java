package com.bright.lynx.modules;

import android.annotation.SuppressLint;
import android.content.Context;

import com.lynx.tasm.LynxEnv;

public class LynxModuleAdapter {
    private Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static final LynxModuleAdapter sInstance = new LynxModuleAdapter();

    private LynxModuleAdapter() {
    }

    public static LynxModuleAdapter getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mContext = context;
        LynxEnv inst = LynxEnv.inst();
        inst.registerModule("NativeLocalStorageModule", NativeLocalStorageModule.class);
        inst.registerModule("NativeSessionStorageModule", NativeSessionStorageModule.class);
    }
}
