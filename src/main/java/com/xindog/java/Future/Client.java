package com.xindog.java.Future;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        //这里会立即返回，因为得到FutureData，而不是RealData
        Data data = client.request("name");
        System.out.println("请求完毕");
        try {
            //这里可以用一个sleep代替对其他业务逻辑的处理
            //在处理这些业务逻辑中，RealData被创建，从而充分利用了等待时间
            Thread.sleep(2000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("数据=" + data.getResult());
    }

    public Data request(final String queryStr) {
        final FutureData future = new FutureData();//实现获取FutureData
        new Thread(() -> {    //RealData的构造很慢，所以在单独的线程中进行
            RealData realdata = new RealData(queryStr);
            future.setRealData(realdata);
        }).start();
        return future;//FutureData会被立即返回
    }

}
