package com.vuliv.vushop.ui.vushop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vuliv.vushop.R;
import com.vuliv.vushop.ui.vushop.entities.EntityDominos;
import com.vuliv.vushop.ui.vushop.ui.callbacks.IQuantityChangeCallback;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<EntityDominos> implements View.OnClickListener{

    private ArrayList<EntityDominos> dataSet;
    Context mContext;
    private IQuantityChangeCallback quantityChangeCallback;
    // View lookup cache
    private static class ViewHolder {
        ImageView vegIcon;
        TextView txtPizzaName;
        TextView txtPrice;
        ImageView pizzaImage;
        ImageView tickImage;
        TextView txtCrustName;
        FrameLayout btnSmallSize;
        FrameLayout btnMediumSize;
        FrameLayout btnLargeSize;
        Button btnMinus;
        TextView txtQty;
        Button btnPlus;
    }

    public CustomAdapter(ArrayList<EntityDominos> data, Context context, IQuantityChangeCallback pQuantityChangeCallback) {
        super(context, R.layout.list_item, data);
        this.dataSet = data;
        this.mContext=context;
        this.quantityChangeCallback = pQuantityChangeCallback;

    }

    @Override
    public void onClick(View v) {
        if(v.getTag()==null){
            return;
        }
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        EntityDominos model=(EntityDominos)object;

        switch (v.getId())
        {
            case R.id.btnSmall:
                model.setSize("S");
                notifyDataSetChanged();
                break;
            case R.id.btnMedium:
                model.setSize("M");
                notifyDataSetChanged();
                break;
            case R.id.btnLarge:
                model.setSize("L");
                notifyDataSetChanged();
                break;
            case R.id.btnPlus:
                int qty = model.getQuantity();
                if(model.getQuantity()==model.getMax_quantity()){
                    Toast.makeText(mContext,"Max quantity reached",Toast.LENGTH_SHORT).show();
                }else {
                    model.setQuantity(++qty);
                    notifyDataSetChanged();
                    if(quantityChangeCallback !=null){
                        quantityChangeCallback.onQuantityChanged();
                    }
                }
                break;
            case R.id.btnMinus:
                qty = model.getQuantity();
                if(qty!=0){
                    model.setQuantity(--qty);
                    notifyDataSetChanged();
                    if(quantityChangeCallback !=null){
                        quantityChangeCallback.onQuantityChanged();
                    }
                }
                break;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        EntityDominos model = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.vegIcon = (ImageView) convertView.findViewById(R.id.veg_icon);
            viewHolder.txtPizzaName = (TextView) convertView.findViewById(R.id.pizza_text);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.price_text);
            viewHolder.pizzaImage = (ImageView) convertView.findViewById(R.id.pizza_icon);
            viewHolder.tickImage = (ImageView) convertView.findViewById(R.id.tick_icon);
            viewHolder.txtCrustName = (TextView) convertView.findViewById(R.id.crust_text);
            viewHolder.btnSmallSize = (FrameLayout)  convertView.findViewById(R.id.btnSmall);
            viewHolder.btnMediumSize = (FrameLayout)  convertView.findViewById(R.id.btnMedium);
            viewHolder.btnLargeSize = (FrameLayout)  convertView.findViewById(R.id.btnLarge);
            viewHolder.btnPlus = (Button)  convertView.findViewById(R.id.btnPlus);
            viewHolder.btnMinus = (Button)  convertView.findViewById(R.id.btnMinus);
            viewHolder.txtQty=(TextView) convertView.findViewById(R.id.txtQty);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(model.isVeg()){
            viewHolder.vegIcon.setImageResource(R.drawable.mark_veg);
        }else{
            viewHolder.vegIcon.setImageResource(R.drawable.mark_non_veg);
        }

        if("pizza1".equals(model.getImage())){
            viewHolder.pizzaImage.setBackgroundResource(R.drawable.pizza1);
        }
        else if("pizza2".equals(model.getImage())){
            viewHolder.pizzaImage.setBackgroundResource(R.drawable.pizza2);
        }

        viewHolder.txtPizzaName.setText(model.getName());
        viewHolder.txtCrustName.setText(model.getCrust());
        viewHolder.txtPrice.setText(String.valueOf(model.getPrice()));
        viewHolder.txtQty.setText(String.valueOf(model.getQuantity()));

        viewHolder.btnMinus.setOnClickListener(this);
        viewHolder.btnMinus.setTag(position);

        viewHolder.btnPlus.setOnClickListener(this);
        viewHolder.btnPlus.setTag(position);

        viewHolder.btnSmallSize.setOnClickListener(this);
        viewHolder.btnSmallSize.setTag(position);
        viewHolder.btnMediumSize.setOnClickListener(this);
        viewHolder.btnMediumSize.setTag(position);

        viewHolder.btnLargeSize.setOnClickListener(this);
        viewHolder.btnLargeSize.setTag(position);

        viewHolder.btnLargeSize.setBackgroundResource(R.drawable.button_bg_round);
        viewHolder.btnMediumSize.setBackgroundResource(R.drawable.button_bg_round);
        viewHolder.btnSmallSize.setBackgroundResource(R.drawable.button_bg_round);
        if("S".equals(model.getSize()))
        {
            viewHolder.btnSmallSize.setBackgroundResource(R.drawable.button_bg_round_blue);
        }
        else if("M".equals(model.getSize()))
        {
            viewHolder.btnMediumSize.setBackgroundResource(R.drawable.button_bg_round_blue);
        }
        else if("L".equals(model.getSize()))
        {
            viewHolder.btnLargeSize.setBackgroundResource(R.drawable.button_bg_round_blue);
        }

        if(model.getQuantity()!=null && model.getQuantity()>0){
            viewHolder.tickImage.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tickImage.setVisibility(View.INVISIBLE);
        }
        // Return the completed view to render on screen
        return convertView;
    }


}
