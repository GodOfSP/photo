package com.fnhelper.photo.beans;

public class UpdateBean {

    /**
     * state : true
     * msg :
     * data : {"version":"0.0.2","title":"更新测试","note":"更新测试","url":"http://www.baidu.com","rape":false}
     */

    private boolean state;
    private String msg;
    private DataBean data;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * version : 0.0.2
         * title : 更新测试
         * note : 更新测试
         * url : http://www.baidu.com
         * rape : false
         */

        private String version;
        private String title;
        private String note;
        private String url;
        private boolean rape;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isRape() {
            return rape;
        }

        public void setRape(boolean rape) {
            this.rape = rape;
        }
    }
}
