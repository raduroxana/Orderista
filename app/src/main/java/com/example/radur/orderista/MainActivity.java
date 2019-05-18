//package com.example.radur.orderista;
//
//import android.app.ProgressDialog;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.IOException;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    public static final String STORAGE_PATH_UPLOADS = "uploads/";
//    public static final String DATABASE_PATH_UPLOADS = "uploads/";
//
//    //constant to track image chooser intent
//    private static final int PICK_IMAGE_REQUEST = 234;
//
//    //view objects
//    private Button buttonChoose;
//    private Button buttonUpload;
//    private EditText editTextName;
//    private TextView textViewShow;
//    private ImageView imageView;
//
//    //uri to store file
//    private Uri filePath;
//
//    //firebase objects
//    private StorageReference storageReference;
//    private DatabaseReference mDatabase;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        buttonChoose = (Button) findViewById(R.id.buttonChoose);
//        buttonUpload = (Button) findViewById(R.id.buttonUpload);
//        imageView = (ImageView) findViewById(R.id.imageView);
//        editTextName = (EditText) findViewById(R.id.editText);
//        textViewShow = (TextView) findViewById(R.id.textViewShow);
//
//        storageReference = FirebaseStorage.getInstance().getReference();
//        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
//
//        buttonChoose.setOnClickListener(this);
//        buttonUpload.setOnClickListener(this);
//        textViewShow.setOnClickListener(this);
//    }
//
//    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            filePath = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (view == buttonChoose) {
//            showFileChooser();
//        } else if (view == buttonUpload) {
//            uploadFile();
//        } else if (view == textViewShow) {
//            Intent intent = new Intent(this,ShowImagesActivity.class);
//            startActivity(intent);
//        }
//    }
//
//    public String getFileExtension(Uri uri) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
//
//    private void uploadFile() {
//        if (filePath != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            StorageReference ref = storageReference.child(STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));
//            ref.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(MainActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
//                        }
//                    });
//        }
//    }
//}
