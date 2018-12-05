package com.fnhelper.photo.beans;

/**
 * Created by little fly on 2018-10-9.
 */

public class PhoneLoginBean {


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

    private String ID;
    private String sToken;
    private String sNickName;
    private String sPhone;
    private String sLinkPhone;
    private String sHeadImg;
    private String sWeiXinNo;
    private String sPhotoName;
    private String sIntroduce;
        /**
         * HtmlPageUrl : {"about":"http://153h79422m.imwork.net:10354/open/html/about","clientPower":"http://153h79422m.imwork.net:10354/open/html/clientPower","drawInstruction":"http://153h79422m.imwork.net:10354/open/html/drawInstruction","link":"http://153h79422m.imwork.net:10354/open/html/link","userAgreement":"http://153h79422m.imwork.net:10354/open/html/userAgreement"}
         */

        private HtmlPageUrlBean HtmlPageUrl;

        public String getsLinkPhone() {
            return sLinkPhone;
        }

        public void setsLinkPhone(String sLinkPhone) {
            this.sLinkPhone = sLinkPhone;
        }

        public String getID() {
        return ID;
    }



        public void setID(String ID) {
        this.ID = ID;
    }

    public String getsToken() {
        return sToken;
    }

    public void setsToken(String sToken) {
        this.sToken = sToken;
    }

    public String getsNickName() {
        return sNickName;
    }

    public void setsNickName(String sNickName) {
        this.sNickName = sNickName;
    }

    public String getsPhone() {
        return sPhone;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getsHeadImg() {
        return sHeadImg;
    }

    public void setsHeadImg(String sHeadImg) {
        this.sHeadImg = sHeadImg;
    }

    public String getsWeiXinNo() {
        return sWeiXinNo;
    }

    public void setsWeiXinNo(String sWeiXinNo) {
        this.sWeiXinNo = sWeiXinNo;
    }

    public String getsPhotoName() {
        return sPhotoName;
    }

    public void setsPhotoName(String sPhotoName) {
        this.sPhotoName = sPhotoName;
    }

    public String getsIntroduce() {
        return sIntroduce;
    }

    public void setsIntroduce(String sIntroduce) {
        this.sIntroduce = sIntroduce;
    }

        public HtmlPageUrlBean getHtmlPageUrl() {
            return HtmlPageUrl;
        }

        public void setHtmlPageUrl(HtmlPageUrlBean HtmlPageUrl) {
            this.HtmlPageUrl = HtmlPageUrl;
        }

        public static class HtmlPageUrlBean {
            /**
             * about : http://153h79422m.imwork.net:10354/open/html/about
             * clientPower : http://153h79422m.imwork.net:10354/open/html/clientPower
             * drawInstruction : http://153h79422m.imwork.net:10354/open/html/drawInstruction
             * link : http://153h79422m.imwork.net:10354/open/html/link
             * userAgreement : http://153h79422m.imwork.net:10354/open/html/userAgreement
             */

            private String about;
            private String clientPower;
            private String drawInstruction;
            private String link;
            private String userAgreement;
            private String shareLink;
            public String getShareLink() {
                return shareLink;
            }

            public void setShareLink(String shareLink) {
                this.shareLink = shareLink;
            }
            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getClientPower() {
                return clientPower;
            }

            public void setClientPower(String clientPower) {
                this.clientPower = clientPower;
            }

            public String getDrawInstruction() {
                return drawInstruction;
            }

            public void setDrawInstruction(String drawInstruction) {
                this.drawInstruction = drawInstruction;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getUserAgreement() {
                return userAgreement;
            }

            public void setUserAgreement(String userAgreement) {
                this.userAgreement = userAgreement;
            }
        }
    }}
