package fr.imt.logolandclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getName();

    static final String BASE_URL = "http://www.logoland-server.com/";
    static final String IMG_SEARCHES_RESOURCES = "img_searches/";
    static final String ID_IMG_SEARCH_KEY = "id_img_search";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LIBRARY_PICKING = 2;

    private Button captureButton;
    private Button libraryButton;
    private Button searchButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        captureButton = (Button) findViewById(R.id.button_capture);
        libraryButton = (Button) findViewById(R.id.button_library);
        searchButton = (Button) findViewById(R.id.button_search);
        imageView = (ImageView) findViewById(R.id.imageView);

    }

    /**
     * @param view
     */
    public void capturePicture(View view) {
        Log.d(TAG, "Click on button " + getResources().getString(R.string.captureButton));

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     * Launch external activity for picking an image in phone library
     *
     * @param view
     */
    public void pickPicture(View view) {
        Log.d(TAG, "Click on button " + getResources().getString(R.string.libraryButton));

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_LIBRARY_PICKING);

    }

    /**
     * Upload the image on the REST server and start the ResultActivity with the ID returned by the REST server
     *
     * @param view
     */
    public void searchPicture(View view) {
        Log.d(TAG, "Click on button " + getResources().getString(R.string.searchButton));

        if (imageView.getDrawable() == null) {
            Toast.makeText(this, "Must capture or pick an image first", Toast.LENGTH_LONG).show();
        } else {
            Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            int imgSearchId = this.uploaduserimage(image);
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(ID_IMG_SEARCH_KEY, imgSearchId);
            startActivity(intent);
        }

    }

    /**
     * Upload an Image on the REST server
     *
     * @param image
     */
    public int uploaduserimage(final Bitmap image) {
        String stringImage = getStringImage(image);
        //TODO
        return 0;
    }

    /**
     * Return an Base64 string form en image file
     * Usefull for JSONObject ;-)
     *
     * @param bitmap
     * @return
     */
    public String getStringImage(Bitmap bitmap) {
        Log.i("MyHitesh", "" + bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            return;
        }
        Bitmap imageBitmap;
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                imageBitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(imageBitmap);
                break;
            case REQUEST_LIBRARY_PICKING:
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Log.i(TAG, e.toString());
                }
                break;
        }
    }

}
