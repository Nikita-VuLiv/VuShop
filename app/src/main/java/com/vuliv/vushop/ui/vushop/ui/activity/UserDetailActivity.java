package com.vuliv.vushop.ui.vushop.ui.activity;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vuliv.vushop.R;
import com.vuliv.vushop.ui.vushop.entities.EntityOtpResponse;
import com.vuliv.vushop.ui.vushop.ui.callbacks.IUniversalCallback;
import com.vuliv.vushop.ui.vushop.ui.controllers.OtpRequestController;
import com.vuliv.vushop.ui.vushop.utils.AppUtils;

/**
 * Created by MB0000004 on 08-Jan-18.
 */

public class UserDetailActivity extends AppCompatActivity {
    ImageView ivProceed;
    private String msisdn;
    private EditText etPhoneNumber;
    private EditText etOtp;
    private RelativeLayout rootMobile;
    private LinearLayout rooOtp;
    private TextView mobileNumber;
    private OtpRequestController otpRequestController;
    private boolean receiverRegistered;
    private Button btnOtpVerify;
    private String otp;
    private ImageView ivEditNumber;
    private TextView resentOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        ivProceed = (ImageView) findViewById(R.id.IvTick);
        rooOtp = (LinearLayout) findViewById(R.id.ll_root_otp);
        rootMobile = (RelativeLayout) findViewById(R.id.rl_root_mobile);
        mobileNumber = (TextView) findViewById(R.id.tv_mobile_number);
        etPhoneNumber = (EditText) findViewById(R.id.input_phoneNo);
        etOtp = (EditText) findViewById(R.id.etOTP);
        ivEditNumber = (ImageView)findViewById(R.id.ivEditNumber);
        btnOtpVerify = (Button) findViewById(R.id.btnOtpVerify);
        resentOtp = (TextView) findViewById(R.id.tv_resend_code);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        otpRequestController = new OtpRequestController(this);

        ivProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msisdnAutoProcess();
            }
        });
        btnOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = etOtp.getText().toString().trim();
                otpLocalVerification(enteredOtp);
            }
        });

        ivEditNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rooOtp.setVisibility(View.GONE);
                rootMobile.setVisibility(View.VISIBLE);
                etOtp.setText("");
            }
        });

        resentOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msisdn !=null) {
                    otpLocalRequest(msisdn, true);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void msisdnAutoProcess() {
        msisdn = etPhoneNumber.getText().toString().trim();
        if (isEmpty(msisdn)) {
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isPhoneNumberValid(msisdn)) {
            Toast.makeText(this, "Please Add Correct Number", Toast.LENGTH_SHORT).show();
            return;
        }
        otpLocalRequest(msisdn, true);
    }


    public static boolean isEmpty(String text) {
        boolean isEmpty = true;
        if (text != null && text.trim().length() > 0) {
            isEmpty = false;
        }
        return isEmpty;
    }

    public static boolean isPhoneNumberValid(String number) {
        boolean isValid = false;
        if (!isEmpty(number) && number.length() == 10) {
            if (!number.matches("[a-zA-Z]+")) {
                isValid = true;
            }
        }
        return isValid;
    }

    private void otpLocalRequest(final String msisdn, boolean resend) {
        otpRequestController.otpRequest(new IUniversalCallback<EntityOtpResponse, String>() {
                                            @Override
                                            public void onPreExecute() {
                                                AppUtils.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        // progressBar.show();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onSuccess(final EntityOtpResponse object) {
                                                AppUtils.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        // progressBar.dismiss();
                                                        rootMobile.setVisibility(View.GONE);
                                                        rooOtp.setVisibility(View.VISIBLE);
                                                        mobileNumber.setText(msisdn);
                                                        //YoYo.with(Techniques.FadeIn).duration(500).playOn(rooOtp);
                                                        etPhoneNumber.setText("");
                                                        otp = object.getOtp();
                                                      /*otpDialog.show();
                                                      otpDialog.setOtpText("");
                                                      */
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(final String msg) {
                                                AppUtils.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        // progressBar.dismiss();
                                                        if (msg != null) {
                                                            //new CustomToast(context, msg).showToastCenter();
                                                        } else {
                                                            //new CustomToast(context, getResources().getString(R.string.internet_error)).showToastCenter();
                                                        }
                                                    }
                                                });
                                            }
                                        }, msisdn, resend, false, true
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        //registerReceiver();
    }

    private void registerReceiver() {
        unregisterReceiver();
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction("AutoFillSms");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentToReceiveFilter);
        receiverRegistered = true;
    }

    private void unregisterReceiver() {
        if (receiverRegistered) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
            receiverRegistered = false;
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("AutoFillSms")) {
                String code = intent.getStringExtra("AutoFillSms");
                try {
                    if (rooOtp.getVisibility() == View.VISIBLE) {
                        etPhoneNumber.setText(code);
                        Toast.makeText(context, "Enter OTP & Click proceed to complete!", Toast.LENGTH_SHORT).show();
                        //otpVerification();
                    }
                    /*
                    if (otpDialog.isShowing()) {
                        otpDialog.setOtpText(code);
                        otpVerification();
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private void otpLocalVerification(String enteredOtp) {
        if (!isEmpty(enteredOtp)) {
            otp = enteredOtp;
            Intent i = new Intent(UserDetailActivity.this, OrderPlacedActivity.class);
            startActivity(i);
        }
    }
}

