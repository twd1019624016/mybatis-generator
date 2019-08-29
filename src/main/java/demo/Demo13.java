package demo;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Demo13 {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		CreateGroup createGroup = new CreateGroup();
		createGroup.connect("localhost");
		createGroup.create("group");
		createGroup.close();
		
		Thread joinThread = new Thread() {

		    public volatile int state = 0;
			@Override
			public void run() {
				try {
					JoinGroup join = new JoinGroup();
					join.connect("localhost");
					join.join("group", "member");
					state = 1;
					Thread.sleep(Long.MAX_VALUE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		};
		
		joinThread.start();
		ListGroup listgroup = new ListGroup();
		listgroup.connect("localhost");
		
		//
		State state = joinThread.getState();
		while(state != State.TIMED_WAITING) {
			state = joinThread.getState();
		}
		listgroup.list("group");
		Thread.sleep(5000);
		
		joinThread.interrupt();
		
		Thread.sleep(5000);
		listgroup.list("group");
		listgroup.close();
		
		DeleteGroup deleteGroup = new DeleteGroup();
		deleteGroup.connect("localhost");
		deleteGroup.delete("group");
		deleteGroup.close();
		
	}
	
	
}

class CreateGroup implements Watcher {
	private static final int SESSION_TIMEOUT = 5000;
	private ZooKeeper zk;
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	@Override
	public void process(WatchedEvent event) {
		System.out.println(event);
		if(event.getState() == KeeperState.SyncConnected) {
			connectedSignal.countDown();
		}
		
	}
	
	public void close() throws InterruptedException {
		zk.close();
	}
	
	public void create(String groupName) throws KeeperException, InterruptedException {
		String path="/" + groupName;
		Stat exists = zk.exists(path,false);
		
		System.out.println(exists);
		
		if(null == exists) {
			zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("created:"+path);
			
		}
		
	}
	
	public void connect(String hosts) throws IOException, InterruptedException {
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		connectedSignal.await();
		
	}

}

class ConnectionWatcher implements Watcher {
	
private static final int SESSION_TIMEOUT=5000;
    
    protected ZooKeeper zk;
    CountDownLatch connectedSignal=new CountDownLatch(1);
    public void connect(String host) throws IOException, InterruptedException{
        zk=new ZooKeeper(host, SESSION_TIMEOUT, this);
        connectedSignal.await();
    }
    @Override
    public void process(WatchedEvent event) {
        if(event.getState()==KeeperState.SyncConnected){
            connectedSignal.countDown();
        }
    }
    public void close() throws InterruptedException{
        zk.close();
    }
	
}

class JoinGroup extends ConnectionWatcher {
	
	public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memberName;
		String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("create-join:" + createPath);
		
	}
}

class ListGroup extends ConnectionWatcher {
	public void list(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		
		List<String> children = zk.getChildren(path, false);
		if(children.isEmpty()) {
			System.out.println("no member");
		}
		for (String string : children) {
			System.out.println(string);
		}
	}
}

class DeleteGroup extends ConnectionWatcher {
	public void delete(String groupName) throws KeeperException, InterruptedException {
		
		String path = "/" + groupName;
		List<String> children = zk.getChildren(path, false);
		for (String string : children) {
			
			zk.delete(path+"/"+string, -1);
		}
		zk.delete(path, -1);
		
		
	}
}















