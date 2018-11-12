package com.fnhelper.photo.beans;

public class BalanceBean {

    /**
     * code : 100
     * info : null
     * data : {"dBalance":0}
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
         * dBalance : 0.0
         */

        private String dBalance;

        public String getDBalance() {
            return dBalance;
        }

        public void setDBalance(String dBalance) {
            this.dBalance = dBalance;
        }
    }
}
