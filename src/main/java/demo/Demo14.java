package demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
/**
 * select * from `PROCESSLIST` where host like '%172.16.178.0%'
show variables like 'max_connections' 8000

show status like 'Threads_connected'
 * @author 1
 *
 */
public class Demo14 {
	
	public static void main(String[] args) throws SQLException {
		
		DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1/");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setMaxActive(1000);
       /* 
        Map<String, DataSource> dataSourceMap = new HashMap<>(2, 1);
        dataSourceMap.put("master_ds", dataSource);
        dataSourceMap.put("slave_ds", dataSource);
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig.setName("logic_ds");
        masterSlaveRuleConfig.setMasterDataSourceName("master_ds");
        masterSlaveRuleConfig.setSlaveDataSourceNames(Collections.singletonList("slave_ds"));
        Map<String, Object> configMap = new ConcurrentHashMap<>();
        configMap.put("key1", "value1");
        DataSource createDataSource = MasterSlaveDataSourceFactory
        		.createDataSource(dataSourceMap, masterSlaveRuleConfig, configMap);*/


        
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        
        int num = 1000;
        List<Connection> list = new ArrayList<>(num);
        for(int i=0; i <num; i++ ) {
        	list.add(dataSource.getConnection());
        	System.out.println(i);
        	
        }
        int numj = 1000;
        AtomicInteger j =new AtomicInteger(0);
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(1000);
        CountDownLatch latch = new CountDownLatch(1000);
        List<Statement> listj = new ArrayList<>(numj);
        for (Connection connection : list) {
        	newFixedThreadPool.submit(new Runnable() {
				
				@Override
				public void run() {
					try {
						latch.countDown();
						latch.await();
						Statement createStatement = connection.createStatement();
						createStatement.executeQuery("select 1");
						listj.add(createStatement);
					} catch (SQLException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	System.out.println("j:"+j.incrementAndGet());
					
				}
			});
        	
		}
		/*int num = 1000;
        List<Connection> list = new ArrayList<>(num);
        for(int i=0; i <num; i++ ) {
        	Connection connection = DriverManager.getConnection("jdbc:mysql://10.2.4.138:3306/sms_0", "qa", "qa123");
        	
        	list.add(connection);
        	System.out.println(i);
        }*/
        while(true) {
        	
        }
      
       
	}
}
