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

public class NBATeamsAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public NBATeamsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(NBATeams object) {
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
        TeamHolder teamHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.nba_teams_row_layout, parent, false);
            teamHolder = new TeamHolder();
            teamHolder.teamPrefix = (TextView) row.findViewById(R.id.nba_team_prefix);
            teamHolder.teamPrefix.setTextColor(Color.parseColor("#CCCCCC"));
            teamHolder.teamName = (TextView) row.findViewById(R.id.nba_team_name);
            teamHolder.teamName.setTextColor(Color.parseColor("#CCCCCC"));
            teamHolder.conference = (TextView) row.findViewById(R.id.nba_conference);
            teamHolder.conference.setTextColor(Color.parseColor("#CCCCCC"));
            teamHolder.division = (TextView) row.findViewById(R.id.nba_division);
            teamHolder.division.setTextColor(Color.parseColor("#CCCCCC"));
            row.setTag(teamHolder);

        } else {
            teamHolder = (TeamHolder) row.getTag();
        }

        NBATeams teams = (NBATeams) this.getItem(position);
        teamHolder.teamPrefix.setText(teams.getTeamPrefix());
        teamHolder.teamName.setText(teams.getTeamName());
        teamHolder.conference.setText(teams.getConference());
        teamHolder.division.setText(teams.getDivision());
        return row;
    }

    static class TeamHolder {
        TextView teamPrefix, teamName, conference, division;
    }
}
