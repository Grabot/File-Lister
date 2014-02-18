package com.Android.games.List_Files;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivityAndroid extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        initialize(new Main(), cfg);
    }
}