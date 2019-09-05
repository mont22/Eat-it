package com.example.montaser.eat_it;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.montaser.eat_it.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Signup extends AppCompatActivity {

    MaterialEditText edtphone,edtName,edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtPassword = (MaterialEditText)findViewById(R.id.edtpassword);
        edtphone = (MaterialEditText)findViewById(R.id.edtphone);
        edtName = (MaterialEditText)findViewById(R.id.edtName);

        btnSignUp = (Button)findViewById(R.id.btnSignIn);

        //initial firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final ProgressDialog mdialog = new ProgressDialog(Signup.this);
                mdialog.setMessage("Please wait...");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if already user exist
                        if(dataSnapshot.child(edtphone.getText().toString()).exists())
                        {
                            mdialog.dismiss();
                            Toast.makeText(Signup.this, "phone number already register", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mdialog.dismiss();
                            User user = new User(edtName.getText().toString(),edtPassword.getText().toString());
                            table_user.child(edtphone.getText().toString()).setValue(user);
                            Toast.makeText(Signup.this, "Sign Up Successfuly !", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
