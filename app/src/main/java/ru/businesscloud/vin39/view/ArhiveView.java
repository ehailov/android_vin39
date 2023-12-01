package ru.businesscloud.vin39.view;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.api.ClientFactory;
import ru.businesscloud.vin39.api.DataHelper;
import ru.businesscloud.vin39.models.Save;

public class ArhiveView extends RecyclerView.ViewHolder {

    private android.widget.Button archive;
    private android.widget.Button dearchive;
    private Context mContext;
    private int mID;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.arhive_button: {
                    arhive();
                    break;
                }
                case R.id.dearhive_button: {
                    deArhive();
                    break;
                }
            }
        }
    };

    public ArhiveView(View view) {
        super(view);
        archive = (android.widget.Button) view.findViewById(R.id.arhive_button);
        dearchive = (android.widget.Button) view.findViewById(R.id.dearhive_button);
        mContext = view.getContext();
    }

    public void bind(int id, int type) {
        mID = id;
        switch (type) {
            case 0: {
                archive.setVisibility(View.GONE);
                dearchive.setVisibility(View.GONE);
                break;
            }
            case 1: {
                archive.setVisibility(View.VISIBLE);
                dearchive.setVisibility(View.GONE);
                break;
            }
            case 2: {
                archive.setVisibility(View.GONE);
                dearchive.setVisibility(View.VISIBLE);
                break;
            }
        }
        archive.setOnClickListener(mClickListener);
        dearchive.setOnClickListener(mClickListener);
    }

    private void arhive() {
        Call<Save> call = ClientFactory.makeClient().arhive(mID);
        call.enqueue(new Callback<Save>() {
            @Override
            public void onResponse(Call<Save> call, Response<Save> response) {
                if (response.code() == 200) {
                    Toast.makeText(mContext, response.body().state, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Save> call, Throwable t) {
                Toast.makeText(mContext, R.string.web_error, Toast.LENGTH_SHORT).show();
            }

        });
        DataHelper.update();
    }

    private void deArhive() {
        Call<Save> call = ClientFactory.makeClient().deArhive(mID);
        call.enqueue(new Callback<Save>() {
            @Override
            public void onResponse(Call<Save> call, Response<Save> response) {
                if (response.code() == 200) {
                    Toast.makeText(mContext, response.body().state, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Save> call, Throwable t) {
                Toast.makeText(mContext, R.string.web_error, Toast.LENGTH_SHORT).show();
            }

        });

        DataHelper.update();
    }

}
