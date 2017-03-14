package com.giyer.noogle.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.giyer.noogle.R;
import com.giyer.noogle.base.ActionFlowActivity;

import java.util.ArrayList;

/**
 * Created by giyer7 on 3/7/17.
 */

public class CategoryActivity extends ActionFlowActivity implements CategoryFragment.CategoryListener {

    private ArrayList<CategoryItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeCategoryActivity(savedInstanceState);
    }

    @Override
    protected int getSelectedNavViewItem() {
        return R.id.nav_category;
    }

    private void initializeCategoryActivity(Bundle savedInstanceState) {
        swapFragment(CategoryFragment.newInstance(new Bundle()),false, false);
    }

    @Override
    public void onCategoryClicked(int position, String category) {
        Toast.makeText(this, "Category "+category+" clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public ArrayList<CategoryItem> getCategories() {
        CategoryItem item1 = new CategoryItem("Music", R.drawable.music);
        CategoryItem item2 = new CategoryItem("Movies", R.drawable.movies);
        CategoryItem item3 = new CategoryItem("Entertainment", R.drawable.entertainment);
        CategoryItem item4 = new CategoryItem("Sports", R.drawable.sports);
        CategoryItem item5 = new CategoryItem("News", R.drawable.news);

        mItems.add(item1);
        mItems.add(item2);
        mItems.add(item3);
        mItems.add(item4);
        mItems.add(item5);

        return mItems;
    }
}
