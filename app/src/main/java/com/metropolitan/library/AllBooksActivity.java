package com.metropolitan.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView bookRecView;
    private BookRecViewAdapter adapter;
    private ArrayList<Book> books;
    DatabaseHelper databaseHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.all_books_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNewBook:
                Intent intent = new Intent(this, AddBookActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        bookRecView = findViewById(R.id.booksRecView);

        books = new ArrayList<>();

        adapter = new BookRecViewAdapter(this);
        bookRecView.setAdapter(adapter);
        bookRecView.setLayoutManager(new GridLayoutManager(this, 3));

        databaseHelper = new DatabaseHelper(this);

        loadBooks();
    }

    private void loadBooks(){
        Cursor cursor = databaseHelper.getAllBooks();
        books.clear();
        if(cursor.getCount() == 0 ){
            Toast.makeText(this, "No Books", Toast.LENGTH_LONG);
        }else{
            while(cursor.moveToNext()) {
                books.add(new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
            }
        }
        adapter.setBooks(books);
    }
}
