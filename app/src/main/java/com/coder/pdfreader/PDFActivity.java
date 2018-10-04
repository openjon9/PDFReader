package com.coder.pdfreader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PDFActivity extends AppCompatActivity {

    private PDFView pdfView;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);


        findview();

        uri = Uri.parse(getIntent().getStringExtra("uri"));

        setPDF();

    }

    private void setPDF() {

        pdfView.fromUri(uri)
                .swipeHorizontal(true)
                .enableSwipe(true)
                .defaultPage(0)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                })
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {

                    }
                })
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {

                    }
                })
                .enableAnnotationRendering(true) //呈現註釋（例如註釋，顏色或表單）
                .enableAntialiasing(true)//在低分辨率屏幕上改進渲染
                .scrollHandle(new DefaultScrollHandle(this))
                .load();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    private void findview() {
        pdfView = (PDFView) findViewById(R.id.pdfView);
    }

}
