package com.example.Digi.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Digi.Interface.ItemClickListner;
import com.example.Digi.R;

import org.jetbrains.annotations.NotNull;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductDescription,TxtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;


    public ProductViewHolder(@NonNull @NotNull View itemView) {

        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.product_image);
        txtProductName=(TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription=(TextView) itemView.findViewById(R.id.product_description);
        TxtProductPrice=(TextView) itemView.findViewById(R.id.product_price);

    }

    public  void  setItemClickListner(ItemClickListner listner){

        this.listner=listner;


    }

    @Override
    public void onClick(View v) {
        listner.onclick(v,getAdapterPosition(),false);

    }
}
