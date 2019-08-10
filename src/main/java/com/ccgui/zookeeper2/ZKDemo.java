package com.ccgui.zookeeper2;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class ZKDemo {
	private String connect = "192.168.91.151:2181,192.168.91.152:2181,192.168.91.153:2181";	
	private int timeout = 2000;
	public ZooKeeper zooKeeper = null;
	//获取zookeeper的客户端
	
	@Before
	public void getClient() throws IOException{
		 zooKeeper = new ZooKeeper(connect, timeout, new Watcher() {
		//接受到zookeeper发来的通知后，做出的处理措施（自己的业务的处理逻辑）	
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.getType() + "--------" + event.getPath());
			}
		});
	}
	//创建节点
	@Test
	public void testCreate() throws KeeperException, InterruptedException{
		String path = zooKeeper.create("/ccgui", 
				"ccgui".getBytes(),
				ZooDefs.Ids.OPEN_ACL_UNSAFE, 
				CreateMode.PERSISTENT);
		System.out.println(path);
	}
	
	/*	
	 * 查看节点是否存在
	*/
	@Test
	public void testExist() throws KeeperException, InterruptedException{
		Stat stat = zooKeeper.exists("/ccgui",false);
		System.out.println(stat == null ?"not exist":"exist");
	}
	
    /*
	查看是否有子节点
	*/
	
	@Test
	public void testList() throws KeeperException, InterruptedException{
	 List<String> children = zooKeeper.getChildren("/ccgui", true);
	 for (String child : children){
		 System.out.println(child);
	 } 
	 Thread.sleep(Long.MAX_VALUE);
	}
	
	/*
	 * 改变节点内容
	 *
	 */
	@Test
	public void testSet() throws KeeperException, InterruptedException{
		zooKeeper.setData("/ccgui/ccgui1", "no death".getBytes(), -1);
		System.out.println("data changed");
	}
	
	/*
	 * 获取节点数据
	 */
	@Test
	
	public void testGet() throws KeeperException, InterruptedException{
		byte[] data = zooKeeper.getData("/ccgui/ccgui1", true, null);
		
		System.out.println(new String(data));
		
		Thread.sleep(Long.MAX_VALUE);
	}
}
