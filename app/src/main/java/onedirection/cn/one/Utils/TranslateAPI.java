package onedirection.cn.one.Utils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TranslateAPI {

	public static String getTranslate(String query) throws Exception {
		TransApi api = new TransApi();
		String queryRes = api.getTransResult(query, "auto", "auto");
		if ("网络连接错误".equals(queryRes))
			return "网络连接错误";
		Log.v("debug", "-----------------------------------" + "请求结果：" + queryRes + "-----------------------------------");
		JSONObject jObject = JSON.parseObject(queryRes);
		Log.v("debug", "-----------------------------------" + "dst：" + jObject.getString("dst") + "-----------------------------------");
		return jObject.getString("dst");
	}
}