package com.vuliv.vushop.ui.vushop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuliv.vushop.R;
import com.vuliv.vushop.ui.vushop.adapters.CustomAdapter;
import com.vuliv.vushop.ui.vushop.entities.EntityDominos;
import com.vuliv.vushop.ui.vushop.ui.callbacks.IQuantityChangeCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IQuantityChangeCallback{

    ArrayList<EntityDominos> models=new ArrayList<>();
    ListView listView;
    TextView totalPrice;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        EntityDominos m = new EntityDominos();
        m.setId("1");
        m.setCrust("Hand Tossed");
        m.setDescription("sample descr");
        m.setGst_price(123);
        m.setPrice(555);
        m.setImage("pizza1");
        m.setMax_quantity(5);
        m.setName("Cheese and pepperoni");
        m.setVeg(false);
        models.add(m);

        m = new EntityDominos();
        m.setId("2");
        m.setCrust("Oregano base");
        m.setDescription("descr2");
        m.setGst_price(123);
        m.setPrice(430);
        m.setMax_quantity(3);
        m.setImage("pizza2");
        m.setName("Panner veg");
        m.setVeg(true);
        models.add(m);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView=(ListView)findViewById(R.id.listview);
        totalPrice = (TextView)findViewById(R.id.totalAmount);
        adapter= new CustomAdapter(models,this, this);
        listView.setAdapter(adapter);
        setSupportActionBar(toolbar);

        Button proceedBtn = (Button)findViewById(R.id.btnProceed);
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<EntityDominos> selectedItems = new ArrayList<EntityDominos>();
                for (EntityDominos entity:models) {
                    if(entity.getQuantity()!=null && entity.getQuantity()>0){
                        selectedItems.add(entity);
                    }
                }
                if(selectedItems.size()>0) {
                    Intent i = new Intent(MainActivity.this, CheckoutActivity.class);
                    i.putExtra("entityDominos", selectedItems);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "No item selected",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onQuantityChanged() {
        TextView txtTotal = (TextView) findViewById(R.id.totalAmount);
        float total = 0;
        for (EntityDominos model:models) {
            if(model.getQuantity()!=null && model.getQuantity()>0) {
                total = total + model.getPrice() * model.getQuantity();
            }
        }
        if(total>0){
            txtTotal.setText("Total - Rs "+total);
        }
        else{
            txtTotal.setText("Total -");
        }
    }
}
