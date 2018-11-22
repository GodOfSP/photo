package com.fnhelper.photo.beans;

/**
 * Created by little fly on 2018-11-22.
 */

public class EmergeNoticeBean {

    /**
     * code : 100
     * info : null
     * data : {"sTitle":"没考虑","sContent":"房间数量的咖啡机萨拉丁发生房价快速的拉法基开发类似大家发"}
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
         * sTitle : 没考虑
         * sContent : 房间数量的咖啡机萨拉丁发生房价快速的拉法基开发类似大家发
         */

        private String sTitle;
        private String sContent;

        public String getSTitle() {
            return sTitle;
        }

        public void setSTitle(String sTitle) {
            this.sTitle = sTitle;
        }

        public String getSContent() {
            return sContent;
        }

        public void setSContent(String sContent) {
            this.sContent = sContent;
        }
    }
}
