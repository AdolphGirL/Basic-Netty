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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BioServer.class); 
	
	public void mExcute() throws IOException {
		ExecutorService pools = Executors.newCachedThreadPool();
		
		ServerSocket serverSocket = new ServerSocket(6666);
		LOGGER.info("[+] [mExcute] server socket start ... ");
		
		while (true) {
			LOGGER.info("[+] [mExcute] ���ݳs�� ... ");
			
			final Socket socket = serverSocket.accept();
			LOGGER.info("[+] [mExcute] �@�ӳs������ ... ");
//			LOGGER.info("[+] [mExcute] socket open ... ");
			
			pools.execute(new Runnable() {
				public void run() {
					handler(socket);
				}
			});
			
		}
	}
	
	public void handler(Socket socket) {
		LOGGER.info("[+] [handler] �u�{��ơAid: {}�Aname: {} ", Thread.currentThread().getId(), Thread.currentThread().getName());
		
		
		byte[] bytes = null;
		InputStream inputStream = null;
		try {
			bytes = new byte[1024];
			inputStream = socket.getInputStream();
			
			while (true) {
				LOGGER.info("[+] [mExcute] ����READ ... ");
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
