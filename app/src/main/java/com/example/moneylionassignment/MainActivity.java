package com.example.moneylionassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> arr_name_and_type;

    private Spinner spinner_type;
    private Button btn_add_entry;
    private EditText ed_field_name;
    private FloatingActionButton fab_generate, fab_add_entry;

    private ListView lv_entries;

    private View dialogView;
    private BottomSheetDialog bottom_sheet;

    private ArrayAdapter<CharSequence> arr_adapter_spinner;
    private ArrayAdapter<String> arr_adapter_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arr_name_and_type = new ArrayList<String>();

        // set up list view
        lv_entries = findViewById(R.id.lv_entries);
        arr_name_and_type = new ArrayList<String>();
        arr_adapter_list_view = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arr_name_and_type);
        lv_entries.setAdapter(arr_adapter_list_view);

        // set up bottom sheet dialog and views
        dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottom_sheet = new BottomSheetDialog(this);
        bottom_sheet.setContentView(dialogView);
        btn_add_entry = dialogView.findViewById(R.id.btn_add_entry);
        ed_field_name = dialogView.findViewById(R.id.ed_field_name);

        // initialize widget
        fab_generate = findViewById(R.id.fab_generate);
        fab_add_entry = findViewById(R.id.fab_add_entry);

        // set up spinner
        spinner_type = dialogView.findViewById(R.id.spinner_type);

        arr_adapter_spinner = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);

        arr_adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(arr_adapter_spinner);

        btn_add_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_field_name.getText().toString().trim().length() > 0) {
                    arr_name_and_type.add(ed_field_name.getText().toString() + " - " + spinner_type.getSelectedItem().toString());
                    arr_adapter_list_view.notifyDataSetChanged();
                    bottom_sheet.dismiss();
                }
                else
                    // check if the field name is empty
                    Toast.makeText(MainActivity.this, "The field name must not be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        fab_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arr_name_and_type.size() > 0) {
                    Intent i = new Intent(MainActivity.this, FormActivity.class);
                    i.putExtra("string_array", arr_name_and_type);
                    startActivity(i);
                }
                else
                    // check if the list is empty
                    Toast.makeText(MainActivity.this, "The list is empty!", Toast.LENGTH_SHORT).show();
            }
        });

        fab_add_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom_sheet.show();
            }
        });
        
        lv_entries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteItem(position);
            }
        });

    }

    // create a dialog box to confirm of deletion
    private void deleteItem(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Click yes to delete this entry");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arr_name_and_type.remove(position);
                arr_adapter_list_view.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // create a dialog box to confirm of cleaning the list
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_clear_list){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Click yes to clear the list");

            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    arr_name_and_type.clear();
                    arr_adapter_list_view.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}