package com.vuliv.vushop.ui.vushop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vuliv.vushop.R;
import com.vuliv.vushop.ui.vushop.adapters.CheckoutAdapter;
import com.vuliv.vushop.ui.vushop.entities.EntityDominos;

import java.util.ArrayList;

/**
 * Created by MB0000004 on 05-Jan-18.
 */

public class CheckoutActivity extends AppCompatActivity {

    ArrayList<EntityDominos> models=new ArrayList<>();
    ListView listView;
    TextView totalPrice;
    private CheckoutAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Checkout");
//        setSupportActionBar(toolbar);
        models = (ArrayList<EntityDominos>) getIntent().getSerializableExtra("entityDominos");
        listView=(ListView)findViewById(R.id.listview);
        totalPrice = (TextView)findViewById(R.id.totalAmount);
        if(models!=null)
        {
            adapter= new CheckoutAdapter(models,this);
            listView.setAdapter(adapter);
            float total = 0;
            for (EntityDominos model:models) {
                total = total + model.getPrice()*model.getQuantity();
            }
            TextView totalTxt = (TextView)findViewById(R.id.totalAmount);
            TextView totalTxt1 = (TextView)findViewById(R.id.totalAmount1);
            totalTxt.setText("Total - Rs "+ total);
            totalTxt1.setText("Rs "+ total);
        }
        Button proceedBtn = (Button)findViewById(R.id.btnProceed);
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckoutActivity.this, UserDetailActivity.class);
                startActivity(i);
            }
    });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
