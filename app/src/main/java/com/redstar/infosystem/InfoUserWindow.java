package com.redstar.infosystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InfoUserWindow extends AppCompatActivity {

    private User user;
    private int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user_window);
        Bundle extras = getIntent().getExtras();
        mode = extras.getInt("mode");
        if (mode == 1)
        {
            setTitle("Add User");
            Button button = (Button) findViewById(R.id.infouserwindow_button);
            button.setText("Add");
            Spinner spinner = (Spinner) findViewById(R.id.permissionSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new String[]{"User", "Employee", "Admin"});
            spinner.setAdapter(adapter);
        }
        else if (mode == 2)
        {
            setTitle("Find User");
            Button button = (Button) findViewById(R.id.infouserwindow_button);
            button.setText("Find");
            Spinner spinner = (Spinner) findViewById(R.id.permissionSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new String[]{"All", "User", "Employee", "Admin"});
            spinner.setAdapter(adapter);
        }
        else if (mode == 3)
        {
            setTitle("Edit User");
            Button button = (Button) findViewById(R.id.infouserwindow_button);
            button.setText("Edit");

            String login = extras.getString("login");
            String password = extras.getString("password");
            int permission = extras.getInt("permission");


            TextView textView = (TextView) findViewById(R.id.loginBox);
            textView.setText(login);
            textView = (TextView) findViewById(R.id.passwordBox);
            textView.setText(password);

            Spinner spinner = (Spinner) findViewById(R.id.permissionSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new String[]{"User", "Employee", "Admin"});
            spinner.setAdapter(adapter);
            spinner.setSelection(permission-1);
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

        textBox = (EditText) findViewById(R.id.loginBox);

        String login = textBox.getText().toString();

        if (login.equals("") && mode != 2)
        {
            Toast.makeText(this, "Wrong input! Fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        textBox = (EditText) findViewById(R.id.passwordBox);
        String password = textBox.getText().toString();
        if (password.equals("") && mode != 2)
        {
            Toast.makeText(this, "Wrong input! Fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        Spinner spinner = (Spinner) findViewById(R.id.permissionSpinner);
        String perm = spinner.getSelectedItem().toString();
        int permission = 0;

        if (perm.equals("User")) permission = 1;
        else if (perm.equals("Employee")) permission = 2;
        else if (perm.equals("Admin")) permission = 3;

        Intent data = new Intent();
        data.putExtra("mode", mode);
        data.putExtra("login", login);
        data.putExtra("password", password);
        data.putExtra("permission", permission);

        setResult(RESULT_OK, data);
        finish();
    }
}
