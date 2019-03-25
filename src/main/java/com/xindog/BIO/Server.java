package com.xindog.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


//代码清单 1-1
public class Server {


/*    ServerSocket 上的accept()方法将会一直阻塞到一个连接建立，随后返回一个
    新的Socket 用于客户端和服务器之间的通信。该 ServerSocket 将继续监听传入的
    连接。
    BufferedReader 和PrintWriter 都衍生自Socket 的输入输出流。前者从一个
    字符输入流中读取文本，后者打印对象的格式化的表示到文本输出流。
    readLine()方法将会阻塞，直到在处一个由换行符或者回车符结尾的字符串被
    读取。
    客户端的请求已经被处理。*/

    public static void main(String[] args) throws IOException {
        int portNumber = 9000;

        //创建一个新的ServerSocket，用以监听指定端口上的连接请求
        ServerSocket serverSocket = new ServerSocket(portNumber);
        // 对accept()方法的调用将被阻塞，直到一个连接建立
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(
                // 这些对象都派生于该套接字的流对象
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String request, response;


        // 开始处理循环
        while ((request = in.readLine()) !=null){
            // 如果客户端发送了"Done"，则退出处理循环
            if("Done".equals(request)){
                break;
            }
            // 请求被传递给服务器的处理方法
            response = processRequest(request);
            // 服务器的响应被发送给了客户端
            out.println(response);
        }

    }

    private static String processRequest(String request) {
        return "";
    }
}
