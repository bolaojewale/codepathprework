package com.example.bola.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        //items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        //items.add("First Item");
        //items.add("Second Item");
        setupListViewListener();
    }

    public void setupListViewListener() {
        //delete item
        lvItems.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        //support edit action
        lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            int newItem = 0;
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id){
                //intent goes here
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                //i.putExtra("position", pos);
                //startActivityForResult(i, REQUEST_CODE);
                //items.get(pos);
                i.putExtra("item", items.get(pos)); //item text
                //System.out.print("Heres Johnny: " + pos);
                //Log.d("MainActivity","Heres Johnny: " + pos);
                i.putExtra("pos", pos); //item position
                //startActivity(i);
                //go to edit screen
                startActivityForResult(i, newItem);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Testing
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            //Toast toast = Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT);
            //toast.show();

        String name = data.getExtras().getString("updatedItem");
        int position = data.getIntExtra("pos", 0);
        items.remove(position);
        items.add(position, name);
        itemsAdapter.notifyDataSetChanged();
        writeItems();

    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
