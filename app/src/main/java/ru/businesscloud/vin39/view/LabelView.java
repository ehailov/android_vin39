package ru.businesscloud.vin39.view;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ru.businesscloud.vin39.R;

public class LabelView extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public LabelView(View view) {
        super(view);
        mTextView = (TextView) view.findViewById(R.id.label);

    }

    public void bind(String text) {
      mTextView.setText(text);
    }

}
