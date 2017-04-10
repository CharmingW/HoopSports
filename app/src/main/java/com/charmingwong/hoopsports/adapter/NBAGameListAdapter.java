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
import com.charmingwong.hoopsports.entity.nbagame.List;
import com.charmingwong.hoopsports.entity.nbagame.Tr;
import com.charmingwong.hoopsports.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 56223 on 2017/2/28.
 */

public class NBAGameListAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private java.util.List mData;
    private String[] mTeamUrl;
    private Context mContext;

    public NBAGameListAdapter(Context context) {
        mData = parseData(Data.nbaGameData);
        mTeamUrl = context.getResources().getStringArray(R.array.nba);
        mContext = context;
    }

    /**
     * 将注入的数据解析成适合列表的数据项
     *
     * @param data 注入的原始数据
     * @return 填充列表的数据
     */
    private java.util.List parseData(Map<String, Object> data) {
        java.util.List items = new ArrayList<>();
        java.util.List<List> list = (java.util.List<List>) data.get("list");
        for (int i = 0; i < list.size(); i++) {
            List l = list.get(i);
            items.add(l.getTitle());
            items.addAll(l.getTr());
        }
        return items;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof String) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        mData = parseData(Data.nbaGameData);
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
            itemView.setBackgroundResource(R.drawable.background_selector);

            return new NBAGameListViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nba_game_header, parent, false);
            return new NBAGameListHeaderViewHolder(itemView);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NBAGameListViewHolder) {
            NBAGameListViewHolder itemHolder = (NBAGameListViewHolder) holder;
            final Tr tr = (Tr) mData.get(position);
            final String team1 = tr.getPlayer1();
            final String team2 = tr.getPlayer2();
            itemHolder.time.setText(tr.getTime());
            int status = tr.getStatus();
            if (status == 0) {
                itemHolder.status.setText(R.string.status_not_start);
            } else if (status == 1) {
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
                    intent.setData(Uri.parse(getTeamUrl(team1)));
                    v.getContext().startActivity(intent);
                }
            });
            itemHolder.guestImage
                    .setImageBitmap(BitmapUtils.compressBitmapByResId(mContext, getTeamImageId(team2)));
            itemHolder.guestImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.setData(Uri.parse(getTeamUrl(team2)));
                    v.getContext().startActivity(intent);
                }
            });
            itemHolder.guestImage.setImageResource(getTeamImageId(tr.getPlayer2()));
            itemHolder.homeName.setText(team1);
            itemHolder.guestName.setText(team2);
            itemHolder.score.setText(tr.getScore());
            itemHolder.statistics.setText(tr.getLink2text());
            itemHolder.statistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.setData(Uri.parse(tr.getLink2url()));
                    v.getContext().startActivity(intent);
                }
            });
            itemHolder.highlights.setText(tr.getLink1text());
            itemHolder.highlights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.setData(Uri.parse(tr.getLink1url()));
                    v.getContext().startActivity(intent);
                }
            });
        } else {
            NBAGameListHeaderViewHolder headerHolder = (NBAGameListHeaderViewHolder) holder;
            String date = ((String) mData.get(position));
            headerHolder.header.setText(date);
        }

    }

    /**
     * 列表项视图缓存
     */
    private static class NBAGameListViewHolder extends RecyclerView.ViewHolder {

        ImageView homeImage, guestImage;
        TextView time, status, homeName, guestName, score, statistics, highlights;

        NBAGameListViewHolder(View itemView) {
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
     * 头列表项缓存
     */
    private static class NBAGameListHeaderViewHolder extends RecyclerView.ViewHolder {

        TextView header;

        NBAGameListHeaderViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header);
        }
    }

    /**
     * 根据球队名返回相应的球队 icon id
     *
     * @param teamName 球队名
     * @return 球队icon id
     */
    private int getTeamImageId(String teamName) {
        switch (teamName) {
            case "骑士":
                return R.drawable.nba_cavaliers;
            case "勇士":
                return R.drawable.nba_warriors;
            case "马刺":
                return R.drawable.nba_spurs;
            case "火箭":
                return R.drawable.nba_rockets;
            case "快船":
                return R.drawable.nba_clippers;
            case "灰熊":
                return R.drawable.nba_grizzlies;
            case "爵士":
                return R.drawable.nba_jazz;
            case "雷霆":
                return R.drawable.nba_thunder;
            case "开拓者":
                return R.drawable.nba_blazers;
            case "掘金":
                return R.drawable.nba_nuggets;
            case "鹈鹕":
                return R.drawable.nba_pelicans;
            case "小牛":
                return R.drawable.nba_mavaricks;
            case "森林狼":
                return R.drawable.nba_wolves;
            case "国王":
                return R.drawable.nba_kings;
            case "湖人":
                return R.drawable.nba_lakers;
            case "太阳":
                return R.drawable.nba_suns;
            case "凯尔特人":
                return R.drawable.nba_celtics;
            case "猛龙":
                return R.drawable.nba_raptors;
            case "奇才":
                return R.drawable.nba_wizards;
            case "老鹰":
                return R.drawable.nba_hawks;
            case "步行者":
                return R.drawable.nba_pacers;
            case "公牛":
                return R.drawable.nba_bulls;
            case "活塞":
                return R.drawable.nba_pistons;
            case "热火":
                return R.drawable.nba_heat;
            case "雄鹿":
                return R.drawable.nba_bucks;
            case "黄蜂":
                return R.drawable.nba_hornets;
            case "尼克斯":
                return R.drawable.nba_knicks;
            case "76人":
                return R.drawable.nba_sixers;
            case "魔术":
                return R.drawable.nba_magic;
            case "篮网":
                return R.drawable.nba_nets;
            default:
                return 0;
        }
    }

    /**
     * 根据球队名返回相应的球队主页 url
     *
     * @param teamName 球队名
     * @return 球队主页 url
     */
    private String getTeamUrl(String teamName) {
        switch (teamName) {
            case "骑士":
                return mTeamUrl[0];
            case "勇士":
                return mTeamUrl[1];
            case "马刺":
                return mTeamUrl[2];
            case "火箭":
                return mTeamUrl[3];
            case "快船":
                return mTeamUrl[4];
            case "灰熊":
                return mTeamUrl[5];
            case "爵士":
                return mTeamUrl[6];
            case "雷霆":
                return mTeamUrl[7];
            case "掘金":
                return mTeamUrl[8];
            case "开拓者":
                return mTeamUrl[9];
            case "小牛":
                return mTeamUrl[10];
            case "鹈鹕":
                return mTeamUrl[11];
            case "森林狼":
                return mTeamUrl[12];
            case "国王":
                return mTeamUrl[13];
            case "湖人":
                return mTeamUrl[14];
            case "太阳":
                return mTeamUrl[15];
            case "凯尔特人":
                return mTeamUrl[16];
            case "猛龙":
                return mTeamUrl[17];
            case "奇才":
                return mTeamUrl[18];
            case "老鹰":
                return mTeamUrl[19];
            case "步行者":
                return mTeamUrl[20];
            case "公牛":
                return mTeamUrl[21];
            case "活塞":
                return mTeamUrl[22];
            case "热火":
                return mTeamUrl[23];
            case "雄鹿":
                return mTeamUrl[24];
            case "黄蜂":
                return mTeamUrl[25];
            case "尼克斯":
                return mTeamUrl[26];
            case "76人":
                return mTeamUrl[27];
            case "魔术":
                return mTeamUrl[28];
            case "篮网":
                return mTeamUrl[29];
            default:
                return "";
        }
    }

}
