package com.test.test2app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.test.test2app.R;
import com.test.test2app.recyclerview.CountryCodeAdapter;
import com.test.test2app.recyclerview.CountryCodeBean;
import com.zhaoyuntao.androidutils.tools.S;
import com.zhaoyuntao.androidutils.tools.thread.TP;
import com.zhaoyuntao.androidutils.tools.thread.ZRunnable;

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
                Intent intent = new Intent(MainActivity7_recyclerView.this, MainActivity1_ad.class);
                startActivity(intent);
                TP.runOnUiDelayedSafely(new ZRunnable(MainActivity7_recyclerView.this) {
                    @Override
                    protected void todo() {
                        S.s("start refresh");
                        mCountryCodeAdapter.notifyItemChanged(0,new Object());
                        mCountryCodeAdapter.notifyItemChanged(0,new Object());
                    }
                }, 1000);
            }
        });
        mCountryCodeList.setLayoutManager(new LinearLayoutManager(this));
        mCountryCodeList.setAdapter(mCountryCodeAdapter);

        EditText editText=findViewById(R.id.edittext);
        final TextView textView1=findViewById(R.id.textview1);
        TextView textView2=findViewById(R.id.textview2);
        textView2.setText("2020-11-12");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView1.setText(s);
            }
        });

    }
}
