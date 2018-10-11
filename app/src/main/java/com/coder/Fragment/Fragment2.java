package com.coder.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.Data.data_f1;
import com.coder.Data.data_f2;
import com.coder.Data.data_f4;
import com.coder.RecyclerViewAdapter.RecyclerView_f2_adapter;
import com.coder.pdfreader.R;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    private String TAG = "coderPDF";
    private View viewContent;
    private RecyclerView recyclerview;
    private ArrayList<data_f2> list;
    private RecyclerView_f2_adapter adapter;
    private SwipeRefreshLayout layout_swipe_refresh;
    private boolean isRefresh = false;

    public Fragment2() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        list = new ArrayList<>();
        initData();
    }

    private void initData() {

        final File filePath = Environment.getExternalStorageDirectory();

        File file = new File(filePath.getPath() + "/PDFReader/PDFReaderFile");
        File file_pic = new File(filePath.getPath() + "/PDFReader/PDFReaderPic");

        File[] arr1 = file.listFiles();
        File[] arr2 = file_pic.listFiles();

        if (!file.exists()) {
            file.mkdirs();
        }

        if (!file_pic.exists()) {
            file_pic.mkdirs();
        }

        if (arr1 == null || arr1.length < 1) {
            return;
        }

        for (int i = 0; i < arr1.length; i++) {
            data_f2 d2 = new data_f2();

            String str1 = arr1[i].getAbsolutePath().split("PDFReader/PDFReaderFile/")[1].split("\\.")[0];
            d2.setName(str1);
            d2.setMp4(arr1[i].getAbsolutePath());

            String str2 = "";

            for (File item2 : arr2) {
                str2 = item2.getAbsolutePath().split("PDFReader/PDFReaderPic/")[1].split("\\.")[0];
                if (str1.equals(str2)) {
                    d2.setImgPath(item2.getAbsolutePath());
                    Log.d(TAG, "圖片路徑:" + item2.getAbsolutePath());
                }
            }

            list.add(d2);
            Log.d(TAG, "檔案內容:" + str1);
            Log.d(TAG, "檔案路徑:" + arr1[i].getAbsolutePath());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewContent = inflater.inflate(R.layout.fragment2, null);
        getActivity().setTitle("My Books");

        recyclerview = (RecyclerView) viewContent.findViewById(R.id.recyclerview);
        adapter = new RecyclerView_f2_adapter(getActivity(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);


        layout_swipe_refresh = (SwipeRefreshLayout) viewContent.findViewById(R.id.layout_swipe_refresh); //下拉刷新
        layout_swipe_refresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED); //下拉刷新顏色 最多設4種 只要刷新沒完成就一直循環
        layout_swipe_refresh.setDistanceToTriggerSync(300); // 設置手指在螢幕下拉動多少距離會觸發下拉刷新

        layout_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                list.clear();
                adapter.notifyDataSetChanged();

                if (!isRefresh) { //是否正在刷新中

                    isRefresh = true;
                    initData();

                    adapter.notifyDataSetChanged();
                    layout_swipe_refresh.setRefreshing(false); //更新完關閉刷新效果
                    isRefresh = false;

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            isRefresh = true;
//                            // list.add(new data_f2());
//                            //list.clear();
//                            initData();
//
//
//                            adapter.notifyDataSetChanged();
//                            layout_swipe_refresh.setRefreshing(false); //更新完關閉刷新效果
//                            isRefresh = false;
//                        }
//                    }, 3000);
//


                }

            }
        });

        return viewContent;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        //  Toast.makeText(getActivity(), "rf1", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (isHidden()) {
            return;
        }
        getActivity().setTitle("My Books");
        // Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();

    }

    public  void updata() {
        if (adapter != null) {
            list.clear();
            initData();
            adapter.notifyDataSetChanged();
        }
    }
}
