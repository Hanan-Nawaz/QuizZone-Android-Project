package com.example.quizzone.Classes.AdapterClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzone.Classes.AddTopics;
import com.example.quizzone.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DashboardTopicsAdapter extends RecyclerView.Adapter<DashboardTopicsAdapter.ViewHolder> {

    ArrayList<AddTopics> addTopicsArrayList;


    public DashboardTopicsAdapter(ArrayList<AddTopics> addTopicsArrayList) {
        this.addTopicsArrayList = addTopicsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topicimage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddTopics addTopics = addTopicsArrayList.get(position);
        try {
            URL url = new URL(addTopics.getImage());
            Bitmap bMap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.logoIV.setImageBitmap(bMap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return addTopicsArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView logoIV;
        ProgressBar TopicsBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logoIV = itemView.findViewById(R.id.imageViewDashboard);
        }
    }



}
