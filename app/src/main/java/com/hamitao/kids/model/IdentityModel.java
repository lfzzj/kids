package com.hamitao.kids.model;

public class IdentityModel {
    private int img;
    private String name;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {

        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}