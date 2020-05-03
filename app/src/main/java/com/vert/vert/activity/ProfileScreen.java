package com.vert.vert.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.vert.vert.R;
import com.vert.vert.Static.Apilist;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.pilot.fragment.ProfileFragment;
import com.vert.vert.services.VolleyMultipartRequest;

import net.gotev.uploadservice.MultipartUploadRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileScreen extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.OnConnectionFailedListener{

    private Button logout,submit,back;
    private ImageView profilepic;
    private EditText username,mobilenumber,email;
    public GoogleApiClient mGoogleApiClient;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;


    private static String profileadd = "no";
    // Storing server url into String variable.
    String HttpUrl = Apilist.VIEWPROFILE;
    StringRequest stringRequest;
    String path,namev,emailidv,phonenumberv;
    String uri = "@mipmap/ic_vert_launcher";
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        // to check profile pic is changed or not
        profileadd = "no";

        logout = (Button)findViewById(R.id.logout);
        submit = (Button)findViewById(R.id.button2);
        back = (Button)findViewById(R.id.back);
        profilepic = (ImageView) findViewById(R.id.profile_pic);
        username = (EditText) findViewById(R.id.ed_username);
        mobilenumber = (EditText) findViewById(R.id.ed_mobilenumber);
        email = (EditText) findViewById(R.id.ed_email_id);


        logout.setOnClickListener(this);
        profilepic.setOnClickListener(this);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);

        if(Userdetails.logintype.equals("phonenumber")){

            mobilenumber.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    mobilenumber.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"You can't change the number",Toast.LENGTH_LONG).show();
                    return false;

                }
            });

        }
        //gmail login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        if(Userdetails.username.equals("")){

            username.setText("");

        }else {

            username.setText(Userdetails.username);

        }

        if(Userdetails.usermailid.equals("")){

            email.setText("");

        }else {

            email.setText(Userdetails.usermailid);

        }


        if(Userdetails.userprofilepic.equals("")||Userdetails.userprofilepic.equals("null")){

            Toast.makeText(getApplicationContext(),"Please Set a Profile Pic",Toast.LENGTH_LONG).show();

//            int imageResource = getResources().getIdentifier(uri, null, ProfileScreen.this.getPackageName());
//            Drawable res = getResources().getDrawable(imageResource);
//            profilepic.setImageDrawable(res);

        }else {

            Glide.with(getApplicationContext()).load(Userdetails.userprofilepic).into(profilepic);
        }



        getprofile();

        //Requesting storage permission
        requestStoragePermission();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);

            return;

        }

        // to solve the URI error when take pic
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.logout:

                logout();

                break;

            case R.id.button2:


                if(profilepic.getDrawable() == null){

                    Toast.makeText(this, "Please Set New profile Pic", Toast.LENGTH_SHORT).show();

                }else {

                        if(mobilenumber.getText().toString().equals("")){

                            mobilenumber.setError("Please Enter Mobile number");

                        }else if (email.getText().toString().equals("")){

                            email.setError("Please Enter Email");

                        }else if (username.getText().toString().equals("")){

                            username.setError("Please Enter Username");

                        }else {

                        uploadMultipart(bitmap);

                    }
                }

                break;

            case R.id.profile_pic:

                selectImage();

                break;

            case R.id.back:

                startActivity(new Intent(ProfileScreen.this,HomeScreen.class));
                finish();

                break;

        }
    }



    private void logout() {


        String social = Userdetails.logintype;


        if (social.equals("gmail")) {


            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Log.d("logout", "logout");
                        }
                    });

            deleteCache(this);
            startActivity(new Intent(ProfileScreen.this, LoginScreen.class));
            finish();


        } else {

            deleteCache(this);
            startActivity(new Intent(ProfileScreen.this, LoginScreen.class));
            finish();


        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

   /*
   * This is the method responsible for image upload
   * We need the full image path and the name for the image in this method
   * */

    public void uploadMultipart(final Bitmap bitmap) {

        if(profileadd.equals("yes")){

            // For get Drawable from Image
            Drawable d = profilepic.getDrawable();

            Bitmap bitmapOrg = ((BitmapDrawable)d).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            byteArray = stream.toByteArray();

        }else {

//                    GlideBitmapDrawable  BitmapDrawable
            Bitmap bmp = ((GlideBitmapDrawable)profilepic.getDrawable().getCurrent()).getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            byteArray = stream.toByteArray();

        }

        //getting name for the image
         namev = username.getText().toString().trim();
         emailidv = email.getText().toString().trim();
         phonenumberv = mobilenumber.getText().toString().trim();


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Apilist.ADDPROFILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        try {
                            JSONObject obj = new JSONObject(new String(response.data));



                            startActivity(new Intent(ProfileScreen.this, HomeScreen.class));
                            finish();
                            Toast.makeText(getApplicationContext(), "Successfully Updated ", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("username", namev);
                params.put("user_id", Userdetails.userid);
                params.put("email_id", emailidv);
                params.put("phone_number", phonenumberv);

                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                params.put("profile_picture", new DataPart(imagename + ".jpeg", byteArray));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    //image from gallery

    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogeTheme);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {

                    //to know user click on image view or not

                    profileadd="yes";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(intent, 1);


                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    //to know user click on image view or not

                    profileadd="yes";

                    Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);


                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }



    @TargetApi(Build.VERSION_CODES.N)
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;

                        break;

                    }

                }

                try {


                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

                            bitmapOptions);


                    profilepic.setImageBitmap(bitmap);


                    String path = Environment

                            .getExternalStorageDirectory()

                            + File.separator

                            + "images" + File.separator + "default";

                    f.delete();

                    OutputStream outFile = null;

                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {

                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outFile);

                        outFile.flush();

                        outFile.close();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            } else if (requestCode == 2) {


                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                bitmap = (BitmapFactory.decodeFile(picturePath));


                profilepic.setImageBitmap(bitmap);


            }

        }
    }


    public void getprofile(){



        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {



                        // Showing response message coming from server.
//                                        Toast.makeText(ProfileScreen.this, ServerResponse, Toast.LENGTH_LONG).show();

                        try {

                            JSONObject jsondata = new JSONObject(ServerResponse);


                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);


                                String namev = json.getString("username");
                                String emailv = json.getString("email_id");
                                String phonenumberv = json.getString("phone_number");
                                String imagev = json.getString("profile_picture");


//                                Toast.makeText(ProfileScreen.this, namev, Toast.LENGTH_SHORT).show();

                                if(namev.equals("null")){

                                    String usernm = "";
                                    username.setText(usernm);


                                }else {

                                    username.setText(namev);
                                    Userdetails.username = namev;

                                }

                                if(emailv.equals("null")){

                                    String emailid = "";
                                    email.setText(emailid);

                                }else {

                                    email.setText(emailv);
                                    Userdetails.usermailid = emailv;

                                }


                                if(phonenumberv.equals("null")){

                                    String phoneno = "";
                                    mobilenumber.setText(phoneno);

                                }else {

                                    mobilenumber.setText(phonenumberv);
                                    Userdetails.userPhonenumber = phonenumberv;

                                }




                                if(imagev.equals("null")){

                                    Toast.makeText(getApplicationContext(),"Please Set a Profile Pic",Toast.LENGTH_LONG).show();
//                                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
//                                    Drawable res = getResources().getDrawable(imageResource);
//                                    profilepic.setImageDrawable(res);

                                }else {

                                    Userdetails.userprofilepic = imagev;


                                    Glide.with(getApplicationContext()).load(imagev).into(profilepic);

                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {



                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                100,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        // Showing error message if something goes wrong.
                        Toast.makeText(ProfileScreen.this, "Please Update The Profile", Toast.LENGTH_LONG).show();

                        Log.d("error", volleyError.toString());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.

                params.put("user_id", Userdetails.userid);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileScreen.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);




    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


}
