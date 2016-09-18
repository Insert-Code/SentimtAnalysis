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

	private String[] strs = new String[]{};// ����һ��String����������ʾListView������
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
		 
		String driverStr = "net.sourceforge.jtds.jdbc.Driver"; // ����JDBC����
		String connStr = "jdbc:jtds:sqlserver://192.168.36.185:1433/EA"; // ���ӷ����������ݿ�sample
		String dbuserName = "sa"; // Ĭ���û���
		String dbuserPwd = "sql"; // ����
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from weibo";
		
		try {
			Class.forName(driverStr);
			con = DriverManager.getConnection(connStr, dbuserName, dbuserPwd);
			stmt = con.createStatement();
		} catch (Exception ex) {
			System.out.println("�޷�ͬ���ݿ⽨�����ӣ�");
			ex.printStackTrace();
		}
		
		//��ȡ����
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> item = new HashMap<String, Object>();
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {// <code>ResultSet</code>���ָ���һ��
				item.put("��������", rs.getString("��������"));
	        	item.put("�ܷ���", rs.getString("�ܷ���"));
	        	data.add(item);
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
        	
       //����SimpleAdapter�����������ݰ󶨵�item��ʾ�ؼ���
       SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item, 
        		new String[]{"��������", "�ܷ���"}, new int[]{R.id.content, R.id.score});
       //ʵ���б����ʾ
       list.setAdapter(adapter);
       //��Ŀ����¼�
       list.setOnItemClickListener(new ItemClickListener());
       
       //�ر����ݿ�����
       try {
			rs.close();
			stmt.close();
			con.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
    }
       //��ȡ����¼�    
    private final class ItemClickListener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ListView listView = (ListView) parent;
			HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
			String personid = data.get("id").toString();
			Toast.makeText(getApplicationContext(), personid, 1).show();
		}
    }
}			

