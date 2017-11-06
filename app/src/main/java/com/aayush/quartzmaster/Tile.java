package com.aayush.quartzmaster;

import java.io.Serializable;

/**
 * Created by aayush on 10/21/17.
 */

public class Tile implements Serializable{
    String url;
    String collection;
    String name;
    String size;
    String thickness;
    int drawable1;
    int drawable2;
    int drawable3;

    public Tile(String url, String collection, String name, String size, String thickness, int drawable1, int drawable2, int drawable3) {
        this.url = url;
        this.collection = collection;
        this.name = name;
        this.size = size;
        this.thickness = thickness;
        this.drawable1 = drawable1;
        this.drawable2 = drawable2;
        this.drawable3 = drawable3;
    }

    public String getID() {
        return name.substring(0, name.indexOf(' ')).toLowerCase();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public int getDrawable1() {
        return drawable1;
    }

    public void setDrawable1(int drawable1) {
        this.drawable1 = drawable1;
    }

    public int getDrawable2() {
        return drawable2;
    }

    public void setDrawable2(int drawable2) {
        this.drawable2 = drawable2;
    }

    public int getDrawable3() {
        return drawable3;
    }

    public void setDrawable3(int drawable3) {
        this.drawable3 = drawable3;
    }
}
