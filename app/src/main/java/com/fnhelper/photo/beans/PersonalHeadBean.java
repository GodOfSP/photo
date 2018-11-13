package com.fnhelper.photo.beans;

public class PersonalHeadBean {

    /**
     * code : 100
     * info : null
     * data : {"sWeiXinNo":null,"sHeadImg":"http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132","sLinkPhone":null,"sIntroduce":"感觉怪怪发","imageCount":0,"videoCount":0}
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
         * sWeiXinNo : null
         * sHeadImg : http://thirdwx.qlogo.cn/mmopen/vi_32/qJdGU91ibBcvlCsBYnMNp0XLBeA2I4Fib4hDx2EickguIVBicPt4DQF4ldCLn0ALUF3t6QRQMVibwqCyQXlrBibnIJvA/132
         * sLinkPhone : null
         * sIntroduce : 感觉怪怪发
         * imageCount : 0
         * videoCount : 0
         */

        private String sWeiXinNo;
        private String sHeadImg;
        private String sLinkPhone;
        private String sIntroduce;
        private int imageCount;
        private int videoCount;

        public String getSWeiXinNo() {
            return sWeiXinNo;
        }

        public void setSWeiXinNo(String sWeiXinNo) {
            this.sWeiXinNo = sWeiXinNo;
        }

        public String  getSHeadImg() {
            return sHeadImg;
        }

        public void setSHeadImg(String sHeadImg) {
            this.sHeadImg = sHeadImg;
        }

        public String getSLinkPhone() {
            return sLinkPhone;
        }

        public void setSLinkPhone(String sLinkPhone) {
            this.sLinkPhone = sLinkPhone;
        }

        public String getSIntroduce() {
            return sIntroduce;
        }

        public void setSIntroduce(String sIntroduce) {
            this.sIntroduce = sIntroduce;
        }

        public int getImageCount() {
            return imageCount;
        }

        public void setImageCount(int imageCount) {
            this.imageCount = imageCount;
        }

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }
    }
}
