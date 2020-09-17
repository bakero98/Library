package com.metropolitan.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookActivity extends AppCompatActivity {

    private EditText bookName;
    private EditText author;
    private EditText imageUrl;
    private EditText link;
    private EditText shortDesc;
    private EditText pages;
    private EditText longDesc;
    private Button addBook;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        databaseHelper = new DatabaseHelper(this);

        bookName = findViewById(R.id.bookName);
        author = findViewById(R.id.author);
        imageUrl = findViewById(R.id.imageUrl);
        link = findViewById(R.id.link);
        shortDesc = findViewById(R.id.shortDesc);
        pages = findViewById(R.id.pages);
        longDesc = findViewById(R.id.longDesc);
        addBook = findViewById(R.id.addBook);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookNameStr = bookName.getText().toString();
                String authorStr = author.getText().toString();
                int pagesInt = Integer.parseInt(pages.getText().toString());
                String imageUrlStr = imageUrl.getText().toString();
                String shortDescStr = shortDesc.getText().toString();
                String longDescSte = longDesc.getText().toString();
                String linkStr = link.getText().toString();

                if(databaseHelper.insertBook(bookNameStr, authorStr, pagesInt, imageUrlStr, shortDescStr, longDescSte, linkStr)){
                    Toast.makeText(v.getContext(), "Book Added", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
