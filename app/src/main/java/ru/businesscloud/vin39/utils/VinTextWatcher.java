package ru.businesscloud.vin39.utils;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.widget.AppCompatEditText;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class VinTextWatcher implements TextWatcher {
    private AppCompatEditText mEditText;

    public VinTextWatcher(AppCompatEditText editText) {
        mEditText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void afterTextChanged(Editable editable) {
        mEditText.removeTextChangedListener(this);
        try {
            String originalString = editable.toString().replaceAll(" ", ",");
            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
            formatter.applyPattern("###,###,###,###");
            String formattedString = formatter.format(longval);
            mEditText.setText(formattedString.replaceAll(",", " "));
            mEditText.setSelection(mEditText.getText().length());
        } catch (NumberFormatException nfe) {}
        mEditText.addTextChangedListener(this);
    }
}
