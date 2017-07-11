package com.example.sd.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sd.myapplication.Acitivity.Main2Activity;
import com.example.sd.myapplication.Acitivity.Picture_Capture;
import com.example.sd.myapplication.Module.Truong;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName() ;
    private DatabaseReference myRef;
    @BindView(R.id.txtData)
    TextView txtData;
    @BindView(R.id.listview)
    ListView lv;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myRef = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<String>();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
//        myRef.child("message").setValue("Hello, World111");
//        myRef.child("studentnoPush").setValue(new Person(1, "vinh", 23));
//        Map<String, Integer> myMap = new HashMap<String, Integer>();
//        myMap.put("vinh",121);
//        myRef.child("ten").setValue(myMap);
//        Student student = new Student("vinh","long an",23);
//        Student student1 = new Student("vinh1","long an1",24);
//        myRef.child("student").push().setValue(student);
//        myRef.child("student").push().setValue(student1);
//        myRef.child("testcompletion").setValue("complete", new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if(databaseError==null)
//                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//            }
//        });
//        myRef.child("a").push().setValue("aa");
//        myRef.child("a").push().setValue("bb");
//        myRef.child("a").push().setValue("cc");
//        Truong truong = new Truong("long hau", "LALT");
//        Truong truong1 = new Truong("phuoc ly", "LALT");
//        myRef.child("truong").push().setValue(truong);
//        myRef.child("truong").push().setValue(truong1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);
        myRef.child("a").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                txtData.append(dataSnapshot.getValue().toString()+"\n");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("truong").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Truong truong = dataSnapshot.getValue(Truong.class);
                arrayList.add(truong.getMa()+" "+truong.getTen());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }





    @OnClick(R.id.btnActivity2)
    public void btnActivity2(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btnGetPicture)
    public void btnGetPicture(){
        Intent intent = new Intent(MainActivity.this, Picture_Capture.class);
        startActivity(intent);
    }
}
