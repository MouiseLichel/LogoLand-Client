package fr.imt.logolandclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    //private EditText numberText;
    private ListView resultListView;
    private String searchLocation;
    private ArrayList<Logo> logos;
    private RequestQueue queue;
    private ResultAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultListView = findViewById(R.id.resultList);
        queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        searchLocation = intent.getStringExtra(MainActivity.ID_IMG_SEARCH_KEY);

        logos = new ArrayList<>();
        adapter = new ResultAdapter(this, logos);
        resultListView.setAdapter(adapter);

        getSearchResult(this, searchLocation);
    }

    /**
     * Request the result list
     *
     * @param searchLocation
     */
    private void getSearchResult(final Context context, String searchLocation) {
        final String url = searchLocation;
        Log.i(MainActivity.TAG, "Request to " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String resultsString = jsonResponse.getString("results");
                            JSONArray resultsArray = new JSONArray(resultsString);
                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject result = resultsArray.getJSONObject(i);
                                String resultImageUrl = result.getString("image_url");
                                double resultscore = result.getDouble("score");
                                Log.i(MainActivity.TAG, resultImageUrl + " - " + resultscore);
                                logos.add(new Logo(""+resultscore, resultImageUrl));
                            }

                            /*JSONArray jsonResults  = jsonResponse.getJSONArray("results");
                            for (int i = 0; i < jsonResults.length(); i++) {
                                JSONObject jsonResult = jsonResults.getJSONObject(i);
                                String resultImageUrl = jsonResult.getString("image_url");
                                double resultscore = jsonResult.getDouble("score");
                            }*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //logos.add(new Logo("snow", "http://i.imgur.com/8lSRzQf.jpg"));
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "GetSearchResult didn't work!", Toast.LENGTH_SHORT).show();
                logos.add(new Logo("snow", "http://i.imgur.com/8lSRzQf.jpg"));
                adapter.notifyDataSetChanged();
            }
        });
        queue.add(stringRequest);
    }

}

class ResultAdapter extends ArrayAdapter<Logo> {
    public ResultAdapter(Context context, List<Logo> logos) {
        super(context, 0, logos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_layout, parent, false);
        }

        ResultViewHolder viewHolder = (ResultViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ResultViewHolder();
            viewHolder.score = (TextView) convertView.findViewById(R.id.score);
            viewHolder.logoImage = (ImageView) convertView.findViewById(R.id.logoImage);
            convertView.setTag(viewHolder);
        }

        Logo logo = getItem(position);

        viewHolder.score.setText(logo.getScore());
        viewHolder.logoImage.setImageDrawable(ImageUtils.loadImageFromWebOperations(logo.getUrl()));

        return convertView;
    }

    private class ResultViewHolder {
        public TextView score;
        public ImageView logoImage;
    }
}