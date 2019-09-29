package com.example.foodbae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodbae.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupPage extends AppCompatActivity {

    Button btnsupmain;
    EditText edtphonesup,edtpasssup,edtnamesup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        edtnamesup=(EditText)findViewById(R.id.editsupusername);
        edtpasssup=(EditText)findViewById(R.id.editsuppass);
        edtphonesup=(EditText)findViewById(R.id.editsupphone);
        btnsupmain=(Button)findViewById(R.id.btnsupmain);


        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnsupmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignupPage.this);
                mDialog.setMessage("Loading...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        //Check if phone no. registered already or not

                        if(dataSnapshot.child(edtphonesup.toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignupPage.this, "Already exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user = new User (edtnamesup.getText().toString(),edtpasssup.getText().toString());
                            table_user.child(edtphonesup.getText().toString()).setValue(user);
                            Toast.makeText(SignupPage.this, "Bakchodi me apka swagat hai", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
