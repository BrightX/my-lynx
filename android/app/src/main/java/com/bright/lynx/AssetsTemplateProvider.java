package com.bright.lynx;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lynx.tasm.provider.AbsTemplateProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsTemplateProvider extends AbsTemplateProvider {
    private final Context mContext;

    AssetsTemplateProvider(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void loadTemplate(@NonNull String uri, Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (InputStream inputStream = mContext.getAssets().open(uri);
                     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, length);
                    }
                    callback.onSuccess(byteArrayOutputStream.toByteArray());
                } catch (IOException e) {
                    callback.onFailed(e.getMessage());
                }
            }
        }).start();
    }
}
