package com.fnhelper.photo.beans;

import java.util.List;

public class FansListBean {

    /**
     * code : 100
     * info : null
     * data : {"page":1,"total":2,"rows":[{"Number":1,"sClientId":"6B617AFA8784424FB234EA934D0BE991","bIsNotSeen":false,"sRemarkName":null,"dConcernTime":"2018-10-30 11:08:02","sNickName":"好疯狂的石头B","bIsVip":false,"dExpireTime":"2018-10-05 10:56:14","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","MaxRows":2},{"Number":2,"sClientId":"E3112B1193664750835C2BBAEEBEAF9C","bIsNotSeen":false,"sRemarkName":null,"dConcernTime":"2018-10-30 11:07:03","sNickName":"好疯狂的石头A","bIsVip":false,"dExpireTime":"2018-10-05 10:56:14","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","MaxRows":2}],"data":null}
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
         * total : 2
         * rows : [{"Number":1,"sClientId":"6B617AFA8784424FB234EA934D0BE991","bIsNotSeen":false,"sRemarkName":null,"dConcernTime":"2018-10-30 11:08:02","sNickName":"好疯狂的石头B","bIsVip":false,"dExpireTime":"2018-10-05 10:56:14","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","MaxRows":2},{"Number":2,"sClientId":"E3112B1193664750835C2BBAEEBEAF9C","bIsNotSeen":false,"sRemarkName":null,"dConcernTime":"2018-10-30 11:07:03","sNickName":"好疯狂的石头A","bIsVip":false,"dExpireTime":"2018-10-05 10:56:14","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","MaxRows":2}]
         * data : null
         */

        private int page;
        private int total;
        private Object data;
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

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
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
             * sClientId : 6B617AFA8784424FB234EA934D0BE991
             * bIsNotSeen : false
             * sRemarkName : null
             * dConcernTime : 2018-10-30 11:08:02
             * sNickName : 好疯狂的石头B
             * bIsVip : false
             * dExpireTime : 2018-10-05 10:56:14
             * sHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132
             * MaxRows : 2
             */

            private int Number;
            private String sClientId;
            private boolean bIsNotSeen;
            private String sRemarkName;
            private String dConcernTime;
            private String sNickName;
            private boolean bIsVip;
            private String dExpireTime;
            private String sHeadImg;
            private int MaxRows;

            public int getNumber() {
                return Number;
            }

            public void setNumber(int Number) {
                this.Number = Number;
            }

            public String getSClientId() {
                return sClientId;
            }

            public void setSClientId(String sClientId) {
                this.sClientId = sClientId;
            }

            public boolean isBIsNotSeen() {
                return bIsNotSeen;
            }

            public void setBIsNotSeen(boolean bIsNotSeen) {
                this.bIsNotSeen = bIsNotSeen;
            }

            public String getSRemarkName() {
                return sRemarkName;
            }

            public void setSRemarkName(String sRemarkName) {
                this.sRemarkName = sRemarkName;
            }

            public String getDConcernTime() {
                return dConcernTime;
            }

            public void setDConcernTime(String dConcernTime) {
                this.dConcernTime = dConcernTime;
            }

            public String getSNickName() {
                return sNickName;
            }

            public void setSNickName(String sNickName) {
                this.sNickName = sNickName;
            }

            public boolean isBIsVip() {
                return bIsVip;
            }

            public void setBIsVip(boolean bIsVip) {
                this.bIsVip = bIsVip;
            }

            public String getDExpireTime() {
                return dExpireTime;
            }

            public void setDExpireTime(String dExpireTime) {
                this.dExpireTime = dExpireTime;
            }

            public String getSHeadImg() {
                return sHeadImg;
            }

            public void setSHeadImg(String sHeadImg) {
                this.sHeadImg = sHeadImg;
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
