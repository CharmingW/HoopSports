package com.charmingwong.hoopsports.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.activity.WebActivity;
import com.charmingwong.hoopsports.config.Data;
import com.charmingwong.hoopsports.utils.BitmapUtils;

import java.util.List;

/**
 * Created by 56223 on 2017/3/6.
 */

public class FootballTeamGameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List mData;
    private Context mContext;

    public FootballTeamGameListAdapter(Context context) {
        mData = (List) Data.footballTeamGameData.get("list");
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        itemView.setBackgroundResource(R.drawable.background_selector);
        return new FootballGameListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FootballGameListViewHolder itemHolder = (FootballGameListViewHolder) holder;
        final com.charmingwong.hoopsports.entity.footballteamgame.List list
                = (com.charmingwong.hoopsports.entity.footballteamgame.List) mData.get(position);
        String team1 = list.getC4T1();
        String team2 = list.getC4T2();
        itemHolder.time.setText(list.getC2() + " " + list.getC3());
        String status = list.getC1();
        if ("未开赛".equals(status)) {
            itemHolder.status.setText(R.string.status_not_start);
        } else if ("直播中".equals(status)) {
            itemHolder.status.setText(R.string.status_live);
        } else {
            itemHolder.status.setText(R.string.status_over);
        }
        itemHolder.homeImage
                .setImageBitmap(BitmapUtils.compressBitmapByResId(mContext, getTeamImageId(team1)));
        itemHolder.homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebActivity.class);
                intent.setData(Uri.parse(list.getC4T1URL()));
                v.getContext().startActivity(intent);
            }
        });
        itemHolder.guestImage
                .setImageBitmap(BitmapUtils.compressBitmapByResId(mContext, getTeamImageId(team2)));
        itemHolder.guestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebActivity.class);
                intent.setData(Uri.parse(list.getC4T2URL()));
                v.getContext().startActivity(intent);
            }
        });
        itemHolder.homeName.setText(team1);
        itemHolder.guestName.setText(team2);
        itemHolder.score.setText(list.getC4R());
        itemHolder.statistics.setText(list.getC52());
        itemHolder.statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebActivity.class);
                intent.setData(Uri.parse(list.getC52Link()));
                v.getContext().startActivity(intent);
            }
        });
        itemHolder.highlights.setText(list.getC53());
        itemHolder.highlights.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), WebActivity.class);
                        intent.setData(Uri.parse(list.getC53Link()));
                        v.getContext().startActivity(intent);
                    }
                }
        );

    }


    static class FootballGameListViewHolder extends RecyclerView.ViewHolder {

        ImageView homeImage, guestImage;
        TextView time, status, homeName, guestName, score, statistics, highlights;

        FootballGameListViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            status = (TextView) itemView.findViewById(R.id.status);
            homeImage = (ImageView) itemView.findViewById(R.id.home_image);
            guestImage = (ImageView) itemView.findViewById(R.id.guest_image);
            homeName = (TextView) itemView.findViewById(R.id.home_name);
            guestName = (TextView) itemView.findViewById(R.id.guest_name);
            score = (TextView) itemView.findViewById(R.id.score);
            statistics = (TextView) itemView.findViewById(R.id.statistics_data);
            highlights = (TextView) itemView.findViewById(R.id.video_highlights);
        }
    }

    /**
     * 根据球队名获取球队 icon id
     *
     * @param team 球队名
     * @return 球队 icon id
     */
    private int getTeamImageId(String team) {
        switch (team) {
            //英超
            case "切尔西":
                return R.drawable.yc_qex;

            case "热刺":
                return R.drawable.yc_rc;

            case "利物浦":
                return R.drawable.yc_lwp;

            case "曼城":
                return R.drawable.yc_mc;

            case "阿森纳":
                return R.drawable.yc_asn;

            case "曼联":
                return R.drawable.yc_ml;

            case "埃弗顿":
                return R.drawable.yc_afd;

            case "西布朗":
                return R.drawable.yc_xblwq;

            case "斯托克城":
                return R.drawable.yc_stkc;

            case "南安普敦":
                return R.drawable.yc_nadp;

            case "西汉姆联":
                return R.drawable.yc_xhml;

            case "伯恩利":
                return R.drawable.yc_bel;

            case "沃特福德":
                return R.drawable.yc_wtfd;

            case "伯恩茅斯":
                return R.drawable.yc_bems;

            case "莱斯特":
                return R.drawable.yc_lst;

            case "斯旺西":
                return R.drawable.yc_swx;

            case "水晶宫":
                return R.drawable.yc_sjg;

            case "米德尔斯堡":
                return R.drawable.yc_mdesb;

            case "赫尔城":
                return R.drawable.yc_hec;

            case "桑德兰":
                return R.drawable.yc_sdl;

            //西甲
            case "巴塞罗那":
                return R.drawable.xj_bsln;

            case "皇家马德里":
                return R.drawable.xj_hjmdl;

            case "塞维利亚":
                return R.drawable.xj_swly;

            case "皇家社会":
                return R.drawable.xj_hjsh;

            case "马德里竞技":
                return R.drawable.xj_mdljj;

            case "比利亚雷亚尔":
                return R.drawable.xj_blylye;

            case "埃瓦尔":
                return R.drawable.xj_awe;

            case "毕尔巴鄂竞技":
                return R.drawable.xj_bebe;

            case "西班牙人":
                return R.drawable.xj_xbyr;

            case "维戈塞尔塔":
                return R.drawable.xj_wgset;

            case "阿拉维斯":
                return R.drawable.xj_alws;

            case "拉斯帕尔马斯":
                return R.drawable.xj_lspem;

            case "瓦伦西亚":
                return R.drawable.xj_wlxy;

            case "皇家贝蒂斯":
                return R.drawable.xj_hjbds;

            case "马拉加":
                return R.drawable.xj_mlj;

            case "莱加内斯":
                return R.drawable.xj_ljls;

            case "拉科鲁尼亚":
                return R.drawable.xj_lklny;

            case "格拉纳达":
                return R.drawable.xj_glnd;

            case "希洪竞技":
                return R.drawable.xj_xhjj;

            case "奥萨苏纳":
                return R.drawable.xj_assn;

            //意甲
            case "尤文图斯":
                return R.drawable.yj_ywts;

            case "罗马":
                return R.drawable.yj_lm;

            case "那不勒斯":
                return R.drawable.yj_nbls;

            case "亚特兰大":
                return R.drawable.yj_ytld;

            case "拉齐奥":
                return R.drawable.yj_lqa;

            case "AC米兰":
                return R.drawable.yj_acml;

            case "国际米兰":
                return R.drawable.yj_gjml;

            case "佛罗伦萨":
                return R.drawable.yj_flls;

            case "桑普多利亚":
                return R.drawable.yj_spdly;

            case "都灵":
                return R.drawable.yj_dl;

            case "切沃":
                return R.drawable.yj_qw;

            case "萨索洛":
                return R.drawable.yj_ssl;

            case "卡利亚里":
                return R.drawable.yj_klyl;

            case "乌迪内斯":
                return R.drawable.yj_wdns;

            case "博洛尼亚":
                return R.drawable.yj_blny;

            case "热那亚":
                return R.drawable.yj_rny;

            case "恩波利":
                return R.drawable.yj_ebl;

            case "巴勒莫":
                return R.drawable.yj_blm;

            case "克罗托内":
                return R.drawable.yj_kltn;

            case "佩斯卡拉":
                return R.drawable.yj_pskl;

            //德甲
            case "拜仁慕尼黑":
                return R.drawable.dj_brmnh;

            case "莱比锡":
                return R.drawable.dj_lbx;

            case "多特蒙德":
                return R.drawable.dj_dtmd;

            case "霍芬海姆":
                return R.drawable.dj_hfhm;

            case "柏林赫塔":
                return R.drawable.dj_blht;

            case "法兰克福":
                return R.drawable.dj_flkf;

            case "科隆":
                return R.drawable.dj_kl;

            case "门兴":
                return R.drawable.dj_mx;

            case "勒沃库森":
                return R.drawable.dj_lwks;

            case "弗赖堡":
                return R.drawable.dj_flb;

            case "美因茨":
                return R.drawable.dj_myc;

            case "奥格斯堡":
                return R.drawable.dj_agsb;

            case "沙尔克04":
                return R.drawable.dj_sek;

            case "不来梅":
                return R.drawable.dj_ydblm;

            case "沃尔夫斯堡":
                return R.drawable.dj_wefsb;

            case "汉堡":
                return R.drawable.dj_hb;

            case "因戈尔施塔特":
                return R.drawable.dj_ygstt;

            case "达姆施塔特":
                return R.drawable.dj_dmstt;

            //意甲
            case "摩纳哥":
                return R.drawable.fj_mng;

            case "巴黎圣日耳曼":
                return R.drawable.fj_blsrem;

            case "尼斯":
                return R.drawable.fj_ns;

            case "里昂":
                return R.drawable.fj_la;

            case "波尔多":
                return R.drawable.fj_bed;

            case "圣埃蒂安":
                return R.drawable.fj_sada;

            case "马赛":
                return R.drawable.fj_ms;

            case "雷恩":
                return R.drawable.fj_le;

            case "昂热":
                return R.drawable.fj_ar;

            case "图卢兹":
                return R.drawable.fj_tlz;

            case "甘冈":
                return R.drawable.fj_gg;

            case "南特":
                return R.drawable.fj_nt;

            case "蒙彼利埃":
                return R.drawable.fj_mbla;

            case "卡昂":
                return R.drawable.fj_ka;

            case "梅斯":
                return R.drawable.fj_ms;

            case "里尔":
                return R.drawable.fj_ler;

            case "南锡":
                return R.drawable.fj_nx;

            case "第戎":
                return R.drawable.fj_dr;

            case "巴斯蒂亚":
                return R.drawable.fj_bsdy;

            case "洛里昂":
                return R.drawable.fj_lla;

            //中超
            case "广州富力":
                return R.drawable.zc_gzfl;

            case "山东鲁能":
                return R.drawable.zc_sdln;

            case "贵州恒丰":
                return R.drawable.zc_gzzc;

            case "辽宁开新":
                return R.drawable.zc_lnkx;

            case "北京国安":
                return R.drawable.zc_bjga;

            case "重庆力帆":
                return R.drawable.zc_cqlf;

            case "广州恒大":
                return R.drawable.zc_gzhd;

            case "华夏幸福":
                return R.drawable.zc_hxxf;

            case "河北华夏":
                return R.drawable.zc_hxxf;

            case "河南建业":
                return R.drawable.zc_hnjy;

            case "江苏苏宁":
                return R.drawable.zc_jssn;

            case "上海上港":
                return R.drawable.zc_shsg;

            case "上海申花":
                return R.drawable.zc_shsh;

            case "延边富德":
                return R.drawable.zc_ybfd;

            case "天津权健":
                return R.drawable.zc_tjqj;

            case "天津泰达":
                return R.drawable.zc_tjtd;

            case "长春亚泰":
                return R.drawable.zc_ccyt;

            //亚太
            case "水原三星":
                return R.drawable.yt_sysx;

            case "首尔FC":
                return R.drawable.yt_sefc;

            case "蔚山现代":
                return R.drawable.yt_sefc;

            case "济州联":
                return R.drawable.yt_jzl;

            case "浦和红钻":
                return R.drawable.yt_phhz;

            case "川崎前锋":
                return R.drawable.yt_cqqf;

            case "大阪钢巴":
                return R.drawable.yt_dbgb;

            case "鹿岛鹿角":
                return R.drawable.yt_ldlj;

            case "香港东方":
                return R.drawable.yt_xgdf;

            case "蒙通联":
                return R.drawable.yt_mtl;

            //其他联赛
            case "圣彼得堡泽尼特":
                return R.drawable.other_sbdbznt;

            case "波尔图":
                return R.drawable.other_bet;

            case "根特":
                return R.drawable.other_gt;

            case "罗斯托夫":
                return R.drawable.other_lstf;

            case "阿尔克马尔":
                return R.drawable.other_aekme;

            case "基辅迪纳摩":
                return R.drawable.other_jfdnm;

            case "莫斯科火车头":
                return R.drawable.other_mskhct;

            case "克拉斯诺达尔":
                return R.drawable.other_klsnde;

            case "阿德莱德联":
                return R.drawable.other_adldl;

            case "西悉尼流浪者":
                return R.drawable.other_xxnllz;

            case "布里斯班狮吼":
                return R.drawable.other_blsbsh;

            case "墨尔本胜利":
                return R.drawable.other_mebsl;

            default:
                return 0;
        }
    }
}
