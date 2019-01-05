package com.tymoorejamal.mytimes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder>{

    private Context mContext;
    ArrayList<byte[]> images;

    public ImageRecyclerAdapter(Context mContext, ArrayList<byte[]> images) {
        this.mContext = mContext;
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_layout, parent, false);
        ImageRecyclerAdapter.ViewHolder viewHolder = new ImageRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        byte[] image = images.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.image.setImageBitmap(bitmap);
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(mContext, "Removed image!", Toast.LENGTH_SHORT);
                toast.show();

                images.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.leftRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(mContext, "Rotate left!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        holder.rightRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(mContext, "Rotate right", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        RelativeLayout parentLayout;
        ImageButton leftRotate;
        ImageButton rightRotate;
        ImageButton deleteImage;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_image);
            parentLayout = itemView.findViewById(R.id.image_parent_layout);

            leftRotate = itemView.findViewById(R.id.left_rotate);
            rightRotate = itemView.findViewById(R.id.right_rotate);
            deleteImage = itemView.findViewById(R.id.delete);

        }

    }
}
