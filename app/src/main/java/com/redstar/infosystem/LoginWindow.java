package com.redstar.infosystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.redstar.infosystem.data.InfoSystemDbHelper;

public class LoginWindow extends AppCompatActivity {

    private InfoSystemDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_window);

        db = new InfoSystemDbHelper(this);
    }

    public void onClick(View view) {

        EditText textBox = (EditText) findViewById(R.id.loginBox);
        String log = textBox.getText().toString();

        textBox = (EditText) findViewById(R.id.passwordBox);
        String pass = textBox.getText().toString();

        if (log.equals("root") && pass.equals("1234"))
        {
            Intent intent = new Intent(this, MainWindow.class);
            intent.putExtra("permission", 3);
            startActivity(intent);
        }
        else if (!log.isEmpty() && !pass.isEmpty())
        {
            int perm = db.checkUserInDatabase(log, pass);
            if (perm > 0)
            {
                Intent intent = new Intent(this, MainWindow.class);
                intent.putExtra("permission", perm);
                startActivity(intent);
            }
            else Toast.makeText(this, "Invalid login or password!", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this, "Incorrect input!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy()
    {
        db.closeDatabase();
        super.onDestroy();
    }
}
