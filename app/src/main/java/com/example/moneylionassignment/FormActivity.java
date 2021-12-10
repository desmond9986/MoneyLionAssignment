package com.example.moneylionassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormActivity extends AppCompatActivity {
    private LinearLayout linear_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        linear_layout = findViewById(R.id.form_layout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<String> name_and_type = extras.getStringArrayList("string_array");
            for(int i = 0; i < name_and_type.size(); i++)
            {
                // use regex to get the type
                Pattern pattern = Pattern.compile("^(.*) - (Text|Number|Boolean|Encrypted Text|Checkbox)$");
                Matcher matcher = pattern.matcher(name_and_type.get(i));
                if(matcher.find()) {
                    createTextField(matcher.group(1));
                    switch (matcher.group(2)) {
                        case "Text":
                            createEditText(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                            break;
                        case "Number":
                            createEditText(InputType.TYPE_CLASS_NUMBER);
                            break;
                        case "Boolean":
                            createToggle();
                            break;
                        case "Encrypted Text":
                            createEditText(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            break;
                        default:
                            createCheckbox();
                    }
                }
            }
        }
    }

    private void createTextField(String field_name){
        TextView textView = new TextView(this);
        textView.setText(field_name);
        textView.setTextSize(15.0f);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        linear_layout.addView(textView);
    }

    private void createEditText(int input_type){
        EditText editText = new EditText(this);
        editText.setTextSize(20.0f);
        editText.setInputType(input_type);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(0, 5, 0, 10);
        editText.setLayoutParams(params1);
        linear_layout.addView(editText);
    }

    private void createToggle(){
        Switch switch_widget = new Switch(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER_HORIZONTAL;
        params1.setMargins(0, 5, 0, 10);
        switch_widget.setLayoutParams(params1);
        linear_layout.addView(switch_widget);
    }

    private void createCheckbox(){
        CheckBox checkbox = new CheckBox(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER_HORIZONTAL;
        params1.setMargins(0, 5, 0, 10);
        checkbox.setLayoutParams(params1);
        linear_layout.addView(checkbox);
    }
}