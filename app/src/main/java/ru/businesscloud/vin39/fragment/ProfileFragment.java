package ru.businesscloud.vin39.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.businesscloud.vin39.MainActivity;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.api.ClientFactory;
import ru.businesscloud.vin39.api.DataHelper;
import ru.businesscloud.vin39.auth.AuthHelper;
import ru.businesscloud.vin39.auth.LoginActivity;
import ru.businesscloud.vin39.auth.RegistrationActivity;
import ru.businesscloud.vin39.models.Auth;
import ru.businesscloud.vin39.models.Data;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileFragment extends Fragment {

    private Subscription subscription;
    private Button mLoginButton;
    private Button mLogoutButton;
    private Button mReginButton;
    private TextView mEmail;
    private TextView mName;
    private TextView mPhone;
    private TextView mProfile;
    private ImageView mEmailImage;
    private ImageView mPhoneImage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmailImage = (ImageView)view.findViewById(R.id.email_image);
        mPhoneImage = (ImageView)view.findViewById(R.id.phone_image);
        mProfile = (TextView)view.findViewById(R.id.profile_image);
        mLoginButton = (Button)view.findViewById(R.id.login_button);
        mReginButton = (Button)view.findViewById(R.id.regin_button);
        mLogoutButton = (Button)view.findViewById(R.id.logout_button);
        mName = (TextView)view.findViewById(R.id.name);
        mEmail = (TextView)view.findViewById(R.id.email);
        mPhone = (TextView)view.findViewById(R.id.phone);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        mReginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regin();
            }
        });
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        subscription();
        if (AuthHelper.isLogin()) {
            mProfile.setVisibility(View.VISIBLE);
            mPhoneImage.setVisibility(View.VISIBLE);
            mEmailImage.setVisibility(View.VISIBLE);
            mPhone.setVisibility(View.VISIBLE);
            mReginButton.setVisibility(View.GONE);
            mLoginButton.setVisibility(View.GONE);
            mLogoutButton.setVisibility(View.VISIBLE);
        } else {
            mProfile.setVisibility(View.GONE);
            mPhoneImage.setVisibility(View.GONE);
            mEmailImage.setVisibility(View.GONE);
            mReginButton.setVisibility(View.VISIBLE);
            mLoginButton.setVisibility(View.VISIBLE);
            mLogoutButton.setVisibility(View.GONE);
        }
    }
    private void subscription() {
        Observable<Data> data = (Observable<Data>) DataHelper.getObservable();
        subscription = data.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Data>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(Data data) {
                        String last = data.profil.last_name;
                        String first = data.profil.first_name;
                        mName.setText(last + " " + first);
                        mEmail.setText(data.profil.email);
                        mPhone.setText(data.profil.phone);
                        mProfile.setText(getTextForProfile(last, first));
                    }
                });
    }

    private void login() {
        openActivity(LoginActivity.class);
    }

    private void regin() {
        openActivity(RegistrationActivity.class);
    }

    private void openActivity(Class<?> activity) {
        Intent intent = new Intent(getContext(),activity);
        startActivity(intent);
        getActivity().finish();
    }

    private void logout() {
        Call<Auth> call = ClientFactory.makeClient().logout(AuthHelper.getSid());
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if (response.code() == 200) {
                    AuthHelper.setSid("");
                    AuthHelper.setRoot(false);
                    openActivity(MainActivity.class);
                }
                else Toast.makeText(getContext(), getText(R.string.web_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Toast.makeText(getContext(), getText(R.string.net_error), Toast.LENGTH_LONG).show();
            }

        });
    }

    private String getTextForProfile(String lastName, String firstName) {
        if (lastName.isEmpty() || firstName.isEmpty()) {
            return "";
        }
        char oneText = firstName.charAt(0);
        char twoText = lastName.charAt(0);
        return twoText + " " + oneText;
    }
}
