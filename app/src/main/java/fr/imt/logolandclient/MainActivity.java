package fr.imt.logolandclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {

    public final static String catImageBase64 = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4QBgRXhpZgAASUkqAAgAAAACADEBAgAHAAAAJgAAAGmHBAABAAAALgAAAAAAAABHb29nbGUAAAMAAJAHAAQAAAAwMjIwAqAEAAEAAAC9AAAAA6AEAAEAAAC0AAAAAAAAAP/bAEMACAYGBwYFCAcHBwkJCAoMFA0MCwsMGRITDxQdGh8eHRocHCAkLicgIiwjHBwoNyksMDE0NDQfJzk9ODI8LjM0Mv/bAEMBCQkJDAsMGA0NGDIhHCEyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMv/AABEIALQAvQMBIgACEQEDEQH/xAAfAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgv/xAC1EAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrHBFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OXm5+jp6vHy8/T19vf4+fr/xAAfAQADAQEBAQEBAQEBAAAAAAAAAQIDBAUGBwgJCgv/xAC1EQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2FxEyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2dri4+Tl5ufo6ery8/T19vf4+fr/2gAMAwEAAhEDEQA/APfXbauffFM81iOFzTpfue/aosZUYPBHNBLeo4TMRnaPYUNPtBOKYo4Cr1H8qXAAPoOSaYrsd5pyRtHXApRKSSMDg00Z25PpUE11FAu6WVIkHJdiBSC7LJlxngcUhmJAKgH61Qt9W064Plw31vIw42rICaufeVgOFHBNA7seZj1xxzR5pxkge1NyACfypigkqADwcHPagLkvnN02jrSee2M7RnsPWmActnqfSm4ADtkDjH0oC5KZzzgA8frQJyQTgcdaiz8oYAhc/nQQRgcjBoC7JfPPoOuBSLcMRnaAKjJ6cZPUAetR8kL3PWgLsn+1Hj5eopDdMMfKMmoGyEVf4jzWTr3iLT9BtGlupRvUfKgPLGgFc22vdgJYAD61Tj8Q2Us4gS5t2m/uLICa8P8AEHjjUtbdly8Nt2jj/qax/D9xcSeJLDyxtJnTnPJ5FZufY0UHa7PpT7YxCnYOSaBduWAKKAepzVYcgAHvikZcgEdB0FaGdy0t6WYYUYJ65q5WWOXAHStSgaGyfdqIcAAdFqV/u1GPu5PUGgTAjAzSMCFIAB7YpT82eeOMUpGRjoTxQIZK6xRsxPGM189eKvFV5r2tXGx2FtGxWNc8ACveNbcrot6VyMQPg/8AATXzSgRImJ5JYk81lVdjajFN6kiF8hvMYN6gkV0ug/EHWPD8ixzMbuz7xSNkj/dPb+Vcj5hJwKnRsrgkfTNZpm0oJn0VoPiTTfENgLqwlDlQN8Z4ZG9CK1yCMDPJ6mvmPTdTvvDmprf6bKY2H3oz91x6Eele3aD8QdI1nT47kyiGYYEsTdUNbRnfc5pQcdjrABjJ4xSZDHCjj3rGm8T6Wo+WZpB32jrTk8UaZJ84kYYGMbaq6JszYP3tpYYzSHgZPQVgz+KrOEExrv55yccVnv44RXI+y5Tsd3WjmQWZ1uDsOfzppwqAHACiuDufGl1P8kSiMe3NUNQ8etHptw7uu2PjI+87elLmQ+Vm74u8bWnh+2ZUbzLonCIO5rxu8vL7Wbpry9cs7HjJOB9BVKTUH1TUWvL6TLs3C5ztHoBVl7m2jXCrn0JrNu5rGNiCUSpn5xx0rb8AWTXXjC3nI8z7Opl2r0HGB+prlri48yT5V3em3oK9T+FOlLFp8+q3LCNp32RpnkqOp/P+VKKuypu0T06Fi685GP5U/IAAyMDkfnSLjB2nNLxnC/dUDn1NbnMCKdyg5ArVrM+8464rToKQ1ug+tRnjJOcZzipG6UzAOAKBMMUuc/WkXpnvzxQM5OeB2oEQXEAubWSGT7jqVPuCDXy7qUE2matc2kq4MMzIeOpBr6nYnGegNeFfFCxig8RfawvyzKC3OMN0/XFZVVdG1GVpHDzyqVHl/ePtSQq+cnC/Wo5Zkhbg8H0qA3sQGWP4HrWUUdEmaW7zvkDAkVWEk+mXJnt2Az98djTYJg8oKKdo746VPOMoeCwNWiGzWg8Qs2w+aUB5Unp9K0BrLFcFiTjgZ4rjoYhIojY7RnOT61faRba0CK2WHQ96ZGhvx6313ZPGeeuKlGto4I24weK5Qz+ZhhnkZOP1p3nhIiQenH1oFY3tX1t0tB5PytJkE1z2q3bNaWsCvlgPMc/7R/8ArAVG98OY5BxVC8ZHJZT09aASI4ruRMjop9e9XPtToi75A3GducfnVK3jUEyPtLDgbjxmpjGsnyx7pnPLyvwo+goKuSCd7iRIlGSxwAvrX0V4esLnTNGs7M24UwxhThup7npXg3hCySTxVp8QPmfv1bpxkHOK+nIizRgv1xzVQRnVlfQSLcRg/LxzTzx0yaUAdhx3+tLj7xH4VoZCKCZB/dGMmtSsscBeATxWpQNDZM7eOuaZgg+wqRjgZqMHgE0A9xcYbNJ1IHagnBA7mgnGOe4oERyOEiLnoK8s8f2yar5UoI3QOOB3U9Qf0r1SRQYip6E88dq861+Ix3TsqnaP72KiexcHZnhGpSM924x5SsTjHX6VUFvIAHCZzyfWum8UWC2d2lzEiiGTJOf4T6VjRXBlBUMOP0rPY3vcW0DRlWXOOmMVpFiSMbSp9T0qgocdO3rVqN2OSRzQhMZPheed3rVbc7q7HscD9aumEsxyPoabJGsYCY4x+tMVxkAzaqfQgfhUEx2cccVcCGJEwvBOTVe6QOQ/HU8UCKV790N/EOQaqTFtysM7SB/OrbfvpAOxHFNdfLlC46DvTGVecrFnAQfMT2Pf/Cn+c74jQERg8L3PuamSDK4xx1J/z/n8qR18sYQbR3Pc0Ad38MrWRtdWVVQOBhWYDK/T3/CveYlZYwpJJ9c14N8NL+G11dEmz83AOcY+te+oQVBJ4/pVQMqm4udo596TPBHbjNGQfmOcnp9KNu58HhevvmrIEQfvF7nOcVq1lK2GGAK1aBooavqUGl2a3Fw21C4XPuQf8K5268a6fG48tzIp9PSk+Jr+X4VRs4/0lP5NXjz3ZCkhs+o9KlyaK5bnsX/CbafnduYAjnI6GrVt4t02RQWnUN23V4S+pMmRvP8A9aoV1aRn2oxyegNLnYch9Jx39tcwkxzowI6qa5TXgjl/LHXvXAeGp7uW8QrKyovX5q7O+mAiJznjrScroErM898Q2LTRMjvx1AriHt/s0pClQwPORXfaw/mbucmuNu4ZRJnZxnrxUmiHWyLOQSvUdq0EtlIwDUNjEsWOAB61c3rz1HuKBkTxIh2HIJ/I1XurdDHvBHAzkVLM4yNpzj1qvdT+ZaMEA3DjGevvQhCph7NS2ASDWdclUhRs9JOn4VJBcKtoiM3zE8j2FU7lmmRVHXrQNIYuFuc4yucinXa4uVbOQeKZKxUAkYIx39abJMsiKp+97etMC6gBj4XJP4AfjUbJ5nU8U6IvIAkaOxVckKP1ppuI45jDIjh15PPTPNJjsWNHlNnfIyk9a+kfD12t3pNtJvLEoM561852ixyyKff0r37wZGY/D1sCeCuTThuZ1Njot3zDP6ChR/8AXNNAwMcZ6fhSnDA5J2/THStTESMruwNx57fWtasoZ3qo4xjOK1aConD/ABWGfCCf9fSfyavD3Lnkcnoa9s+LZI8HRkHH+lp/6C1eJQq7Nnk1nLc0WxDNHuXjJ4/Kp7S1wVbbuI6YrQgs5ZnzHGNvuK3rHSoFdXfYXHOFpA2EFzHpGnJJJCcsc7Bxk03+3bjUDxC0SerEAfgKh1/96m2M5VTjjpWBbXA80+Y7Hb2pDsdQYPOBJwV7k96wb6MKzDGfcLWpZ3q3BEWMk8cinXloC7Ps6decUwucwTMQdmOO2ar3M0sRjJY+WGHmAD+EnmtG7jEbbwCB/Os69lj2nJHoQe4pDNPxRHYwaQL3S1ZQF5O5iDzjPNZmn6fLeaZHOZWDsm4qR7ZrL+1y28ZgjPn2zDmMnkZ7Y71cTWL64tmtbK38tmXBkP8ACPYVnLn2RUUuo+101p1WVT8hPH+fzqx/ZyKjOwbPXp7Vf0SA2ljFG48xjk9enrWncW2223FRlu3vWtiWzg7tOFXvtGaS2hTzcORuAzitC+gKyYbg4/Osi8h8y73BzGFGMipeqsNElo7afdXEjycPwMHtmo2mNzcSXBGN5+X6DiqrRKG+eQyH3pysS2QMelNJ7sLrodDpOXmRR68V9FaChj0a0jx0jB6e1fPeiIIv3jE5AzXuHgjWBf6OsTk74/lJPcU4bkVNjqhwox1PU4ySaX7qjaeQcZ/GkBJGegxgUmCeAeB0/wAa1MQXduKr1JHPt/kVr1kRqBt4By2OT1rXoKicB8YDjwXH/wBfkf8A6C1eM25+QZJ9q9n+Lwz4NiHH/H5H1/3Wrxq3iZcZwQfSs5bmiNaxZ8gZwM9hW1bExxuc7jjpWZZWr7NwJ9+a0wmyEgHt+VIDOuGDQNu/GuWuA0cnJG08ggV0jybWZWwRWXfW44yuc84HWpKKdjqBtplY7mOetdEdUjntcv169O9cqwaL5yoAHTNLFqaKCD83sKdxWNqWaKZdgxu9D1rDu4TGzKe/60S6nDkNEVBHqaeL5bpdpClvWgZiyRBZPl4OB1qS2uJ4ZQqn5T1FWJkfcuMAZ6GpIbR5pfkBZ/YcAUwZu6cwMMZxyH6/yrfuIy8KpnnoaxbOxliSMnOE+YgHua6S1InRy4wEHTrimQzjtWsTG29ecDBArm7kBd/IznI57dK7bUpFkmdtuMcHnrXKXVsGkkzwM7cipe5aMRhzzxUtsgL45I9alktwp+8CKakoQ4UY9KYzetH2Lt5O6vYfh6qrYN8oILcnkf8A668OtZ3LjOQK9n+Hs3+inJyPr0pR3JqbHoKMCwJPGeAaUsSODwerY/lTBJwTjjqO2aN33SeSBz6CtjnJVJ3rjp2yfetesUMCVOTksDnH5VtUFxOG+K0Zk8JRKo/5fE/9BavMdPtUiAEgBPWvV/iT/wAixH0/4+U6/Rq8m3MM4LAdqiW5aNGe7EQKgKAKgiujNKVPTHrVB3YoOSTTY5TFKvTrUDsSzoQ544zVOaQSKVPYcnpWhdjfGHX9KzDh8gJnPWgZRliWWEkKSnT1qgbBDGzMrKGOOTjH5VqEkMqqSB27c1WnJ+4zjzT0UdaB3OZurMplo3JHTim2N6IpQHbGPetGeHb90DP96RuB+FZd0SpxEWz38sbR+NNCOpgKXUQG4L6cDn9au2ULRkYdHGckZ2muP0fUpIZzvcbB1CnOPqa6+0njucPlWBIx2p2A3xJvhG5kRzkbVOTj1NUJdZm04TEjKN0cc8ehqONxbD5IVDE54fnGaZPcQeW4kizuHILU7isc9f8AiWV2eMRASfdz/wDWqOKWWWLfKR82Mg9BUdja2rzXFxI/muGKx5OMAd6hv9RjskKABmJ4HpUvVlpWQ25eOCPduBJ9P6Cs1JgzkkAioJrt7qTc3Q9qUIMK6564oEbNky71IOPrXr/gJlaParFX454NeM2Eh3/NyO9en+CbpUu1UOVVhj1FJaMJ/CesJMRnDhiOArdqesgkbDEqpAz7iqcbqkTMc7hwBnj/ACfeneb5m05VnxjI6DJPStjmLysDInXO/v2Hauirk0ceai4G0Ed/fvXWUFxOO+JThPC6E/8APyn8mrx+SSVx8uQR29a9i+JBA8LoSM/6SmPyavHbiVsjHB9KiW5aIgWK/vCB3GTzQMchTk9jUZfzTsK5H8qWOIKCSSAO4qCi3BMwTa+cehoeJXbchwPXsKqhy+Qr7geB2pkkxU7FYjb79aAJZFEbFldCehJHJrNnuEhdkWMB2PJPU1beQyEHaMr2Y1TuY4blgIeGzyvQ0AZ93CsjuxLbgMhcmsO6jIUiU/Pn/VDgD6/5zXVtCyRMSvzLwpLZ/Wufvo41mKuCGJzu24oQzKEeB82Mt0XoBSW95cw3C5mYdmPt6VNKhM77hhlOT7Cka05PoehqrhY6eDV7aeMR+Y27GPf86ju/swBDXOd4HAP4VzqwGM45DHvSTIVOWLbx0OaBJWLV9dW1s4W3+ZsZzWNPcvduxYfNiluB8yswwQaiK4bevQ+naiwx0X3fTHarS8rgN97nFVVk+b51x/tLViMqwBHUUAWopHUDC5x6da9E8B5uLtMSqpHQNXA2jxlxuwGr0PwhbmS4SQDaPUVPUHseroxZAXw2OWbt6U4OVdVGBuJzz296qxELC3AGTjnmpBIBhjjJOT9OlanOW4pN86oDwpGfTrXZ1wls4EyALgbhx689a7umVE4v4nqzeE1C9ftKfyavGn3Kv3gP1FezfE7H/CKLn/n5T+TV4xsXzMnJ/wBkZzWctzRDVbcQEVt3pirbh0jUGPr1wOtJCUhV5EAAA/I1JNcKxUswLkcYbr7ClYLleMqivkYcnHIrMuGXeSvy4HGen+f881rXUqSWyqqA7h0zmsWQyFmUKVUDG5iCD7UAiRLpY4z5h4xwxP3T/hTWiku182NDBMoI35wW/wARWXNdS2jjfEGXPC9f51PHq6PtJz5ijIIFMBLvUTuETKyXKnbkDIf/AD9KHtjdQGS7AjZR0HQmoriRbx0ZnC3Gfl2gf5zVEXN2WCtIQrkKQe496VhkLqbfUN7HdHJwT2xUsQEEjQyjMfO0nritSa1trq18mMFZkHCn+VVHtpW0/wCdcvETz3x6f1oGQbQV+Vgy55qnO235XU8UsTlFZgcgjBprSh49r9RyDSGUrpSE4O9D6VWU4UL1B6H0q3MQVyPxqBYw4xiqECgn7wFTxLg4HFRxozHDZ4qyuyI57+tArGlplt51wI5cAnoT0NelaEj6VCChVm9xkV5hZ6pHHMu0c+pr0XSr5by1DDkj0NJbilsdENbvB08vrn7tB1y8IA/d4HbbWb2pK0MTZt9bvHvIlJjAaRc4XHevZK8Js/8Aj+t/+ui/zr3amVE474lLv8LIp/5+U/k1eOSxhD8hO4cZr2j4hKW8NoB0+0KT9MNXkc6EFgEGAM46VD3KMoEpN8zMckZwDxVyXF1KgQlSvIIXkev0pqRyMhyRk46dPwqdV24CAlvbgGgBs0ccSLDHH5szL91uR9Tms2SEgBJNu4nACrtB+uK0VhaFmmJ+d+COv4DNZ127RiQ5+baW3seE4zSsO5z9wLYXZiSLc4zwCeOfeq72kbXP2fDNLtyU2H+eK1oLVbPTBclWa4dcqTySxH/16u6XYmC2a6kVVOAXx2+tA7mEmhyKw3rsZuQCCc/hVq68PaveKn2WAP7hgD1z3xXUiIRgynLt/wBNHJ/75qFd7TArcMXzwnb+X86YrmbJpVzBqMVzOoj2gFhkHnvWbcXDwmRY1LI7Zzg/L1/xNdZelgq+aXU9wFBH4isKRhuJBG01L0KWpxsTSR3Dh42ETZHIxTQo8wjPy+9ddvDDDAEemM1SvNMtpIt8SbGzyB0ouM52eJV5ToeopsKdSeKddI1vKYi3PUGmoGdepyPSnYVyZimzapGfpUTEBckjNOELbSWNJ9mG7k0AMRVIYjqAfxFdj4RlkBdSeK5aKIldqjluBXb+HdPFpAzscs5z9KNxN2R0w5FJTQ3FIWNWjItWZ/063/66L/Ovd68Fsm/063/66r/MV71THEiuLaC6j8u4hjlTOdrqGGfxqodC0huumWZ+sC/4UUUFB/YOkZz/AGXZ5/64L/hR/YOkZB/suzyO/kL/AIUUUAB0LSD10yzP/bBf8Kjbw1obghtHsGB6g26c/pRRQAv/AAjmiEAf2RY4XoPs68fpThoGjgYGlWQ/7YL/AIUUUAB0DRyADpVkcdMwLx+lJ/wj2i5z/ZNln18hf8KKKAFbQdHcYbS7Mj3gX/Coz4Z0Fuui6efrbJ/hRRQAn/CL6B/0BdP/APAZP8KD4Y0A9dF0/wD8Bk/wooosBG/g/wAMynMnh/S2Pq1oh/pSDwd4ZXp4e0sfS0T/AAoooAX/AIRDw1/0L+mf+Aif4Uf8If4Zzn/hH9L/APARP8KKKAFHhHw2pBGgaYMelqn+FTr4e0VBhdJsh9IF/wAKKKAHf2FpH/QMs/8Avwv+FH9haT/0DLP/AL8r/hRRQAo0PSVYMum2gIOQRCvH6VfoooA//9k=";

    static final String TAG = MainActivity.class.getName();

    static final String BASE_URL = "http://10.0.2.2:8000/";
    static final String IMG_SEARCHES_RESOURCES = "img_searches/";
    static final String ID_IMG_SEARCH_KEY = "id_img_search";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LIBRARY_PICKING = 2;

    private RequestQueue queue;

    private Button captureButton;
    private Button libraryButton;
    private Button searchButton;
    private ImageView imageView;
    private ProgressBar loadingCircle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        captureButton = findViewById(R.id.button_capture);
        libraryButton = findViewById(R.id.button_library);
        searchButton = findViewById(R.id.button_search);
        imageView = findViewById(R.id.imageView);
        loadingCircle = findViewById(R.id.loadingCircle);
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
     * Called by click on the search button
     * Shows loading circle and call uploadUserImage()
     *
     * @param view
     */
    public void searchPicture(View view) {
        Log.d(TAG, "Click on button " + getResources().getString(R.string.searchButton));
        if (imageView.getDrawable() == null) {
            Toast.makeText(this, "Must capture or pick an image first", Toast.LENGTH_LONG).show();
        } else {
            searchButton.setVisibility(View.GONE);
            loadingCircle.setVisibility(View.VISIBLE);
            captureButton.setClickable(false);
            libraryButton.setClickable(false);
            Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            this.uploadUserImage(this, image);
        }

    }

    static String responseLocation = null;
    /**
     * Upload an Image on the REST server
     *
     * @param image
     */
    public void uploadUserImage(final Context context, final Bitmap image) {
        //Url émulateur
        String url = "http://10.0.2.2:8000/img_searches/";
        //Url si réseau local, à modifier en fonction de l'ip du serveur
        //String url = "http://192.168.43.92:8000/img_searches/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadResultView(responseLocation);
                        searchButton.setVisibility(View.VISIBLE);
                        loadingCircle.setVisibility(View.GONE);
                        captureButton.setClickable(true);
                        libraryButton.setClickable(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Upload did not work!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Upload didn't work!");
                searchButton.setVisibility(View.VISIBLE);
                loadingCircle.setVisibility(View.GONE);
                captureButton.setClickable(true);
                libraryButton.setClickable(true);
            }
        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("image", ImageUtils.convert(image));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    return jsonBody == null ? null : jsonBody.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonBody, "utf-8");
                    return null;
                }
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                responseLocation = response.headers.get("Location");
                Log.i(TAG, responseLocation);
                String data = null;
                try {
                    data = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        queue.add(stringRequest);
    }

    private void loadResultView(String searchLocation) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ID_IMG_SEARCH_KEY, searchLocation);
        startActivity(intent);
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
