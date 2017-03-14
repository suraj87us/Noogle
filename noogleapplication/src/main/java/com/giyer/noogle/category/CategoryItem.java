package com.giyer.noogle.category;

/**
 * Created by giyer7 on 3/13/17.
 */

public class CategoryItem {
    private String categoryText;
    private int categoryImage;

    public CategoryItem(String categoryText, int categoryImage) {
        this.categoryText = categoryText;
        this.categoryImage = categoryImage;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }
}
