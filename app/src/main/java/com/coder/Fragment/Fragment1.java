package com.coder.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coder.Data.data_f1;
import com.coder.OnFragmentInteractionListener;
import com.coder.RecyclerViewAdapter.RecyclerView_f1_adapter;
import com.coder.pdfreader.MainActivity;
import com.coder.pdfreader.R;

import java.util.List;


/*************
 *
 *    Fragment 生命週期   產生階段（未出現在畫面上） onAttach --> onCreate --> onCreateView --> onActivityCreated
 *                                      準備階段（出現在畫面上）      onStart -->  onResume  -->
 *                                     暫停階段                                       onPause  -->  onStop
 *                                     Fragment已不在畫面中                onDestroyView
 *                                    Fragment要被清除之前                 onDestroy
 *                                   當初被加入的Activity卸載時        onDetach
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerview;
    private View viewContent;
    private Bundle bundle;
    private List<data_f1> mlist;
    private RecyclerView_f1_adapter adapter;

    private String TAG = "PDF";
    private SearchView searchView;


    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        //  mActivity = (Activity)context;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            bundle = getArguments();
            mlist = (List<data_f1>) bundle.get("list");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewContent = inflater.inflate(R.layout.fragment1, null);
        recyclerview = (RecyclerView) viewContent.findViewById(R.id.recyclerview);

        adapter = new RecyclerView_f1_adapter(getActivity(), mlist);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);

        //  Toast.makeText(getActivity(), "f1", Toast.LENGTH_SHORT).show();

        getActivity().setTitle("Home");
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

        //  getActivity().setTitle("Home");
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
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.Search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setSubmitButtonEnabled(true);//搜尋框右邊小箭頭 跟回車鍵一樣意思 占空間

        // searchView.setIconifiedByDefault(true);
        //searchView.setIconified(false);

        // searchView.setMaxWidth(1000);

        searchView.setOnQueryTextListener(new queryTextListener());

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                setItemsVisibility(menu, searchItem, false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                setItemsVisibility(menu, searchItem, true);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//
//        MenuItem mSearchMenuItem = menu.findItem(R.id.Search);
//         searchView = (SearchView) mSearchMenuItem.getActionView();
//
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shoppingcar:
                Toast.makeText(getActivity(), "shoppingcar", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.Search:

                // Toast.makeText(getActivity(), searchView.getMaxWidth()+"", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(getActivity(), "select", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class queryTextListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) { //查詢的時候回應
            Toast.makeText(getActivity(), "query:" + query, Toast.LENGTH_SHORT).show();

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) { //剛開啟跟每次文字改變時回應

            //  Toast.makeText(getActivity(), "newText:" + newText, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (isHidden()) {
            return;
        }
        getActivity().setTitle("Home");
        // Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();

    }

}
