package com.example.sd.myapplication.Module;

/**
 * Created by kh on 7/11/2017.
 */

public class Picture {
    private String name;
    private String path;

    public Picture(){

    }
    public Picture(String name, String path){
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
