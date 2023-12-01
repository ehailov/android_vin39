package ru.businesscloud.vin39.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.adapter.CarAdapter;
import ru.businesscloud.vin39.adapter.CarsAdapter;
import ru.businesscloud.vin39.api.DataHelper;
import ru.businesscloud.vin39.models.Cars;
import ru.businesscloud.vin39.models.CarsFields;
import ru.businesscloud.vin39.models.Data;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CarFragment extends Fragment {

    private static final String TAG = "MyLog";
    private Subscription subscription;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Cars mCar;
    private int typeFragment;

    public CarFragment(Cars car, int type) {
        mCar = car;
        typeFragment = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.car_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataHelper.update();
            }
        });

        CarAdapter adapter = new CarAdapter(typeFragment, getContext(), mCar, new CarAdapter.DelegateFragment() {
            @Override
            public void openImage(String path) {
                replaceFragment(new ImageFragment(path));
            }
        });

        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.root_frame, fragment);
        ft.commit();
    }

}
