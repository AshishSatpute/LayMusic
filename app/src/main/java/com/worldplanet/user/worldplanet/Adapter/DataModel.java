package com.worldplanet.user.worldplanet.Adapter;

public class DataModel {String name;
    int id_;
    int image;

    public DataModel(String name, int id_, Integer integer) {
        this.name = name;

        this.id_ = id_;
        this.image=integer;
    }

    public String getName() {
        return name;
    }



    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}



