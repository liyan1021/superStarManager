package com.liyan.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.liyan.superstar.model.StoreRequest;

public class SocketUtil {
	final static Logger logger = Logger.getLogger(SocketUtil.class);
	//生成一个SOKET发送与接收消息线程，用来告诉服务端 已发布哪些记录
	public static void pushSoket(String param, String ids) {
		
		final String methodName = param ;  
		final String t_ids = ids ; 
		new Thread(new Runnable(){

			@Override
			public void run(){
				try {
					
					//与服务端链接
					Socket socket = new Socket(Param.SERVER_IP,9001);
					//生成发送消息对象字符串
					StoreRequest storeRequest = new StoreRequest("", methodName, t_ids);
					String encodeString = storeRequest.toEncodeString();
					//发送消息
					OutputStream out = socket.getOutputStream();
					out.write(encodeString.getBytes(Param.CHARSET_UTF8));
					
					//接收消息
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String readLine = br.readLine();//从服务端读取发来的一行字符串；
					//写入日志
					byte[] decode = Base64.decode(readLine);
					readLine = new String(decode,Param.CHARSET_UTF8);
					System.out.println(readLine);
					logger.info(readLine);
					out.close();
					br.close();
					socket.close();
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
					logger.error(e.getStackTrace().toString());
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e.getStackTrace().toString());
				}
			}
		}).start();
	}

}
