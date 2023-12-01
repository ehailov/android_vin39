package ru.businesscloud.vin39.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import ru.businesscloud.vin39.R;

public class ImageFragment extends Fragment  {
    static private final String TAG = "MyLog";
    private ImageView mShare;
    private CardView mCard;
    private String path;
    private ImageView mImage;
    private ProgressBar progressBar;

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.share : {
                    shareImage(mImage);
                    break;
                }
                default: break;
            }
        }
    };

    public ImageFragment(String image) {
        path = "http://bc-24.ru:8080/media/" + image;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShare = (ImageView) view.findViewById(R.id.share);
        mImage = (ImageView) view.findViewById(R.id.image);
        mCard = (CardView) view.findViewById(R.id.card);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mShare.setOnClickListener(mClick);
        loadImage();
    }

   private void loadImage() {
        mShare.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(path).into(mImage, new Callback() {
            @Override
            public void onSuccess() {
                mCard.setVisibility(CardView.VISIBLE);
                mShare.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                mCard.setVisibility(CardView.GONE);
                mShare.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void shareImage(ImageView imageView) {
        if (imageView.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            Uri mImageUri = getImageUri(getContext(), bitmap);
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("image/jpeg");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, mImageUri);
            startActivity(Intent.createChooser(sharingIntent, "Поделиться"));
        }
        else Toast.makeText(getContext(), "Фото еще не загружено!", Toast.LENGTH_SHORT).show();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Отправка", null);
        return Uri.parse(path);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}