package com.ksn.amatorfutboltv.ui.prelogin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ksn.amatorfutboltv.R;
import com.ksn.amatorfutboltv.ui.BaseActivity;
import com.ksn.amatorfutboltv.ui.browsing.MainActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private EditText input_name;
    private EditText input_surname;
    private EditText input_birth_date;
    private FrameLayout input_birth_date_frame;
    private FrameLayout input_type_frame;
    private EditText input_mail;
    private EditText input_phone;
    private EditText input_pass1;
    private EditText input_pass2;
    private EditText input_type;
    private ImageButton image;
    private Button btnSignUp;
    private int userType = -1;
    private static final int SELECT_PICTURE = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private boolean valuesOk;
    private Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prelogin_activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        input_name = (EditText) findViewById(R.id.input_name);
        input_surname = (EditText) findViewById(R.id.input_surname);
        input_birth_date = (EditText) findViewById(R.id.input_birth_date);
        input_type = (EditText) findViewById(R.id.input_type);
        input_birth_date_frame = (FrameLayout) findViewById(R.id.input_birth_date_frame);
        input_type_frame = (FrameLayout) findViewById(R.id.input_type_frame);
        image = (ImageButton) findViewById(R.id.image);
        input_mail = (EditText) findViewById(R.id.input_mail);
        input_phone = (EditText) findViewById(R.id.input_phone);
        input_pass1 = (EditText) findViewById(R.id.input_pass1);
        input_pass2 = (EditText) findViewById(R.id.input_pass2);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        image.setOnClickListener(this);
        input_birth_date_frame.setOnClickListener(this);
        input_birth_date.setKeyListener(null);
        input_type_frame.setOnClickListener(this);
        input_type.setKeyListener(null);
        btnSignUp.setOnClickListener(this);
    }

    private void CheckValuesAndLogin() {
        valuesOk = isEditTextFilled(input_name);
        valuesOk = isEditTextFilled(input_surname);
        valuesOk = isEditTextFilled(input_birth_date);
        valuesOk = isEditTextFilled(input_phone);
        valuesOk = isEditTextFilled(input_mail);
        valuesOk = isEditTextFilled(input_pass1);

        if (!isEmailValid(input_mail.getText().toString())) {
            input_mail.setError("Hatalı mail adresi");
            valuesOk = false;
        }

        if (!isPhoneValid(input_phone.getText().toString())) {
            input_phone.setError("Telefon numarası 10 hane olmalı");
            valuesOk = false;
        }


        if (!input_pass1.getText().toString().equals(input_pass2.getText().toString())) {
            valuesOk = false;
            input_pass2.setError("Şifre aynı olmalı");
        }

        if (userType == -1) {
            valuesOk = false;
            input_type.setError("Üyelik tipi seçilmeli");
        }

        if (valuesOk) {
            final ParseUser user = new ParseUser();
            user.setUsername(input_mail.getText().toString());
            user.setPassword(input_pass1.getText().toString());
            user.setEmail(input_mail.getText().toString());
            user.put("PhoneNumber", input_phone.getText().toString());

            try {
                user.put("BirthDate", convertStringToDate(input_birth_date.getText().toString()));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            try {
                user.put("UserType", userType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Snackbar.make(getWindow().getDecorView(), e.toString(), Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    if (bm != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        String UsetPhoto = "user" + System.currentTimeMillis() + "image";
                        ParseFile file = new ParseFile(UsetPhoto, byteArray);
                        user.put("UserImage", file);
                    }
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }
            });

        }
    }

    private boolean isEditTextFilled(EditText text) {
        if (text.getText().toString().equals("")) {
            text.setError("Boş bırakılmaz!");
            return false;
        }

        return true;
    }

    private boolean isPhoneValid(String phone) {
        if (phone.length() == 10 || phone.length() == 11)
            return true;

        return false;
    }

    private boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public Date convertStringToDate(String string) throws java.text.ParseException {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(string);
        return date;
    }

    private void showTypeDialog() {
        new MaterialDialog.Builder(this)
                .title("Üyelik Tipi")
                .items(getApp().getModal().getUserTypeAsArray())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        input_type.setText(getApp().getModal().getUserTypes().get(which).getTypeName());
                        userType = (int) getApp().getModal().getUserTypes().get(which).getTypeId();
                    }
                })
                .show();
    }

    private void showDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SignUpActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] items = {"Kamera", "Galeri", "İptal"};
        builder.setTitle("Fotoğraf ekle");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Kamera")) {
                    openCameraActivity();
                } else if (items[item].equals("Galeri")) {
                    openGallery();
                } else if (items[item].equals("İptal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_PICTURE);
    }

    private void openCameraActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = {
                MediaStore.MediaColumns.DATA
        };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String sMonthOfYear;
        String sDayOfMonth;

        if (dayOfMonth < 10)
            sDayOfMonth = "0" + dayOfMonth;
        else
            sDayOfMonth = dayOfMonth + "";

        if (monthOfYear < 10)
            sMonthOfYear = "0" + monthOfYear;
        else
            sMonthOfYear = monthOfYear + "";

        input_birth_date.setText(sDayOfMonth + "-" + sMonthOfYear + "-" + String.valueOf(year));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                    } else {
                        selectImage();
                    }
                } else {
                    selectImage();
                }
                break;
            case R.id.input_birth_date_frame:
                showDateDialog();
                break;
            case R.id.input_type_frame:
                showTypeDialog();
                break;
            case R.id.btnSignUp:
                CheckValuesAndLogin();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        selectImage();
                        Log.d("Permissions", "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Log.d("Permissions", "Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                    btmapOptions.inSampleSize = 2;
                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(), btmapOptions);

                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
                    image.setImageBitmap(bm);

                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "test";
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    fOut = new FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, this);
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                btmapOptions.inSampleSize = 2;
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                image.setImageBitmap(bm);
            }
        }
    }
}
