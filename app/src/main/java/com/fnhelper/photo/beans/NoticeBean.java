package com.fnhelper.photo.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NoticeBean implements Parcelable{

    /**
     * code : 100
     * info : null
     * data : {"page":1,"total":1,"rows":[{"Number":1,"ID":"689e004b7f234353ae2388a41a9d3a7f","sTitle":"没考虑","sContent":"房间数量的咖啡机萨拉丁发生房价快速的拉法基开发类似大家发","dInsertTime":"2018-11-01 11:36:09","MaxRows":1}],"data":null}
     */

    private int code;
    private String info;
    private DataBean data;

    protected NoticeBean(Parcel in) {
        code = in.readInt();
        info = in.readString();
    }

    public static final Creator<NoticeBean> CREATOR = new Creator<NoticeBean>() {
        @Override
        public NoticeBean createFromParcel(Parcel in) {
            return new NoticeBean(in);
        }

        @Override
        public NoticeBean[] newArray(int size) {
            return new NoticeBean[size];
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
         * total : 1
         * rows : [{"Number":1,"ID":"689e004b7f234353ae2388a41a9d3a7f","sTitle":"没考虑","sContent":"房间数量的咖啡机萨拉丁发生房价快速的拉法基开发类似大家发","dInsertTime":"2018-11-01 11:36:09","MaxRows":1}]
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
            rows = in.createTypedArrayList(RowsBean.CREATOR);
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
            dest.writeTypedList(rows);
        }

        public static class RowsBean implements Parcelable{
            /**
             * Number : 1
             * ID : 689e004b7f234353ae2388a41a9d3a7f
             * sTitle : 没考虑
             * sContent : 房间数量的咖啡机萨拉丁发生房价快速的拉法基开发类似大家发
             * dInsertTime : 2018-11-01 11:36:09
             * MaxRows : 1
             */

            private int Number;
            private String ID;
            private String sTitle;
            private String sContent;
            private String dInsertTime;
            private int MaxRows;

            protected RowsBean(Parcel in) {
                Number = in.readInt();
                ID = in.readString();
                sTitle = in.readString();
                sContent = in.readString();
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

            public String getSTitle() {
                return sTitle;
            }

            public void setSTitle(String sTitle) {
                this.sTitle = sTitle;
            }

            public String getSContent() {
                return sContent;
            }

            public void setSContent(String sContent) {
                this.sContent = sContent;
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
                dest.writeString(sTitle);
                dest.writeString(sContent);
                dest.writeString(dInsertTime);
                dest.writeInt(MaxRows);
            }
        }
    }
}
