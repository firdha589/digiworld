package com.example.Digi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baoyachi.stepview.VerticalStepView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderTrackingActivity extends AppCompatActivity {

    VerticalStepView step_view;
    Button first,second,third,forth,fifth;
    private String orderState,estimateDate;
    private String saveCurrentDate;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        orderState=getIntent().getStringExtra("orderState");

        estimateDate=getIntent().getStringExtra("estimateDate");



        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        step_view=findViewById(R.id.step_view);
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        third=findViewById(R.id.third);
        forth=findViewById(R.id.forth);
        fifth=findViewById(R.id.fifth);
        //Date=findViewById(R.id.date_tra);

        first.setVisibility(View.GONE);
        second.setVisibility(View.GONE);
        third.setVisibility(View.GONE);
        forth.setVisibility(View.GONE);
        fifth.setVisibility(View.GONE);


        setStepView();




    }

    private void setStepView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            step_view.setStepsViewIndicatorComplectingPosition(getList().size())
                    .reverseDraw(false)
                    .setStepViewTexts(getList())
                    .setLinePaddingProportion(0.85f)
                    .setTextSize(16)
                    .setStepsViewIndicatorCompletedLineColor(getColor(R.color.colorPrimary))
                    .setStepViewUnComplectedTextColor(Color.WHITE)
                    .setStepViewComplectedTextColor(getColor(R.color.yellow))
                    .setStepsViewIndicatorCompleteIcon(getDrawable(R.drawable.complted))
                    .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.attention))
                    .setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.default_icon));

            if (saveCurrentDate==estimateDate) {

                orderState = "compleate";
            }


            switch (orderState){
                case "not shipped":
                    step_view.setStepsViewIndicatorComplectingPosition(2);
                    break;
                case "shipped":
                    step_view.setStepsViewIndicatorComplectingPosition(4);
                    break;
                default:
                    step_view.setStepsViewIndicatorComplectingPosition(5);
                    break;
            }





        }


    }

    private List<String> getList(){

        List<String> list=new ArrayList<>();
        list.add("Place Order");
        list.add("Pay Success");
        list.add("Shipped Order");
        list.add("Arriving to the Location");
        list.add("Order Complete");
        return list;



    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideLeft(OrderTrackingActivity.this);
    }



}