  package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.Digi.Model.products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

  public class ProductDetailsActivity extends AppCompatActivity {

    private Button buyNow;
    private ImageView Backto;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private  String productID="";


      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID=getIntent().getStringExtra("pid");


        buyNow=(Button)findViewById(R.id.buy_now_btn);
        Backto=(ImageView)findViewById(R.id.back_home_btn);
        productName=(TextView)findViewById(R.id.product_name_details);
        productDescription=(TextView)findViewById(R.id.product_description_details);
        productPrice=(TextView)findViewById(R.id.product_price_details);
        numberButton=(ElegantNumberButton)findViewById(R.id.number_btn);
        productImage=(ImageView)findViewById(R.id.product_image_details);







        getProductDetails(productID);


        Backto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ProductDetailsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                Intent intent= new Intent(ProductDetailsActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("productId",productID);
                intent.putExtra("productname",productName.getText().toString());
                intent.putExtra("productQuentity",numberButton.getNumber());


                startActivity(intent);
                Animatoo.animateInAndOut(ProductDetailsActivity.this);

            }
        });

    }



      private void getProductDetails(String productID) {

          DatabaseReference productsRef= FirebaseDatabase.getInstance().getReference().child("Products");
          productsRef.child(productID).addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                  if (snapshot.exists()){
                      products products1=snapshot.getValue(products.class);

                      productName.setText(products1.getPname());
                      productPrice.setText("Rs :" +products1.getPrice());
                      productDescription.setText(products1.getDescription());
                      Picasso.get().load(products1.getImage()).into(productImage);
                  }
              }

              @Override
              public void onCancelled(@NonNull @NotNull DatabaseError error) {

              }
          });
      }
      @Override
      public void onBackPressed(){
          super.onBackPressed();
          Animatoo.animateSlideLeft(ProductDetailsActivity.this);
      }
  }