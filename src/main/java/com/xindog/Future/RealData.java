package com.xindog.Future;

public class RealData implements Data {

    protected final String result;// protected可访问同包的元素

    public RealData(String para) {
        // RealData的构造可能很慢，需要用户等待好久，这里使用sleep模拟
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        result = sb.toString();
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub

        return result;
    }

}
