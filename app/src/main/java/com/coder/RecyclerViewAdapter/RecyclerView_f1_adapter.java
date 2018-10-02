package com.coder.RecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.Data.data_f1;
import com.coder.pdfreader.R;
import com.coder.pdfreader.ScrollingActivity;

import java.util.List;

/**
 * Created by Rey on 2018/10/2.
 */

public class RecyclerView_f1_adapter extends RecyclerView.Adapter<RecyclerView_f1_adapter.ViewHolder> {


    private Activity context;
    private List<data_f1> mData;

    public RecyclerView_f1_adapter(Activity context, List<data_f1> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(context).inflate(R.layout.rviewitem_1, parent, false);
        return new RecyclerView_f1_adapter.ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

      //  holder.text.setText(mData.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ScrollingActivity.class);
                context.startActivity(intent);

                //context.overridePendingTransition(R.anim.activity_open,0);
                context.overridePendingTransition(R.anim.activity_close, R.anim.activity_open); //淡出淡入
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            text = (TextView) itemView.findViewById(R.id.text);

        }
    }

}
