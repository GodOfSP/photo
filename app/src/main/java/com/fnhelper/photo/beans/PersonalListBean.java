package com.fnhelper.photo.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PersonalListBean implements Parcelable{

    /**
     * code : 100
     * info : null
     * data : {"page":1,"total":9,"rows":[{"Number":1,"ID":"67d6c16b4a714d73b060f751dad26296","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"环境保护","bIsTop":false,"iType":0,"sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-12/5280918526263069188.jpeg","sVideoUrl":"","sGoodsNo":"dhh","dCommodityPrices":2222,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-12 10:44:59","MaxRows":9},{"Number":2,"ID":"3c595ec27d0149b8be0ae900a7d76423","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"我黄金季节","bIsTop":false,"iType":0,"sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-11/5563412242346926569.jpeg,http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-11/4742018106098905856.jpeg,http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-11/5128748373024730824.jpeg","sVideoUrl":"","sGoodsNo":"233","dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":56,"iRetailpricesPrivate":0,"dTradePrices":5666,"iTradePricesPrivate":1,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":"衣服一分一分有点远","dInsertTime":"2018-11-11 13:49:04","MaxRows":9},{"Number":3,"ID":"9ae1cb84223d4149af5bc7e268ff13e6","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"fggg","bIsTop":false,"iType":0,"sImagesUrl":"","sVideoUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Video/2018-11-11/5651463179794807482.mp4","sGoodsNo":null,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-11 10:28:57","MaxRows":9},{"Number":4,"ID":"22b70b0f5e3047a8b7111b57896b7b77","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"环境保护","bIsTop":false,"iType":0,"sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-10/4946322311874773988.jpeg","sVideoUrl":"","sGoodsNo":null,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-10 22:54:36","MaxRows":9},{"Number":5,"ID":"a0eeb8e083514ed5a58df8ee6bc12680","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"v哥哥","bIsTop":false,"iType":0,"sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-10/5536825593320949139.jpeg","sVideoUrl":"","sGoodsNo":null,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-10 22:53:44","MaxRows":9}]}
     */

    private int code;
    private String info;
    private DataBean data;

    protected PersonalListBean(Parcel in) {
        code = in.readInt();
        info = in.readString();
    }

    public static final Creator<PersonalListBean> CREATOR = new Creator<PersonalListBean>() {
        @Override
        public PersonalListBean createFromParcel(Parcel in) {
            return new PersonalListBean(in);
        }

        @Override
        public PersonalListBean[] newArray(int size) {
            return new PersonalListBean[size];
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

    public static class DataBean implements Parcelable{
        /**
         * page : 1
         * total : 9
         * rows : [{"Number":1,"ID":"67d6c16b4a714d73b060f751dad26296","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"环境保护","bIsTop":false,"iType":0,"sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-12/5280918526263069188.jpeg","sVideoUrl":"","sGoodsNo":"dhh","dCommodityPrices":2222,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-12 10:44:59","MaxRows":9},{"Number":2,"ID":"3c595ec27d0149b8be0ae900a7d76423","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"我黄金季节","bIsTop":false,"iType":0,"sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-11/5563412242346926569.jpeg,http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-11/4742018106098905856.jpeg,http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-11/5128748373024730824.jpeg","sVideoUrl":"","sGoodsNo":"233","dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":56,"iRetailpricesPrivate":0,"dTradePrices":5666,"iTradePricesPrivate":1,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":"衣服一分一分有点远","dInsertTime":"2018-11-11 13:49:04","MaxRows":9},{"Number":3,"ID":"9ae1cb84223d4149af5bc7e268ff13e6","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"fggg","bIsTop":false,"iType":0,"sImagesUrl":"","sVideoUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Video/2018-11-11/5651463179794807482.mp4","sGoodsNo":null,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-11 10:28:57","MaxRows":9},{"Number":4,"ID":"22b70b0f5e3047a8b7111b57896b7b77","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"环境保护","bIsTop":false,"iType":0,"sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-10/4946322311874773988.jpeg","sVideoUrl":"","sGoodsNo":null,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-10 22:54:36","MaxRows":9},{"Number":5,"ID":"a0eeb8e083514ed5a58df8ee6bc12680","sClientId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sSourceId":"EC5AD1D4674E4EB7AA68DD5939BC5BD6","sContext":"v哥哥","bIsTop":false,"iType":0,"sImagesUrl":"http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-10/5536825593320949139.jpeg","sVideoUrl":"","sGoodsNo":null,"dCommodityPrices":null,"iCommodityPricesPrivate":0,"dRetailprices":null,"iRetailpricesPrivate":0,"dTradePrices":null,"iTradePricesPrivate":0,"dPackPrices":null,"iPackPricesPrivate":0,"sRemark":null,"dInsertTime":"2018-11-10 22:53:44","MaxRows":9}]
         */

        private int page;
        private int total;
        private List<RowsBean> rows;

        protected DataBean(Parcel in) {
            page = in.readInt();
            total = in.readInt();
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
        }

        public static class RowsBean implements Parcelable{
            /**
             * Number : 1
             * ID : 67d6c16b4a714d73b060f751dad26296
             * sClientId : EC5AD1D4674E4EB7AA68DD5939BC5BD6
             * sSourceId : EC5AD1D4674E4EB7AA68DD5939BC5BD6
             * sContext : 环境保护
             * bIsTop : false
             * iType : 0
             * sImagesUrl : http://153h79422m.imwork.net:10354/Material/EC5AD1D4674E4EB7AA68DD5939BC5BD6/Images/2018-11-12/5280918526263069188.jpeg
             * sVideoUrl :
             * sGoodsNo : dhh
             * dCommodityPrices : 2222.0
             * iCommodityPricesPrivate : 0
             * dRetailprices : null
             * iRetailpricesPrivate : 0
             * dTradePrices : null
             * iTradePricesPrivate : 0
             * dPackPrices : null
             * iPackPricesPrivate : 0
             * sRemark : null
             * dInsertTime : 2018-11-12 10:44:59
             * MaxRows : 9
             */

            private int Number;
            private String ID;
            private String sClientId;
            private String sSourceId;
            private String sContext;
            private boolean bIsTop;
            private String iType;
            private String sImagesUrl;
            private String sVideoUrl;
            private String sGoodsNo;
            private String dCommodityPrices;
            private int iCommodityPricesPrivate;
            private String dRetailprices;
            private int iRetailpricesPrivate;
            private String dTradePrices;
            private int iTradePricesPrivate;
            private String dPackPrices;
            private int iPackPricesPrivate;
            private String sRemark;
            private String dInsertTime;
            private int MaxRows;

            protected RowsBean(Parcel in) {
                Number = in.readInt();
                ID = in.readString();
                sClientId = in.readString();
                sSourceId = in.readString();
                sContext = in.readString();
                bIsTop = in.readByte() != 0;
                iType = in.readString();
                sImagesUrl = in.readString();
                sVideoUrl = in.readString();
                sGoodsNo = in.readString();
                dCommodityPrices = in.readString();
                iCommodityPricesPrivate = in.readInt();
                dRetailprices = in.readString();
                iRetailpricesPrivate = in.readInt();
                dTradePrices = in.readString();
                iTradePricesPrivate = in.readInt();
                dPackPrices = in.readString();
                iPackPricesPrivate = in.readInt();
                sRemark = in.readString();
                dInsertTime = in.readString();
                MaxRows = in.readInt();
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

            public int getNumber() {
                return Number;
            }

            public void setNumber(int Number) {
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

            public String getSContext() {
                return sContext;
            }

            public void setSContext(String sContext) {
                this.sContext = sContext;
            }

            public boolean isBIsTop() {
                return bIsTop;
            }

            public void setBIsTop(boolean bIsTop) {
                this.bIsTop = bIsTop;
            }

            public String getIType() {
                return iType;
            }

            public void setIType(String iType) {
                this.iType = iType;
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

            public String getSGoodsNo() {
                return sGoodsNo;
            }

            public void setSGoodsNo(String sGoodsNo) {
                this.sGoodsNo = sGoodsNo;
            }

            public String getDCommodityPrices() {
                return dCommodityPrices;
            }

            public void setDCommodityPrices(String dCommodityPrices) {
                this.dCommodityPrices = dCommodityPrices;
            }

            public int getICommodityPricesPrivate() {
                return iCommodityPricesPrivate;
            }

            public void setICommodityPricesPrivate(int iCommodityPricesPrivate) {
                this.iCommodityPricesPrivate = iCommodityPricesPrivate;
            }

            public String getDRetailprices() {
                return dRetailprices;
            }

            public void setDRetailprices(String dRetailprices) {
                this.dRetailprices = dRetailprices;
            }

            public int getIRetailpricesPrivate() {
                return iRetailpricesPrivate;
            }

            public void setIRetailpricesPrivate(int iRetailpricesPrivate) {
                this.iRetailpricesPrivate = iRetailpricesPrivate;
            }

            public String getDTradePrices() {
                return dTradePrices;
            }

            public void setDTradePrices(String dTradePrices) {
                this.dTradePrices = dTradePrices;
            }

            public int getITradePricesPrivate() {
                return iTradePricesPrivate;
            }

            public void setITradePricesPrivate(int iTradePricesPrivate) {
                this.iTradePricesPrivate = iTradePricesPrivate;
            }

            public String getDPackPrices() {
                return dPackPrices;
            }

            public void setDPackPrices(String dPackPrices) {
                this.dPackPrices = dPackPrices;
            }

            public int getIPackPricesPrivate() {
                return iPackPricesPrivate;
            }

            public void setIPackPricesPrivate(int iPackPricesPrivate) {
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

            public int getMaxRows() {
                return MaxRows;
            }

            public void setMaxRows(int MaxRows) {
                this.MaxRows = MaxRows;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(Number);
                dest.writeString(ID);
                dest.writeString(sClientId);
                dest.writeString(sSourceId);
                dest.writeString(sContext);
                dest.writeByte((byte) (bIsTop ? 1 : 0));
                dest.writeString(iType);
                dest.writeString(sImagesUrl);
                dest.writeString(sVideoUrl);
                dest.writeString(sGoodsNo);
                dest.writeString(dCommodityPrices);
                dest.writeInt(iCommodityPricesPrivate);
                dest.writeString(dRetailprices);
                dest.writeInt(iRetailpricesPrivate);
                dest.writeString(dTradePrices);
                dest.writeInt(iTradePricesPrivate);
                dest.writeString(dPackPrices);
                dest.writeInt(iPackPricesPrivate);
                dest.writeString(sRemark);
                dest.writeString(dInsertTime);
                dest.writeInt(MaxRows);
            }
        }
    }
}
