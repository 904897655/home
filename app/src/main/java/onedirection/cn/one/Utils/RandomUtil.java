package onedirection.cn.one.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by wang_zhen1 on 2018/6/11 0011.
 */

public class RandomUtil {
    public static List<String> getList() {
        Random ran = new java.util.Random();
        List<String> mListStr = new ArrayList<>();
        Set<Integer> set = new LinkedHashSet<Integer>();
        while (set.size() < 10) {
            int num = (int) (Math.random()*10);
            set.add(num);
        }
        for (int i : set) {
            mListStr.add(i + "");
        }
        return mListStr;
    }
}
