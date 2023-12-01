package ru.businesscloud.vin39.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.models.Cars;
import ru.businesscloud.vin39.view.ArhiveView;
import ru.businesscloud.vin39.view.ImageView;
import ru.businesscloud.vin39.view.LabelView;

public class CarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MyLog";
    private static final int IMAGE = 0;
    private static final int TEXT = 1;
    private static final int BUTTON = 2;
    private int count;
    private Cars mCar;
    private List<String> list;
    private Context mContext = null;
    private int typeFragment;
    private CarAdapter.DelegateFragment mDelegate;

    public interface DelegateFragment {
        void openImage(String path);
    }

    public CarAdapter(int type, Context context, Cars car, CarAdapter.DelegateFragment delegate) {
        mDelegate = delegate;
        mCar = car;
        typeFragment = type;
        mContext = context;
        list = new ArrayList<>();
        getLabel("Марка: ", mCar.fields.model.get(1));
        getLabel("Модель: ", mCar.fields.model.get(0));
        getLabel("Поколение: ", mCar.fields.generation);
        getLabel("Год: ", mCar.fields.release);
        getLabel("Vin: ", mCar.fields.vin);
        getLabel("Кузов: ", mCar.fields.body_type);
        getLabel("Состояние: ", mCar.fields.condition);
        getLabel("Мощность: ", mCar.fields.engine_capacity);
        getLabel("Тип двигателя: ", mCar.fields.engine_type);
        getLabel("Трансмисия: ", mCar.fields.equipment);
        getLabel("Пробег: ", mCar.fields.mileage);
        getLabel("Кол. дверей: ", mCar.fields.number_of_doors);
        getLabel("Цена: ", mCar.fields.price);
        getLabel("Руль: ", mCar.fields.rudder);
        getLabel("Привод: ", mCar.fields.drive);
        getLabel("Цвет: ", mCar.fields.color);
        getLabel("Город: ", mCar.fields.city);
        count = 2 + list.size();
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return IMAGE;
        else if (position == count - 1) return BUTTON;
        else return TEXT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMAGE: {
                View view = LayoutInflater.from(mContext).inflate(R.layout.image_view, parent, false);
                return new ImageView(view);
            }
            case TEXT: {
                View view = LayoutInflater.from(mContext).inflate(R.layout.label_view, parent, false);
                return new LabelView(view);
            }
            case BUTTON: {
                View view = LayoutInflater.from(mContext).inflate(R.layout.arhive_view, parent, false);
                return new ArhiveView(view);
            }
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            ImageView viewHolder = (ImageView) holder;
            viewHolder.bind(mCar.fields, new ImageView.DelegateImageView() {
                @Override
                public void openImageFragment(String image) {
                    mDelegate.openImage(image);
                }
            });
        } else if (position == count - 1) {
            ArhiveView viewHolder = (ArhiveView) holder;
            viewHolder.bind(mCar.pk, typeFragment);
        } else {
            LabelView viewHolder = (LabelView) holder;
            viewHolder.bind(list.get(position - 1));
        }
    }

    private void getLabel(String header, String text) {
        String result = " -";
        if (text != null) {
            result = text;
        }
        list.add(header + result);
    }
}
