package ru.businesscloud.vin39.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.api.ClientFactory;
import ru.businesscloud.vin39.models.Model;

public class FilterFragment extends Fragment {

    private static final String TAG = "MyLog";
    private Spinner brand;
    private Spinner model;
    private TextView data0;
    private TextView data1;
    private Context context;
    private Button mButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_car_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        model = (Spinner)view.findViewById(R.id.model);
        brand = (Spinner)view.findViewById(R.id.brand);
        data0 = (AppCompatEditText)view.findViewById(R.id.car_date0);
        data1 = (AppCompatEditText)view.findViewById(R.id.car_date1);
        mButton = (Button)view.findViewById(R.id.filter_button);
        getBrand();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }

        });
        brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                getModel(selectedItem);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void getBrand() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getBrand();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    brand.setAdapter(adapter);
                    brand.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private void getModel(String brand) {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getModel(brand);
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArrayModels(response.body()));
                    model.setAdapter(adapter);
                    model.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private ArrayList<String> getArray(ArrayList<Model> orig) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-");
        for (Model item : orig) {
            arrayList.add(item.fields.name);
        }
        return arrayList;
    }

    private ArrayList<String> getArrayModels(ArrayList<Model> orig) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-");
        for (Model item : orig) {
            arrayList.add(item.fields.name);
        }
        return arrayList;
    }

    public void save() {
        //DataHelper.getFilterCars(model.getSelectedItem().toString(), data0.getText().toString(), data1.getText().toString());
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.root_frame, new CarsFragment());
        ft.commit();
    }
}