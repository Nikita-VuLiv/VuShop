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

import java.util.ArrayList;

public class CheckoutAdapter extends ArrayAdapter<EntityDominos>{

    private ArrayList<EntityDominos> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ImageView vegIcon;
        TextView txtPizzaName;
        TextView txtPrice;
        ImageView pizzaImage;
        TextView txtSize;
        TextView txtQty;
    }



    public CheckoutAdapter(ArrayList<EntityDominos> data, Context context) {
        super(context, R.layout.checkout_item, data);
        this.dataSet = data;
        this.mContext=context;

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
            convertView = inflater.inflate(R.layout.checkout_item, parent, false);
            viewHolder.vegIcon = (ImageView) convertView.findViewById(R.id.veg_icon);
            viewHolder.txtPizzaName = (TextView) convertView.findViewById(R.id.pizza_text);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.price_text);
            viewHolder.pizzaImage = (ImageView) convertView.findViewById(R.id.pizza_icon);
            viewHolder.txtQty=(TextView) convertView.findViewById(R.id.txtQty);
            viewHolder.txtSize=(TextView) convertView.findViewById(R.id.size);
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
        viewHolder.txtPrice.setText(String.valueOf(model.getPrice()));
        viewHolder.txtQty.setText(String.valueOf(model.getQuantity()));
        viewHolder.txtSize.setText(String.valueOf(model.getSize()));
        return convertView;
    }


}
