package onedirection.cn.one;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import onedirection.cn.one.Utils.RandomUtil;
import onedirection.cn.one.adapter.GridVeiwAdapter;

/**
 * Created by wang_zhen1 on 2018/6/11 0011.
 */

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private GridVeiwAdapter mAdapter;
    private List<String> mListStr=new ArrayList<>();
    private EditText mEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mGridView= (GridView) findViewById(R.id.gv_keyboard);
        mEditText= (EditText) findViewById(R.id.et_nums);

        mListStr= RandomUtil.getList();
        mListStr.add(9,getResources().getString(R.string.text_key_cancel_no));
        mListStr.add(mListStr.size(),getResources().getString(R.string.text_key_ok));

        mAdapter=new GridVeiwAdapter(this,mListStr);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(this);
     //   hideInput(this,mEditText);
    }
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 9:
                mEditText.setText("");
                break;
            case 11:
                if (mEditText.getText()!=null && mEditText.getText().toString().equals("123")){

                    Intent intent=new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {

                    Toast.makeText(this,"密码错误",Toast.LENGTH_LONG).show();
                }

                break;
            default:
                mEditText.setText(mEditText.getText()+mListStr.get(position));
                break;
        }
    }
}
