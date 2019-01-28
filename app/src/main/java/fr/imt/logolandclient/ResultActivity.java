package fr.imt.logolandclient;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    //private EditText numberText;
    private ListView resultListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int imgSearchId = getIntent().getExtras().getInt(MainActivity.ID_IMG_SEARCH_KEY);
        //numberText = findViewById(R.id.numberText);
        //numberText.setText(""+imgSearchId);

        resultListView = findViewById(R.id.resultList);

        List<Logo> logos = genererLogos();
        ResultAdapter adapter = new ResultAdapter(this, logos);
        resultListView.setAdapter(adapter);




    }
    private List<Logo> genererLogos(){
        List<Logo> logos = new ArrayList<Logo>();
        logos.add(new Logo("Florent", Color.BLACK));
        logos.add(new Logo("Kevin", Color.BLUE));
        logos.add(new Logo("Logan", Color.GREEN));
        logos.add(new Logo( "Mathieu", Color.RED));
        logos.add(new Logo("Willy", Color.GRAY));
        return logos;
    }
}

class Logo{
    private String logoName;
    private int color;

    public Logo(String logoName, int color) {
        this.logoName = logoName;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }
}

class ResultAdapter extends ArrayAdapter<Logo> {

    //tweets est la liste des models à afficher
    public ResultAdapter(Context context, List<Logo> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_layout,parent, false);
        }

        ResultViewHolder viewHolder = (ResultViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ResultViewHolder();
            viewHolder.logoName = (TextView) convertView.findViewById(R.id.logoName);
            viewHolder.logoImage = (ImageView) convertView.findViewById(R.id.logoImage);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Logo logo = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.logoName.setText(logo.getLogoName());
        viewHolder.logoImage.setImageDrawable(new ColorDrawable(logo.getColor()));

        return convertView;
    }

    private class ResultViewHolder{
        public TextView logoName;
        public ImageView logoImage;
    }
}