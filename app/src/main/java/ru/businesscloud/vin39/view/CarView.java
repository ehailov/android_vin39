package ru.businesscloud.vin39.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.api.DjangoHelper;
import ru.businesscloud.vin39.models.Cars;

public class CarView extends RecyclerView.ViewHolder {

    public TextView mModel;
    public TextView mPrice;
    public TextView mDate;
    public ImageView mImage;
    private DelegateView mDelegate;
    public interface DelegateView {
        void openCar(Cars cars);
    }

    public CarView(View view) {
        super(view);
        mModel = (TextView)view.findViewById(R.id.model);
        mPrice = (TextView)view.findViewById(R.id.price);
        mImage = (ImageView) view.findViewById(R.id.imageJurnal);
        mDate = (TextView) view.findViewById(R.id.date);
    }

    public void bind(Cars cars, DelegateView delegate) {
        mDelegate = delegate;
        mModel.setText(cars.fields.model.get(1) + " " + cars.fields.model.get(0) + " " );
        mPrice.setText(cars.fields.price + "P.");
        mDate.setText(DjangoHelper.formatDjangoDateToFormatString(cars.fields.date));

        if (cars.fields.bitmap == null) {
            Picasso.get().load("http://bc-24.ru:8080/media/" + cars.fields.file1).resize(500, 500).centerCrop().into(mImage, new Callback() {
                @Override
                public void onSuccess() {
                    cars.fields.bitmap = mImage.getDrawingCache();
                }

                @Override
                public void onError(Exception e) {}
            });
        }
        else mImage.setImageBitmap(cars.fields.bitmap);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.openCar(cars);
            }
        });
    }
}
