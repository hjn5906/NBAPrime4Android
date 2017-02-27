package jegan.nbaprimetime;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.ArrayList;


public class DisplayNBALeaders extends Fragment {

    ArrayList<String> jsonArray = new ArrayList<String>();
    ArrayList<NBALeaders> leadersArray;
    ArrayList<String> jsonArrayResult = new ArrayList<String>();
    ArrayList<String> displayNameList;
    String domain = "https://erikberg.com";
    String sport = "nba";
    String accessToken = "894346fa-07b9-4878-ba14-0a1b83eb04f8";
    String format = ".json";


    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray2;
    NBALeadersAdapter leadersAdapter;
    NBALeadersAdapter leadersHeaderAdapter;
    ListView listView;
    ListView listViewHeader;
    ListView listViewResults;
    Spinner spinner;
    String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_nba_leaders_layout,
                container, false);

        spinner = (Spinner) view.findViewById(R.id.statsCategorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.stat_categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.listview2);
        listViewHeader = (ListView) view.findViewById(R.id.listviewLeadersHeader);
        leadersHeaderAdapter = new NBALeadersAdapter(getActivity(),
                R.layout.nba_leaders_header_row_layout, new ArrayList<NBALeaders>());
        NBALeaders leadersHeader = new NBALeaders("Player", "Rank", "Stat");
        leadersHeaderAdapter.add(leadersHeader);
        listViewHeader.setAdapter(leadersHeaderAdapter);

        leadersHeaderAdapter.notifyDataSetChanged();

        leadersArray = new ArrayList<NBALeaders>();
        leadersAdapter = new NBALeadersAdapter(getActivity(), R.layout.nba_leaders_row_layout, leadersArray);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                text = spinner.getSelectedItem().toString();
                switch (text) {
                    case "No Stat Selected":
                        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        break;
                    case "Points":
                        text = "points_per_game";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "Assists":
                        leadersAdapter.notifyDataSetChanged();
                        text = "assists_per_game";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "Rebounds":
                        leadersAdapter.notifyDataSetChanged();
                        text = "rebounds_per_game";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "Field Goal Percentages":
                        leadersAdapter.notifyDataSetChanged();
                        text = "field_goal_pct";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "Free Throw Percentages":
                        leadersAdapter.notifyDataSetChanged();
                        text = "free_throw_pct";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "3 Point Field Goal Percentages":
                        leadersAdapter.notifyDataSetChanged();
                        text = "three_point_pct";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "Blocks":
                        leadersAdapter.notifyDataSetChanged();
                        text = "blocks_per_game";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "Steals":
                        leadersAdapter.notifyDataSetChanged();
                        text = "steals_per_game";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "Double Doubles":
                        leadersAdapter.notifyDataSetChanged();
                        text = "double_doubles";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;

                    case "Triple Doubles":
                        leadersAdapter.notifyDataSetChanged();
                        text = "triple_doubles";
                        new BackgroundTask(listView, text, getActivity()).execute();
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Toast.makeText(getActivity(), "Select a stat category to see NBA players leading in " +
                        "that particular stat.", Toast.LENGTH_LONG).show();
            }

        });
    }


    class BackgroundTask extends AsyncTask<Void, Void, ArrayList<String>> {
        private WeakReference vRef;
        private Context mcontext;
        private String category;
        String json_url;
        String JSON_STRING;

        public BackgroundTask(ListView v, String category, Context context) {
            vRef = new WeakReference(v);
            this.category = category;
            mcontext = context;
        }

        @Override
        protected void onPreExecute() {

            json_url = domain + "/" + sport + "/" + "leaders" + "/" + category + format + "?"
                    + "limit=5" + "&" + "access_token=" + accessToken;

        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (hjn5906@rit.edu)");
                InputStream inputStream = httpURLConnection.getInputStream();
                String code = Integer.toString(httpURLConnection.getResponseCode());
                Log.d("Server Response Code:", code);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                jsonArray.add(stringBuilder.toString().trim());

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
            leadersArray = new ArrayList<NBALeaders>();
            leadersAdapter = new NBALeadersAdapter(mcontext, R.layout.nba_leaders_row_layout, leadersArray);
            displayNameList = new ArrayList<>();

            listViewResults = (ListView) vRef.get();
            listViewResults.setClickable(true);
            json_string = jsonArrayResult.get(0);

            // Toast.makeText(getActivity(), json_string2, Toast.LENGTH_SHORT).show();

            listViewResults.setAdapter(leadersAdapter);

            listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String displayName = displayNameList.get(position);
                    displayName = displayName.replaceAll(" ", "_").toLowerCase();
                    //Toast.makeText(getActivity(), displayName, Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://www.nba.com/playerfile/" + displayName + "/"));
                    startActivity(i);
                }
            });

            try {
                int count = 0;
                Object json = new JSONTokener(json_string).nextValue();
                if (json instanceof JSONArray) {
                    jsonArray2 = new JSONArray(json_string);

                    for (int i = 0; i < jsonArray2.length(); i++) {
                        jsonObject = jsonArray2.getJSONObject(i);
                    }

                    String displayName, rank, statValue;

                    while (count < jsonObject.length()) {
                        JSONObject JO = jsonArray2.getJSONObject(count);
                        displayName = JO.getString("display_name");
                        rank = Integer.toString(JO.getInt("rank"));
                        statValue = Double.toString(JO.getDouble("value"));
                        NBALeaders leaders = new NBALeaders(displayName, rank, statValue);
                        leaders.setDisplayName(displayName);

                        displayNameList.add(displayName);
                        leadersAdapter.add(leaders);
                        // Notifies attached observers that data has been changed and the View refreshes
                        // Without this app crashes at times
                        leadersAdapter.notifyDataSetChanged();
                        count++;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
