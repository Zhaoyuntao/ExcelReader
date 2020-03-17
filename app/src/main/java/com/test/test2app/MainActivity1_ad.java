package com.test.test2app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.zhaoyuntao.androidutils.component.ZButton;
import com.zhaoyuntao.androidutils.tools.T;


public class MainActivity1_ad extends BaseActivity {

    AdView adView;
    ZButton container;
    FrameLayout container_top;
    InterstitialAd mInterstitialAd;
    FrameLayout listview;
    FrameLayout listview2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listview = findViewById(R.id.listview);

        final ZTextView zTextView = findViewById(R.id.ztextview);
        final ZTextView zTextView2 = findViewById(R.id.ztextview2);


        final TextView textViewContent = new TextView(this);
        textViewContent.setTextSize(23);
        TextView textView = new TextView(this);
        textView.setText("12:01");
        textView.setTextSize(19);

        zTextView.setTailView(textView);
        zTextView.setContentView(textViewContent);

        final TextView textView2Content = new TextView(this);
        textView2Content.setTextSize(23);
        TextView textView2 = new TextView(this);
        textView2.setText("12:01");
        textView2.setTextSize(19);

        zTextView2.setTailView(textView2);
        zTextView2.setContentView(textView2Content);

        EditText editText = findViewById(R.id.edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textViewContent.setText(s.toString());
                textView2Content.setText(s.toString());
            }
        });


//        Emojicon[] emojicons = SelectEmojis.getData(getApplicationContext());


//       zButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        MobileAds.initialize(this, "ca-app-pub-7084074281104788/7205120728");
//        container = findViewById(R.id.sessionInfo2);
//        container_top = findViewById(R.id.container_top);
//        textView_name = findViewById(R.id.name);
//        final FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(0)
//                .build();
//        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//
//        //default
//        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
//
//        mFirebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        T.t(MainActivity1_ad.this, "" + task.isSuccessful());
//                        S.s("task:"+task.isSuccessful()+" " + task.toString() + " " + task.getResult());
//                        if (task.isSuccessful()) {
//                            boolean updated = task.getResult();
//                            if (updated) {
//                                S.s(mFirebaseRemoteConfig.getString("test1"));
//
//                            }
//                        } else {
//                            T.t(MainActivity1_ad.this, "" + task.getResult() + ":" + task.toString());
//                        }
//                        textView_name.setText(mFirebaseRemoteConfig.getString("test1"));
//                    }
//                });

//        Task<Void>fetch=mFirebaseRemoteConfig.fetch(0);
//        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                mFirebaseRemoteConfig.activate();
//                S.s("成功："+mFirebaseRemoteConfig.getString("test2"));
//                textView_name.setText(mFirebaseRemoteConfig.getString("test2"));
//            }
//        });


//        replaceFragment(R.id.sessionInfo, new CallFragment());
//
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                S.s("onAdLoaded");
//                mInterstitialAd.show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//                S.s("onAdFailedToLoad:" + errorCode);
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//                S.s("onAdOpened");
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//                S.s("onAdClicked");
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//                S.s("onAdLeftApplication");
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the interstitial ad is closed.
//                S.s("onAdClosed");
//            }
//        });
    }

    int size2 = 0;

    public void top(View v) {
        if (adView != null) {
            container_top.removeView(adView);
            adView.destroy();
        }
        S.s("size2:" + size2);
        adView = new AdView(v.getContext());
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        container_top.setVisibility(View.VISIBLE);

        switch (size2++) {
            case 0:
                adView.setAdSize(AdSize.BANNER);
                break;
            case 1:
                adView.setAdSize(AdSize.LARGE_BANNER);
                break;
            case 2:
                adView.setAdSize(AdSize.SMART_BANNER);
                break;
            case 3:
                adView.setAdSize(AdSize.FULL_BANNER);
                break;
            case 4:
                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                break;
            case 5:
                adView.setAdSize(AdSize.LEADERBOARD);
                break;
            default:
                size2 = 0;
                container_top.setVisibility(View.GONE);
                return;
        }
        container_top.addView(adView);
        adView.loadAd(new AdRequest.Builder().build());
    }

    int size = 0;

    public void center(View v) {
        if (adView != null) {
            container.removeView(adView);
            adView.destroy();
        }
        S.s("size:" + size);
        adView = new AdView(v.getContext());
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        container.setVisibility(View.VISIBLE);
        switch (size++) {
            case 0:
                adView.setAdSize(AdSize.BANNER);
                break;
            case 1:
                adView.setAdSize(AdSize.LARGE_BANNER);
                break;
            case 2:
                adView.setAdSize(AdSize.SMART_BANNER);
                break;
            case 3:
                adView.setAdSize(AdSize.FULL_BANNER);
                break;
            case 4:
                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                break;
            case 5:
                adView.setAdSize(AdSize.LEADERBOARD);
                break;
            default:
                size = 0;
                container.setVisibility(View.GONE);
                return;
        }
        container.addView(adView);
        adView.loadAd(new AdRequest.Builder().build());
    }

    public void page(View view) {
        if (!mInterstitialAd.isLoading()) {
            if (!mInterstitialAd.isLoaded()) {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                T.t(this, "开始加载页面");
            }
        } else {
            T.t(this, "正在加载中，请稍等");
        }
    }

    void replaceFragment(final int resId, Fragment fragment) {
        if (isFinishing()) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(resId, fragment);

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commitAllowingStateLoss();
    }

    public void close(View view) {
        finish();
    }


}
