package com.reyes.netty.nio;

import java.nio.IntBuffer;

public class BufferStart {
	
	public static void main(String[] args) {
		IntBuffer intBuffer = IntBuffer.allocate(5);
		
//		放入內容
		for(int i=0; i<intBuffer.capacity(); i++) {
			intBuffer.put(i);
		}
		
//		改為讀取版本
		intBuffer.flip();
		
//		可以異動position位置
		intBuffer.position(1);
		
//		可以異動limit
		intBuffer.limit(2);
		
//		讀取
		while (intBuffer.hasRemaining()) {
			System.out.println(intBuffer.get());
		}
		
	}
	
}
