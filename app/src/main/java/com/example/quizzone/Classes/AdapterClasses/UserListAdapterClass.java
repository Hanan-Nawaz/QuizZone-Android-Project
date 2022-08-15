package com.example.quizzone.Classes.AdapterClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzone.Classes.Insert;
import com.example.quizzone.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapterClass extends RecyclerView.Adapter<UserListAdapterClass.ViewHolder>  {

    ArrayList<Insert> arrayList;

    public UserListAdapterClass(ArrayList<Insert> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public UserListAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usercard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapterClass.ViewHolder holder, int position) {
        Insert insert = arrayList.get(position);

        holder.userName.setText(insert.getName());

        try {
            URL url = new URL(insert.getImage());
            Bitmap bMap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.userImage.setImageBitmap(bMap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName;
        Button ActiveBtn, InActiveBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userListIV);
            userName = itemView.findViewById(R.id.userListTV);
            ActiveBtn = itemView.findViewById(R.id.userListActiveBtn);
            InActiveBtn = itemView.findViewById(R.id.userListInActiveBtn);



        }
    }
}
