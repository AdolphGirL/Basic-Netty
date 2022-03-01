package com.reyes.netty;

import java.io.IOException;

import org.junit.Test;

import com.reyes.netty.bio.BioServer;

public class AppTest {
	
	@Test
	public void runBioServer() throws IOException {
		BioServer bioServer = new BioServer();
		bioServer.mExcute();
	}
	
}
