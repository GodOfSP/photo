package com.fnhelper.photo.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NewsListBean implements Parcelable {

    /**
     * code : 100
     * info : null
     * data : {"page":1,"total":4,"rows":[{"Number":1,"ID":"788752da234a4617a9545c2e1ef5fd71","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","iType":0,"sContext":"地球地球","sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-07/5426938914619803425.jpeg","sVideoUrl":"","bIsTop":false,"sGoodsNo":null,"iPrivate":0,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-07 14:56:29","dUpdateTime":null,"dShareTime":null,"bIsDeleted":false,"sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sNickName":"好疯狂的石头","MaxRows":4},{"Number":2,"ID":"788752da234a4617a9545c2e1ef5fd71","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","iType":0,"sContext":"地球地球","sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-07/5426938914619803425.jpeg","sVideoUrl":"","bIsTop":false,"sGoodsNo":null,"iPrivate":0,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-07 14:56:29","dUpdateTime":null,"dShareTime":null,"bIsDeleted":false,"sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sNickName":"好疯狂的石头","MaxRows":4}],"data":null}
     */

    private int code;
    private String info;
    private DataBean data;

    protected NewsListBean(Parcel in) {
        code = in.readInt();
        info = in.readString();
    }

    public static final Creator<NewsListBean> CREATOR = new Creator<NewsListBean>() {
        @Override
        public NewsListBean createFromParcel(Parcel in) {
            return new NewsListBean(in);
        }

        @Override
        public NewsListBean[] newArray(int size) {
            return new NewsListBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(info);
    }

    public static class DataBean  implements Parcelable{
        /**
         * page : 1
         * total : 4
         * rows : [{"Number":1,"ID":"788752da234a4617a9545c2e1ef5fd71","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","iType":0,"sContext":"地球地球","sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-07/5426938914619803425.jpeg","sVideoUrl":"","bIsTop":false,"sGoodsNo":null,"iPrivate":0,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-07 14:56:29","dUpdateTime":null,"dShareTime":null,"bIsDeleted":false,"sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sNickName":"好疯狂的石头","MaxRows":4},{"Number":2,"ID":"788752da234a4617a9545c2e1ef5fd71","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","iType":0,"sContext":"地球地球","sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-07/5426938914619803425.jpeg","sVideoUrl":"","bIsTop":false,"sGoodsNo":null,"iPrivate":0,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-07 14:56:29","dUpdateTime":null,"dShareTime":null,"bIsDeleted":false,"sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sNickName":"好疯狂的石头","MaxRows":4}]
         * data : null
         */

        private int page;
        private int total;
        private String data;
        private List<RowsBean> rows;

        protected DataBean(Parcel in) {
            page = in.readInt();
            total = in.readInt();
            data = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(page);
            dest.writeInt(total);
            dest.writeString(data);
        }

        public static class RowsBean  implements Parcelable{
            /**
             * Number : 1
             * ID : 788752da234a4617a9545c2e1ef5fd71
             * sClientId : EC5AD1D4674E4EB7AA68DD5939BC5BD6
             * sSourceId : EC5AD1D4674E4EB7AA68DD5939BC5BD6
             * iType : 0
             * sContext : 地球地球
             * sImagesUrl : http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-07/5426938914619803425.jpeg
             * sVideoUrl :
             * bIsTop : false
             * sGoodsNo : null
             * iPrivate : 0
             * dCommodityPrices : null
             * iCommodityPricesPrivate : 0
             * dRetailprices : null
             * iRetailpricesPrivate : 0
             * dTradePrices : null
             * iTradePricesPrivate : 0
             * dPackPrices : null
             * iPackPricesPrivate : 0
             * sRemark : null
             * dInsertTime : 2018-11-07 14:56:29
             * dUpdateTime : null
             * dShareTime : null
             * bIsDeleted : false
             * sHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132
             * sNickName : 好疯狂的石头
             * MaxRows : 4
             */

            private String Number;
            private String ID;
            private String sClientId;
            private String sSourceId;
            private String iType;
            private String sContext;
            private String sImagesUrl;
            private String sVideoUrl;
            private boolean bIsTop;
            private String sGoodsNo;
            private String iPrivate;
            private String dCommodityPrices;
            private String iCommodityPricesPrivate;
            private String dRetailprices;
            private String iRetailpricesPrivate;
            private String dTradePrices;
            private String iTradePricesPrivate;
            private String dPackPrices;
            private String iPackPricesPrivate;
            private String sRemark;
            private String dInsertTime;
            private String dUpdateTime;
            private String dShareTime;
            private boolean bIsDeleted;
            private String sHeadImg;
            private String sNickName;
            private int MaxRows;




            protected RowsBean(Parcel in) {
                Number = in.readString();
                ID = in.readString();
                sClientId = in.readString();
                sSourceId = in.readString();
                iType = in.readString();
                sContext = in.readString();
                sImagesUrl = in.readString();
                sVideoUrl = in.readString();
                bIsTop = in.readByte() != 0;
                sGoodsNo = in.readString();
                iPrivate = in.readString();
                dCommodityPrices = in.readString();
                iCommodityPricesPrivate = in.readString();
                dRetailprices = in.readString();
                iRetailpricesPrivate = in.readString();
                dTradePrices = in.readString();
                iTradePricesPrivate = in.readString();
                dPackPrices = in.readString();
                iPackPricesPrivate = in.readString();
                sRemark = in.readString();
                dInsertTime = in.readString();
                dUpdateTime = in.readString();
                dShareTime = in.readString();
                bIsDeleted = in.readByte() != 0;
                sHeadImg = in.readString();
                sNickName = in.readString();
                MaxRows = in.readInt();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(Number);
                dest.writeString(ID);
                dest.writeString(sClientId);
                dest.writeString(sSourceId);
                dest.writeString(iType);
                dest.writeString(sContext);
                dest.writeString(sImagesUrl);
                dest.writeString(sVideoUrl);
                dest.writeByte((byte) (bIsTop ? 1 : 0));
                dest.writeString(sGoodsNo);
                dest.writeString(iPrivate);
                dest.writeString(dCommodityPrices);
                dest.writeString(iCommodityPricesPrivate);
                dest.writeString(dRetailprices);
                dest.writeString(iRetailpricesPrivate);
                dest.writeString(dTradePrices);
                dest.writeString(iTradePricesPrivate);
                dest.writeString(dPackPrices);
                dest.writeString(iPackPricesPrivate);
                dest.writeString(sRemark);
                dest.writeString(dInsertTime);
                dest.writeString(dUpdateTime);
                dest.writeString(dShareTime);
                dest.writeByte((byte) (bIsDeleted ? 1 : 0));
                dest.writeString(sHeadImg);
                dest.writeString(sNickName);
                dest.writeInt(MaxRows);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
                @Override
                public RowsBean createFromParcel(Parcel in) {
                    return new RowsBean(in);
                }

                @Override
                public RowsBean[] newArray(int size) {
                    return new RowsBean[size];
                }
            };

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getSClientId() {
                return sClientId;
            }

            public void setSClientId(String sClientId) {
                this.sClientId = sClientId;
            }

            public String getSSourceId() {
                return sSourceId;
            }

            public void setSSourceId(String sSourceId) {
                this.sSourceId = sSourceId;
            }

            public String getIType() {
                return iType;
            }

            public void setIType(String iType) {
                this.iType = iType;
            }

            public String getSContext() {
                return sContext;
            }

            public void setSContext(String sContext) {
                this.sContext = sContext;
            }

            public String getSImagesUrl() {
                return sImagesUrl;
            }

            public void setSImagesUrl(String sImagesUrl) {
                this.sImagesUrl = sImagesUrl;
            }

            public String getSVideoUrl() {
                return sVideoUrl;
            }

            public void setSVideoUrl(String sVideoUrl) {
                this.sVideoUrl = sVideoUrl;
            }

            public boolean isBIsTop() {
                return bIsTop;
            }

            public void setBIsTop(boolean bIsTop) {
                this.bIsTop = bIsTop;
            }

            public String getSGoodsNo() {
                return sGoodsNo;
            }

            public void setSGoodsNo(String sGoodsNo) {
                this.sGoodsNo = sGoodsNo;
            }

            public String getIPrivate() {
                return iPrivate;
            }

            public void setIPrivate(String iPrivate) {
                this.iPrivate = iPrivate;
            }

            public String getDCommodityPrices() {
                return dCommodityPrices;
            }

            public void setDCommodityPrices(String dCommodityPrices) {
                this.dCommodityPrices = dCommodityPrices;
            }

            public String getICommodityPricesPrivate() {
                return iCommodityPricesPrivate;
            }

            public void setICommodityPricesPrivate(String iCommodityPricesPrivate) {
                this.iCommodityPricesPrivate = iCommodityPricesPrivate;
            }

            public String getDRetailprices() {
                return dRetailprices;
            }

            public void setDRetailprices(String dRetailprices) {
                this.dRetailprices = dRetailprices;
            }

            public String getIRetailpricesPrivate() {
                return iRetailpricesPrivate;
            }

            public void setIRetailpricesPrivate(String iRetailpricesPrivate) {
                this.iRetailpricesPrivate = iRetailpricesPrivate;
            }

            public String getDTradePrices() {
                return dTradePrices;
            }

            public void setDTradePrices(String dTradePrices) {
                this.dTradePrices = dTradePrices;
            }

            public String getITradePricesPrivate() {
                return iTradePricesPrivate;
            }

            public void setITradePricesPrivate(String iTradePricesPrivate) {
                this.iTradePricesPrivate = iTradePricesPrivate;
            }

            public String getDPackPrices() {
                return dPackPrices;
            }

            public void setDPackPrices(String dPackPrices) {
                this.dPackPrices = dPackPrices;
            }

            public String getIPackPricesPrivate() {
                return iPackPricesPrivate;
            }

            public void setIPackPricesPrivate(String iPackPricesPrivate) {
                this.iPackPricesPrivate = iPackPricesPrivate;
            }

            public String getSRemark() {
                return sRemark;
            }

            public void setSRemark(String sRemark) {
                this.sRemark = sRemark;
            }

            public String getDInsertTime() {
                return dInsertTime;
            }

            public void setDInsertTime(String dInsertTime) {
                this.dInsertTime = dInsertTime;
            }

            public String getDUpdateTime() {
                return dUpdateTime;
            }

            public void setDUpdateTime(String dUpdateTime) {
                this.dUpdateTime = dUpdateTime;
            }

            public String getDShareTime() {
                return dShareTime;
            }

            public void setDShareTime(String dShareTime) {
                this.dShareTime = dShareTime;
            }

            public boolean isBIsDeleted() {
                return bIsDeleted;
            }

            public void setBIsDeleted(boolean bIsDeleted) {
                this.bIsDeleted = bIsDeleted;
            }

            public String getSHeadImg() {
                return sHeadImg;
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

            public int getMaxRows() {
                return MaxRows;
            }

            public void setMaxRows(int MaxRows) {
                this.MaxRows = MaxRows;
            }
        }
    }
}
