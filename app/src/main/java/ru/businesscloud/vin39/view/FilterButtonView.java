package ru.businesscloud.vin39.view;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import ru.businesscloud.vin39.R;

public class FilterButtonView extends RecyclerView.ViewHolder {

    private static final String TAG = "MyLog";
    private View mView;
    private ImageView mFilter;
    private Delegate mDelegate;
    public interface Delegate {
        void filterCar();
    }

    private View.OnClickListener mOkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.filter_button : {
                    mDelegate.filterCar();
                    break;
                }
                default:break;
            }
        }
    };

    public FilterButtonView(View view) {
        super(view);
        mView = view;
        mFilter = (ImageView)view.findViewById(R.id.filter_button);
        mFilter.setOnClickListener(mOkClick);
    }

    public void bind(Delegate delegate) {
        mFilter.setImageDrawable(mView.getResources().getDrawable(R.drawable.ic_filter));
        mFilter.setColorFilter(ContextCompat.getColor(mView.getContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        mDelegate = delegate;
    }
}
