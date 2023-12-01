package ru.businesscloud.vin39.view;

import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import ru.businesscloud.vin39.R;

public class AddButtonView extends RecyclerView.ViewHolder {

    private static final String TAG = "MyLog";
    private View mView;
    private ImageView mAdd;
    private Delegate mDelegate;
    public interface Delegate {
        void addCar();
    }

    private View.OnClickListener mOkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_button : {
                    mDelegate.addCar();
                    break;
                }
                default:break;
            }
        }
    };

    public AddButtonView(View view) {
        super(view);
        mView = view;
        mAdd = (ImageView)view.findViewById(R.id.add_button);
        mAdd.setOnClickListener(mOkClick);
    }

    public void bind(Delegate delegate) {
        mAdd.setImageDrawable(mView.getResources().getDrawable(R.drawable.ic_plus));
        mAdd.setColorFilter(ContextCompat.getColor(mView.getContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        mDelegate = delegate;
    }
}
