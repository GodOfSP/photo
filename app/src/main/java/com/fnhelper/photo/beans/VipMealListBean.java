package com.fnhelper.photo.beans;

import java.util.List;

public class VipMealListBean {

    /**
     * code : 100
     * info : null
     * data : [{"ID":"02f316d19f2e47ffa8e34494bba5b700","sVipName":"半年会员","dVipPrices":128,"dOldPrices":188},{"ID":"fcf9664ca6a245fba28e7b03712ed2f9","sVipName":"季度会员","dVipPrices":68,"dOldPrices":98},{"ID":"116824a363d5464a853c5f3ecf39ab21","sVipName":"年度会员","dVipPrices":218,"dOldPrices":368}]
     */

    private int code;
    private String info;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 02f316d19f2e47ffa8e34494bba5b700
         * sVipName : 半年会员
         * dVipPrices : 128.0
         * dOldPrices : 188.0
         */

        private String ID;
        private String sVipName;
        private String dVipPrices;
        private String dOldPrices;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getSVipName() {
            return sVipName;
        }

        public void setSVipName(String sVipName) {
            this.sVipName = sVipName;
        }

        public String getDVipPrices() {
            return dVipPrices;
        }

        public void setDVipPrices(String dVipPrices) {
            this.dVipPrices = dVipPrices;
        }

        public String getDOldPrices() {
            return dOldPrices;
        }

        public void setDOldPrices(String dOldPrices) {
            this.dOldPrices = dOldPrices;
        }
    }
}
