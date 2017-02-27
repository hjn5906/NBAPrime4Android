package jegan.nbaprimetime;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class DisplayNBATeamsRoster extends Fragment {

    ArrayList<String> jsonArray = new ArrayList<>();
    ArrayList<String> displayNameList;
    ArrayList<String> jsonArrayResult = new ArrayList<>();
    String domain = "https://erikberg.com";
    String sport = "nba";
    String accessToken = "894346fa-07b9-4878-ba14-0a1b83eb04f8";
    String format = ".json";
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray2;
    NBATeamsRosterAdapter rosterAdapter;
    ListView listView;
    ListView listViewResults;
    ImageView teamLogo;
    String teamID;
    ListView listViewRosterHeader;
    NBATeamsRosterAdapter rosterHeaderadapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_nba_teams_roster_layout,
                container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.listviewRoster);
        listViewRosterHeader = (ListView) view.findViewById(R.id.listviewRosterHeader);
        rosterHeaderadapter = new NBATeamsRosterAdapter(getActivity(), R.layout.nba_roster_header_row_layout);

        listViewRosterHeader.setAdapter(rosterHeaderadapter);
        NBATeamsRoster roster = new NBATeamsRoster("Player Name", "Position", "Age", "Height", "Weight");
        rosterHeaderadapter.add(roster);
        rosterHeaderadapter.notifyDataSetChanged();

        teamLogo = (ImageView) view.findViewById(R.id.test_image);
        teamID = getArguments().getString("teamID");
        String formattedTeamID = teamID;
        formattedTeamID = formattedTeamID.replaceAll("-", " ").toLowerCase();
        String formattedLogoID = teamID;
        formattedLogoID = formattedLogoID.replaceAll("-", "_").toLowerCase();
        int id = getActivity().getResources().getIdentifier(formattedLogoID,
                "drawable", getActivity().getPackageName());
        teamLogo.setImageResource(id);
        String[] teamNameArray = formattedTeamID.split(" ");
        List<String> teamNameList = new ArrayList<String>(Arrays.asList(formattedTeamID));

        String cityName = teamNameArray[0];
        String teamName = teamNameArray[1];
        String output = cityName.substring(0, 1).toUpperCase() + cityName.substring(1) + " " +
                teamName.substring(0, 1).toUpperCase() + teamName.substring(1);
        TextView textView = (TextView) view.findViewById(R.id.teamRosterTitle);
        textView.setTextColor(Color.parseColor("#CCCCCC"));
        textView.setText(output);

        new BackgroundTask(listView, teamLogo, teamID, getActivity()).execute();
    }


    class BackgroundTask extends AsyncTask<Void, Void, ArrayList<String>> {
        private WeakReference vRef;
        private Context mcontext;
        private String mTeamID;
        private ImageView mImageView;
        String json_url;
        String JSON_STRING;
        int averageAge;
        int sumAge;
        String averageAgeDisplay;
        int convertAge;

        public BackgroundTask(ListView v, ImageView imageView, String mTeamID, Context context) {
            vRef = new WeakReference(v);
            mcontext = context;
            this.mTeamID = mTeamID;
            mImageView = imageView;
        }

        @Override
        protected void onPreExecute() {

            json_url = domain + "/" + sport + "/" + "roster" + "/" + mTeamID + format + "?"
                    + "access_token=" + accessToken;


        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            String result;
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (hjn5906@rit.edu)");
                InputStream inputStream = httpURLConnection.getInputStream();
                String code = Integer.toString(httpURLConnection.getResponseCode());
                Log.d("Server Response Code:", code);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                JSON_STRING = null;
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                result = stringBuilder.toString().trim();
                jsonArray.add(result);

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return jsonArray;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        protected void onPostExecute(ArrayList<String> result) {

            jsonArrayResult = result;
            rosterAdapter = new NBATeamsRosterAdapter(mcontext, R.layout.nba_teams_row_layout);
            listViewResults = (ListView) vRef.get();
            json_string = jsonArrayResult.get(0);
            displayNameList = new ArrayList<>();

            listViewResults.setAdapter(rosterAdapter);

            try {
                int count = 0;
                Object json = new JSONTokener(json_string).nextValue();

                if (json instanceof JSONObject) {
                    jsonObject = new JSONObject(json_string);
                    jsonArray2 = jsonObject.getJSONArray("players");
                    String playerName, playerPosition, playerAge, playerHeight, playerWeight;

                    while (count < jsonArray2.length()) {
                        JSONObject JO = jsonArray2.getJSONObject(count);
                        playerName = JO.getString("display_name");
                        playerPosition = JO.getString("position");
                        playerAge = JO.getString("age");
                        convertAge = Integer.parseInt(playerAge);
                        sumAge += convertAge;
                        playerHeight = JO.getString("height_formatted");
                        playerWeight = JO.getString("weight_lb");
                        NBATeamsRoster roster = new NBATeamsRoster(playerName, playerPosition, playerAge,
                                playerHeight, playerWeight);
                        //displayNameList.add(jsonObject.getString("team_id"));
                        rosterAdapter.add(roster);
                        // Notifies attached observers that data has been changed and the View refreshes
                        // Without this app crashes at times
                        rosterAdapter.notifyDataSetChanged();
                        count++;
                    }

                    averageAge = sumAge / count;
                    averageAgeDisplay = Integer.toString(averageAge);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View viewIn) {

                    Toast toast = Toast.makeText(getActivity(), "Average age: "
                            + averageAgeDisplay, Toast.LENGTH_SHORT);

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 140);
                    toast.show();
                }
            });


        }
    }


}
