package ru.businesscloud.vin39.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.models.Cars;
import ru.businesscloud.vin39.models.CarsFields;
import ru.businesscloud.vin39.view.AddButtonView;
import ru.businesscloud.vin39.view.FilterButtonView;
import ru.businesscloud.vin39.view.CarView;
import ru.businesscloud.vin39.view.PlugView;

public class CarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MyLog";
    private static final int VIEW = 0;
    private static final int BUTTON = 1;
    private ArrayList<Cars> carsList;
    private Context mContext = null;
    private int typeFragment;
    private CarsAdapter.DelegateFragment mDelegate;

    public interface DelegateFragment {
        void addCar();
        void filterCar();
        void addImageFragment(Cars cars);
    }

    public CarsAdapter(int type, Context context, ArrayList<Cars> list, CarsAdapter.DelegateFragment delegate) {
        mDelegate = delegate;
        carsList = list;
        mContext = context;
        typeFragment = type;
    }

    @Override
    public int getItemCount() {
        return carsList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return BUTTON;
        else return VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW: {
                View view = LayoutInflater.from(mContext).inflate(R.layout.car_view, parent, false);
                return new CarView(view);
            }
            case BUTTON: {
                switch (typeFragment) {
                    case 0: {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_button_view, parent, false);
                        return new FilterButtonView(view);
                    }
                    case 1: {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.add_button_view, parent, false);
                        return new AddButtonView(view);
                    }
                    case 2: {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.plug_view, parent, false);
                        return new PlugView(view);
                    }
                }
            }
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            switch (typeFragment) {
                case 0: {
                    FilterButtonView viewHolder = (FilterButtonView) holder;
                    viewHolder.bind(new FilterButtonView.Delegate() {
                        @Override
                        public void filterCar() {
                            mDelegate.filterCar();
                        }
                    });
                    break;
                }
                case 1: {
                    AddButtonView viewHolder = (AddButtonView) holder;
                    viewHolder.bind(new AddButtonView.Delegate() {
                        @Override
                        public void addCar() {
                            mDelegate.addCar();
                        }
                    });
                    break;
                }
                case 2: {
                    PlugView viewHolder = (PlugView) holder;
                    viewHolder.bind();
                    break;
                }
            }
        }
        else {
            CarView viewHolder = (CarView) holder;
            viewHolder.bind(carsList.get(position - 1), new CarView.DelegateView() {
                @Override
                public void openCar(Cars cars) {
                    mDelegate.addImageFragment(cars);
                }
            });
        }
    }
}
