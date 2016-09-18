package com.example.sentimtanalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class TwoActivity extends Activity {

	private String[] strs = new String[]{};// 定义一个String数组用来显示ListView的内容
	private ListView list;

	/** Called when the activity is first created. */

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		list = (ListView) findViewById(R.id.listView);
		 
		String driverStr = "net.sourceforge.jtds.jdbc.Driver"; // 加载JDBC驱动
		String connStr = "jdbc:jtds:sqlserver://192.168.36.185:1433/EA"; // 连接服务器和数据库sample
		String dbuserName = "sa"; // 默认用户名
		String dbuserPwd = "sql"; // 密码
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from weibo";
		
		try {
			Class.forName(driverStr);
			con = DriverManager.getConnection(connStr, dbuserName, dbuserPwd);
			stmt = con.createStatement();
		} catch (Exception ex) {
			System.out.println("无法同数据库建立连接！");
			ex.printStackTrace();
		}
		
		//获取数据
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> item = new HashMap<String, Object>();
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {// <code>ResultSet</code>最初指向第一行
				item.put("博文内容", rs.getString("博文内容"));
	        	item.put("总分数", rs.getString("总分数"));
	        	data.add(item);
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
        	
       //创建SimpleAdapter适配器将数据绑定到item显示控件上
       SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item, 
        		new String[]{"博文内容", "总分数"}, new int[]{R.id.content, R.id.score});
       //实现列表的显示
       list.setAdapter(adapter);
       //条目点击事件
       list.setOnItemClickListener(new ItemClickListener());
       
       //关闭数据库连接
       try {
			rs.close();
			stmt.close();
			con.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
    }
       //获取点击事件    
    private final class ItemClickListener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ListView listView = (ListView) parent;
			HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
			String personid = data.get("id").toString();
			Toast.makeText(getApplicationContext(), personid, 1).show();
		}
    }
}			

