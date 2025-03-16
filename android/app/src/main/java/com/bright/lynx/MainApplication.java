package com.bright.lynx;

import android.app.Application;
import android.util.Log;

import com.bright.lynx.modules.LynxModuleAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.memory.PoolConfig;
import com.facebook.imagepipeline.memory.PoolFactory;
import com.lynx.service.http.LynxHttpService;
import com.lynx.service.image.LynxImageService;
import com.lynx.service.log.LynxLogService;
import com.lynx.tasm.LynxEnv;
import com.lynx.tasm.service.LynxServiceCenter;
import com.tencent.mmkv.MMKV;

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        initMmkv();
        initLynxService();
        initLynxEnv();
        // register native module.
        installLynxJsModule();
    }

    private void initMmkv() {
        String rootDir = MMKV.initialize(this);
        Log.d(TAG, "initMmkv: mmkv root: " + rootDir);
    }

    private void initLynxService() {
        // init Fresco which is needed by LynxImageService
        final PoolFactory factory = new PoolFactory(PoolConfig.newBuilder().build());
        ImagePipelineConfig.Builder builder =
                ImagePipelineConfig.newBuilder(getApplicationContext()).setPoolFactory(factory);
        Fresco.initialize(getApplicationContext(), builder.build());

        LynxServiceCenter inst = LynxServiceCenter.inst();
        inst.registerService(LynxImageService.getInstance());
        inst.registerService(LynxLogService.INSTANCE);
        inst.registerService(LynxHttpService.INSTANCE);
    }

    private void initLynxEnv() {
        LynxEnv inst = LynxEnv.inst();
        inst.init(this, null, new AssetsTemplateProvider(this), null);
    }

    /**
     * merge it into InitProcessor later.
     */
    private void installLynxJsModule() {
        LynxModuleAdapter.getInstance().init(this);
    }
}
