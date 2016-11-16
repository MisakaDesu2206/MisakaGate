package com.mahoucoder.misakagate;

import android.app.Application;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.lang.ref.WeakReference;

/**
 * Created by jamesji on 28/10/2016.
 */

public class GateApplication extends Application {
    private static WeakReference<Application> appRef;
    protected String userAgent;

    @Override
    public void onCreate() {
        super.onCreate();
        appRef = new WeakReference<Application>(GateApplication.this);

        userAgent = Util.getUserAgent(this, getClass().getSimpleName());
        FlowManager.init(new FlowConfig.Builder(GateApplication.this).build());
    }

    public static Application getGlobalContext() {
        return appRef.get();
    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(this, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }
}
