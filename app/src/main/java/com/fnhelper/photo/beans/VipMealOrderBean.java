package com.fnhelper.photo.beans;

import com.google.gson.annotations.SerializedName;

public class VipMealOrderBean {

    /**
     * code : 100
     * info : 创建订单成功
     * data : {"appid":"wx04ff3ceebc3fc690","partnerid":"1510148341","prepayid":"wx141044516017854265fcad0d0531829010","package":"Sign=WXPay","noncestr":"1542163491","sign":"CFEDB7809D975B50F9490E6C9BDA0AB9"}
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
         * appid : wx04ff3ceebc3fc690
         * partnerid : 1510148341
         * prepayid : wx141044516017854265fcad0d0531829010
         * package : Sign=WXPay
         * noncestr : 1542163491
         * sign : CFEDB7809D975B50F9490E6C9BDA0AB9
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String sign;
        private String timestamp;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
