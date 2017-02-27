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


public class NBATeamsRosterAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public NBATeamsRosterAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(NBATeamsRoster object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        TeamRosterHolder teamRosterHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.nba_teams_roster_row_layout, parent, false);
            teamRosterHolder = new TeamRosterHolder();
            teamRosterHolder.playerName = (TextView) row.findViewById(R.id.nba_team_roster_name);
            teamRosterHolder.playerName.setTextColor(Color.parseColor("#CCCCCC"));
            teamRosterHolder.playerPosition = (TextView) row.findViewById(R.id.nba_team_roster_position);
            teamRosterHolder.playerPosition.setTextColor(Color.parseColor("#CCCCCC"));
            teamRosterHolder.playerAge = (TextView) row.findViewById(R.id.nba_team_roster_age);
            teamRosterHolder.playerAge.setTextColor(Color.parseColor("#CCCCCC"));
            teamRosterHolder.playerHeight = (TextView) row.findViewById(R.id.nba_team_roster_height);
            teamRosterHolder.playerHeight.setTextColor(Color.parseColor("#CCCCCC"));
            teamRosterHolder.playerWeight = (TextView) row.findViewById(R.id.nba_team_roster_weight);
            teamRosterHolder.playerWeight.setTextColor(Color.parseColor("#CCCCCC"));
            row.setTag(teamRosterHolder);

        } else {
            teamRosterHolder = (TeamRosterHolder) row.getTag();
        }

        NBATeamsRoster roster = (NBATeamsRoster) this.getItem(position);
        teamRosterHolder.playerName.setText(roster.getPlayerName());
        teamRosterHolder.playerPosition.setText(roster.getPlayerPosition());
        teamRosterHolder.playerAge.setText(roster.getPlayerAge());
        teamRosterHolder.playerHeight.setText(roster.getPlayerHeight());
        teamRosterHolder.playerWeight.setText(roster.getPlayerWeight());
        return row;
    }

    static class TeamRosterHolder {
        TextView playerName, playerPosition, playerAge, playerWeight, playerHeight;
    }
}
