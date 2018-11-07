package com.fnhelper.photo.beans;

import java.util.List;

public class UpdatePicBean {

    /**
     * code : 100
     * info : 上传成功
     * data : ["http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-07/4840638404549814391.jpeg"]
     */

    private int code;
    private String info;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
