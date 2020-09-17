package com.metropolitan.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class SingleBookView extends AppCompatActivity {

    private ImageView imageView;
    private TextView author;
    private TextView naslov;
    private TextView description;
    private TextView numberOfPages;
    private Button orderButton;

    private Book book;
    private Book bookInIntent;
    private int bookInIntentId;

    DatabaseHelper databaseHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.single_book_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteBook:
                if(databaseHelper.deleteBook(Integer.toString(bookInIntent.getId()))){
                    Toast.makeText(this, "Book Deleted", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.editBookIntent:
                Intent intent = new Intent(this, EditBookActivity.class);
                intent.putExtra("bookInIntent", bookInIntent);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBookInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book_view);

        databaseHelper = new DatabaseHelper(this);

        imageView = findViewById(R.id.image);
        author = findViewById(R.id.author);
        naslov = findViewById(R.id.naslov);
        description = findViewById(R.id.longDescription);
        numberOfPages = findViewById(R.id.numberOfPages);
        orderButton = findViewById(R.id.orderButton);


        if(getIntent().getExtras() != null) {
            bookInIntent = (Book) getIntent().getSerializableExtra("bookInIntent");
            bookInIntentId = bookInIntent.getId();
        }


        loadBookInfo();


        // MENU


    }

    private void loadBookInfo(){
        Cursor cursor = databaseHelper.getBookById(bookInIntentId);
        if(cursor.getCount() == 0 ){
            Toast.makeText(this, "No Book Info", Toast.LENGTH_LONG);
            finish();
        }else{
            cursor.moveToFirst();
            book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        }

        new DownloadImageTask(imageView).execute(book.getImageUrl());
        author.setText("Author : " + book.getAuthor());
        naslov.setText(book.getName());
        description.setText(book.getLongDesc());
        numberOfPages.setText("Pages : " + book.getPages());

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getLink()));
                startActivity(intent);
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
