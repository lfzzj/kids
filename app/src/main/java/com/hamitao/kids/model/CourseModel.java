package com.hamitao.kids.model;

/**
 * Created by linjianwen on 2018/3/24.
 * 课程模型
 */

public class CourseModel {

    public CourseModel(String corseName) {
        this.corseName = corseName;
    }

    String corseName;

    public String getCorseName() {
        return corseName;
    }

    public void setCorseName(String corseName) {
        this.corseName = corseName;
    }
}
