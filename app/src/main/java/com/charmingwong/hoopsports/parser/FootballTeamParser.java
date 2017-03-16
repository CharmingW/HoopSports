package com.charmingwong.hoopsports.parser;

import com.charmingwong.hoopsports.entity.footballteamgame.Result;
import com.charmingwong.hoopsports.entity.footballteamgame.Root;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 56223 on 2017/3/7.
 */

public class FootballTeamParser implements GsonParser {

    @Override
    public Map<String, Object> parse(String data) {
        Gson gson = new Gson();
        try {

            Root root = gson.fromJson(data, Root.class);
            if (0 == root.getError_code() && "查询成功".equals(root.getReason())) {
                Result result = root.getResult();
                Map<String, Object> map = new HashMap<>();
                map.put("title", result.getKey());
                map.put("list", result.getList());
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
