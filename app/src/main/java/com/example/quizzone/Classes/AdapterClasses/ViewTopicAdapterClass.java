package com.example.quizzone.Classes.AdapterClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzone.Classes.AddTopics;
import com.example.quizzone.R;
import com.google.thirdparty.publicsuffix.PublicSuffixType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ViewTopicAdapterClass extends RecyclerView.Adapter<ViewTopicAdapterClass.ViewHolder>{

    ArrayList<AddTopics> addTopics;


    public ViewTopicAdapterClass(ArrayList<AddTopics> addTopics) {
        this.addTopics = addTopics;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddTopics addTopic = addTopics.get(position);
        holder.titleTV.setText(addTopic.getName());
        URL newurl = null;
        try {
            newurl = new URL(addTopic.getImage());
            Bitmap bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            holder.topicLogoIV.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return addTopics.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView topicLogoIV;
        TextView titleTV;
        Button takeTestBtn;
        Button addMcqBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             topicLogoIV = itemView.findViewById(R.id.logoIV);
             titleTV = itemView.findViewById(R.id.titleTV);
             takeTestBtn = itemView.findViewById(R.id.takeTestBtn);
             addMcqBtn = itemView.findViewById(R.id.addMcqBtn);

        }
    }
}
