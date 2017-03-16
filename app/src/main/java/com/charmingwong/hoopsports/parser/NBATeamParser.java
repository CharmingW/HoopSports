package com.charmingwong.hoopsports.parser;

import com.charmingwong.hoopsports.entity.nbateamgame.JsonRootBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 56223 on 2017/3/4.
 */

public class NBATeamParser implements GsonParser {

    @Override
    public Map<String, Object> parse(String data) {
        Gson gson = new Gson();
        try {

            JsonRootBean root = gson.fromJson(data, JsonRootBean.class);
            if (0 == root.getErrorCode() && "查询成功".equals(root.getReason())) {
                com.charmingwong.hoopsports.entity.nbateamgame.Result result
                        = root.getResult();
                Map<String, Object> map = new HashMap<>();
                map.put("title", result.getTitle());
                map.put("list", result.getList());
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
