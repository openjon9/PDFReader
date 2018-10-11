package com.coder.RecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.Data.data_f1;
import com.coder.pdfreader.R;
import com.coder.pdfreader.ScrollingActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rey on 2018/10/2.
 */

public class RecyclerView_f1_adapter extends RecyclerView.Adapter<RecyclerView_f1_adapter.ViewHolder> implements Filterable {


    private Activity context;
    private List<data_f1> mData;
    private List<data_f1> filter_data;
    TestFilter myFilter;

    public RecyclerView_f1_adapter(Activity context, List<data_f1> mData) {
        this.context = context;
        this.mData = mData;
        filter_data = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(context).inflate(R.layout.rviewitem_1, parent, false);
        return new RecyclerView_f1_adapter.ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.text.setText(mData.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ScrollingActivity.class);
                intent.putExtra("imgPath", mData.get(position).getImgPath());
                intent.putExtra("mp4", mData.get(position).getMp4());
                intent.putExtra("name", mData.get(position).getName());
                context.startActivity(intent);

                //context.overridePendingTransition(R.anim.activity_open,0);
                context.overridePendingTransition(R.anim.activity_close, R.anim.activity_open); //淡出淡入
            }
        });


        //這邊是非同步下載圖片
        Picasso.with(context)
                .load(mData.get(position).getImgPath())
                .placeholder(R.drawable.preset_img) //圖片下載完成前的預設本地圖片
                .error(R.drawable.preset_img) //加載失敗顯示的預設圖
                .resize(480, 200) //服务端可能给我们一些奇怪的尺寸的图片，我们可以使用resize(int w,int hei) 来重新设置尺寸。
                .into(holder.img);

      //  Picasso.with(context).setIndicatorsEnabled(true); //緩存指示器   圖片的左上角出現一個帶色塊的三角形標示 綠色表示從內存加載、藍色表示從磁盤加載、紅色表示從網絡加載

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new TestFilter();
        }
        return myFilter;
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


    private class TestFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence text) {

            List<data_f1> new_data = new ArrayList();
            if (text != null && text.toString().trim().length() > 0) {
                for (int i = 0; i < filter_data.size(); i++) {
                    String content = (String) filter_data.get(i).getName();
                    if (content.contains(text)) {
                        data_f1 d1 = new data_f1();
                        d1.setName(filter_data.get(i).getName());
                        d1.setImgPath(filter_data.get(i).getImgPath());
                        d1.setMp4(filter_data.get(i).getMp4());
                        new_data.add(d1);
                    }
                }

            } else {
                new_data = filter_data; //filter_data 存放最原始的資料
            }
            FilterResults filterResults = new FilterResults();
            filterResults.count = new_data.size();
            filterResults.values = new_data;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence text, FilterResults results) {

            //這邊把物件塞回原集合
            mData = (List) results.values;

            if (results.count > 0) {
                notifyDataSetChanged();
            } else {

                //這邊可以做一個搜尋不到的提示
                notifyDataSetChanged();
            }

        }
    }

}
