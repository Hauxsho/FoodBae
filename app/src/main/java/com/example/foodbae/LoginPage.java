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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;

public class LoginPage extends AppCompatActivity {


    EditText edtphone , edtpass ;
    Button btnloginmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        edtpass=(EditText)findViewById(R.id.editlogpass);
        edtphone=(EditText)findViewById(R.id.editlogphone);
        btnloginmain=(Button)findViewById(R.id.btnloginmain);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnloginmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(LoginPage.this);
                mDialog.setMessage("Loading...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        //Check if user exists or not
                        if(dataSnapshot.child(edtphone.getText().toString()).exists())
                        {

                            //Get user info

                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtphone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtpass.getText().toString())) {
                                Toast.makeText(LoginPage.this, "Logged In", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginPage.this, "Afsos", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(LoginPage.this, "SignUp kar pehle -_- ", Toast.LENGTH_SHORT).show();
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
