package com.bright.lynx;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lynx.tasm.LynxView;
import com.lynx.tasm.LynxViewBuilder;

public class MainActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LynxView lynxView = buildLynxView();
        setContentView(lynxView);

        String url = "main.lynx.bundle";
        lynxView.renderTemplateUrl(url, "");
    }

    private LynxView buildLynxView() {
        LynxViewBuilder viewBuilder = new LynxViewBuilder();
        viewBuilder.setTemplateProvider(new AssetsTemplateProvider(this));
        return viewBuilder.build(this);
    }
}
