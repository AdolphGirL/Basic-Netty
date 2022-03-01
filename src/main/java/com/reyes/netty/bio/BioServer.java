package com.reyes.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BioServer {
	
	/**
	 * 使用線程池機制模擬bio
	 * 
	 * 1. 創建線程池
	 * 2. 有客戶端，就創建一個線程，與其通訊
	 */
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BioServer.class); 
	
	public void mExcute() throws IOException {
//		創建線程池
		ExecutorService pools = Executors.newCachedThreadPool();
		
//		創建server socket
		ServerSocket serverSocket = new ServerSocket(6666);
		LOGGER.info("[+] [mExcute] server socket start ... ");
		
//		進行監聽
		while (true) {
			LOGGER.info("[+] [mExcute] 等待連接 ... ");
			
			final Socket socket = serverSocket.accept();
			LOGGER.info("[+] [mExcute] 一個連接完成 ... ");
//			LOGGER.info("[+] [mExcute] socket open ... ");
			
//			啟動一個線程
			pools.execute(new Runnable() {
				public void run() {
					handler(socket);
				}
			});
			
		}
	}
	
//	與客戶單通訊
	public void handler(Socket socket) {
		LOGGER.info("[+] [handler] 線程資料，id: {}，name: {} ", Thread.currentThread().getId(), Thread.currentThread().getName());
		
		
		byte[] bytes = null;
		InputStream inputStream = null;
		try {
			bytes = new byte[1024];
			inputStream = socket.getInputStream();
			
			while (true) {
				LOGGER.info("[+] [mExcute] 等待READ ... ");
				int read = inputStream.read(bytes);
				if (read != -1) {
					LOGGER.info("[+] [handler] client data: {} ", new String(bytes, 0, read));
				}
				
				break;
			}
		} catch (Exception e) {
			LOGGER.error("[+] [handler] error: {} ", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error("[+] [handler] close socket inputStream error: {} ", e);
				}
			}
			if(socket != null) {
				LOGGER.info("[+] [handler] socket close ... ");
				try {
					socket.close();
				} catch (IOException e) {
					LOGGER.error("[+] [handler] close socket error: {} ", e);
				}
			}
		}
	}
	
}
