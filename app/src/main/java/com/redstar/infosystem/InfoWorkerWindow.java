package com.redstar.infosystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Modes:
//1) Add worker/user
//2) Find worker/user
//3) Edit worker/user

public class InfoWorkerWindow extends AppCompatActivity {
    private Worker worker;
    private int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_worker_window);
        Bundle extras = getIntent().getExtras();
        mode = extras.getInt("mode");
        if (mode == 1)
        {
            Button button = (Button) findViewById(R.id.infoworkerwindow_button);
            button.setText("Add");
        }
        else if (mode == 2)
        {
            setTitle("Find Worker");
            Button button = (Button) findViewById(R.id.infoworkerwindow_button);
            button.setText("Find");
        }
        else if (mode == 3)
        {
            setTitle("Edit Worker");
            Button button = (Button) findViewById(R.id.infoworkerwindow_button);
            button.setText("Edit");

            String name = extras.getString("name");
            String surname = extras.getString("surname");
            String patronymic = extras.getString("patronymic");
            int age = extras.getInt("age");
            String post = extras.getString("post");
            String group = extras.getString("group");
            String company = extras.getString("company");

            TextView textView = (TextView) findViewById(R.id.nameBox);
            textView.setText(name);
            textView = (TextView) findViewById(R.id.surnameBox);
            textView.setText(surname);
            textView = (TextView) findViewById(R.id.patronymicBox);
            textView.setText(patronymic);
            textView = (TextView) findViewById(R.id.ageBox);
            textView.setText(String.valueOf(age));
            textView = (TextView) findViewById(R.id.postBox);
            textView.setText(post);
            textView = (TextView) findViewById(R.id.groupBox);
            textView.setText(group);
            textView = (TextView) findViewById(R.id.companyBox);
            textView.setText(company);
        }
        else
        {
            Toast.makeText(this, "Ooops! Error!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    public void onClick(View view) {
        EditText textBox;

        textBox = (EditText) findViewById(R.id.nameBox);

        String name = textBox.getText().toString();

        if (name.equals("") && mode != 2)
        {
            Toast.makeText(this, "Wrong input! Fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        textBox = (EditText) findViewById(R.id.surnameBox);
        String surname = textBox.getText().toString();
        if (surname.equals("") && mode != 2)
        {
            Toast.makeText(this, "Wrong input! Fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        textBox = (EditText) findViewById(R.id.patronymicBox);
        String patronymic = textBox.getText().toString();
        if (patronymic.equals("") && mode != 2)
        {
            Toast.makeText(this, "Wrong input! Fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        textBox = (EditText) findViewById(R.id.ageBox);

        int age;

        try{age = Integer.valueOf(textBox.getText().toString());}
        catch (NumberFormatException e){age = 0;}

        if (age < 1 && mode != 2)
        {
            Toast.makeText(this, "Wrong input!", Toast.LENGTH_SHORT).show();
            return;
        }

        textBox = (EditText) findViewById(R.id.postBox);
        String post = textBox.getText().toString();
        if (post.equals("") && mode != 2)
        {
            Toast.makeText(this, "Wrong input! Fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        textBox = (EditText) findViewById(R.id.groupBox);
        String group = textBox.getText().toString();
        if (group.equals("") && mode != 2)
        {
            Toast.makeText(this, "Wrong input! Fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        textBox = (EditText) findViewById(R.id.companyBox);
        String company = textBox.getText().toString();
        if (company.equals("") && mode != 2)
        {
            Toast.makeText(this, "Wrong input! Fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("mode", mode);
        data.putExtra("name", name);
        data.putExtra("surname", surname);
        data.putExtra("patronymic", patronymic);
        data.putExtra("age", age);
        data.putExtra("post", post);
        data.putExtra("group", group);
        data.putExtra("company", company);

        setResult(RESULT_OK, data);
        finish();
    }

}
