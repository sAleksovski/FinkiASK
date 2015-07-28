package mk.ukim.finki.tr.finkiask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.ButterKnife;
import mk.ukim.finki.tr.finkiask.helper.AuthHelper;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.input_username) EditText mUsernameText;
    @Bind(R.id.input_password) EditText mPasswordText;
    @Bind(R.id.btn_login) Button mLoginButton;
    @Bind(R.id.link_no_login) TextView mNoLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mPasswordText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER
                            || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        login();
                        return true;
                    }
                }

                return false;
            }
        });

        mNoLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void login() {

        mUsernameText.setEnabled(false);
        mPasswordText.setEnabled(false);
        mLoginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_FinkiASK_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final Activity mActivity = this;

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        boolean success = validate();
                        progressDialog.dismiss();

                        if ( ! success) {
                            Snackbar.make(findViewById(android.R.id.content), "Wrong username or password", Snackbar.LENGTH_LONG)
                                    .show();
                            mUsernameText.setEnabled(true);
                            mPasswordText.setEnabled(true);
                            mLoginButton.setEnabled(true);
                        } else {
                            String username = mUsernameText.getText().toString();
                            String password = mPasswordText.getText().toString();
                            String encryptedString = "";

                            MessageDigest messageDigest;
                            try {
                                messageDigest = MessageDigest.getInstance("SHA-256");
                                messageDigest.update(password.getBytes());
                                encryptedString = new String(messageDigest.digest());
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }

                            AuthHelper.setCredentials(mActivity, username, encryptedString);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                    }
                }, 3000);

    }

    private boolean validate() {
        String username = mUsernameText.getText().toString();
        String password = mPasswordText.getText().toString();

        return username.equals("demo") && password.equals("demo");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
