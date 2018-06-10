package onedirection.cn.one;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import onedirection.cn.one.Utils.TranslateAPI;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button btn_showdb,btn_save,btn_loc,btn_net;

    EditText editText_src;
    TextView res_net,res_loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_showdb = (Button) findViewById(R.id.btn_showdb);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_loc = (Button) findViewById(R.id.btn_loc);
        btn_net = (Button) findViewById(R.id.btn_net);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+ "/translate.db3", null);
        editText_src = (EditText) findViewById(R.id.editText_src);
        res_net = (TextView) findViewById(R.id.res_net);
        res_loc = (TextView) findViewById(R.id.res_loc);



        btn_showdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName comp = new ComponentName(MainActivity.this,ShowDBActivity.class);
                Intent intent = new Intent();
                intent.setComponent(comp);
                startActivity(intent);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String srcText = editText_src.getText().toString();
                String netText = res_net.getText().toString();
                if(netText.equals("翻译结果")||srcText.equals("翻译内容"))
                {
                    Toast.makeText(MainActivity.this,"查询为空，无法保存！",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(MainActivity.this,"保存成功！",Toast.LENGTH_LONG).show();
                insertData(db, srcText, netText);
                Log.v("debug","-----------------------------------"+srcText+"\t"+netText+"-----------------------------------");
            }

            private void insertData(SQLiteDatabase db, String srcText, String netText){
                try
                {
                    Log.v("debug","-----------------------------------"+"try插入成功"+"-----------------------------------");
                    db.execSQL("insert into history values(null , ? , ?)", new String[] {srcText, netText });
                }
                catch (SQLiteException se)
                {
                    db.execSQL("create table history(_id integer primary key AUTOINCREMENT, src varchar(255), dst varchar(255))");
                    db.execSQL("insert into history values(null , ? , ?)", new String[] {srcText, netText });
                    Log.v("debug","-----------------------------------"+"catch插入成功"+"-----------------------------------");
                }
            }
        });

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String srcText = editText_src.getText().toString();
                String dstText = queryOneBySrc(srcText);
                if(dstText==null||dstText.equals(""))
                    Toast.makeText(MainActivity.this,"查询无果！",Toast.LENGTH_LONG).show();
                else
                    res_loc.setText(dstText);
            }

            private String queryOneBySrc(String src){
                String[] params = new String[]{src};
                try
                {
                    Log.v("debug","-----------------------------------"+"try查询成功"+"-----------------------------------");
                    Cursor cursor = db.rawQuery("select * from history where src = ?", params);
                    String srcRes = "";
                    String dstRes = "";
                    while (cursor.moveToNext()) {
                        srcRes = cursor.getString(1).toString();
                        dstRes = cursor.getString(2).toString();
                        Log.v("debug", "-----------------------------------" + "src：" + srcRes + "\t" + "dst: " + dstRes + "-----------------------------------");

                    }
                    return dstRes;
                }
                catch (SQLiteException se)
                {
                    db.execSQL("create table history(_id integer primary key AUTOINCREMENT, src varchar(255), dst varchar(255))");
                    Cursor cursor = db.rawQuery("select * from history where src = ?", params);
                    String srcRes = "";
                    String dstRes = "";
                    while (cursor.moveToNext()) {
                        srcRes = cursor.getString(1).toString();
                        dstRes = cursor.getString(2).toString();
                        Log.v("debug", "-----------------------------------" + "src：" + srcRes + "\t" + "dst: " + dstRes + "-----------------------------------");

                    }
                    Log.v("debug","-----------------------------------"+"catch查询成功"+"-----------------------------------");
                    return dstRes;
                }

            }
        });

        btn_net.setOnClickListener(new View.OnClickListener() {
            String resText = "";
            @Override
            public void onClick(View view) {
                final String srcText = editText_src.getText().toString();
                final Handler mHandler = new Handler()
                {
                    public void handleMessage(Message msg)
                    {
                        //更新UI
                        switch (msg.what)
                        {
                            case 1:
                            {
                                res_net.setText(resText);
                                break;
                            }
                            case 2:
                            {
                                Toast.makeText(MainActivity.this,"查询异常，请重试",Toast.LENGTH_LONG).show();
                                Log.v("debug","-----------------------------------"+"查询异常"+"-----------------------------------");
                                break;
                            }
                        }
                    };
                };
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            resText = TranslateAPI.getTranslate(srcText);
                            Log.v("debug","-----------------------------------"+"来自btn_net："+resText+"-----------------------------------");
                            Message message = new Message();
                            message.what = 1;
                            mHandler.sendMessage(message);
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.v("debug","-----------------------------------"+"查询无果"+"-----------------------------------");
                            Message message = new Message();
                            message.what = 2;
                            mHandler.sendMessage(message);
                        }
                    }
                });
                thread.start();

            }
        });

    }
}
