package com.fnhelper.photo.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 动态详情
 */
public class NewDetailBean implements Parcelable{

    /**
     * code : 100
     * info : null
     * data : {"ID":"98c3cb8f6e634c3cb3e7ac7a9e1fd87d","sClientId":"3F170FA73E7F4682AA60BE20B342D908","sSourceId":"98c3cb8f6e634c3cb3e7ac7a9e1fd87d","iType":1,"sContext":"视频成都","sImagesUrl":"","sVideoUrl":"http://153h79422m.imwork.net:10354/Material/3F170FA73E7F4682AA60BE20B342D908/Video/2018-11-28/5715339453460686883.mp4","bIsTop":false,"sGoodsNo":null,"dCommodityPrices":null,"dRetailprices":null,"dTradePrices":null,"dPackPrices":null,"sRemark":null,"dInsertTime":"2018-11-28 10:18:42","dUpdateTime":null,"dShareTime":null,"bIsDeleted":false,"iCommodityPricesPrivate":0,"iRetailpricesPrivate":0,"iTradePricesPrivate":0,"iPackPricesPrivate":0,"iPrivate":0,"sVideoImageUrl":"http://153h79422m.imwork.net:10354/Material/3F170FA73E7F4682AA60BE20B342D908/Video/2018-11-28/5715339453460686883.jpg"}
     */

    private int code;
    private String info;
    private DataBean data;

    protected NewDetailBean(Parcel in) {
        code = in.readInt();
        info = in.readString();
    }

