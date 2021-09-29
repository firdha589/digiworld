package com.example.Digi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private  String CategoryName,Description,Price,Pname,Quantity,saveCurrentDate,saveCurrentTime;
    private Button AddNewProductButton;
    private ImageView InputproductImage;
    private EditText InputProductName,inputProductDescription,InputProductQuantity,InputProductPrice;
    private  static final int GalleryPick=1;
    private Uri ImageUri;
    private String productRandomKey,downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private DatabaseReference ProductsCategotyRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        InputproductImage=(ImageView)findViewById(R.id.select_product_image);
        AddNewProductButton=(Button)findViewById(R.id.add_product_button);
        InputProductName=(EditText)findViewById(R.id.product_name);
        inputProductDescription=(EditText)findViewById(R.id.product_description);
        InputProductQuantity=(EditText)findViewById(R.id.product_quantity);
        InputProductPrice=(EditText)findViewById(R.id.product_price);
        loadingBar=new ProgressDialog(this);

        CategoryName=getIntent().getExtras().get("category").toString();
        ProductImagesRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef=FirebaseDatabase.getInstance().getReference().child("Products");
        ProductsCategotyRef=FirebaseDatabase.getInstance().getReference().child("Products Categoty");

        InputproductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateProductData();
            }
        });


    }



    private void openGallery() {

        Intent galleryintent =new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            InputproductImage.setImageURI(ImageUri);

        }
    }
    private void validateProductData() {
        Pname=InputProductName.getText().toString();
        Description=inputProductDescription.getText().toString();
        Price=InputProductPrice.getText().toString();
        Quantity=InputProductQuantity.getText().toString();


        if (ImageUri==null){
            Toast.makeText(this, "Product image is mandatory..", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please add some description", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Please add product price", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "Please add product name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Quantity)){
            Toast.makeText(this, "Please add quantity", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }


    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Adding new product");
        loadingBar.setMessage("Please wait,while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomKey=saveCurrentDate+saveCurrentTime;

        StorageReference filePath=ProductImagesRef.child(ImageUri.getLastPathSegment()+productRandomKey+".jpg");
        final UploadTask uploadTask =filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {

                String message= e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "ERROR:"+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Images uploaded succesfully", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();


                        }
                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                       if (task.isSuccessful()){
                           downloadImageUrl=task.getResult().toString();

                           Toast.makeText(AdminAddNewProductActivity.this, "got the product image url successfully... ", Toast.LENGTH_SHORT).show();
                           saveProductInfoTodatabase();
                       }

                    }
                });
            }
        });



    }

    private void saveProductInfoTodatabase() {
        HashMap<String ,Object>productMap=new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("price",Price);
        productMap.put("quantity",Quantity);
        productMap.put("pname",Pname);
        productMap.put("category",CategoryName);
        productMap.put("image",downloadImageUrl);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent =new Intent(AdminAddNewProductActivity.this,AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            loadingBar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "ERROR:"+ message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        ProductsCategotyRef.child(CategoryName).child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(AdminAddNewProductActivity.this); //fire the slide left animation
    }

}