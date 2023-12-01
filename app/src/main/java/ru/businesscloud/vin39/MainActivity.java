package ru.businesscloud.vin39;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import ru.businesscloud.vin39.api.DataHelper;
import ru.businesscloud.vin39.auth.AuthHelper;
import ru.businesscloud.vin39.auth.LoginActivity;
import ru.businesscloud.vin39.fragment.ArchiveFragment;
import ru.businesscloud.vin39.fragment.CarsFragment;
import ru.businesscloud.vin39.fragment.MyCarsFragment;
import ru.businesscloud.vin39.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MyLog";
    private TextView mTitle;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mClick = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_my_car: {
                    mTitle.setText(R.string.menu_my_car);
                    openFragment(new MyCarsFragment());
                    return true;
                }
                case R.id.navigation_car: {
                    mTitle.setText(R.string.menu_car);
                    openFragment(new CarsFragment());
                    return true;
                }
                case R.id.navigation_archive: {
                    mTitle.setText(R.string.archive);
                    openFragment(new ArchiveFragment());
                    return true;
                }
                case R.id.navigation_profile: {
                    mTitle.setText(R.string.menu_profile);
                    openFragment(new ProfileFragment());
                    return true;
                }
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setContentView(R.layout.activity_main);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.menu_car);
        AuthHelper.create(this);
        DataHelper.initObservable(this);
        DataHelper.update();
        openFragment(new CarsFragment());
        Log.e(TAG, " " + AuthHelper.getSid() + AuthHelper.getMqtt());
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_car);
        navigation.setVisibility(BottomNavigationView.VISIBLE);
        navigation.setOnNavigationItemSelectedListener(mClick);
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.root_frame, fragment);
        ft.commit();
    }

    private void openActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.root_frame, new CarsFragment());
        ft.commit();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] != 0) openActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        //DataHelper.update();

    }
}
