package ru.businesscloud.vin39.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.businesscloud.vin39.MainActivity;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.api.ClientFactory;
import ru.businesscloud.vin39.models.Auth;
import ru.businesscloud.vin39.models.Save;

public class ActivateActivity extends Activity {

    private String TAG = "MyLog";
    private String mPass;
    private String mEmail;
    private Button mActivateButton;
    private Button mPinButton;
    private AppCompatEditText mPinField;
    private View.OnClickListener mOnClickLisner = view -> {
        switch (view.getId()) {
            case R.id.activate_button: {
                doActivate();
                break;
            }
            case R.id.pin_button: {
                getPin();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        AuthHelper.create(this);
        Bundle arguments = getIntent().getExtras();
        mEmail = arguments.get("email").toString();
        mPass = arguments.get("pass").toString();
        mActivateButton = (Button)findViewById(R.id.activate_button);
        mPinButton = (Button)findViewById(R.id.pin_button);
        mPinField = (AppCompatEditText)findViewById(R.id.pin_field);
        mActivateButton.setOnClickListener(mOnClickLisner);
        mPinButton.setOnClickListener(mOnClickLisner);
        getPin();
    }

    private void doActivate() {
       Call<Save> call = ClientFactory.makeClient().activate(mEmail, mPinField.getText().toString());
        call.enqueue(new Callback<Save>() {
            @Override
            public void onResponse(Call<Save> call, Response<Save> response) {
                if (response.code() == 200) {
                    if (!response.body().hasError) doLogin();
                    else Toast.makeText(getBaseContext(), response.body().state, Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getBaseContext(), getText(R.string.web_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Save> call, Throwable t) {
                Toast.makeText(getBaseContext(), getText(R.string.net_error), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void doLogin() {
        Call<Auth> call = ClientFactory.makeClient().login(mEmail, mPass, "");
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if (response.code() == 200) {
                    if (!response.body().sid.isEmpty()) {
                        AuthHelper.setSid(response.body().sid);
                        AuthHelper.setRoot(response.body().root);
                        openActivity(MainActivity.class);
                    }
                    else {
                        openActivity(LoginActivity.class);
                        Toast.makeText(getBaseContext(), response.body().error.toString(), Toast.LENGTH_LONG).show();
                    }
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
        openActivity(RegistrationActivity.class);
    }

    private void getPin() {
        Call<Save> call = ClientFactory.makeClient().pin(mEmail);
        call.enqueue(new Callback<Save>() {
            @Override
            public void onResponse(Call<Save> call, Response<Save> response) {
                if (response.code() == 200) {
                    Toast.makeText(getBaseContext(), getText(R.string.pin_cod), Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getBaseContext(), getText(R.string.web_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Save> call, Throwable t) {
                Toast.makeText(getBaseContext(), getText(R.string.net_error), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void openActivity(Class<?> typeActivity) {
        Intent intent = new Intent(ActivateActivity.this, typeActivity);
        startActivity(intent);
        finish();
    }
}