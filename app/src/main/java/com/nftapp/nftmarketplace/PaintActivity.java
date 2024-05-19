package com.nftapp.nftmarketplace;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.nftapp.nftmarketplace.model.Common;
import com.nftapp.nftmarketplace.model.PaintView;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PaintActivity extends AppCompatActivity implements SpectrumPalette.OnColorSelectedListener {

    private PaintView paintView;
    private ImageView close_button;
    private ImageView save_button;
    private ConstraintLayout save_dialog_layout;

    //request code for file related permission
    public static final int request_code = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        //fill algorithm i.e. spectrum
        SpectrumPalette spectrumPalette = findViewById(R.id.palette);
        spectrumPalette.setOnColorSelectedListener(this);
        // for paintview
        paintView =  findViewById(R.id.paintView);
        close_button = findViewById(R.id.close_button);
        save_button = findViewById(R.id.save_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(PaintActivity.this,R.raw.close_effect);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                onBackPressed();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(PaintActivity.this,R.raw.click_effect);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                showDialogForSave();
            }
        });


    }
    private void showDialogForSave() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=-PackageManager.PERMISSION_GRANTED){
            requestPermissions(new  String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, request_code);
        }else {
            View view = LayoutInflater.from(PaintActivity.this).inflate(R.layout.save_dialog, save_dialog_layout);
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PaintActivity.this);
            builder.setView(view);
            Button cancel_button = view.findViewById(R.id.cancel_button);
            Button accept = view.findViewById(R.id.accept_button);
            final android.app.AlertDialog alertDialog = builder.create();
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();

            cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MediaPlayer mediaPlayer = MediaPlayer.create(PaintActivity.this,R.raw.close_effect);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });
                    alertDialog.dismiss();
                }
            });
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MediaPlayer mediaPlayer = MediaPlayer.create(PaintActivity.this,R.raw.close_effect);
                    mediaPlayer.start(); mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });
                    try {
                        save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode  == request_code  && grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // to save file
    private void save() throws IOException {
        Bitmap bitmap = paintView.getBitmap();
        String file_name = UUID.randomUUID() + ".png";
        boolean saved;
        File folder;


        // for saving files in android q or 10
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)+
                    File.separator + "Let Me Craft !!");
        }else {
            folder = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES +
                    File.separator+getString(R.string.app_name));
        }
        if(!folder.exists()){
            folder.mkdirs();
        }

        File subFolder = new File(folder, Common.ITEM_SELECTED);

        if(!subFolder.exists())   // to make subfolder if not exist
            subFolder.mkdirs();

        File image = new File(subFolder + File.separator + file_name);
        Uri imageUri = Uri.fromFile(image);

        FileOutputStream outputStream = new FileOutputStream(image);
        saved = bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ContentResolver contentResolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,file_name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES +
                    File.separator + getString(R.string.app_name));

            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            outputStream = (FileOutputStream) contentResolver.openOutputStream(uri);
            saved = bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
        }else{
            sendPictureToGallery(imageUri);
        }

        if(saved)
            Toast.makeText(this, "Picture saved", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Picture Not saved", Toast.LENGTH_LONG).show();

        outputStream.flush();
        outputStream.close();
        finish();
    }

    private void sendPictureToGallery(Uri imageUri) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }

    @Override
    public void onColorSelected(int color) {
        Common.COLOR_SELECTED = color;
    }

    public void selectColor(View view) {

        ColorPickerDialogBuilder.with(this)
                .initialColor(Common.COLOR_SELECTED)
                .setTitle("Choose Color")
                .density(12)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)  // TYPE FOR COLOR PICKER
                .setPositiveButton("OK", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int lastSelectedColor, Integer[] allColors) {
                        Common.COLOR_SELECTED = lastSelectedColor;
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).build()
                .show();
    }

    public void undoLastAction(View view) {
        paintView.undoLastAction();
    }
}