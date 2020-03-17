package com.test.test2app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.test2app.BaseActivity;
import com.test.test2app.R;
import com.test.test2app.S;
import com.test.test2app.smsview.PhoneNumberDivider;
import com.test.test2app.smsview.PhoneNumberView;
import com.test.test2app.smsview.VerificationCodeView;
import com.zhaoyuntao.androidutils.tools.T;
import com.zhaoyuntao.androidutils.tools.ZThread;

public class MainActivity3_sms extends BaseActivity {

    ZThread zThread;
    Button button;
    Button buttonfocus;
    VerificationCodeView verificationCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3_sms);

        PhoneNumberView textView = findViewById(R.id.phonenumber);
        buttonfocus = findViewById(R.id.buttonfocus);
        S.s("1111111111111");

        button = findViewById(R.id.button);
        textView.setCountryCode("+971");
        textView.setPhoneNumber(PhoneNumberDivider.dividePhoneNumber("11028789217839273889789", "-", new int[]{1, 0, -1, 2, 4,6}));

        verificationCodeView = findViewById(R.id.verify);
        verificationCodeView.setOnCompletedListener(new VerificationCodeView.OnCompletedListener() {
            @Override
            public void onComplete(String content) {
                verificationCodeView.clearFocus();
                verificationCodeView.closeIntputMethod();
                T.t(activity(), "ok:" + content);
                button.setEnabled(true);
            }

            @Override
            public void onInput() {
                button.setEnabled(false);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificationCodeView.clearVerificationCode();
//                if(zThread!=null){
//                    zThread.close();
//                }
//                zThread=new ZThread(1) {
//                    int i=0;
//
//                    @Override
//                    protected void init() {
//                        S.s(verificationCodeView.getVerificationCode());
//                    }
//
//                    @Override
//                    protected void todo() {
//                        if(i++>3){
//                            S.s("ok");
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    close();
//                                }
//                            });
//                        }
//                    }
//                };
//                zThread.start();
            }
        });

        buttonfocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationCodeView.requestInputFocus();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}