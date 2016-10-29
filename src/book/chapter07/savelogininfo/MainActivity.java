package book.chapter07.savelogininfo;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/*
	 由于不是每一次都会进入登录界面，因此需要在界面中进行判断，如果保存的登录信息中，自动登录为true，那么就直接显示
	 欢迎界面。在该程序中，通过改变界面布局来改变显示内容的，所以整个应用仍然只有一个Activity。
	 */
	
	
	private Button login;// 登录按钮
	private CheckBox rememberPsdBox,autoLoginBox;// 记住密码、自动登录复选框
	private EditText name, psd;// 用户名和密码
	private TextView userInfo;
	SharedPreferences loginPreferences,accessPreferences;// 保存登录信息和访问次数
	SharedPreferences.Editor loginEditor, accessEditor;// 对应的编辑器
	boolean isSavePsd,isAutoLogin;
	String userName,userPsd;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginPreferences = getSharedPreferences("login",Context.MODE_PRIVATE);
	    accessPreferences = getSharedPreferences("access",Context.MODE_WORLD_READABLE);//其他应用程序可读
		int count = accessPreferences.getInt("count",1);//获取访问次数，默认为1
		Toast.makeText(MainActivity.this, "欢迎您，这是第"+count+"次访问！", Toast.LENGTH_LONG).show();//每次登录时显示访问次数信息
		loginEditor = loginPreferences.edit();//获取写入登录信息的Editor对象
		accessEditor = accessPreferences.edit();//获取写入访问信息的Editor对象
	    accessEditor.putInt("count",++count);//写入访问次数信息，每次自动加1
		accessEditor.commit();//提交写入的数据
		userName = loginPreferences.getString("name", null);//获取保存的用户信息
		userPsd = loginPreferences.getString("psd", null);//获取保存的密码信息
	    isSavePsd = loginPreferences.getBoolean("isSavePsd", false);//是否保存密码
		isAutoLogin = loginPreferences.getBoolean("isAutoLogin", false);//是否自动登录
		System.out.println("userName=" + userName + ",userPsd=" + userPsd);
		
		if(isAutoLogin){//如果自动登录为true
			this.setContentView(R.layout.activity_welcome);//显示欢迎界面
			userInfo = (TextView)this.findViewById(R.id.userInfo);
			userInfo.setText("欢迎您："+userName+",登录成功！");
		}else{//如果自动登录为false
			loadActivity();
		}
	}

	//加载新的布局文件的方法
	public void loadActivity() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.activity_main);//设置界面为登录界面
		login = (Button)this.findViewById(R.id.login);
		rememberPsdBox=(CheckBox)this.findViewById(R.id.remenberPsd);
		autoLoginBox=(CheckBox)this.findViewById(R.id.autoLogin);
		name=(EditText)this.findViewById(R.id.name);
		psd=(EditText)this.findViewById(R.id.psd);
		if(isSavePsd){//如果获取的保存密码为true
			psd.setText(userPsd);//设置密码框的值为保存的值
			name.setText(userName);//显示用户名为保存的用户名
			rememberPsdBox.setChecked(true);//设置“保存密码”复选框为选中状态	
		}
		
		
		
		login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginEditor.putString("name",name.getText().toString());//写入用户名
				loginEditor.putString("psd",psd.getText().toString());//写入密码
				loginEditor.putBoolean("isSavedPsd",rememberPsdBox.isChecked());
				loginEditor.putBoolean("isAutoLogin",autoLoginBox.isChecked());
				loginEditor.commit();//提交写入的登录信息
				MainActivity.this.setContentView(R.layout.activity_welcome);//切换到欢迎界面
				userInfo=(TextView)findViewById(R.id.userInfo);
				userInfo.setText("欢迎您："+name.getText().toString()+",登录成功！");
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//创建上下文菜单方法
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);//绑定菜单资源文件
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//为菜单项添加事件处理
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.menu_settings://注销菜单项事件处理
			loginEditor.putBoolean("isAutoLogin", false);
			loginEditor.commit();//提交写入的登录信息
			onCreate(null);//重新调用onCreate方法，显示登录界面
			break;
		case R.id.exit://退出菜单项事件处理
			this.finish();//结束当前Activity
			break;
		default:
			break;
		}
		return true;
	}
	
	

}
