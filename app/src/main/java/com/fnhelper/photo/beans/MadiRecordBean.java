package com.fnhelper.photo.beans;

import java.util.List;

public class MadiRecordBean {

    /**
     * code : 100
     * info : null
     * data : {"page":2,"total":35,"rows":[{"Number":3,"dRechargePrice":250,"dMoney":2.5,"dInsertTime":"2018-10-31 15:08:17","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sNickName":"好疯狂的石头A","MaxRows":35},{"Number":4,"dRechargePrice":250,"dMoney":2.5,"dInsertTime":"2018-10-31 15:08:15","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sNickName":"好疯狂的石头A","MaxRows":35}],"data":null}
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
         * page : 2
         * total : 35
         * rows : [{"Number":3,"dRechargePrice":250,"dMoney":2.5,"dInsertTime":"2018-10-31 15:08:17","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sNickName":"好疯狂的石头A","MaxRows":35},{"Number":4,"dRechargePrice":250,"dMoney":2.5,"dInsertTime":"2018-10-31 15:08:15","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sNickName":"好疯狂的石头A","MaxRows":35}]
         * data : null
         */

        private int page;
        private int total;
        private String data;
        private List<RowsBean> rows;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * Number : 3
             * dRechargePrice : 250.0
             * dMoney : 2.5
             * dInsertTime : 2018-10-31 15:08:17
             * sHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132
             * sNickName : 好疯狂的石头A
             * MaxRows : 35
             */

            private String Number;
            private String dRechargePrice;
            private String dMoney;
            private String dInsertTime;
            private String sHeadImg;
            private String sNickName;
            private String MaxRows;
            private boolean bIsVip;

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public String getDRechargePrice() {
                return dRechargePrice;
            }

            public void setDRechargePrice(String dRechargePrice) {
                this.dRechargePrice = dRechargePrice;
            }

            public String getDMoney() {
                return dMoney;
            }

            public void setDMoney(String dMoney) {
                this.dMoney = dMoney;
            }

            public String getDInsertTime() {
                return dInsertTime;
            }

            public void setDInsertTime(String dInsertTime) {
                this.dInsertTime = dInsertTime;
            }

            public String getSHeadImg() {
                return sHeadImg;
            }  public boolean isBIsVip() {
                return bIsVip;
            }

            public void setBIsVip(boolean bIsVip) {
                this.bIsVip = bIsVip;
            }

            public void setSHeadImg(String sHeadImg) {
                this.sHeadImg = sHeadImg;
            }

            public String getSNickName() {
                return sNickName;
            }

            public void setSNickName(String sNickName) {
                this.sNickName = sNickName;
            }

            public String getMaxRows() {
                return MaxRows;
            }

            public void setMaxRows(String MaxRows) {
                this.MaxRows = MaxRows;
            }
        }
    }
}
