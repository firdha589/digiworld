package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Model.products;
import com.example.Digi.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class UserSpecificCategoryActivity extends AppCompatActivity {
    private String categotyType;
    private TextView categoryName;
    private ImageView sort;
    private RecyclerView category_recyclerView;
    private DatabaseReference ProductsCategoryRef;
    RecyclerView.LayoutManager layoutManager;
    private  String selected_unit2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_specific_category);

        categotyType=getIntent().getStringExtra("category");
        category_recyclerView=findViewById(R.id.category_recycle);
        categoryName=findViewById(R.id.category_name_user);
        sort=findViewById(R.id.sort_btn_2);

        categoryName.setText(categotyType);
        ProductsCategoryRef= FirebaseDatabase.getInstance().getReference().child("Products Categoty").child(categotyType);

        category_recyclerView.setHasFixedSize(true);
        //layoutManager=new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        category_recyclerView.setLayoutManager(gridLayoutManager);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDialog2();
            }
        });


    }

    private void showOptionDialog2() {

        final String[] sort_unit={"Price Lowest First","Price Higher First"};

        AlertDialog.Builder builder =new AlertDialog.Builder(UserSpecificCategoryActivity.this);
        builder.setTitle("Sort your product");
        builder.setSingleChoiceItems(sort_unit, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected_unit2=sort_unit[which];

                if (selected_unit2=="Price Lowest First"){
                    priceSortLower();

                }else {

                }





            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(UserSpecificCategoryActivity.this, "select is :"+selected_unit2, Toast.LENGTH_SHORT).show();








            }
        });
        builder.setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        builder.show();




    }

    private void priceSortLower() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products Categoty").child(categotyType);

        FirebaseRecyclerOptions<products> options=
                new FirebaseRecyclerOptions.Builder<products>()
                        .setQuery(reference.orderByChild("price"),products.class)
                        .build();
        FirebaseRecyclerAdapter<products, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int position, @NonNull @NotNull products model) {

                        holder.txtProductName.setText(model.getPname());
                        //holder.txtProductDescription.setText(model.getDescription());
                        holder.TxtProductPrice.setText("Price : Rs "+model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= new Intent(UserSpecificCategoryActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @NotNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                        ProductViewHolder holder=new ProductViewHolder(view);
                        return holder;
                    }
                };
        category_recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseRecyclerOptions<products> options=
                new FirebaseRecyclerOptions.Builder<products>()
                        .setQuery(ProductsCategoryRef,products.class)
                        .build();
        FirebaseRecyclerAdapter<products, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int position, @NonNull @NotNull products model) {
                        holder.txtProductName.setText(model.getPname());
                        //holder.txtProductDescription.setText(model.getDescription());
                        holder.TxtProductPrice.setText("Price : Rs "+model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                    Intent intent= new Intent(UserSpecificCategoryActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                    Animatoo.animateSlideUp(UserSpecificCategoryActivity.this);



                            }
                        });



                    }

                    @NonNull
                    @NotNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                        ProductViewHolder holder=new ProductViewHolder(view);
                        return holder;
                    }
                };
        category_recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(UserSpecificCategoryActivity.this);
    }


}