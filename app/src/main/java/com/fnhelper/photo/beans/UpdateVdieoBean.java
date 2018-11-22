package com.fnhelper.photo.beans;

public class UpdateVdieoBean {


    /**
     * code : 100
     * info : 上传成功
     * data : {"sVideoUrl":"http://153h79422m.imwork.net:10354/Material/3F170FA73E7F4682AA60BE20B342D908/Video/2018-11-22/5552010608722451990.mp4","sImageUrl":"http://153h79422m.imwork.net:10354/Material/3F170FA73E7F4682AA60BE20B342D908/Video/2018-11-22/5552010608722451990.jpg"}
     */

    private int code;
    private String info;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sVideoUrl : http://153h79422m.imwork.net:10354/Material/3F170FA73E7F4682AA60BE20B342D908/Video/2018-11-22/5552010608722451990.mp4
         * sImageUrl : http://153h79422m.imwork.net:10354/Material/3F170FA73E7F4682AA60BE20B342D908/Video/2018-11-22/5552010608722451990.jpg
         */

        private String sVideoUrl;
        private String sImageUrl;

        public String getSVideoUrl() {
            return sVideoUrl;
        }

        public void setSVideoUrl(String sVideoUrl) {
            this.sVideoUrl = sVideoUrl;
        }

        public String getSImageUrl() {
            return sImageUrl;
        }

        public void setSImageUrl(String sImageUrl) {
            this.sImageUrl = sImageUrl;
        }
    }
}
