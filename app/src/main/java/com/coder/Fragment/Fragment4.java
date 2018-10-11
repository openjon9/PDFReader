package com.coder.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.coder.Data.data_f2;
import com.coder.Data.data_f4;
import com.coder.RecyclerViewAdapter.RecyclerView_f4_adapter;
import com.coder.pdfreader.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {

    private String TAG = "coderPDF";
    private View viewContent;
    private RecyclerView recyclerview;
    private RecyclerView_f4_adapter adapter;
    private ArrayList<data_f4> list;


    public Fragment4() {
        // Required empty public constructor
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
            data_f4 d4 = new data_f4();

            String str1 = arr1[i].getAbsolutePath().split("PDFReader/PDFReaderFile/")[1].split("\\.")[0];
            d4.setName(str1);
            d4.setMp4(arr1[i].getAbsolutePath());

            String str2 = "";

            for (File item2 : arr2) {
                str2 = item2.getAbsolutePath().split("PDFReader/PDFReaderPic/")[1].split("\\.")[0];
                if (str1.equals(str2)) {
                    d4.setImgPath(item2.getAbsolutePath());
                    Log.d(TAG, "圖片路徑:" + item2.getAbsolutePath());
                }
            }

            list.add(d4);
            Log.d(TAG, "檔案內容:" + str1);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewContent = inflater.inflate(R.layout.fragment4, container, false);

        getActivity().setTitle("Downloaded Books");


        recyclerview = (RecyclerView) viewContent.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        recyclerview.setLayoutManager(linearLayout);

        adapter = new RecyclerView_f4_adapter(getActivity(), list);
        recyclerview.setAdapter(adapter);


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
        //inflater.inflate(R.menu.main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (isHidden()) {
            return;
        }
        getActivity().setTitle("Downloaded Books");
        // Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();

    }

    public void updata() {
        if (adapter != null) {
            list.clear();
            initData();
            adapter.notifyDataSetChanged();
        }
    }

}