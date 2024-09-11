package com.eugene.tabs.entity;

import android.widget.ImageView;

public class Engineer {
    private String name;
    private String salary;

    private int ImageId;


    private int color;


    public Engineer(String name, String salary, int imageId, int color) {
        this.name = name;
        this.salary = salary;
        this.ImageId = imageId;
        this.color = color;
    }

    public int getImageId() {
        return ImageId;
    }

    public int getColor() {
        return color;
    }


    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }


}

