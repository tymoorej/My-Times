package com.tymoorejamal.mytimes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewGoodTimeRecyclerViewAdapter extends RecyclerView.Adapter<ViewGoodTimeRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<GoodTime> goodTimes;


    public ViewGoodTimeRecyclerViewAdapter(Context mContext, ArrayList<GoodTime> goodTimes) {
        this.mContext = mContext;
        this.goodTimes = goodTimes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemTitle.setText(goodTimes.get(position).getTitle());
        holder.itemRating.setText(Integer.toString(goodTimes.get(position).getRating()));
        holder.itemDescription.setText(goodTimes.get(position).getDescription());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler databaseHandler = new DatabaseHandler(mContext);
                databaseHandler.removeRow(goodTimes.get(position).getTid());

                Toast toast = Toast.makeText(mContext, "Removed: " + goodTimes.get(position).getTitle(), Toast.LENGTH_SHORT);
                toast.show();

                goodTimes.remove(position);
                notifyDataSetChanged();

            }

        });
    }

    @Override
    public int getItemCount() {
        return goodTimes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemRating;
        TextView itemDescription;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemRating = itemView.findViewById(R.id.item_rating);
            itemDescription = itemView.findViewById(R.id.item_decription);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
