package com.example.developerguideapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import androidx.camera.lifecycle.ProcessCameraProvider;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.developerGuideApp.MESSAGE";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_2 = 2;
    Bitmap imageBitmap, imageBitmap2;
    String currentPhotoPath;
    //private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        /*Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

        Toast.makeText(getApplicationContext(), getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Uri uri = Uri.parse(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/");
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
        intent.setDataAndType(uri,"image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Didnot work out dude !!", Toast.LENGTH_LONG).show();
        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // CODE FOR WATCHING TWO IMAGES SIMULTANEOUSLY

        /*if (requestCode == REQUEST_IMAGE_CAPTURE_2 && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            Toast.makeText(getApplicationContext(), "Capture 2", Toast.LENGTH_LONG).show();

        }*/

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            imageBitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            imageView.setImageBitmap(imageBitmap);

            // CODE FOR IMAGE FOR VIEWING WITHOUT SAVING THE IMAGE INTO THE STORAGE

            /*Bundle extras = data.getExtras();
            imageBitmap2 = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap2);
            Toast.makeText(getApplicationContext(), "Capture 1", Toast.LENGTH_LONG).show();*/

        }
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // CODE FOR WATCHING TWO IMAGES SIMULTANEOUSLY

            /*Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent takePictureIntent_1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePictureIntent_1, REQUEST_IMAGE_CAPTURE_2);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });*/

            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            File photofile = null;
            try {
                photofile  = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "This is a toast", Toast.LENGTH_LONG).show();
            }
            if (photofile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.developerguideapp.fileprovider", photofile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            //onActivityResult(REQUEST_IMAGE_CAPTURE, RESULT_OK, takePictureIntent);
        }

    }

    public void imageLabellingOutput(View view) {
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);
        final ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
        final Intent intent = new Intent(this, DisplayMessageActivity.class);
        final ArrayList<String> imageText = new ArrayList<String>();
        labeler.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    
                    @Override
                    public void onSuccess(List<ImageLabel> imageLabels) {
                        for (ImageLabel label : imageLabels) {
                            StringBuilder text = new StringBuilder(label.getText());
                            text.append("\n");
                            float confidence = label.getConfidence();
                            text.append(confidence);
                            text.append("\n");
                            int index = label.getIndex();
                            text.append(index);
                            text.append("\n");
                            imageText.add(text.toString());
                        }
                        intent.putStringArrayListExtra(EXTRA_MESSAGE,imageText);
                        startActivity(intent);
                    }
                });

        // CODE FOR WATCHING TWO IMAGES SIMULTANEOUSLY

        //image = InputImage.fromBitmap(imageBitmap2, 0);

        /*labeler.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    StringBuilder text = new StringBuilder("Image2\n\n");

                    @Override
                    public void onSuccess(List<ImageLabel> imageLabels) {
                        imageText.add(text.toString());
                        for (ImageLabel label : imageLabels) {
                            StringBuilder text = new StringBuilder();
                            text.append(label.getText());
                            text.append("\n");
                            float confidence = label.getConfidence();
                            text.append(confidence);
                            text.append("\n");
                            int index = label.getIndex();
                            text.append(index);
                            text.append("\n");
                            imageText.add(text.toString());
                        }
                        intent.putStringArrayListExtra(EXTRA_MESSAGE,imageText);
                        startActivity(intent);
                    }
                });*/
    }

    //RANDOM TOAST CODE

    /*public void displayToastIntent(View view)
    {
        Toast.makeText(getApplicationContext(), "This is a toast", Toast.LENGTH_LONG).show();
    }*/
}