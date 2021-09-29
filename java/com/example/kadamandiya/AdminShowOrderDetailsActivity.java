package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Model.AdminOrders;
import com.example.Digi.Model.products;
import com.example.Digi.Prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AdminShowOrderDetailsActivity extends AppCompatActivity {
    private TextView name,address,phonenumber,city,totalprice,date,time,quantity,productname,pid;
    private DatabaseReference ordersref;
    private String name1,address1,phonenumber1,city1,totalprice1,date1,time1,quantity1,productname1,pid1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_order_details);

        name1=getIntent().getStringExtra("name");
        address1=getIntent().getStringExtra("address");
        phonenumber1=getIntent().getStringExtra("phonenumber");
        productname1=getIntent().getStringExtra("product");
        city1=getIntent().getStringExtra("city");
        quantity1=getIntent().getStringExtra("quantity");
        pid1=getIntent().getStringExtra("pid");
        date1=getIntent().getStringExtra("date");
        time1=getIntent().getStringExtra("time");
        totalprice1=getIntent().getStringExtra("price");




        name = (TextView) findViewById(R.id.show_details_username);
        address = (TextView) findViewById(R.id.show_details_address);
        city = (TextView) findViewById(R.id.show_details_city);
        phonenumber = (TextView) findViewById(R.id.show_details_phonenumber);
        totalprice = (TextView) findViewById(R.id.show_details_price);
        date = (TextView) findViewById(R.id.show_details_date);
        time = (TextView) findViewById(R.id.show_details_time);
        quantity = (TextView) findViewById(R.id.show_details_quantity);
        productname = (TextView) findViewById(R.id.show_details_product);
        pid = (TextView) findViewById(R.id.show_details_pid);

        name.setText(name1);
        address.setText(address1);
        city.setText(city1);
        phonenumber.setText(phonenumber1);
        totalprice.setText(totalprice1);
        date.setText(date1);
        time.setText(time1);
        quantity.setText(quantity1);
        productname.setText(productname1);
        pid.setText(pid1);



    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(AdminShowOrderDetailsActivity.this);
    }


}