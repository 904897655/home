package onedirection.cn.one.Utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TransApi {

    public String getTransResult(String query, String from, String to) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("src",query);
        Log.v("debug",jsonObject.toJSONString());
        return  GetPostUtil.sendPost("http://www.onedirection.cn/AndroidHost/translation.action",jsonObject.toJSONString());
    }
}
