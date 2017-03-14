package com.giyer.noogle.category;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.giyer.noogle.R;
import com.giyer.noogle.base.ActionFlowFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by giyer7 on 3/7/17.
 */

public class CategoryFragment extends ActionFlowFragment<CategoryFragment.CategoryListener> {

    private ArrayList<CategoryItem> mItems = new ArrayList<>();

    private CategoryAdapter mAdapter;

    @Bind(R.id.rv_category_home)
    RecyclerView mCategoryRecyclerView;

    public static CategoryFragment newInstance(Bundle args) {
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onInitFragment(View rootView, Bundle savedInstanceState) {
        mCategoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mCategoryRecyclerView.setHasFixedSize(true);
        if (mItems.isEmpty()) {
            mItems = getCallback().getCategories();
        }
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        mAdapter = new CategoryAdapter(mItems, getCallback());
        mCategoryRecyclerView.setAdapter(mAdapter);
        mCategoryRecyclerView.setNestedScrollingEnabled(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayout() {
        return R.layout.category_recyclerview;
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.category_fragment_title);
    }

    public interface CategoryListener extends ActionFlowFragment.ActionFlowFragmentListener {
        void onCategoryClicked(int position, String category);

        ArrayList<CategoryItem> getCategories();
    }

    @Override
    protected DisplayRequest actionBarDisplay() {
        return DisplayRequest.ACTION_HAMBURGER;
    }
}
