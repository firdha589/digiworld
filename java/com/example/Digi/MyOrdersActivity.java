package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Model.UsersOrders;
import com.example.Digi.Prevalent.prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyOrdersActivity extends AppCompatActivity {

    private RecyclerView myorderlist;
    private DatabaseReference myordersref,myorderimage;
    private String dateInString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);


        myordersref= FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.currentOnlineUser.getPhone()).child("my orders");

        myorderlist=findViewById(R.id.r6);
        myorderlist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();


        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String CurrentDate = mYear + "/" + mMonth + "/" + mDay;
        dateInString = "dob"; // Select date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateInString));
        } catch (ParseException e) {

            e.printStackTrace();
        }
        c.add(Calendar.DATE, 3);
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date resultdate = new Date(c.getTimeInMillis());
        dateInString = sdf.format(resultdate);



        FirebaseRecyclerOptions<UsersOrders> options=
                new FirebaseRecyclerOptions.Builder<UsersOrders>()
                        .setQuery(myordersref,UsersOrders.class)
                        .build();
        FirebaseRecyclerAdapter<UsersOrders, MyOrdersActivity.UsersOrdersViewHolder> adapter=
                new FirebaseRecyclerAdapter<UsersOrders, MyOrdersActivity.UsersOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull MyOrdersActivity.UsersOrdersViewHolder holder, int position, @NonNull @NotNull UsersOrders model) {

                        holder.productName.setText(model.getPname());
                        holder.paidTotalPrice.setText("Total Price: "+model.getTotalprice());
                        holder.PurchesDateTime.setText("Purchesed Date: "+model.getDate());
                        holder.estimateDate.setText("Estimate Delivery at : "+dateInString);

                        try {
                            Picasso.get().load(model.getImage()).into(holder.orderImage);
                        }catch (Exception e){
                            Toast.makeText(MyOrdersActivity.this, "error :"+e, Toast.LENGTH_SHORT).show();
                        }

                        holder.showTrackingButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(MyOrdersActivity.this, "oder tracking", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MyOrdersActivity.this,OrderTrackingActivity.class);
                                intent.putExtra("orderState",model.getOrder_state());
                                intent.putExtra("estimateDate",dateInString);
                                startActivity(intent);
                                Animatoo.animateSlideDown(MyOrdersActivity.this);
                            }
                        });




                    }

                    @NonNull
                    @NotNull
                    @Override
                    public UsersOrdersViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_layout,parent,false);
                        return new UsersOrdersViewHolder(view);
                    }
                };
        myorderlist.setAdapter(adapter);
        adapter.startListening();

    }



    public  static  class UsersOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView productName,paidTotalPrice,PurchesDateTime,estimateDate;
        public ImageView orderImage;

        public Button showTrackingButton;



        public UsersOrdersViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productName=itemView.findViewById(R.id.my_order_name);
            paidTotalPrice=itemView.findViewById(R.id.my_order_paid_price);
            PurchesDateTime=itemView.findViewById(R.id.my_order_purches_date);
            estimateDate=itemView.findViewById(R.id.my_order_estimate_date);
            showTrackingButton=itemView.findViewById(R.id.show_tracking_btn);
            orderImage=itemView.findViewById(R.id.myorder_img);




        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(MyOrdersActivity.this);
    }
    
}