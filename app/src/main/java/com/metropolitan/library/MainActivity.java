package com.metropolitan.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        Button btn1 = findViewById(R.id.allBooksBtn);
        Button addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean a = databaseHelper.insertBook("ime knjige", "autorcic", 484, "https://www.srbijadanas.com/sites/default/files/styles/full_article_image/public/a/t/2019/01/14/jaje.jpg","jaje", "jajejaje", "https://www.youtube.com/watch?v=KUq5wf3Mh0c&list=PLS1QulWo1RIaRdy16cOzBO5Jr6kEagA07&index=4");
                if(a){
                    Toast.makeText(MainActivity.this, "jeste", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "nije", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AllBooksActivity.class);
                startActivity(intent);
            }
        });

    }
}
