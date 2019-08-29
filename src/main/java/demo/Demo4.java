package demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.common.io.Files;

public class Demo4 {

	private Connection connection;

	public static void main(String[] args) throws SQLException, IOException {
		String connectionURL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL";
		String userId = "root";
		String password = "123456";
		Connection connection = DriverManager.getConnection(connectionURL, userId, password);

		Statement createStatement = connection.createStatement();
		long currentTimeMillis = System.currentTimeMillis();
		ResultSet resultSet = createStatement.executeQuery("select * from tprj_chukudan_items as a1 "
				+ " inner join (select id from tprj_chukudan_items order by id limit 1000 ) a2 on a1.id = a2.id");

		long currentTimeMillis2 = System.currentTimeMillis();
		System.out.println(currentTimeMillis2 - currentTimeMillis);
		Long count = 0L;
		/*
		 * ResultSetMetaData metaData = resultSet.getMetaData(); int columnCount =
		 * metaData.getColumnCount(); for(int i= 1;i<=columnCount;i++) {
		 * System.out.println(metaData.getColumnName(i)); }
		 */

		StringBuilder stringBuilder = new StringBuilder("replace into tprj_chukudan_items (id,ckd_id) values");
		if (resultSet.next()) {
			resultSet.getObject(1);

			stringBuilder.append("(");
			stringBuilder.append(resultSet.getObject("id"));
			stringBuilder.append(",");
			stringBuilder.append(666666);
			stringBuilder.append(")");

			count++;
		}
		while (resultSet.next() && count < 1000) {
			resultSet.getObject(1);
			stringBuilder.append(",");
			stringBuilder.append("(");
			stringBuilder.append(resultSet.getObject("id"));
			stringBuilder.append(",");
			stringBuilder.append(666666);
			stringBuilder.append(")");
			count++;
		}

		// stringBuilder.
		Files.write(stringBuilder.toString(), new File("C:\\Users\\1\\Desktop\\cc.json"), Charset.forName("utf8"));

		long currentTimeMillis3 = System.currentTimeMillis();
		System.out.println(currentTimeMillis3 - currentTimeMillis2);

		StringBuilder stringBuilder2 = new StringBuilder("UPDATE  tprj_chukudan_items set ckd_id = case id ");
		StringBuilder stringBuilderwhere = new StringBuilder(" where id in ( ");
		count = 0L;
		while (resultSet.next() && count < 1000) {
			resultSet.getObject(1);
			stringBuilder2.append(" WHEN ");
			stringBuilder2.append(resultSet.getObject("id"));
			stringBuilder2.append(" THEN ");
			stringBuilder2.append(666666);

			stringBuilderwhere.append(resultSet.getObject("id")).append(",");
			count++;
		}
		stringBuilderwhere.append(")");
		stringBuilder2.append(" end ").append(stringBuilderwhere.toString());
		Files.write(stringBuilder2.toString(), new File("C:\\Users\\1\\Desktop\\cciii.json"), Charset.forName("utf8"));

		long currentTimeMillis4 = System.currentTimeMillis();
		System.out.println(currentTimeMillis4 - currentTimeMillis3);
		// insert into test_tbl (id,dr) values (1,'2'),(2,'3'),...(x,'y') on duplicate
		// key update dr=values(dr);
		System.err.println("-----------------");
		/*
		 * int update = createStatement.executeUpdate(stringBuilder.toString());
		 * System.out.println(update);
		 */

		// replace into test_tbl (id,dr) values (1,'2'),(2,'3'),...(x,'y');
		System.out.println(count);

		resultSet.close();
		createStatement.close();
		connection.close();
	}

	@Test
	public void testeee() {

	}

