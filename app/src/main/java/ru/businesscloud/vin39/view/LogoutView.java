package ru.businesscloud.vin39.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.businesscloud.vin39.BuildConfig;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.api.ClientFactory;
import ru.businesscloud.vin39.auth.AuthHelper;
import ru.businesscloud.vin39.auth.LoginActivity;

public class LogoutView extends RecyclerView.ViewHolder {

    private Button mLogout;
    private View mView;
    private TextView mVer;

    public LogoutView(View view) {
        super(view);
        mView = view;

    }

    public void bind(FragmentActivity fragmentActivity) {
        mLogout = (Button)mView.findViewById(R.id.logout_button);
        mVer = (TextView)mView.findViewById(R.id.profile_ver);
        mVer.setText("v. " + BuildConfig.VERSION_NAME);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Call<Auth> call = ClientFactory.makeClient().logout(AuthHelper.getSid());
                call.enqueue(new Callback<Auth>() {
                    @Override
                    public void onResponse(Call<Auth> call, Response<Auth> response) {
                        if (response.code() == 200) {
                            AuthHelper.setSid("");
                            openActivity(fragmentActivity);
                        }
                        else Toast.makeText(mView.getContext(), mView.getContext().getText(R.string.web_error), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Auth> call, Throwable t) {
                        Toast.makeText(mView.getContext(), mView.getContext().getText(R.string.net_error), Toast.LENGTH_LONG).show();
                    }

                });*/
            }
        });
    }

    private void openActivity(FragmentActivity fragmentActivity) {
        Intent intent = new Intent(fragmentActivity, LoginActivity.class);
        fragmentActivity.startActivity(intent);
        fragmentActivity.finish();
    }


}
