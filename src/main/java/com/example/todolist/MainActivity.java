package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ListView listViewData;
    ArrayAdapter<String> adapter;
    //String[] array = {"Task1", "Task2", "Task3", "Task4", "Task5"};
    ArrayList<String> items = new ArrayList<>();
    Map<Integer, String> selectedItems = new HashMap<>();

    Button addButton;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewData = (ListView) findViewById(R.id.listView_data);
        listViewData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items);
        listViewData.setAdapter(adapter);

        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray checkedItems = listViewData.getCheckedItemPositions();
                String itemName = (String) parent.getItemAtPosition(position);
                if(checkedItems.get(position)) {
                    selectedItems.put(position, itemName);
                }else{
                    selectedItems.remove(position);
                }
            }
        });

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null);
                TextInputEditText inputText = dialogView.findViewById(R.id.editText);
                AlertDialog alertDialog = new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Add New Task")
                        .setView(dialogView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!String.valueOf(inputText.getText()).strip().isEmpty()){
                                    items.add(String.valueOf(Objects.requireNonNull(inputText.getText())));
                                    adapter.notifyDataSetChanged();
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Map.Entry<Integer, String> item : selectedItems.entrySet()) {
                    items.remove(item.getValue());
                    listViewData.setItemChecked(item.getKey(), false);
                }
                selectedItems = new HashMap<>();
                adapter.notifyDataSetChanged();
            }
        });
    }

}