    public static final Creator<NewDetailBean> CREATOR = new Creator<NewDetailBean>() {
        @Override
        public NewDetailBean createFromParcel(Parcel in) {
            return new NewDetailBean(in);
        }

        @Override
        public NewDetailBean[] newArray(int size) {
            return new NewDetailBean[size];
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
         * ID : 98c3cb8f6e634c3cb3e7ac7a9e1fd87d
         * sClientId : 3F170FA73E7F4682AA60BE20B342D908
         * sSourceId : 98c3cb8f6e634c3cb3e7ac7a9e1fd87d
         * iType : 1
         * sContext : 视频成都
         * sImagesUrl : 
         * sVideoUrl : http://153h79422m.imwork.net:10354/Material/3F170FA73E7F4682AA60BE20B342D908/Video/2018-11-28/5715339453460686883.mp4
         * bIsTop : false
         * sGoodsNo : null
         * dCommodityPrices : null
         * dRetailprices : null
         * dTradePrices : null
         * dPackPrices : null
         * sRemark : null
         * dInsertTime : 2018-11-28 10:18:42
         * dUpdateTime : null
         * dShareTime : null
         * bIsDeleted : false
         * iCommodityPricesPrivate : 0
         * iRetailpricesPrivate : 0
         * iTradePricesPrivate : 0
         * iPackPricesPrivate : 0
         * iPrivate : 0
         * sVideoImageUrl : http://153h79422m.imwork.net:10354/Material/3F170FA73E7F4682AA60BE20B342D908/Video/2018-11-28/5715339453460686883.jpg
         */

        private String sHeadImg;
        private String sNickName;
        private String ID;
        private String sClientId;
        private String sSourceId;
        private int iType;
        private String sContext;
        private String sImagesUrl;
        private String sVideoUrl;
        private boolean bIsTop;
        private String sGoodsNo;
        private String dCommodityPrices;
        private String dRetailprices;
        private String dTradePrices;
        private String dPackPrices;
        private String sRemark;
        private String dInsertTime;
        private String dUpdateTime;
        private String dShareTime;
        private boolean bIsDeleted;
        private int iCommodityPricesPrivate;
        private int iRetailpricesPrivate;
        private int iTradePricesPrivate;
        private int iPackPricesPrivate;
        private int iPrivate;
        private String sVideoImageUrl;

        protected DataBean(Parcel in) {
            sHeadImg = in.readString();
            sNickName = in.readString();
            ID = in.readString();
            sClientId = in.readString();
            sSourceId = in.readString();
            iType = in.readInt();
            sContext = in.readString();
            sImagesUrl = in.readString();
            sVideoUrl = in.readString();
            bIsTop = in.readByte() != 0;
            sGoodsNo = in.readString();
            dCommodityPrices = in.readString();
            dRetailprices = in.readString();
            dTradePrices = in.readString();
            dPackPrices = in.readString();
            sRemark = in.readString();
            dInsertTime = in.readString();
            dUpdateTime = in.readString();
            dShareTime = in.readString();
            bIsDeleted = in.readByte() != 0;
            iCommodityPricesPrivate = in.readInt();
            iRetailpricesPrivate = in.readInt();
            iTradePricesPrivate = in.readInt();
            iPackPricesPrivate = in.readInt();
            iPrivate = in.readInt();
            sVideoImageUrl = in.readString();
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

        public String getsHeadImg() {
            return sHeadImg;
        }

        public void setsHeadImg(String sHeadImg) {
            this.sHeadImg = sHeadImg;
        }

        public String getsNickName() {
            return sNickName;
        }

        public void setsNickName(String sNickName) {
            this.sNickName = sNickName;
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

        public int getIType() {
            return iType;
        }

        public void setIType(int iType) {
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

        public String getDCommodityPrices() {
            return dCommodityPrices;
        }

        public void setDCommodityPrices(String dCommodityPrices) {
            this.dCommodityPrices = dCommodityPrices;
        }

        public String getDRetailprices() {
            return dRetailprices;
        }

        public void setDRetailprices(String dRetailprices) {
            this.dRetailprices = dRetailprices;
        }

        public String getDTradePrices() {
            return dTradePrices;
        }

        public void setDTradePrices(String dTradePrices) {
            this.dTradePrices = dTradePrices;
        }

        public String getDPackPrices() {
            return dPackPrices;
        }

        public void setDPackPrices(String dPackPrices) {
            this.dPackPrices = dPackPrices;
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

        public int getICommodityPricesPrivate() {
            return iCommodityPricesPrivate;
        }

        public void setICommodityPricesPrivate(int iCommodityPricesPrivate) {
            this.iCommodityPricesPrivate = iCommodityPricesPrivate;
        }

        public int getIRetailpricesPrivate() {
            return iRetailpricesPrivate;
        }

        public void setIRetailpricesPrivate(int iRetailpricesPrivate) {
            this.iRetailpricesPrivate = iRetailpricesPrivate;
        }

        public int getITradePricesPrivate() {
            return iTradePricesPrivate;
        }

        public void setITradePricesPrivate(int iTradePricesPrivate) {
            this.iTradePricesPrivate = iTradePricesPrivate;
        }

        public int getIPackPricesPrivate() {
            return iPackPricesPrivate;
        }

        public void setIPackPricesPrivate(int iPackPricesPrivate) {
            this.iPackPricesPrivate = iPackPricesPrivate;
        }

        public int getIPrivate() {
            return iPrivate;
        }

        public void setIPrivate(int iPrivate) {
            this.iPrivate = iPrivate;
        }

        public String getSVideoImageUrl() {
            return sVideoImageUrl;
        }

        public void setSVideoImageUrl(String sVideoImageUrl) {
            this.sVideoImageUrl = sVideoImageUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(sHeadImg);
            dest.writeString(sNickName);
            dest.writeString(ID);
            dest.writeString(sClientId);
            dest.writeString(sSourceId);
            dest.writeInt(iType);
            dest.writeString(sContext);
            dest.writeString(sImagesUrl);
            dest.writeString(sVideoUrl);
            dest.writeByte((byte) (bIsTop ? 1 : 0));
            dest.writeString(sGoodsNo);
            dest.writeString(dCommodityPrices);
            dest.writeString(dRetailprices);
            dest.writeString(dTradePrices);
            dest.writeString(dPackPrices);
            dest.writeString(sRemark);
            dest.writeString(dInsertTime);
            dest.writeString(dUpdateTime);
            dest.writeString(dShareTime);
            dest.writeByte((byte) (bIsDeleted ? 1 : 0));
            dest.writeInt(iCommodityPricesPrivate);
            dest.writeInt(iRetailpricesPrivate);
            dest.writeInt(iTradePricesPrivate);
            dest.writeInt(iPackPricesPrivate);
            dest.writeInt(iPrivate);
            dest.writeString(sVideoImageUrl);
        }
    }
}
