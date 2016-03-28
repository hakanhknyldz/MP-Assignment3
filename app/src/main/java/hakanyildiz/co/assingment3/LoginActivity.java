package hakanyildiz.co.assingment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hakanyildiz.co.assingment3.MyClasses.DatabaseHelper;
import hakanyildiz.co.assingment3.MyClasses.User;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnRegisterOrLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegisterOrLogin = (Button) findViewById(R.id.btnRegisterOrLogin);

        btnRegisterOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    if (!databaseHelper.authentication(etEmail.getText().toString(), etPassword.getText().toString())) //authentication true ise user kayıtlıdır database'de,
                    //değilse account oluşturulur!
                    {
                        User user = new User();
                        user.setEmail(etEmail.getText().toString());
                        user.setPassword(etPassword.getText().toString());
                        databaseHelper.insertUser(user);
                        Toast.makeText(getApplicationContext(), "This e-mail and pass combination is new! You have been registered. Enjoy!", Toast.LENGTH_LONG).show();
                        goMain();
                    } else {
                        Toast.makeText(getApplicationContext(), "You have already registered! LOGIN works on! Enjoy :)", Toast.LENGTH_LONG).show();
                        goMain();

                    }
                }


            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("This email address is invalid");
            valid = false;
        }
        else {
            etEmail.setError(null);
        }

        if(password.isEmpty() || password.length() <5)
        {
            etPassword.setError("This password is too short");
            valid = false;
        }
        else if(password.length() > 10)
        {
            etPassword.setError("This password should include max. 10 characters!");
            valid = false;
        }
        else {
            etPassword.setError(null);
        }

        return valid;
    }

    private void goMain() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("email", etEmail.getText().toString());
        i.putExtras(bundle);
        startActivity(i);
    }
}
