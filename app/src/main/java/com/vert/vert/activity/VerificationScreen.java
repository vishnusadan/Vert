package com.vert.vert.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.vert.vert.R;
import com.vert.vert.Static.Apilist;
import com.vert.vert.Static.Userdetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerificationScreen extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";
    private String verify_from;

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    EditText otpno, otpno1, otpno2, otpno3, otpno4, otpno5;
    TextView status, resend;
    Button verifybutton;
    String name1, otpnumber, userid, number, key,inputName,inputlName,inputPhone,inputEmail,inputPassword;


    // Storing server url into String variable.
    String Verify = Apilist.VERIFICATION;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_screen);


        otpno = (EditText) findViewById(R.id.ed_otpno);
        otpno1 = (EditText) findViewById(R.id.ed_otpno1);
        otpno2 = (EditText) findViewById(R.id.ed_otpno2);
        otpno3 = (EditText) findViewById(R.id.ed_otpno3);
        otpno4 = (EditText) findViewById(R.id.ed_otpno4);
        otpno5 = (EditText) findViewById(R.id.ed_otpno5);



        status = (TextView) findViewById(R.id.status);
        resend = (TextView) findViewById(R.id.textView7);


        Intent intent = getIntent();
        number = intent.getStringExtra("number");


        Log.d("number", number);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(VerificationScreen.this);

        //progress daialog
        progressDialog = new ProgressDialog(VerificationScreen.this);


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendVerificationCode(number, mResendToken);

                otpno.setText("");
                otpno1.setText("");
                otpno2.setText("");
                otpno3.setText("");
                otpno4.setText("");
                otpno5.setText("");


            }
        });

        otpno.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = otpno.getText().length();

                if (textlength1 >= 1) {
                    otpno1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });

        otpno1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = otpno1.getText().length();

                if (textlength2 >= 1) {
                    otpno2.requestFocus();

                }
                if (textlength2  < 1) {
                    otpno.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });

        otpno2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = otpno2.getText().length();

                if (textlength2 >= 1) {
                    otpno3.requestFocus();

                }if (textlength2  < 1) {
                    otpno1.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });

        otpno3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = otpno3.getText().length();

                if (textlength2 >= 1) {
                    otpno4.requestFocus();

                } if (textlength2 < 1) {
                    otpno2.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });

        otpno4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = otpno4.getText().length();

                if (textlength2 >= 1) {
                    otpno5.requestFocus();

                } if (textlength2 < 1) {
                    otpno3.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });
        otpno5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = otpno5.getText().length();

                if (textlength2 < 1) {
                    otpno4.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
//                updateUI(STATE_VERIFY_SUCCESS, credential);
                status.setText("Verification Sucess");
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    Toast.makeText(getApplicationContext(), "Phone Number is Wrong", Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Please Contact JERT",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
//                updateUI(STATE_VERIFY_FAILED);
                status.setText("Verification Failed");
                Toast.makeText(getApplicationContext(), "May Phone Number Format is Wrong", Toast.LENGTH_LONG).show();
//                Toast.makeText(VerificationScreen.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
//                updateUI(STATE_CODE_SENT);
                status.setText("Verification Code Send");
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbacks]

        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;


        verifybutton = (Button) findViewById(R.id.verifybutton);

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate()) {
                    Toast.makeText(getBaseContext(), R.string.toastOtp,
                            Toast.LENGTH_LONG).show();
                } else {

                    otpnumber = otpno.getText().toString() + otpno1.getText().toString() + otpno2.getText().toString() + otpno3.getText().toString() + otpno4.getText().toString() + otpno5.getText().toString();

                    verifyPhoneNumberWithCode(mVerificationId, otpnumber);

                }


            }
        });


    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);

        // [START_EXCLUDE]
//        if (mVerificationInProgress && validatePhoneNumber()) {
//            startPhoneNumberVerification(number);
//        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }


    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            otpno.setText("8");
                            otpno1.setText("2");
                            otpno2.setText("4");
                            otpno3.setText("7");
                            otpno4.setText("5");
                            otpno5.setText("3");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
//                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            status.setText("Success");

                            Userdetails.userPhonenumber=number;

                            verified();
//
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                status.setText("Invalid Code");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
//                            updateUI(STATE_SIGNIN_FAILED);
                            status.setText("SignIn Failed");
                            // [END_EXCLUDE]
                        }
                    }
                });
    }



    @Override
    public void onBackPressed() {
        finish();

    }


    private boolean validate() {


        if (otpno.getText().toString().trim().equals(""))
            return false;
        else if (otpno1.getText().toString().trim().equals(""))
            return false;
        else if (otpno2.getText().toString().trim().equals(""))
            return false;
        else if (otpno3.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }


    public void verified(){

        //          Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Verify,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
//                        Toast.makeText(VerificationScreen.this, ServerResponse, Toast.LENGTH_LONG).show();
//
//                        Log.d("Response",ServerResponse);


                        try {
                            JSONObject jsondata = new JSONObject(ServerResponse);


                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);

                                String id = json.getString("user_id");
                                String verify = json.getString("verify");

                                Userdetails.userid=id;

                                if(verify.equals("yes")) {

                                    startActivity(new Intent(VerificationScreen.this, HomeScreen.class));
                                    finish();

                                }else {

                                    Toast.makeText(VerificationScreen.this, "Please Contact JERT ", Toast.LENGTH_LONG).show();

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

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                100,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        // Showing error message if something goes wrong.
                        Toast.makeText(VerificationScreen.this, "Please Check your Internet", Toast.LENGTH_LONG).show();

                        Log.d("error",volleyError.toString());
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
        RequestQueue requestQueue = Volley.newRequestQueue(VerificationScreen.this);

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }



}







