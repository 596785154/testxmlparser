package com.zcn.testxmlparser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "XML";

    private BookParser parser;
    private List<Book> books;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button readBtn = (Button) findViewById(R.id.readBtn);
        Button writeBtn = (Button) findViewById(R.id.writeBtn);

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream is = getAssets().open("books.xml");
                    //parser = new SaxBookParser();  //创建SaxBookParser实例
                    //parser = new DomBookParser();
                    parser = new PullBookParser();
                    books = parser.parse(is);  //解析输入流
                    for (Book book : books) {
                        Log.i(TAG, book.toString());
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String xml = parser.serialize(books);  //序列化
                    FileOutputStream fos = openFileOutput("books.xml", Context.MODE_PRIVATE);
                    fos.write(xml.getBytes("UTF-8"));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }
}
