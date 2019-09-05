package com.example.montaser.eat_it;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.montaser.eat_it.Common.Common;
import com.example.montaser.eat_it.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtphone,edtpassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtpassword = (MaterialEditText)findViewById(R.id.edtpassword);
        edtphone = (MaterialEditText)findViewById(R.id.edtphone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //initial firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final ProgressDialog mdialog = new ProgressDialog(SignIn.this);
                mdialog.setMessage("Please wait...");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if user exist in database
                        if (dataSnapshot.child(edtphone.getText().toString()).exists()){
                            //Get user information
                            mdialog.dismiss();
                            User user = dataSnapshot.child(edtphone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtpassword.getText().toString()))
                            {
                                Intent home = new Intent(SignIn.this,Home.class);
                                Common.currentUser = user;
                                startActivity(home);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(SignIn.this, "Sign in failed !", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            mdialog.dismiss();
                            Toast.makeText(SignIn.this, "User not exist", Toast.LENGTH_SHORT).show();
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
