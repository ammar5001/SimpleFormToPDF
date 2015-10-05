package com.ammar.android.simpleformtopdf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveAsPDF(View view){
        if(isExternalStorageWritable()) {
            String filename = getFileName();
            File file = new File(getAlbumStorageDir("PDF"), filename);
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                createPDF(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

    private void createPDF(FileOutputStream outputStream) throws IOException {
        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(570,792,1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        // draw something on the page
        drawPage(page);

        // finish the page
        document.finishPage(page);
        //. . .
        // add more pages
        //. . .
        // write the document content
        document.writeTo(outputStream);

        // close the document
        document.close();

    }

    private void drawPage(PdfDocument.Page page) {
        Canvas canvas = page.getCanvas();

        // units are in points (1/72 of an inch)
        int titleBaseLine = 72;
        int leftMargin = 54;
        String address = "Ontario Environmental & Safety Network\n" +
                "184 Scott Street. Unit 8 & 9\n" +
                "St. Catharines, ON\n\n" +
                "L2N 1H1\n1-888-71-2111\nwww.oesn.net";

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(11);
        canvas.drawText(((EditText)findViewById(R.id.editText)).getText().toString(), leftMargin, titleBaseLine, paint);
        canvas.drawText(address, leftMargin + 300, titleBaseLine, paint);
        canvas.drawText(((EditText)findViewById(R.id.editText2)).getText().toString(), leftMargin, titleBaseLine + 25, paint);




    }


    private String getFileName() {
        return "Ammar" + ".pdf";
    }

    /* Checks if external storage is available for read and write */
    private boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public documents directory.

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), albumName );
        if (!file.mkdirs()) {
            Toast.makeText(this,"Directory not created", Toast.LENGTH_SHORT).show();
        }
        return file;
    }

    public void email(View view){

    }
}
