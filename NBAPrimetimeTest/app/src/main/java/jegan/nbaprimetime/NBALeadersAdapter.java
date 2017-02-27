package jegan.nbaprimetime;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NBALeadersAdapter extends ArrayAdapter<NBALeaders> {

    String name;
    List list = new ArrayList();
    List<NBALeaders> leaderObject;

    public NBALeadersAdapter(Context context, int resource, List<NBALeaders> object) {
        super(context, resource, object);

    }

    public void add(NBALeaders object) {
        super.add(object);
        list.add(object);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NBALeaders getItem(int position) {
        return (NBALeaders) list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        LeaderHolder leaderHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.nba_leaders_row_layout, parent, false);
            leaderHolder = new LeaderHolder();
            leaderHolder.displayName = (TextView) row.findViewById(R.id.nba_leader_name);
            leaderHolder.displayName.setTextColor(Color.parseColor("#CCCCCC"));
            leaderHolder.position1 = (TextView) row.findViewById(R.id.nba_leader_pos);
            leaderHolder.position1.setTextColor(Color.parseColor("#CCCCCC"));
            leaderHolder.points = (TextView) row.findViewById(R.id.nba_leader_points);
            leaderHolder.points.setTextColor(Color.parseColor("#CCCCCC"));
            row.setTag(leaderHolder);

        } else {
            leaderHolder = (LeaderHolder) row.getTag();
        }

        NBALeaders leaders = (NBALeaders) this.getItem(position);
        leaderHolder.displayName.setText(leaders.getDisplayName());
        name = leaders.getDisplayName();
        leaderHolder.position1.setText(leaders.getPosition());
        leaderHolder.points.setText(leaders.getPoints());
        return row;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    static class LeaderHolder {
        TextView displayName, position1, points;
    }
}
