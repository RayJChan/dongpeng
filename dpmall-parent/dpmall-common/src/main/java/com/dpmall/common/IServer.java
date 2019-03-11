package com.dpmall.common;

/**
 * <p>
 * 服务器接口
 * 
 * @author river
 * @since 2017-05-01
 */
public interface IServer {
	
	/**
	 * <p>
	 * 启动服务器
	 * @return
	 */
	public int start();
	
	/**
	 * <p>
	 * 停止服务器
	 * @return
	 */
	public int stop();

}
