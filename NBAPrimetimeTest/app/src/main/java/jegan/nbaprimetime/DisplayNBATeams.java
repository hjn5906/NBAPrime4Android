package jegan.nbaprimetime;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
import java.util.Calendar;
import java.util.List;

public class DisplayNBATeams extends Fragment {

    ArrayList<String> jsonArray = new ArrayList<>();
    ArrayList<String> displayNameList;
    ArrayList<String> jsonArrayResult = new ArrayList<>();
    String domain = "https://erikberg.com";
    String sport = "nba";
    String date = "20160504";
    String accessToken = "894346fa-07b9-4878-ba14-0a1b83eb04f8";
    String format = ".json";


    Calendar cal = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String formatDate = df.format(cal.getTime());


    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray2;
    NBATeamsAdapter teamsAdapter;
    NBATeamsAdapter teamsAdapterHeader;
    ListView listView;
    ListView listViewHeader;
    ListView listViewResults;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_nba_teams_layout,
                container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listViewHeader = (ListView) view.findViewById(R.id.listviewHeader);
        listView = (ListView) view.findViewById(R.id.listview);
        teamsAdapterHeader = new NBATeamsAdapter(getActivity(), R.layout.nba_teams_header_row_layout);
        final Bundle bundle = new Bundle();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String displayName = displayNameList.get(position);

                //Toast.makeText(getActivity(), displayName, Toast.LENGTH_SHORT).show();

                //Toast.makeText(getActivity(), displayName, Toast.LENGTH_SHORT).show();

                bundle.putString("teamID", displayName);

                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction();
                Fragment displayNBATeamsRoster = new DisplayNBATeamsRoster();//the fragment you want to show
                displayNBATeamsRoster.setArguments(bundle);
                fragmentTransaction
                        .replace(R.id.displayFragment, displayNBATeamsRoster);//R.id.content_frame is the layout you want to replace
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        listViewHeader.setAdapter(teamsAdapterHeader);
        NBATeams teams = new NBATeams("City", "Name", "Conference", "Division");
        teamsAdapterHeader.add(teams);
        teamsAdapterHeader.notifyDataSetChanged();

        new BackgroundTask(listView, getActivity()).execute();
    }


    class BackgroundTask extends AsyncTask<Void, Void, ArrayList<String>> {
        private WeakReference vRef;
        private Context mcontext;
        String json_url;
        String JSON_STRING;

        public BackgroundTask(ListView v, Context context) {
            vRef = new WeakReference(v);
            mcontext = context;
        }

        @Override
        protected void onPreExecute() {

            json_url = domain + "/" + sport + "/" + "teams" + "/" + format;

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
            teamsAdapter = new NBATeamsAdapter(mcontext, R.layout.nba_teams_row_layout);
            listViewResults = (ListView) vRef.get();
            json_string = jsonArrayResult.get(0);
            displayNameList = new ArrayList<>();

            // Toast.makeText(getActivity(), json_string, Toast.LENGTH_LONG).show();

            listViewResults.setAdapter(teamsAdapter);

            /*listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String displayName = displayNameList.get(position);

                    //Toast.makeText(getActivity(), displayName, Toast.LENGTH_SHORT).show();

                    Toast.makeText(getActivity(), displayName, Toast.LENGTH_SHORT).show();
                }
            });*/

            try {
                int count = 0;
                Object json = new JSONTokener(json_string).nextValue();
                if (json instanceof JSONArray) {
                    //you have an array

                    jsonArray2 = new JSONArray(json_string);

                    /*//tests to see json output is correct
                    int maxLogStringSize = 1000;
                    for(int i = 0; i <= jsonArray2.length() / maxLogStringSize; i++) {
                        int start = i * maxLogStringSize;
                        int end = (i + 1) * maxLogStringSize;
                        end = end > json_string.length() ? json_string.length() : end;
                            Log.v("outputTest", json_string.substring(start, end));
                    } */

                    String teamName, teamPrefix, conference, division;

                    for (int i = 0; i < jsonArray2.length(); i++) {
                        jsonObject = jsonArray2.getJSONObject(i);
                        teamPrefix = jsonObject.getString("first_name");
                        teamName = jsonObject.getString("last_name");
                        conference = jsonObject.getString("conference");
                        division = jsonObject.getString("division");
                        NBATeams teams = new NBATeams(teamPrefix, teamName, conference, division);
                        displayNameList.add(jsonObject.getString("team_id"));
                        teamsAdapter.add(teams);
                        // Notifies attached observers that data has been changed and the View refreshes
                        // Without this app crashes at times
                        teamsAdapter.notifyDataSetChanged();
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
