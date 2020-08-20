package com.nextgenit.doctor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nextgenit.doctor.R;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import java.util.HashMap;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class VideoChatActivity extends AppCompatActivity implements  Session.SessionListener, PublisherKit.PublisherListener {
    public static final String API_KEY = "46887094";
    public static final String SESSION_ID = "1_MX40Njg4NzA5NH5-MTU5NzY1NDY0ODk2Nn5xWTBLdzB3M0xaVlJ1ZVAxcjZ3SjB4Y2R-fg";
    public static final String TOKEN = "T1==cGFydG5lcl9pZD00Njg4NzA5NCZzaWc9OTYzZDM3YmNiMmMzNDE0NDlmODFhZTc2YWExYTY5ZjM2YzBlNDIxNTpzZXNzaW9uX2lkPTFfTVg0ME5qZzROekE1Tkg1LU1UVTVOelkxTkRZME9EazJObjV4V1RCTGR6QjNNMHhhVmxKMVpWQXhjalozU2pCNFkyUi1mZyZjcmVhdGVfdGltZT0xNTk3NjU0NjY1Jm5vbmNlPTAuMjcyMTE4MDU5MTI5MDk1NyZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNjAwMjkzMzIzJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static final String TAG = VideoChatActivity.class.getSimpleName();
    private static final int RC_VIDEO_APP_PERM = 124;
    private ImageView closeVideoChatBtn;
    private DatabaseReference usersRef;
    private String userID = "";
    private FrameLayout mPublisherViewController;
    private FrameLayout mSubscribeViewController;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    String user="";
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        closeVideoChatBtn = findViewById(R.id.close_video_chat_btn);
        user = getIntent().getStringExtra("value");
        closeVideoChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (mPublisher!=null){
                    mPublisher.destroy();
                }
                if (mSubscriber!=null){
                    mSubscriber.destroy();
                }
                status(user);
            }

        });
        requestPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForRecevingCall();
    }

    private void checkForRecevingCall() {
        usersRef.child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("snapshot","value"+ snapshot.child("type").getValue().toString());

                String type=snapshot.child("type").getValue().toString();
                if (type.equals("End")){
                    if (mPublisher!=null){
                        mPublisher.destroy();
                    }
                    if (mSubscriber!=null){
                        mSubscriber.destroy();
                    }
                    Intent intent= new Intent(VideoChatActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(status);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", "End");

        reference.updateChildren(hashMap);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {

        String[] perms = {Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mPublisherViewController = findViewById(R.id.publisher_container);
            mSubscribeViewController = findViewById(R.id.subscriber_container);
            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(VideoChatActivity.this);
            mSession.connect(TOKEN);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_video_app), RC_VIDEO_APP_PERM, perms);

        }
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//
//        Log.d(LOG_TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//
//        Log.d(LOG_TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
//
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this)
//                    .setTitle(getString(R.string.title_settings_dialog))
//                    .setRationale(getString(R.string.rationale_ask_again))
//                    .setPositiveButton(getString(R.string.setting))
//                    .setNegativeButton(getString(R.string.cancel))
//                    .setRequestCode(RC_SETTINGS_SCREEN_PERM)
//                    .build()
//                    .show();
//        }
//    }
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }

    @Override
    public void onConnected(Session session) {

        Log.e("Session","Connected");
        mPublisher= new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(VideoChatActivity.this);
        mPublisherViewController.addView(mPublisher.getView());
        if (mPublisher.getView() instanceof GLSurfaceView) {
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.e("Stream","Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.e("Stream","Received");
        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscribeViewController.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.e("Stream","Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscribeViewController.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e("Stream","Error");
    }
}