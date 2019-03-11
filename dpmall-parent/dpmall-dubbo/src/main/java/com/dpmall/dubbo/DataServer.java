package com.dpmall.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dpmall.common.IServer;

/**
 * 
 * dubbo本地启动器
 */
public class DataServer implements IServer {
	private static final Logger LOG = LoggerFactory.getLogger(DataServer.class);

	public int start() {
		if(LOG.isInfoEnabled()){
			LOG.info("{method:'PedigreeServer::start',msg:'starting.....'}");
		}
		com.alibaba.dubbo.container.Main.main(null);
		return 0;
	}

	public int stop() {
		if(LOG.isInfoEnabled()){
			LOG.info("{method:'PedigreeServer::stop',msg:'stopping.....'}");
		}
		return 0;
	}

	public static void main(String[] args) {
		com.alibaba.dubbo.container.Main.main(args);
	}

}
