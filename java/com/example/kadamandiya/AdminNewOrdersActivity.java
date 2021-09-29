package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Model.AdminOrders;
import com.example.Digi.Prevalent.prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AdminNewOrdersActivity extends AppCompatActivity {

    private RecyclerView orderlist;
    private DatabaseReference ordersref,userOrderStatesRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        ordersref= FirebaseDatabase.getInstance().getReference().child("Confirmed Orders").child("All orders").child(prevalent.currentOnlineUser.getPhone()).child("Products");
        userOrderStatesRef=FirebaseDatabase.getInstance().getReference().child("Users").child(prevalent.currentOnlineUser.getPhone());

        orderlist=findViewById(R.id.r3);
        orderlist.setLayoutManager(new LinearLayoutManager(this));





    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options =
                    new FirebaseRecyclerOptions.Builder<AdminOrders>()
                            .setQuery(ordersref, AdminOrders.class)
                            .build();
        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                    new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull @NotNull AdminOrdersViewHolder holder, int position, @NonNull @NotNull AdminOrders model) {

                            holder.username.setText("Name : " + model.getBuyers_name());
                            holder.userPhoneNumber.setText("phone : " + model.getBuyers_phone_number());
                            holder.userTotalPrice.setText("Total price : " + model.getTotalprice());
                            holder.userDateTime.setText("order at : " + model.getDate() + " " + model.getTime());
                            holder.userShippingAddress.setText("Name : " + model.getBuyers_shipping_address() + ", " + model.getBuyers_city());


                            holder.showOrderButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //String uID = getRef(position).getKey();
                                    Intent intent = new Intent(AdminNewOrdersActivity.this, AdminShowOrderDetailsActivity.class);
                                    intent.putExtra("name", model.getBuyers_name());
                                    intent.putExtra("address", model.getBuyers_shipping_address());
                                    intent.putExtra("phonenumber", model.getBuyers_phone_number());
                                    intent.putExtra("product", model.getPname());
                                    intent.putExtra("city", model.getBuyers_city());
                                    intent.putExtra("quantity", model.getQuantity());
                                    intent.putExtra("pid", model.getPid());
                                    intent.putExtra("date", model.getDate());
                                    intent.putExtra("time", model.getTime());
                                    intent.putExtra("price", model.getTotalprice());
                                    startActivity(intent);

                                }
                            });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]=new CharSequence[]
                                        {
                                            "Yes",
                                            "No"

                                        };
                                AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                builder.setTitle("Have you shipped this order ?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      if (which==0){
                                          String uID=getRef(position).getKey();
                                          RemoveOrder(uID);

                                          HashMap<String ,Object> productMap=new HashMap<>();
                                          productMap.put("order_state","shipped");

                                          userOrderStatesRef.child("my orders").child(model.getPid()).updateChildren(productMap)
                                                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                      @Override
                                                      public void onComplete(@NonNull @NotNull Task<Void> task) {

                                                          if (task.isSuccessful()){

                                                              Toast.makeText(AdminNewOrdersActivity.this, "order shipped  successfully", Toast.LENGTH_SHORT).show();


                                                          }
                                                          else {
                                                              Toast.makeText(AdminNewOrdersActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                                                          }

                                                      }
                                                  });




                                      } else {
                                          finish();

                                      }
                                    }
                                });
                                builder.show();
                            }
                        });


                        }

                        @NonNull
                        @NotNull
                        @Override
                        public AdminOrdersViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                            return new AdminOrdersViewHolder(view);
                        }
                    };
            orderlist.setAdapter(adapter);
            adapter.startListening();




    }



    public  static  class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView username,userPhoneNumber,userTotalPrice,userDateTime,userShippingAddress;

        public Button showOrderButton;



        public AdminOrdersViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.order_user_name);
            userPhoneNumber=itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=itemView.findViewById(R.id.order_total_price);
            userDateTime=itemView.findViewById(R.id.order_date_time);
            userShippingAddress=itemView.findViewById(R.id.order_address_city);
            showOrderButton=itemView.findViewById(R.id.show_details_btn);




        }

    }


    private void RemoveOrder(String uID) {
        ordersref.child(uID).removeValue();


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(AdminNewOrdersActivity.this);
    }


}