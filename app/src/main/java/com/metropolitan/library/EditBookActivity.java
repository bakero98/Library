package com.metropolitan.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditBookActivity extends AppCompatActivity {

    private EditText bookName;
    private EditText author;
    private EditText imageUrl;
    private EditText link;
    private EditText shortDesc;
    private EditText pages;
    private EditText longDesc;

    private Button editBook;

    DatabaseHelper databaseHelper;

    private Book bookInIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        databaseHelper = new DatabaseHelper(this);

        bookName = findViewById(R.id.bookNameEdit);
        author = findViewById(R.id.authorEdit);
        imageUrl = findViewById(R.id.imageUrlEdit);
        link = findViewById(R.id.linkEdit);
        shortDesc = findViewById(R.id.shortDescEdit);
        pages = findViewById(R.id.pagesEdit);
        longDesc = findViewById(R.id.longDescEdit);

        editBook = findViewById(R.id.editBook);

        if(getIntent().getExtras() != null) {
            bookInIntent = (Book) getIntent().getSerializableExtra("bookInIntent");
        }

        bookName.setText(String.valueOf(bookInIntent.getName()));
        author.setText(String.valueOf(bookInIntent.getAuthor()));
        imageUrl.setText(String.valueOf(bookInIntent.getImageUrl()));
        link.setText(String.valueOf(bookInIntent.getLink()));
        shortDesc.setText(String.valueOf(bookInIntent.getShortDesc()));
        pages.setText(String.valueOf(bookInIntent.getPages()));
        longDesc.setText(String.valueOf(bookInIntent.getLongDesc()));

        editBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idForDb = Integer.toString(bookInIntent.getId());
                String nameForDb = bookName.getText().toString();
                String authorForDb = author.getText().toString();
                String pagesForDb = pages.getText().toString();
                String imageUrlForDb = imageUrl.getText().toString();
                String shortDescForDb = shortDesc.getText().toString();
                String longDescForDb = longDesc.getText().toString();
                String linkForDb = link.getText().toString();

                if(databaseHelper.updateBook(idForDb, nameForDb, authorForDb, pagesForDb, imageUrlForDb, shortDescForDb, longDescForDb, linkForDb)){
                    Toast.makeText(v.getContext(), "Book Edited", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(v.getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
