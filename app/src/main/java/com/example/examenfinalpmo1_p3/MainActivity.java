package com.example.examenfinalpmo1_p3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.examenfinalpmo1_p3.Configuracion.SQLiteConexion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.examenfinalpmo1_p3.Configuracion.Transacciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView imagen;
    EditText descripcion,fecha;

    Button btnGuard,btnList;
    FloatingActionButton grabarVideo;
    static final int REQUESTCODECAMARA = 100;
    static final int REQUESTTAKEPHOTO = 101;
    Bitmap bmImagen;
    String path;
    SQLiteConexion conexion;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fecha= (EditText) findViewById(R.id.TxtFecha) ;
        imagen=(ImageView) findViewById(R.id.uploadImg);
        descripcion=(EditText) findViewById(R.id.txtDescripcion);
        btnGuard=(Button) findViewById(R.id.btnGuardar);
        grabarVideo=(FloatingActionButton) findViewById(R.id.grabarVideo);


    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "com.example.examenfinalpmo1_p3.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUESTTAKEPHOTO);
                }
            } catch (IOException ex) {
                message(ex.getMessage());
            }catch (Exception e){
                message(e.getMessage());
            }
        }
    }



    public void onClickGuardar(View view) {

        if(emptyFields(descripcion))
        {
            if (emptyFields(fecha))
            {
                if(bmImagen.getByteCount() > 0)
                {guardar();
                }else message("DEBE TOMAR LA FOTO");
            } else message("DEBE INGRESAR LA FECHA");
        } else message("DEBE INGRESAR LA DESCRIPCION");


    }

    private void guardar() {
        try{
        conexion = new SQLiteConexion(getApplicationContext(), Transacciones.NameDatabase, null, 2);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Transacciones.descripcion, descripcion.getText().toString());
        values.put(Transacciones.fecha, fecha.getText().toString());
        values.put(Transacciones.pathImage, path);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(10480);
        bmImagen.compress(Bitmap.CompressFormat.JPEG, 0 , baos);
        byte[] blob = baos.toByteArray();
        values.put(Transacciones.image, blob);

        Long result = db.insert(Transacciones.tablaDatos, Transacciones.id, values);
        db.close();
        message("DATOS SE GUARDARON CORRECTAMENTE");
        cleanFields();
    }catch (
    SQLiteException ex){
        message(ex.getMessage());
    }
    }



    private void onClickTakePhoto(View view) {
        assignPermissions();
    }

    private void assignPermissions() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA }, REQUESTCODECAMARA);
        }else takePhoto();
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpeg", storageDir);

        path = image.getAbsolutePath();
        return image;
    }


    public void cleanFields(){
        descripcion.setText("");
        fecha.setText("");
        imagen.setImageBitmap(null);
        bmImagen = null;
    }

    public boolean emptyFields(EditText field){
        return field.getText().toString().length() > 1 ? true : false;
    }

    public void message(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUESTCODECAMARA) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) takePhoto();
            else message("PERMISO DENEGADO");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTTAKEPHOTO && resultCode == RESULT_OK) {
            Bitmap image = BitmapFactory.decodeFile(path);
            bmImagen = image;
            imagen.setImageBitmap(image);
            message("IMAGEN SE GUARDARON EXITOSAMENTE ");
        }
    }


    public void ActivityVideo(View view)
    {
        startActivity(new Intent(MainActivity.this,ActivityGrabar.class));
        finish();
    }
}