package com.example.Digi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView mens,babies,women,health;
    private ImageView srilankan,electronics,watches,sports;
    private Button LogoutBtn,CheckOrdersBtn,MaintainProductsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        mens=(ImageView)findViewById(R.id.mens_fashion);
        babies=(ImageView)findViewById(R.id.babies_kids);
        women=(ImageView)findViewById(R.id.women_fashion);
        health=(ImageView)findViewById(R.id.health_beauti);



        srilankan=(ImageView)findViewById(R.id.srilankan_tradition);
        electronics=(ImageView)findViewById(R.id.electronics_items);
        watches=(ImageView)findViewById(R.id.watches_accessories);
        sports=(ImageView)findViewById(R.id.sports_outdoor);

        LogoutBtn=(Button)findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn=(Button)findViewById(R.id.check_orders_btn);
        MaintainProductsBtn=(Button)findViewById(R.id.maintain_products_btn);

        MaintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,SearchProductsActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminNewOrdersActivity.class);
                startActivity(intent);


            }
        });



        mens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","men's fashion");
                startActivity(intent);
            }
        });
        babies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","babies and toys");
                startActivity(intent);
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","women's fashion");
                startActivity(intent);
            }
        });

        srilankan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","srilankan traditional");
                startActivity(intent);
            }
        });
        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","electronics devices");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Watches and accessories");
                startActivity(intent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","sports and outdoor");
                startActivity(intent);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Health and Beauty");
                startActivity(intent);

            }
        });
    }

    public void onBackPressed(){
        AlertDialog.Builder dialogBoxShow=new AlertDialog.Builder(this);
        dialogBoxShow.setTitle("EXIT !");
        dialogBoxShow.setMessage("Do you want to  EXIT admin panel?");
        dialogBoxShow.setCancelable(false);
        dialogBoxShow.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent =new Intent(AdminCategoryActivity.this,LoginActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(AdminCategoryActivity.this);

            }
        });
        dialogBoxShow.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialogBoxOpen=dialogBoxShow.create();
        alertDialogBoxOpen.show();


    }

}