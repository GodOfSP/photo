package com.fnhelper.photo.beans;

/**
 * Created by little fly on 2018-11-24.
 */

public class CanShareBean {

    /**
     * code : 100
     * info : null
     * data : {"IsCanShare":false}
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
         * IsCanShare : false
         */

        private boolean IsCanShare;

        public boolean isIsCanShare() {
            return IsCanShare;
        }

        public void setIsCanShare(boolean IsCanShare) {
            this.IsCanShare = IsCanShare;
        }
    }
}
