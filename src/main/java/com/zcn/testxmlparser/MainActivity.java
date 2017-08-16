package com.zcn.testxmlparser;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Chunna==XML";

    private BookParser parser;
    private List<Book> books;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parser = new PullBookParser();

        Button readBtn = (Button) findViewById(R.id.readBtn);
        Button writeBtn = (Button) findViewById(R.id.writeBtn);

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   //InputStream is = getAssets().open("books.xml");
                    InputStream is = openFileInput("books.xml");
                    //parser = new SaxBookParser();  //创建SaxBookParser实例
                    //parser = new DomBookParser();
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
                    File file = getFileStreamPath("points.xml");
                    Log.d(TAG, "File path :" + file.getParentFile());
                    if (!file.exists()) {
                        Log.d(TAG, "文件不存在");
                        file.createNewFile();
                    }
                    Log.d(TAG, "文件创建完成，开始写入数据");
                    //String xml = parser.serialize(books);  //序列化
                    String xml = parser.serialize(getBookList());
                    FileOutputStream fos = openFileOutput("books.xml", Context.MODE_PRIVATE);
                    fos.write(xml.getBytes("UTF-8"));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public List<Book> getBookList(){
       List<Book> list = new ArrayList<Book>();
        for(int i = 0;i<5;i++){
            Book book = new Book();
            book.setId(i);
            book.setName("张三"+i);
            book.setPrice((float) 10*i);
            list.add(book);
        }
        list.size();
        return list;
    }
}
