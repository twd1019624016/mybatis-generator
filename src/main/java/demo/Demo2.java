package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class Demo2 {

	public static void main(String[] args) throws SQLException {
		String connectionURL="jdbc:mysql://127.0.0.1:3306/wms-local?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL";
				String userId="root";
				String password="123456";
		Connection connection = DriverManager.getConnection(connectionURL, userId, password);
		ResultSet tables = connection.getMetaData().getTables(null, "wms-local", null, new String[] {"TABLE","VIEW"});
		ResultSetMetaData metaData = tables.getMetaData();
		   
		int columnCount = metaData.getColumnCount();
		System.out.println(columnCount);
		while(tables.next()) {
			 for(int i=1;i<=columnCount;i++)
			    {
			     System.out.print(tables.getString(i));
			     System.out.print("\t\t");
			    } 
			 System.out.println();
		}
		
		ResultSet columns = connection.getMetaData().getColumns(null, "wms-local", null, null);

		ResultSetMetaData metaData2 = tables.getMetaData();
		   
		int columnCount2 = metaData.getColumnCount();
		System.out.println(columnCount2);
		while(columns.next()) {
			 for(int i=1;i<=columnCount2;i++)
			    {
			     System.out.print(columns.getString(i));
			     System.out.print("\t\t");
			    } 
			 System.out.println();
		}
		
	}
}
