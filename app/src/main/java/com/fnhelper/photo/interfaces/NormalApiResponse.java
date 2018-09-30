package com.fnhelper.photo.interfaces;

/**
 * <Pre>
 * 通用基础响应数据
 * <p>
 * 1.请求的接口地址为 http://330a10ec.nat123.cc:18395/open 加上接口详细地址
 * 2.所有的接口请求必须带上ID,和Token参数加上跟在请求连接后面，除了登录接口
 * 3.所有的接口返回的数据结构都是一致的 { code:100， info:'',data:{},}
 * code:请求接口的是否成功标识 100-成功，200-失败，300登录过期，400-帐号冻结，500--服务异常
 * info：信息描述 data：返回数据
 * </Pre>
 */
public class NormalApiResponse<T> {
    public String code;
    public String info;
    public T data;


    @Override
    public String toString() {
        return "NormalApiResponse{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }


}
