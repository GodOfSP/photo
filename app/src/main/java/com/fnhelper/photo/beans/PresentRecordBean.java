package com.fnhelper.photo.beans;

import java.util.List;

public class PresentRecordBean {


    /**
     * code : 100
     * info : null
     * data : {"page":1,"total":1,"rows":[{"Number":1,"dDrawMoney":20,"dInsertTime":"2018-10-18 00:00:00","MaxRows":1}],"data":null}
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
         * page : 1
         * total : 1
         * rows : [{"Number":1,"dDrawMoney":20,"dInsertTime":"2018-10-18 00:00:00","MaxRows":1}]
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
             * Number : 1
             * dDrawMoney : 20.0
             * dInsertTime : 2018-10-18 00:00:00
             * MaxRows : 1
             */

            private String Number;
            private String dDrawMoney;
            private String dInsertTime;
            private int MaxRows;

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public String getDDrawMoney() {
                return dDrawMoney;
            }

            public void setDDrawMoney(String dDrawMoney) {
                this.dDrawMoney = dDrawMoney;
            }

            public String getDInsertTime() {
                return dInsertTime;
            }

            public void setDInsertTime(String dInsertTime) {
                this.dInsertTime = dInsertTime;
            }

            public int getMaxRows() {
                return MaxRows;
            }

            public void setMaxRows(int MaxRows) {
                this.MaxRows = MaxRows;
            }
        }
    }
}
