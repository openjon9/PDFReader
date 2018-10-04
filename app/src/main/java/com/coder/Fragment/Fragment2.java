package com.coder.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.Data.data_f4;
import com.coder.RecyclerViewAdapter.RecyclerView_f2_adapter;
import com.coder.pdfreader.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    private String TAG = "PDF";
    private View viewContent;
    private RecyclerView recyclerview;
    private ArrayList<data_f4> list;
    private RecyclerView_f2_adapter adapter;

    public Fragment2() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initData();
    }

    private void initData() {

        list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new data_f4());
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewContent = inflater.inflate(R.layout.fragment2, null);
        getActivity().setTitle("My Books");

        recyclerview = (RecyclerView) viewContent.findViewById(R.id.recyclerview);
        adapter = new RecyclerView_f2_adapter(getActivity(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
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
}
