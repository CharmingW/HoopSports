package com.charmingwong.hoopsports.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 56223 on 2017/3/2.
 */

public class NBATeamAdapter extends BaseAdapter {

    private Context mContext;

    private int mCount;

    public int getmCount() {
        return mCount;
    }

    public List<String> getSelectedTeams() {
        return mSelectedTeams;
    }

    private List<String> mSelectedTeams;
    private java.util.List<String> mTeamNames;
    private java.util.List<Integer> mTeamIconIds;

    public NBATeamAdapter(Context context) {
        mContext = context;
        mTeamNames = getNames();
        mTeamIconIds = getTeamIconIds();
        mSelectedTeams = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nba_team, parent, false);
            holder.teamIcon = (ImageView) convertView.findViewById(R.id.team_icon);
            holder.teamName = (TextView) convertView.findViewById(R.id.title);
            holder.selectedItem = convertView.findViewById(R.id.selected_item);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCount == 2) {
                        if (holder.selectedItem.getVisibility() == View.VISIBLE) {
                            holder.selectedItem.setVisibility(View.GONE);
                            mSelectedTeams.remove(mTeamNames.get(position));
                            mCount--;
                        }
                        return;
                    }
                    View view = holder.selectedItem;
                    if (view.getVisibility() == View.VISIBLE) {
                        view.setVisibility(View.GONE);
                        mSelectedTeams.remove(mTeamNames.get(position));
                        mCount--;
                    } else {
                        view.setVisibility(View.VISIBLE);
                        mSelectedTeams.add(mTeamNames.get(position));
                        mCount++;
                    }
                    TextView ensure = (TextView) ((Activity) v.getContext()).findViewById(R.id.text_ensure);;
                    if (mCount >= 1) {
                        ensure.setText("确定");
                        ensure.setBackground(mContext.getDrawable(R.drawable.bg_ensure));
                    } else {
                        ensure.setText(mContext.getString(R.string.tips_text));
                        ensure.setBackground(null);
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.teamIcon.setImageBitmap(BitmapUtils.compressBitmapByResId(mContext, mTeamIconIds.get(position)));
        holder.teamName.setText(mTeamNames.get(position));
        return convertView;
    }

    static class ViewHolder {
        ImageView teamIcon;
        TextView teamName;
        View selectedItem;
    }

    /**
     * @return 球队名集合
     */
    private java.util.List<String> getNames() {
        java.util.List<String> names = new ArrayList<>();
        names.add("骑士");
        names.add("凯尔特人");
        names.add("奇才");
        names.add("猛龙");
        names.add("老鹰");
        names.add("步行者");
        names.add("活塞");
        names.add("公牛");
        names.add("热火");
        names.add("雄鹿");
        names.add("黄蜂");
        names.add("尼克斯");
        names.add("76人");
        names.add("魔术");
        names.add("篮网");
        names.add("勇士");
        names.add("马刺");
        names.add("火箭");
        names.add("快船");
        names.add("爵士");
        names.add("灰熊");
        names.add("雷霆");
        names.add("掘金");
        names.add("开拓者");
        names.add("国王");
        names.add("森林狼");
        names.add("小牛");
        names.add("鹈鹕");
        names.add("太阳");
        names.add("湖人");
        return names;
    }

    /**
     * @return 球队 icon id 集合
     */
    private java.util.List<Integer> getTeamIconIds() {
        java.util.List<Integer> ids = new ArrayList<>();
        ids.add(R.drawable.nba_cavaliers);
        ids.add(R.drawable.nba_celtics);
        ids.add(R.drawable.nba_wizards);
        ids.add(R.drawable.nba_raptors);
        ids.add(R.drawable.nba_hawks);
        ids.add(R.drawable.nba_pacers);
        ids.add(R.drawable.nba_pistons);
        ids.add(R.drawable.nba_bulls);
        ids.add(R.drawable.nba_heat);
        ids.add(R.drawable.nba_bucks);
        ids.add(R.drawable.nba_hornets);
        ids.add(R.drawable.nba_knicks);
        ids.add(R.drawable.nba_sixers);
        ids.add(R.drawable.nba_magic);
        ids.add(R.drawable.nba_nets);
        ids.add(R.drawable.nba_warriors);
        ids.add(R.drawable.nba_spurs);
        ids.add(R.drawable.nba_rockets);
        ids.add(R.drawable.nba_clippers);
        ids.add(R.drawable.nba_jazz);
        ids.add(R.drawable.nba_grizzlies);
        ids.add(R.drawable.nba_thunder);
        ids.add(R.drawable.nba_nuggets);
        ids.add(R.drawable.nba_blazers);
        ids.add(R.drawable.nba_kings);
        ids.add(R.drawable.nba_wolves);
        ids.add(R.drawable.nba_mavaricks);
        ids.add(R.drawable.nba_pelicans);
        ids.add(R.drawable.nba_suns);
        ids.add(R.drawable.nba_lakers);
        return ids;
    }

}
