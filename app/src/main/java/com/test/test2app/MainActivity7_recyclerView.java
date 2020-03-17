package com.test.test2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.test.test2app.recyclerview.CountryCodeAdapter;
import com.test.test2app.recyclerview.CountryCodeBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity7_recyclerView extends AppCompatActivity {
    List<CountryCodeBean> mList;
    CountryCodeAdapter mCountryCodeAdapter;
    RecyclerView mCountryCodeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity7_recycler_view);


        //country code
        mCountryCodeList = findViewById(R.id.country_code_list);
        mList = new ArrayList<>();

        Resources resources = getResources();
        if (resources == null) {
            return;
        }
        CountryCodeBean uae = new CountryCodeBean();
        uae.setCountryCode(971);
        uae.setDrawableId(R.drawable.country_ar);
        uae.setCountryName("UAE");
        mList.add(uae);
        CountryCodeBean china = new CountryCodeBean();
        china.setCountryCode(86);
        china.setDrawableId(R.drawable.china);
        china.setCountryName("China");
        mList.add(china);


        mCountryCodeAdapter = new CountryCodeAdapter(mList);
        mCountryCodeAdapter.setOnItemClickListener(new CountryCodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, CountryCodeBean countryCodeBean) {
                if (countryCodeBean == null) {
                }
            }
        });
        mCountryCodeList.setLayoutManager(new LinearLayoutManager(this));
        mCountryCodeList.setAdapter(mCountryCodeAdapter);


    }
}
