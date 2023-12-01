package ru.businesscloud.vin39.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import ru.businesscloud.vin39.models.Save;

public class RegistrationActivity extends Activity {

    private Button mRegButton;
    private AppCompatEditText mEmailField;
    private AppCompatEditText mPassord0Field;
    private AppCompatEditText mPassord1Field;
    private AppCompatEditText mLastNameField;
    private AppCompatEditText mFirstNameField;
    private AppCompatEditText mPhoneField;

    private boolean isEmptyForm() {
        if (mPhoneField.getText().length() == 0) {
            return true;
        } else if (mEmailField.getText().length() == 0) {
            return true;
        } else if (mEmailField.getText().length() == 0) {
            return true;
        } else if (mLastNameField.getText().length() == 0) {
            return true;
        } else if(mFirstNameField.getText().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private View.OnClickListener mOnClickLisner = view -> {
        switch (view.getId()) {
            case R.id.reg_button: {
                if (!Patterns.EMAIL_ADDRESS.matcher(mEmailField.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(), R.string.error_email, Toast.LENGTH_LONG).show();
                }
                else if (mPassord0Field.getText().toString().equals(mPassord1Field.getText().toString())) {
                    if (isEmptyForm()) {
                        Toast.makeText(getApplicationContext(), R.string.not_all_data, Toast.LENGTH_LONG).show();
                    } else {
                        doRegistation();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.error_password, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mRegButton = (Button)findViewById(R.id.reg_button);
        mEmailField = (AppCompatEditText)findViewById(R.id.email_field);
        mPassord0Field = (AppCompatEditText)findViewById(R.id.password0_field);
        mPassord1Field = (AppCompatEditText)findViewById(R.id.password1_field);
        mLastNameField = (AppCompatEditText)findViewById(R.id.last_name);
        mFirstNameField = (AppCompatEditText)findViewById(R.id.first_name);
        mPhoneField = (AppCompatEditText)findViewById(R.id.phone);
        mRegButton.setOnClickListener(mOnClickLisner);
    }

    private void doRegistation() {
        Call<Save> call = ClientFactory.makeClient().registration(mEmailField.getText().toString(),
                                                               mPassord0Field.getText().toString(),
                                                               mFirstNameField.getText().toString(),
                                                               mLastNameField.getText().toString(),
                                                                mPhoneField.getText().toString());
        call.enqueue(new Callback<Save>() {
            @Override
            public void onResponse(Call<Save> call, Response<Save> response) {
                if (response.code() == 200) {
                    registration();
                }
                else Toast.makeText(getBaseContext(), getText(R.string.web_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Save> call, Throwable t) {
                Toast.makeText(getBaseContext(), getText(R.string.net_error), Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        openActivity(intent);
    }

    private void registration() {
        Intent intent = new Intent(RegistrationActivity.this, ActivateActivity.class);
        intent.putExtra("email", mEmailField.getText());
        intent.putExtra("pass", mPassord0Field.getText());
        openActivity(intent);
    }

    private void openActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
}