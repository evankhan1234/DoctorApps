package com.nextgenit.doctor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nextgenit.doctor.R;

import java.util.HashMap;

public class CallingActivity extends AppCompatActivity {
    private ImageView cancelCallBtn;
    String user="";
    String session="";
    String token="";
    DatabaseReference reference;
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        cancelCallBtn=findViewById(R.id.cancel_call);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        user = getIntent().getStringExtra("value");
        session = getIntent().getStringExtra("session");
        token = getIntent().getStringExtra("token");
        cancelCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status(user);
                Intent intent= new Intent(CallingActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForRecevingCall();
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(status);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", "End");

        reference.updateChildren(hashMap);
    }

    private void checkForRecevingCall() {
        usersRef.child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("snapshot","value"+ snapshot.child("type").getValue().toString());

                String type=snapshot.child("type").getValue().toString();
                if (type.equals("Accept")){

                    Intent intent= new Intent(CallingActivity.this, VideoChatActivity.class);
                    intent.putExtra("value",user);
                    intent.putExtra("session",session);
                    intent.putExtra("token",token);
                    startActivity(intent);
                    finish();
                }
                else if (type.equals("End")){
                    Intent intent= new Intent(CallingActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}