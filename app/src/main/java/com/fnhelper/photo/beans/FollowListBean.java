package com.fnhelper.photo.beans;

import java.util.List;

/**
 * Created by little fly on 2018-10-29.
 */

public class FollowListBean {

    /**
     * code : 100
     * info : null
     * data : {"page":1,"total":1,"rows":[{"Number":1,"sConcernId":"E3112B1193664750835C2BBAEEBEAF9C","sRemarkName":null,"dConcernTime":"2018-10-29 15:41:02","bIsVip":false,"sNickName":"好疯狂的石头A","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","ImageTextCount":0,"MaxRows":1}],"data":null}
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
         * rows : [{"Number":1,"sConcernId":"E3112B1193664750835C2BBAEEBEAF9C","sRemarkName":null,"dConcernTime":"2018-10-29 15:41:02","bIsVip":false,"sNickName":"好疯狂的石头A","sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","ImageTextCount":0,"MaxRows":1}]
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
             * sConcernId : E3112B1193664750835C2BBAEEBEAF9C
             * sRemarkName : null
             * dConcernTime : 2018-10-29 15:41:02
             * bIsVip : false
             * sNickName : 好疯狂的石头A
             * sHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132
             * ImageTextCount : 0
             * MaxRows : 1
             */

            private int Number;
            private String sConcernId;
            private String sRemarkName;
            private String dConcernTime;
            private boolean bIsVip;
            private String sNickName;
            private String sHeadImg;
            private int ImageTextCount;
            private int MaxRows;

            public int getNumber() {
                return Number;
            }

            public void setNumber(int Number) {
                this.Number = Number;
            }

            public String getSConcernId() {
                return sConcernId;
            }

            public void setSConcernId(String sConcernId) {
                this.sConcernId = sConcernId;
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

            public boolean isBIsVip() {
                return bIsVip;
            }

            public void setBIsVip(boolean bIsVip) {
                this.bIsVip = bIsVip;
            }

            public String getSNickName() {
                return sNickName;
            }

            public void setSNickName(String sNickName) {
                this.sNickName = sNickName;
            }

            public String getSHeadImg() {
                return sHeadImg;
            }

            public void setSHeadImg(String sHeadImg) {
                this.sHeadImg = sHeadImg;
            }

            public int getImageTextCount() {
                return ImageTextCount;
            }

            public void setImageTextCount(int ImageTextCount) {
                this.ImageTextCount = ImageTextCount;
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
