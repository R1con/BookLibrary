package com.example.booklibrary.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booklibrary.R;
import com.example.booklibrary.dao.BookDataBaseHelper;

public class AddBookActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText authorInput;
    private EditText pagesInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleInput = findViewById(R.id.title_input);
        authorInput = findViewById(R.id.author_input);
        pagesInput = findViewById(R.id.pages_input);
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
                        new AddBookTask().execute();
                }
        );
    }

    private class AddBookTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try (BookDataBaseHelper bookDataBaseHelper = new BookDataBaseHelper(AddBookActivity.this)) {
                bookDataBaseHelper.addBook(titleInput.getText().toString().trim(),
                        authorInput.getText().toString().trim(),
                        Integer.parseInt(pagesInput.getText().toString().trim()));
            } catch (Exception e) {
                Log.d("error on connection to database", "");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(AddBookActivity.this, "Книга успешно добавлена!", Toast.LENGTH_SHORT).show();
        }
    }
}