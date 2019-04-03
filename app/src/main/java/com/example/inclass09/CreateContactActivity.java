package com.example.inclass09;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.regex.Pattern;

public class CreateContactActivity extends AppCompatActivity {

    EditText fullName, email, phone;
    Button submit;
    ImageView userImage;
    public static final int REQUEST_CAMERA = 2;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mContactRef;
    Contact contact;
    static String CONTACT_KEY = "CONTACT";
    Uri imageURI;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    String url;
    boolean noImageUploaded;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        fullName = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        phone = findViewById(R.id.phoneEditText);
        userImage = findViewById(R.id.userImage);
        submit = findViewById(R.id.submitbtn);
        progressBar = findViewById(R.id.progressBar);
        noImageUploaded = true;
        mContactRef = mRootRef.child("contact").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Camera
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullNameString = fullName.getText().toString();
                String emailString = email.getText().toString();
                String phoneString = phone.getText().toString();
                if (fullNameString.equals("")) {
                    fullName.setError("Please enter a name");
                    return;
                }

                if (emailString.equals("") || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                    email.setError("Please enter a valid email");
                    return;
                }

                if (!isValidMobile(phoneString)) {
                    phone.setError("Please enter a valid phone number");
                    return;
                }


               // boolean imageLoaded = hasImage(userImage);

                if (noImageUploaded) {
                    userImage.setImageDrawable(getResources().getDrawable(R.drawable.noimage, null));
                    url = Uri.parse("android.resource://com.example.inclass09/" + R.drawable.noimage).toString();
                    Log.d("url666 ", "" + url);
                    //url = getResources().getDrawable(R.drawable.ic_launcher_background, null).toString();
                }
                String key = mContactRef.push().getKey();
                Contact contact = new Contact(fullNameString, emailString, phoneString, url);

                contact.setKey(key);
                mContactRef.child(key).setValue(contact);


                //Intent intent = new Intent(CreateContactActivity.this, ContactList.class);
                finish();

               /* Intent intent = new Intent();
                intent.putExtra(CONTACT_KEY, contact);
                setResult(RESULT_OK, intent);
                finish();*/
            }
        });

    }

/*    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }

        return hasImage;
    }*/

    private void uploadImage() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == REQUEST_CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageURI = data.getData();
            submit.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            uploadFile(thumbnail);
        }
    }

    private void uploadFile(final Bitmap bitmap) {
        userImage.setDrawingCacheEnabled(true);
        userImage.buildDrawingCache();
        // Bitmap bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataByte = baos.toByteArray();

        String path = "fireimage/" + UUID.randomUUID() + ".jpeg";
        StorageReference fireimageRef = firebaseStorage.getReference(path);
        UploadTask uploadTask = fireimageRef.putBytes(dataByte);
        uploadTask.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        url = task.getResult().toString();
                        noImageUploaded = false;
                        userImage.setImageBitmap(bitmap);
                        submit.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(CreateContactActivity.this, "Image Loaded!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateContactActivity.this, "Can not upload", Toast.LENGTH_SHORT);
                    }
                });
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 14 || phone.length() > 15) {
                // if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }
}
