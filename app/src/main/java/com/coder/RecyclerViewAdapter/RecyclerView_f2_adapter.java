package com.coder.RecyclerViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.Data.data_f1;
import com.coder.Data.data_f4;
import com.coder.pdfreader.R;

import java.util.List;

/**
 * Created by Rey on 2018/10/4.
 */

public class RecyclerView_f2_adapter extends RecyclerView.Adapter<RecyclerView_f2_adapter.ViewHolder> {


    private Activity context;
    private List<data_f4> mData;


    public RecyclerView_f2_adapter(Activity context, List<data_f4> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mview = LayoutInflater.from(context).inflate(R.layout.rviewitem_2, parent, false);
        return new RecyclerView_f2_adapter.ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                context.startActivityForResult(intent, 100);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView text;


        public ViewHolder(View itemView) {
            super(itemView);


        }
    }

}
