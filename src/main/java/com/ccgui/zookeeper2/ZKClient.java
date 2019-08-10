package com.ccgui.zookeeper2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZKClient {
	private static String connect = "192.168.91.151:2181,192.168.91.151:2181,192.168.91.151:2181";
	private static int timeout = 2000;
	private static ZooKeeper zooKeeper = null;
	private static String parentPath = "/servers";
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {		
		//��ȡzookeeper�Ŀͻ���
		getClient();
		//��ȡ�������б�,������
		getServers();
		//ҵ���߼�
		bussiness();
	}

	private static void bussiness() throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Client is working...");
		Thread.sleep(Long.MAX_VALUE);
	}

	private static void getServers() throws KeeperException, InterruptedException {
		// TODO Auto-generated method stub
		 List<String> children = zooKeeper.getChildren(parentPath, true);
		 //����װ��������������
		 ArrayList<String> hosts = new ArrayList<>();
		 for (String child : children){
			byte[] data = zooKeeper.getData(parentPath + "/" + child, false, null);
			hosts.add(new String(data));
		 }
		 System.out.println(hosts);
	}

	private static void getClient() throws IOException {
		// TODO Auto-generated method stub
		zooKeeper = new ZooKeeper(connect, timeout, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				
				System.out.println(event.getType() + "--------" + event.getPath());
				//���»�ȡ�ͻ��˵��б�
				try {
					getServers();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}
}
