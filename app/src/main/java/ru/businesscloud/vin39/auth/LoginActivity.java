package ru.businesscloud.vin39.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.businesscloud.vin39.MainActivity;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.api.ClientFactory;
import ru.businesscloud.vin39.models.Auth;

public class LoginActivity extends Activity {

    private Button mLoginButton;
    private EditText mLoginField;
    private EditText mPassordField;

    private View.OnClickListener mOnClickLisner = view -> {
        switch (view.getId()) {
            case R.id.login_button: {
                doLogin();
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AuthHelper.create(this);
        mLoginButton = (Button)findViewById(R.id.login_button);
        mLoginField = (EditText)findViewById(R.id.login_field);
        mPassordField = (EditText)findViewById(R.id.password_field);
        mLoginButton.setOnClickListener(mOnClickLisner);

    }

    private void doLogin() {
        Call<Auth> call = ClientFactory.makeClient().login(mLoginField.getText().toString(), mPassordField.getText().toString(), "");
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if (response.code() == 200) {
                    if (!response.body().sid.isEmpty()) {
                        AuthHelper.setSid(response.body().sid);
                        AuthHelper.setRoot(response.body().root);
                        openActivity();
                    }
                    else Toast.makeText(getBaseContext(), response.body().error.toString(), Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getBaseContext(), getText(R.string.web_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Toast.makeText(getBaseContext(), getText(R.string.net_error), Toast.LENGTH_LONG).show();
            }

        });
    }
    @Override
    public void onBackPressed() {
        openActivity();
    }

    private void openActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}