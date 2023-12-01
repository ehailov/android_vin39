package ru.businesscloud.vin39.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.api.DjangoHelper;
import ru.businesscloud.vin39.models.CarsFields;

public class MyCarView extends RecyclerView.ViewHolder {

    public TextView mModel;
    public TextView mPrice;
    public ImageView mImage;
    public TextView mDate;
    private DelegateView mDelegate;
    public interface DelegateView {
        void addImageFragment(CarsFields fields);
    }

    public MyCarView(View view) {
        super(view);
        mModel = (TextView)view.findViewById(R.id.model);
        mPrice = (TextView)view.findViewById(R.id.price);
        mImage = (ImageView) view.findViewById(R.id.imageJurnal);
        mDate = (TextView) view.findViewById(R.id.date);
    }

    public void bind(CarsFields fields, DelegateView delegate) {
        mDelegate = delegate;
        mModel.setText(fields.model.get(1) + " " + fields.model.get(0) + " ");
        mPrice.setText(fields.price + "P.");

        mDate.setText(DjangoHelper.formatDjangoDateToFormatString(fields.date));

        if (fields.bitmap == null) {
            Picasso.get().load("http://bc-24.ru:8080/media/" + fields.file1).resize(500, 500).centerCrop().into(mImage, new Callback() {
                @Override
                public void onSuccess() {
                    fields.bitmap = mImage.getDrawingCache();
                }

                @Override
                public void onError(Exception e) {}
            });
        }
        else mImage.setImageBitmap(fields.bitmap);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.addImageFragment(fields);
            }
        });
    }
}
