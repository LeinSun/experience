package com.zookeeper.watcher.zookeeperwatcher.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author: sunlei
 * @date: 2022/3/22
 * @description:
 */
public class ZookeeperWatcher implements Watcher {
    // 集群连接地址
    private static final String CONNECT_ADDRES = "10.101.106.11:2181";
    // 会话超时时间
    private static final int SESSIONTIME = 2000;
    // 信号量,zk连接异步，用于阻塞，保证所有的操作都在zk创建连接成功以后再执行
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);
    private ZooKeeper zk;

    public void createConnection(String connectAddres, int sessionTimeOut) {
        try {
            zk = new ZooKeeper(connectAddres, sessionTimeOut, this);
            System.out.println("LOG" + "zk 开始启动连接服务器....");
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createPath(String path, String data) {
        try {
            this.exists(path, true);
            // 创建持久节点
            this.zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("LOG" + "节点创建成功, Path:" + path + ",data:" + data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断指定节点是否存在 不存在该节点返回null
     *
     * @param path
     *            节点路径
     */
    public Stat exists(String path, boolean needWatch) {
        try {
            return this.zk.exists(path, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateNode(String path, String data) throws KeeperException, InterruptedException {
        exists(path, true);
        this.zk.setData(path, data.getBytes(), -1);
        return false;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        // 获取事件状态
        Event.KeeperState keeperState = watchedEvent.getState();
        // 获取事件类型
        Event.EventType eventType = watchedEvent.getType();
        // zk 路径
        String path = watchedEvent.getPath();
        // 判断是否建立连接
        if (Event.KeeperState.SyncConnected == keeperState) {
            // 如果当前状态已经连接上了 SyncConnected：连接，AuthFailed：认证失败,Expired:失效过期,
            // ConnectedReadOnly:连接只读,Disconnected:连接失败
            if (Event.EventType.None == eventType) {
                // 如果建立建立成功,让后程序往下走
                System.out.println("LOG" + "zk 建立连接成功!");
                countDownLatch.countDown();
            } else if (Event.EventType.NodeCreated == eventType) {
                System.out.println("LOG" + "事件通知,新增node节点" + path);
            } else if (Event.EventType.NodeDataChanged == eventType) {
                System.out.println("LOG" + "事件通知,当前node节点" + path + "被修改....");
            } else if (Event.EventType.NodeDeleted == eventType) {
                System.out.println("LOG" + "事件通知,当前node节点" + path + "被删除....");
            }

        }
    }
}
