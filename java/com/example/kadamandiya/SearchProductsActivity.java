package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SearchProductsActivity extends AppCompatActivity {

    private ImageView searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;

    private ImageView priceSort;

    String selected_unit="Price Lowest First";

    private String type=" ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle !=null ){
            type=getIntent().getExtras().get("Admin").toString();


        }

        searchBtn=findViewById(R.id.search_btn);
        inputText=findViewById(R.id.search_procuct_name);
        searchList=findViewById(R.id.search_list);


        priceSort=findViewById(R.id.sort_btn);




        //searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));

        searchList.setHasFixedSize(true);
        //layoutManager=new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        searchList.setLayoutManager(gridLayoutManager);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInput=inputText.getText().toString();
                onStart();

            }
        });

        priceSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDialog();
            }
        });


    }

    private void showOptionDialog() {

        final String[] sort_unit={"Price Lowest First","Price Higher First"};

        AlertDialog.Builder builder =new AlertDialog.Builder(SearchProductsActivity.this);
        builder.setTitle("Sort your product");
        builder.setSingleChoiceItems(sort_unit, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected_unit=sort_unit[which];

                if (selected_unit=="Price Lowest First"){
                    priceSortLower();

                }else {

                }





            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(SearchProductsActivity.this, "select is :"+selected_unit, Toast.LENGTH_SHORT).show();








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
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");

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
                                Intent intent= new Intent(SearchProductsActivity.this,ProductDetailsActivity.class);
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
        searchList.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<products> options=
                new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(reference.orderByChild("pname").startAt(SearchInput),products.class)
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
                                if (type.equals("Admin")){
                                    Intent intent= new Intent(SearchProductsActivity.this,AdminMaintainProductsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    intent.putExtra("category",model.getCategory());
                                    startActivity(intent);


                                }else {
                                    Intent intent= new Intent(SearchProductsActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);

                                }



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
        searchList.setAdapter(adapter);
        adapter.startListening();

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(SearchProductsActivity.this);
    }
}