package ru.businesscloud.vin39.api;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.auth.AuthHelper;
import ru.businesscloud.vin39.models.Cars;
import ru.businesscloud.vin39.models.Data;
import ru.businesscloud.vin39.models.Profil;
import rx.Observable;
import rx.subjects.BehaviorSubject;

public class DataHelper {

    private static final String TAG = "MyLog";
    private static Activity mMainActivity;
    private static Data mData;
    private static BehaviorSubject<Data> dataObservable;

    public static Observable<?> getObservable() {
        return dataObservable;
    }

    public static void initObservable(Activity mainActivity) {
        dataObservable = BehaviorSubject.create();
        mMainActivity = mainActivity;
        mData = new Data();
    }

    public static void update() {
        getCars();
    }

    private static void getCars() {
        Call<ArrayList<Cars>> call = ClientFactory.makeClient().getCars();
        call.enqueue(new Callback<ArrayList<Cars>>() {
            @Override
            public void onResponse(Call<ArrayList<Cars>> call, Response<ArrayList<Cars>> response) {
                if (response.code() == 200) {
                    mData.cars = response.body();
                } else {
                    mData.cars = new ArrayList<>();
                    if (response.code() == 403) {
                        Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.session_expired), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.web_error), Toast.LENGTH_LONG).show();
                    }
                }
                getMyCars();
            }

            @Override
            public void onFailure(Call<ArrayList<Cars>> call, Throwable t) {
                Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.net_error), Toast.LENGTH_LONG).show();
                mData.cars = new ArrayList<>();
                getMyCars();
            }
        });
    }

    private static void getMyCars() {
        if (!AuthHelper.getSid().isEmpty()) {
            Call<ArrayList<Cars>> call = ClientFactory.makeClient().getMyCars(AuthHelper.getSid());
            call.enqueue(new Callback<ArrayList<Cars>>() {
                @Override
                public void onResponse(Call<ArrayList<Cars>> call, Response<ArrayList<Cars>> response) {
                    if (response.code() == 200) {
                        mData.myCars = response.body();
                    } else {
                        mData.myCars = new ArrayList<>();
                        if (response.code() == 403) {
                            Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.session_expired), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.web_error), Toast.LENGTH_LONG).show();
                        }
                    }
                    getArchve();
                }

                @Override
                public void onFailure(Call<ArrayList<Cars>> call, Throwable t) {
                    Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.net_error), Toast.LENGTH_LONG).show();
                    mData.myCars = new ArrayList<>();
                    getArchve();
                }

            });
        } else {
            mData.myCars = new ArrayList<>();
            getArchve();
        }
    }

    private static void getArchve() {
        Call<ArrayList<Cars>> call = ClientFactory.makeClient().getArchive(AuthHelper.getSid());
        call.enqueue(new Callback<ArrayList<Cars>>() {
            @Override
            public void onResponse(Call<ArrayList<Cars>> call, Response<ArrayList<Cars>> response) {
                if (response.code() == 200) {
                    mData.arhiv = response.body();
                } else {
                    if (response.code() == 403) {
                        Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.session_expired), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.web_error), Toast.LENGTH_LONG).show();
                    }
                    mData.arhiv = new ArrayList<>();
                }
                getProfile();
            }

            @Override
            public void onFailure(Call<ArrayList<Cars>> call, Throwable t) {
                Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.net_error), Toast.LENGTH_LONG).show();
                mData.arhiv = new ArrayList<>();
                getProfile();
            }
        });
    }

    private static void getProfile() {
        Call<Profil> call = ClientFactory.makeClient().getProfile(AuthHelper.getSid());
        call.enqueue(new Callback<Profil>() {
            @Override
            public void onResponse(Call<Profil> call, Response<Profil> response) {
                if (response.code() == 200) {
                    mData.profil = response.body();
                } else {
                    if (response.code() == 403) {
                        Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.session_expired), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.web_error), Toast.LENGTH_LONG).show();
                    }
                }
                dataObservable.onNext(mData);
            }

            @Override
            public void onFailure(Call<Profil> call, Throwable t) {
                Toast.makeText(mMainActivity.getBaseContext(), mMainActivity.getText(R.string.net_error), Toast.LENGTH_LONG).show();
                dataObservable.onNext(mData);
            }
        });
    }
}
