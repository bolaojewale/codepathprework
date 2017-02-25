package com.example.bola.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //edit screen
        setContentView(R.layout.activity_edit_item);
        EditText save_lable = (EditText) findViewById(R.id.save_lable);
        //set edit labe
        save_lable.setText(getIntent().getStringExtra("item"));
        //move cursor to end of line
        save_lable.setSelection(save_lable.getText().length());
    }

    public void onSaveItem(View v) {
        // closes the activity and returns to first screen
        EditText save_lable = (EditText) findViewById(R.id.save_lable);
        String saveText = save_lable.getText().toString();
        int position = getIntent().getIntExtra("pos", 0);
        //Testing
            //Log.d("MainActivity","Heres Johnny: " + position);
            //String testText = getIntent().getStringExtra("pos");
            //Toast toast = Toast.makeText(getApplicationContext(), testText, Toast.LENGTH_SHORT);
            //toast.show();
        Intent data = new Intent();
        data.putExtra("updatedItem", saveText);
        data.putExtra("pos", position);
        setResult(0, data);
        this.finish();
    }
}
