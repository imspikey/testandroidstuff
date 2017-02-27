package com.ma2.imspikey.trainingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ma2.imspikey.trainingapplication.adapters.RecyclerAdapter;
import com.ma2.imspikey.trainingapplication.model.Food;

import java.util.ArrayList;

public class RecyclerMain extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);

        mLinearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ArrayList<Food> foods = new ArrayList<>();
        for (int i = 0;i < 10;i++)
        {
            Food food = new Food();

            food.name ="Cheken Kabab With Extra Salad"+ i;
            food.description="One of the best availabale meals on this days menue it should be not lost"+ i;
            foods.add(food);
        }

        RecyclerAdapter adapter = new RecyclerAdapter(foods);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
       mRecyclerView.setAdapter(adapter);
    }
}
