package com.xindog.Future;

public class FutureData implements Data {

    protected RealData realdata = null;//FutureData是RealData的包装
    protected boolean isReady = false;

    public synchronized void setRealData(RealData realdata) {
        if (isReady) {
            return;
        }
        this.realdata = realdata;
        isReady = true;
        notifyAll();//RealData已经被注入，通知getResult()
    }

    @Override
    public synchronized String getResult() {  //会等待RealData构造完成
        // TODO Auto-generated method stub
        while (!isReady) {
            try {
                wait();     //一直等待，知道RealData被注入
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        return realdata.result;
    }

}
