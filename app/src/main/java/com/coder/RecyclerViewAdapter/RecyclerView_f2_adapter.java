package com.coder.RecyclerViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.Data.data_f1;
import com.coder.Data.data_f2;
import com.coder.Data.data_f4;
import com.coder.pdfreader.PDFActivity;
import com.coder.pdfreader.R;
import com.coder.pdfreader.VideoActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rey on 2018/10/4.
 */

public class RecyclerView_f2_adapter extends RecyclerView.Adapter<RecyclerView_f2_adapter.ViewHolder> {


    private Activity context;
    private List<data_f2> mData;
    private String TAG = "coderPDF";


    public RecyclerView_f2_adapter(Activity context, List<data_f2> mData) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {



        holder.title.setText(mData.get(position).getName());

        Log.d(TAG, "imgPath2:" + mData.get(position).getImgPath());

        Picasso.with(context)
                .load("file://" + mData.get(position).getImgPath())
                .placeholder(R.drawable.preset_img) //圖片下載完成前的預設本地圖片
                .error(R.drawable.preset_img) //加載失敗顯示的預設圖
                //.resize(480, 200) //服务端可能给我们一些奇怪的尺寸的图片，我们可以使用resize(int w,int hei) 来重新设置尺寸。
                .fit()
                .centerCrop()
                .into(holder.img);


        if (mData.get(position).getMp4().contains(".mp4")){
            holder.img_tag.setImageResource(R.drawable.ic_play_circle_filled);
        }else if (mData.get(position).getMp4().contains(".pdf")){
            holder.img_tag.setImageResource(R.drawable.ic_picture_as_pdf);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String filePath = mData.get(position).getMp4();

                Log.d(TAG, "路徑:" + filePath);

                if (mData.get(position).getMp4().contains(".mp4")) {
                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra("uri", mData.get(position).getMp4());
                    context.startActivity(intent);
                } else if (filePath.contains(".pdf")) {
                    Intent intent = new Intent(context, PDFActivity.class);
                    intent.putExtra("uri", mData.get(position).getMp4());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        ImageView img_tag;
        TextView title;


        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            img = (ImageView) itemView.findViewById(R.id.img);
            img_tag = (ImageView) itemView.findViewById(R.id.img_tag);
        }
    }

}
