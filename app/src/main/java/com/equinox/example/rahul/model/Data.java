package com.equinox.example.rahul.model;


import androidx.databinding.BaseObservable;

public class Data extends BaseObservable {
    private String name;
    private String dept_name;


    public Data(String name, String dept_name) {
        this.name = name;
        this.dept_name = dept_name;
    }

    public Data() {
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
