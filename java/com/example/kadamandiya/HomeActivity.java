package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.Digi.Model.products;
import com.example.Digi.Prevalent.prevalent;
import com.example.Digi.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class HomeActivity extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String type=" ";

    ImageSlider imageSlider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle !=null ){
            type=getIntent().getExtras().get("Admin").toString();


        }
        imageSlider=findViewById(R.id.image_slider);

        ArrayList<SlideModel> images=new ArrayList<>();
        images.add(new SlideModel(R.drawable.slider1,null));
        images.add(new SlideModel(R.drawable.slider2,null));
        images.add(new SlideModel(R.drawable.slider4,null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);






        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        nav=(NavigationView)findViewById(R.id.nav_view);
        drawerLayout  =(DrawerLayout)findViewById(R.id.drawer_layout);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView=nav.getHeaderView(0);
        TextView userNameTextView=headerView.findViewById(R.id.user_profile_name) ;
        CircleImageView profileImageView=headerView.findViewById(R.id.user_profile_image);

        if (!type.equals("Admin")){
            userNameTextView.setText(prevalent.currentOnlineUser.getName());
            Picasso.get().load(prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);

        }


        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        //layoutManager=new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        //recyclerView.setLayoutManager(layoutManager);





        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_orders:

                        if (!type.equals("Admin")){
                            Intent intent3=new Intent(HomeActivity.this,MyOrdersActivity.class);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            startActivity(intent3);
                            Animatoo.animateSlideRight(HomeActivity.this);
                            break;
                        }

                    case R.id.nav_search:
                        if (!type.equals("Admin")){
                            Intent intent2=new Intent(HomeActivity.this,SearchProductsActivity.class);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            startActivity(intent2);
                            Animatoo.animateSlideRight(HomeActivity.this);
                            break;
                        }

                    case R.id.nav_categories:

                            Intent intentcat=new Intent(HomeActivity.this,UserCategoryActivity.class);
                            intent.putExtra("type",type);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            startActivity(intentcat);
                            Animatoo.animateSlideRight(HomeActivity.this);
                            break;


                    case R.id.nav_settings:
                        if (!type.equals("Admin")){
                            Intent intent=new Intent(HomeActivity.this,SettingsActivity.class);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            startActivity(intent);
                            Animatoo.animateSlideRight(HomeActivity.this);
                            break;
                        }

                    case R.id.nav_logout:
                        if (!type.equals("Admin")){
                            Paper.book().destroy();
                            Intent intent1 =new Intent(HomeActivity.this,DashboardActivity.class);
                            intent1.addFlags(intent1.FLAG_ACTIVITY_NEW_TASK | intent1.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                            finish();
                            Animatoo.animateSlideRight(HomeActivity.this);
                            break;
                        }

                }
                return true;
            }
        });



    }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseRecyclerOptions<products> options=
                new FirebaseRecyclerOptions.Builder<products>()
                        .setQuery(ProductsRef,products.class)
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
                                    Intent intent= new Intent(HomeActivity.this,AdminMaintainProductsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    intent.putExtra("category",model.getCategory());
                                    startActivity(intent);


                                }else {
                                    Intent intent= new Intent(HomeActivity.this,ProductDetailsActivity.class);
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
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public void onBackPressed(){
        AlertDialog.Builder dialogBoxShow=new AlertDialog.Builder(this);
        dialogBoxShow.setTitle("EXIT !");
        dialogBoxShow.setMessage("Do you want to  EXIT application?");
        dialogBoxShow.setCancelable(false);
        dialogBoxShow.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);;

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