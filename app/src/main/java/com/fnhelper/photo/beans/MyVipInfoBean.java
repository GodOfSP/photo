package com.fnhelper.photo.beans;

public class MyVipInfoBean {

    /**
     * code : 100
     * info : null
     * data : {"bIsVip":false,"sVipName":"","dExpireTime":"2018-10-05 10:56:14"}
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
         * bIsVip : false
         * sVipName :
         * dExpireTime : 2018-10-05 10:56:14
         */

        private boolean bIsVip;
        private String sVipName;
        private String dExpireTime;

        public boolean isBIsVip() {
            return bIsVip;
        }

        public void setBIsVip(boolean bIsVip) {
            this.bIsVip = bIsVip;
        }

        public String getSVipName() {
            return sVipName;
        }

        public void setSVipName(String sVipName) {
            this.sVipName = sVipName;
        }

        public String getDExpireTime() {
            return dExpireTime;
        }

        public void setDExpireTime(String dExpireTime) {
            this.dExpireTime = dExpireTime;
        }
    }
}
