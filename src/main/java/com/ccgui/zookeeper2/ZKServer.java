package com.ccgui.zookeeper2;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Time;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class ZKServer {
	
	
	private static String connect = "192.168.91.151:2181,192.168.91.152:2181,192.168.91.153:2181";
	private static int timeout = 2000;
	private static ZooKeeper zooKeeper = null;
	private static String parentPath = "/servers";
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		//获取zookeeper的客户端
		getClient();
		//启动注册
		registerServer(args[0]);
		//业务逻辑
		business(args[0]);
	}
	
	

	private static void registerServer(String hostname) throws KeeperException, InterruptedException {
		// TODO Auto-generated method stub
		//创建临时节点
		String path = zooKeeper.create(parentPath + "/server", 
				hostname.getBytes(),
				ZooDefs.Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		System.out.println(hostname + " is online" + path);
	}
	

	
	private static void getClient() throws IOException {
		// TODO Auto-generated method stub
		zooKeeper = new ZooKeeper(connect, timeout, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.getType() + "------" + event.getPath());
			}
		});
	}
	
	private static void business(String hostname) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println(hostname + "is working...");	
		Thread.sleep(Long.MAX_VALUE);
	}

}
