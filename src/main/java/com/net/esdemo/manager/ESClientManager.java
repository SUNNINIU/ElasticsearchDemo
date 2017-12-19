package com.net.esdemo.manager;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 作者 E-mail: issacgo@outlook.com
 * @date 创建时间：2017年12月19日 上午10:42:04
 * @explain ESClientManager管理类
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Component
public class ESClientManager {

	@Value("${es.name}")
	private String esName;

	@Value("${es.host}")
	private String host;

	@Value("${es.port}")
	private String port;

	@Value("${es.index}")
	private String index;

	public String getEsName() {
		return esName;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getIndex() {
		return index;
	}

	/**
	 * getClient
	 * @return
	 * @throws UnknownHostException
	 */
	public Client getClient() throws UnknownHostException {
		Client client = TransportClient
				.builder()
				.build()
				.addTransportAddress(
						new InetSocketTransportAddress(InetAddress
								.getByName(host), Integer.valueOf(port)));

		return client;

	}

}
