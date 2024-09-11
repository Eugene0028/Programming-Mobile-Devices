package com.eugene.database.entity;

public class Student
{
    private String name;
    private Float weight;
    private Float height;
    private Integer age;

    public Student(String name, Float weight, Float height, Integer age)
    {
        this.name = name;
        this.weight = (float) Math.round(weight);
        this.height = (float) Math.round(height);
        this.age = age;
    }

    public String getName() {
        return name;
    }


    public Float getWeight() {
        return weight;
    }


    public Float getHeight() {
        return height;
    }



    public Integer getAge() {
        return age;
    }


}
