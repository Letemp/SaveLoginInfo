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
	 ���ڲ���ÿһ�ζ�������¼���棬�����Ҫ�ڽ����н����жϣ��������ĵ�¼��Ϣ�У��Զ���¼Ϊtrue����ô��ֱ����ʾ
	 ��ӭ���档�ڸó����У�ͨ���ı���沼�����ı���ʾ���ݵģ���������Ӧ����Ȼֻ��һ��Activity��
	 */
	
	
	private Button login;// ��¼��ť
	private CheckBox rememberPsdBox,autoLoginBox;// ��ס���롢�Զ���¼��ѡ��
	private EditText name, psd;// �û���������
	private TextView userInfo;
	SharedPreferences loginPreferences,accessPreferences;// �����¼��Ϣ�ͷ��ʴ���
	SharedPreferences.Editor loginEditor, accessEditor;// ��Ӧ�ı༭��
	boolean isSavePsd,isAutoLogin;
	String userName,userPsd;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginPreferences = getSharedPreferences("login",Context.MODE_PRIVATE);
	    accessPreferences = getSharedPreferences("access",Context.MODE_WORLD_READABLE);//����Ӧ�ó���ɶ�
		int count = accessPreferences.getInt("count",1);//��ȡ���ʴ�����Ĭ��Ϊ1
		Toast.makeText(MainActivity.this, "��ӭ�������ǵ�"+count+"�η��ʣ�", Toast.LENGTH_LONG).show();//ÿ�ε�¼ʱ��ʾ���ʴ�����Ϣ
		loginEditor = loginPreferences.edit();//��ȡд���¼��Ϣ��Editor����
		accessEditor = accessPreferences.edit();//��ȡд�������Ϣ��Editor����
	    accessEditor.putInt("count",++count);//д����ʴ�����Ϣ��ÿ���Զ���1
		accessEditor.commit();//�ύд�������
		userName = loginPreferences.getString("name", null);//��ȡ������û���Ϣ
		userPsd = loginPreferences.getString("psd", null);//��ȡ�����������Ϣ
	    isSavePsd = loginPreferences.getBoolean("isSavePsd", false);//�Ƿ񱣴�����
		isAutoLogin = loginPreferences.getBoolean("isAutoLogin", false);//�Ƿ��Զ���¼
		System.out.println("userName=" + userName + ",userPsd=" + userPsd);
		
		if(isAutoLogin){//����Զ���¼Ϊtrue
			this.setContentView(R.layout.activity_welcome);//��ʾ��ӭ����
			userInfo = (TextView)this.findViewById(R.id.userInfo);
			userInfo.setText("��ӭ����"+userName+",��¼�ɹ���");
		}else{//����Զ���¼Ϊfalse
			loadActivity();
		}
	}

	//�����µĲ����ļ��ķ���
	public void loadActivity() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.activity_main);//���ý���Ϊ��¼����
		login = (Button)this.findViewById(R.id.login);
		rememberPsdBox=(CheckBox)this.findViewById(R.id.remenberPsd);
		autoLoginBox=(CheckBox)this.findViewById(R.id.autoLogin);
		name=(EditText)this.findViewById(R.id.name);
		psd=(EditText)this.findViewById(R.id.psd);
		if(isSavePsd){//�����ȡ�ı�������Ϊtrue
			psd.setText(userPsd);//����������ֵΪ�����ֵ
			name.setText(userName);//��ʾ�û���Ϊ������û���
			rememberPsdBox.setChecked(true);//���á��������롱��ѡ��Ϊѡ��״̬	
		}
		
		
		
		login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginEditor.putString("name",name.getText().toString());//д���û���
				loginEditor.putString("psd",psd.getText().toString());//д������
				loginEditor.putBoolean("isSavedPsd",rememberPsdBox.isChecked());
				loginEditor.putBoolean("isAutoLogin",autoLoginBox.isChecked());
				loginEditor.commit();//�ύд��ĵ�¼��Ϣ
				MainActivity.this.setContentView(R.layout.activity_welcome);//�л�����ӭ����
				userInfo=(TextView)findViewById(R.id.userInfo);
				userInfo.setText("��ӭ����"+name.getText().toString()+",��¼�ɹ���");
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//���������Ĳ˵�����
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);//�󶨲˵���Դ�ļ�
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Ϊ�˵�������¼�����
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.menu_settings://ע���˵����¼�����
			loginEditor.putBoolean("isAutoLogin", false);
			loginEditor.commit();//�ύд��ĵ�¼��Ϣ
			onCreate(null);//���µ���onCreate��������ʾ��¼����
			break;
		case R.id.exit://�˳��˵����¼�����
			this.finish();//������ǰActivity
			break;
		default:
			break;
		}
		return true;
	}
	
	

}