	@Test
	public void testInsertIntoOnduplicatupdate() throws SQLException, IOException {

		Statement createStatement = connection.createStatement();

		ResultSet resultSet = createStatement.executeQuery("select * from tprj_chukudan_items as a1 "
				+ " inner join (select id from tprj_chukudan_items order by id limit 1000000 ) a2 on a1.id = a2.id");

		StringBuilder stringBuilder = new StringBuilder("insert into tprj_chukudan_items (id,ckd_id) values");

		if (resultSet.next()) {
			resultSet.getObject(1);

			stringBuilder.append("(");
			stringBuilder.append(resultSet.getObject("id"));
			stringBuilder.append(",");
			stringBuilder.append(333333);
			stringBuilder.append(")");

		}

		while (resultSet.next()) {
			resultSet.getObject(1);
			stringBuilder.append(",");
			stringBuilder.append("(");
			stringBuilder.append(resultSet.getObject("id"));
			stringBuilder.append(",");
			stringBuilder.append(333333);
			stringBuilder.append(")");

		}
		stringBuilder.append("on duplicate key update ckd_id=values(ckd_id)");

		long currentTimeMillis = System.currentTimeMillis();
		Files.write(stringBuilder.toString(), new File("C:\\Users\\1\\Desktop\\cciii.json"), Charset.forName("utf8"));

		int executeUpdate = createStatement.executeUpdate(stringBuilder.toString());
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		System.out.println(executeUpdate);

		resultSet.close();
		createStatement.close();

	}

	@Test
	public void testInsertIntoOnduplicatupdatedivide100() throws SQLException, IOException {

		Statement createStatement = connection.createStatement();
		Statement createStatement2 = connection.createStatement();

		ResultSet resultSet = createStatement2.executeQuery("select * from tprj_chukudan_items as a1 "
				+ " inner join (select id from tprj_chukudan_items order by id limit 1000000 ) a2 on a1.id = a2.id");

		long count = 0L;
		long time = 0L;
		while (resultSet.next()) {
			StringBuilder stringBuilder = new StringBuilder("insert into tprj_chukudan_items (id,ckd_id) values");
			resultSet.getObject(1);

			stringBuilder.append("(");
			stringBuilder.append(resultSet.getObject("id"));
			stringBuilder.append(",");
			stringBuilder.append(33356);
			stringBuilder.append(")");
			count++;

			while (resultSet.next() && count % 1000 != 0) {
				resultSet.getObject(1);
				stringBuilder.append(",");
				stringBuilder.append("(");
				stringBuilder.append(resultSet.getObject("id"));
				stringBuilder.append(",");
				stringBuilder.append(33356);
				stringBuilder.append(")");
				count++;

			}
			stringBuilder.append("on duplicate key update ckd_id=values(ckd_id)");

			long currentTimeMillis = System.currentTimeMillis();
			Files.write(stringBuilder.toString(), new File("C:\\Users\\1\\Desktop\\cciii.json"),
					Charset.forName("utf8"));

			int executeUpdate = createStatement.executeUpdate(stringBuilder.toString());
			System.out.println(System.currentTimeMillis() - currentTimeMillis);
			System.out.println(executeUpdate);
			time += System.currentTimeMillis() - currentTimeMillis;
			count = 0L;
		}

		System.out.println(time);

		resultSet.close();
		createStatement.close();
	}

	@Test
	public void testreplaceInto() throws SQLException {

		Statement createStatement = connection.createStatement();

		ResultSet resultSet = createStatement.executeQuery("select * from tprj_chukudan_items as a1 "
				+ " inner join (select id from tprj_chukudan_items order by id limit 1000000 ) a2 on a1.id = a2.id");

		StringBuilder stringBuilder = new StringBuilder("replace into tprj_chukudan_items (id,ckd_id) values");
		if (resultSet.next()) {
			resultSet.getObject(1);
			stringBuilder.append("(");
			stringBuilder.append(resultSet.getObject("id"));
			stringBuilder.append(",");
			stringBuilder.append(666666);
			stringBuilder.append(")");

		}
		while (resultSet.next()) {
			resultSet.getObject(1);
			stringBuilder.append(",");
			stringBuilder.append("(");
			stringBuilder.append(resultSet.getObject("id"));
			stringBuilder.append(",");
			stringBuilder.append(666666);
			stringBuilder.append(")");

		}

		long currentTimeMillis = System.currentTimeMillis();
		int executeUpdate = createStatement.executeUpdate(stringBuilder.toString());
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		System.out.println(executeUpdate);

		resultSet.close();
		createStatement.close();

	}

