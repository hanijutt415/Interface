package com.example.aninterface;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private EditText studentName, fatherName, fatherCNIC, registrationNo;
    private ImageView profileImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        studentName = findViewById(R.id.studentName);
        fatherName = findViewById(R.id.fatherName);
        fatherCNIC = findViewById(R.id.fatherCNIC);
        profileImage = findViewById(R.id.profileimage);
        registrationNo = findViewById(R.id.registrationNo);
        registerButton = findViewById(R.id.registerBtn);  // Button initialized here

        // Profile Image Click Listener
        profileImage.setOnClickListener(v -> {
            openFileChooser();
        });

        // Register Button Click Listener
        registerButton.setOnClickListener(v -> {
            saveUserData();
            Intent intent = new Intent(MainActivity.this,AllStudents.class);
            startActivity(intent);
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void saveUserData() {
        if (imageUri != null) {
            // Create a unique filename for the image
            StorageReference fileReference = storageReference.child("students/" + System.currentTimeMillis() + ".jpg");

            // Upload the image
            UploadTask uploadTask = fileReference.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {

                String regNumber = registrationNo.getText().toString().trim();
                String userName = studentName.getText().toString().trim();
                String userFatherName = fatherName.getText().toString().trim();
                String userFatherCNIC = fatherCNIC.getText().toString().trim();
                String imageUriString = imageUri.toString();

                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {


                    // Create a Student object with image URI
                    ModelStudent student = new ModelStudent(regNumber, userName, userFatherName, userFatherCNIC, imageUriString);

                    // Push data to Firestore
                    db.collection("Students").add(student).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(MainActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(MainActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }
}
