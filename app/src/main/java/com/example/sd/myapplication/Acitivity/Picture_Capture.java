package com.example.sd.myapplication.Acitivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sd.myapplication.Adapter.MyAdapter;
import com.example.sd.myapplication.Module.Picture;
import com.example.sd.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Picture_Capture extends AppCompatActivity {
    private static final String TAG = Picture_Capture.class.getSimpleName();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final int IMAGE_CAPTURE = 1;
    private DatabaseReference database;
    @BindView(R.id.etPicture)
    EditText etPicture;
    @BindView(R.id.img)
     ImageView img;
    private StorageReference mStorageRef;
    private StorageReference mountainsRef;
    private StorageReference mountainImagesRef;
    private StorageReference storageRef;
    private ArrayList<Picture> arrayList;
    @BindView(R.id.list1)
    ListView lv;
    private MyAdapter adapter;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture__capture);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance().getReference();
        storageRef = storage.getReference();

// Create a reference to "mountains.jpg"
        mountainsRef = storageRef.child("mountains.jpg");

// Create a reference to 'images/mountains.jpg'
        arrayList = new ArrayList<Picture>();
        adapter = new MyAdapter(Picture_Capture.this, R.layout.rowlist,arrayList);
        if(adapter ==null)
            Log.i(TAG, "onCreate: null adapter");else
            Log.i(TAG, "onCreate: non null adapter");
        lv.setAdapter(adapter);

// While the file names are the same, the references point to different files
//        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
//        mStorageRef = FirebaseStorage.getInstance().getReference();

        database.child("Picture").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Picture picture = dataSnapshot.getValue(Picture.class);
                Log.i(TAG, "onChildAdded: "+ picture.getPath());
                arrayList.add(new Picture(picture.getName(), picture.getPath()));
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
        Log.i(TAG, "onCreate: "+1212);
        if(arrayList.size()==0)
            Log.i(TAG, "onCreate: list rong");else
            Log.i(TAG, "onCreate: list full data");
        for(Picture p: arrayList){
            Log.i(TAG, "onCreate: 111111111111111111111111"+p.getPath());
        }

    }

    @OnClick(R.id.btnCapture)
    public void btnCapture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE);
    }
    @OnClick(R.id.btnSave)
    public void btnSave(){
       final  String name;
        if(etPicture.getText().toString().trim().length()==0)
            return;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String datestring = dateFormat.format(date);
         name = etPicture.getText().toString().trim();
        mountainImagesRef = storageRef.child("images/" +name+datestring+".jpg");

        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
//         bitmap = img.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(Picture_Capture.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(Picture_Capture.this, "success", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onSuccess: "+downloadUrl);
                Picture picture = new Picture(name, ""+downloadUrl);
                database.child("Picture").push().setValue(picture);
                etPicture.setText("");
                img.setImageResource(R.mipmap.aaa);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE_CAPTURE && resultCode==RESULT_OK && data!=null){
            bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
        }
    }


}
