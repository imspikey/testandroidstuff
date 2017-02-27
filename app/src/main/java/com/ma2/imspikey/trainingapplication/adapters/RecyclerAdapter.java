package com.ma2.imspikey.trainingapplication.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ma2.imspikey.trainingapplication.R;
import com.ma2.imspikey.trainingapplication.model.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by imspikey on 2/20/2017.
 */

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.FoodHolder>{
private ArrayList<Food> foods;

private int[] images ={R.drawable.kb1,R.drawable.kb2,
        R.drawable.kb3,R.drawable.kb4,R.drawable.kb5,R.drawable.kb6,
        R.drawable.kb7,R.drawable.kb8,R.drawable.kb9,R.drawable.kb1,R.drawable.kb2,
        R.drawable.kb3,R.drawable.kb4,R.drawable.kb5,R.drawable.kb6,
        R.drawable.kb7,R.drawable.kb8,R.drawable.kb9};
    public  RecyclerAdapter(ArrayList<Food> foods)
    {
        this.foods = foods;
    }

    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView  = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_item_row,parent,false);
        return new FoodHolder(inflateView,(Activity)parent.getContext());
    }

    @Override
    public void onBindViewHolder(FoodHolder foodHolder, int position) {
        foodHolder.bindToView(foods.get(position),images[position]);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class FoodHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImteIMG;
        TextView mTitle;
        TextView mDescroption;
        Activity parentActivity;
        private static final String PHOTO_KEY = "PHOTO";

        public FoodHolder(View v,Activity parentActivity) {
            super(v);
            this.parentActivity = parentActivity;
            mImteIMG = (ImageView) v.findViewById(R.id.item_image);
            mTitle = (TextView) v.findViewById(R.id.food_title);
            mDescroption = (TextView) v.findViewById(R.id.food_description);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Log.d("RecyclerView", "CLICK!");
        }

        public void  bindToView(Food food, int imagePosition)
        {
            Picasso.with(this.parentActivity).load(imagePosition).fit().into(this.mImteIMG);


            this.mDescroption.setText(food.description);
            this.mTitle.setText(food.name);

        }
    }
}
