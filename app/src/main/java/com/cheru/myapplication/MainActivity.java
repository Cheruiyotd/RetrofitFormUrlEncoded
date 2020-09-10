package com.cheru.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cheru.myapplication.RetrofitUtyls.Project;
import com.cheru.myapplication.RetrofitUtyls.RetroApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText edtName, edtAddress, edtLink, edtMail;
    //RequestQueue queue;
    Button submitBtn;
    Project signUpResponsesData;
    EditText emailId, password, name;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        edtName = findViewById(R.id.name);
        edtMail = findViewById(R.id.editTextTextEmailAddress);
        edtLink = findViewById(R.id.link);
        edtAddress = findViewById(R.id.address);
        submitBtn = findViewById(R.id.submit_form);

        edtName.setText("myname");
        edtMail.setText("dummy@mail.mail");
        edtAddress.setText("567 home");
        edtLink.setText("https://docs.google.com");

        submitBtn.setText("Submit");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate the fields and call sign method to implement the api
                if (validate(edtAddress) && validateEmail() && validate(edtLink) && validate(edtName)) {
                    submitProject();
                }
            }
        });
    }

    private boolean validateEmail() {
        String email = edtMail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            emailId.setError("Email is not valid.");
            emailId.requestFocus();
            return false;
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validate(EditText editText) {
        // check the lenght of the enter data in EditText and give error if its empty
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returns true if field is not empty
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void submitProject() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog


        (RetroApi.getClient().projectEntry(edtName.getText().toString().trim(),
                edtMail.getText().toString().trim(),
                edtAddress.getText().toString().trim(),
                edtLink.getText().toString().trim())).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                signUpResponsesData = response.body();
                Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                Log.d("response", t.getStackTrace().toString());
                progressDialog.dismiss();

            }
        });
    }
}