	@Test
	public void testcasewhen() throws SQLException {

		Statement createStatement = connection.createStatement();

		ResultSet resultSet = createStatement.executeQuery("select * from tprj_chukudan_items as a1 "
				+ " inner join (select id from tprj_chukudan_items order by id limit 100000 ) a2 on a1.id = a2.id");

		StringBuilder stringBuilder2 = new StringBuilder("UPDATE  tprj_chukudan_items set ckd_id = case id ");
		StringBuilder stringBuilderwhere = new StringBuilder(" where id in ( ");

		if (resultSet.next()) {
			resultSet.getObject(1);
			stringBuilder2.append(" WHEN ");
			stringBuilder2.append(resultSet.getObject("id"));
			stringBuilder2.append(" THEN ");
			stringBuilder2.append(999999);

			stringBuilderwhere.append(resultSet.getObject("id"));

		}

		while (resultSet.next()) {
			resultSet.getObject(1);
			stringBuilder2.append(" WHEN ");
			stringBuilder2.append(resultSet.getObject("id"));
			stringBuilder2.append(" THEN ");
			stringBuilder2.append(999999);

			stringBuilderwhere.append(",").append(resultSet.getObject("id"));

		}
		stringBuilderwhere.append(")");
		stringBuilder2.append(" end ").append(stringBuilderwhere.toString());

		long currentTimeMillis = System.currentTimeMillis();
		int executeUpdate = createStatement.executeUpdate(stringBuilder2.toString());
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		System.out.println(executeUpdate);

		resultSet.close();
		createStatement.close();

	}

	@Test
	public void testbatchupdate() throws SQLException {

		Statement createStatement = connection.createStatement();

		ResultSet resultSet = createStatement.executeQuery("select * from tprj_chukudan_items as a1 "
				+ " inner join (select id from tprj_chukudan_items order by id limit 10000 ) a2 on a1.id = a2.id");

		StringBuilder stringBuilder2 = new StringBuilder("");

		while (resultSet.next()) {
			stringBuilder2.delete(0, stringBuilder2.length());
			resultSet.getObject(1);
			stringBuilder2.append("UPDATE  tprj_chukudan_items set ckd_id = 1000 where id = ");
			stringBuilder2.append(resultSet.getObject("id"));
			createStatement.addBatch(stringBuilder2.toString());

		}

		long currentTimeMillis = System.currentTimeMillis();
		int[] executeUpdate = createStatement.executeBatch();
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		System.out.println(executeUpdate.length);

		resultSet.close();
		createStatement.close();

	}

	@Test
	public void testbatchIn() throws SQLException {

		Statement createStatement = connection.createStatement();
		Statement createStatement2 = connection.createStatement();

		ResultSet resultSet = createStatement.executeQuery("select * from tprj_chukudan_items as a1 "
				+ " inner join (select id from tprj_chukudan_items order by id limit 100000 ) a2 on a1.id = a2.id");

		Long count = 0L;

		while (resultSet.next()) {
			StringBuilder stringBuilderwhere = new StringBuilder(
					"UPDATE  tprj_chukudan_items set ckd_id=910238  where id in ( ");
			stringBuilderwhere.append(resultSet.getObject("id"));
			count++;
			while (resultSet.next() && count % 10000 != 0) {

				resultSet.getObject(1);

				stringBuilderwhere.append(",").append(resultSet.getObject("id"));
				count++;
			}
			stringBuilderwhere.append(")");
			long currentTimeMillis = System.currentTimeMillis();
			int executeUpdate = createStatement2.executeUpdate(stringBuilderwhere.toString());
			System.out.println(System.currentTimeMillis() - currentTimeMillis);
			System.out.println(executeUpdate);
			count = 0L;
		}

		resultSet.close();
		createStatement.close();

	}

	@Before
	public void getConnection() throws SQLException {
		String connectionURL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL";
		String userId = "root";
		String password = "123456";
		connection = DriverManager.getConnection(connectionURL, userId, password);

		Statement statement = connection.createStatement();
		statement.executeUpdate("update tprj_chukudan_items a1 "
				+ "		inner join (select id from tprj_chukudan_items order by id limit 100) a2 on a1.id = a2.id"
				+ "		set ckd_id = 8888;");
		statement.close();

	}

	@After
	public void afterTest() throws SQLException {
		connection.close();

	}
}
