package ru.businesscloud.vin39.view;

import android.util.Log;
import android.view.View;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.models.CarsFields;

public class ImageView extends RecyclerView.ViewHolder {

    static private final String TAG = "MyLog";
    private DelegateImageView mDelegate;
    private android.widget.ImageView mImage0;
    private android.widget.ImageView mImage1;
    private android.widget.ImageView mImage2;
    private android.widget.ImageView mImage3;
    private android.widget.ImageView mImage4;
    private android.widget.ImageView mImage5;
    private android.widget.ImageView mImage6;
    private android.widget.ImageView mImage7;
    private android.widget.ImageView mImage8;
    private android.widget.ImageView mImage9;
    private android.widget.ImageView mImage10;
    private android.widget.ImageView mImage11;
    private CardView mCard0;
    private CardView mCard1;
    private CardView mCard2;
    private CardView mCard3;
    private CardView mCard4;
    private CardView mCard5;
    private CardView mCard6;
    private CardView mCard7;
    private CardView mCard8;
    private CardView mCard9;
    private CardView mCard10;
    private CardView mCard11;
    private CarsFields mFields;

    public interface DelegateImageView {
        void openImageFragment(String image);
    }

    public ImageView(View view) {
        super(view);
        mImage0 = (android.widget.ImageView) view.findViewById(R.id.image0);
        mImage1 = (android.widget.ImageView) view.findViewById(R.id.image1);
        mImage2 = (android.widget.ImageView) view.findViewById(R.id.image2);
        mImage3 = (android.widget.ImageView) view.findViewById(R.id.image3);
        mImage4 = (android.widget.ImageView) view.findViewById(R.id.image4);
        mImage5 = (android.widget.ImageView) view.findViewById(R.id.image5);
        mImage6 = (android.widget.ImageView) view.findViewById(R.id.image6);
        mImage7 = (android.widget.ImageView) view.findViewById(R.id.image7);
        mImage8 = (android.widget.ImageView) view.findViewById(R.id.image8);
        mImage9 = (android.widget.ImageView) view.findViewById(R.id.image9);
        mImage10 = (android.widget.ImageView) view.findViewById(R.id.image10);
        mImage11 = (android.widget.ImageView) view.findViewById(R.id.image11);
        mCard0 = (CardView) view.findViewById(R.id.card0);
        mCard1 = (CardView) view.findViewById(R.id.card1);
        mCard2 = (CardView) view.findViewById(R.id.card2);
        mCard3 = (CardView) view.findViewById(R.id.card3);
        mCard4 = (CardView) view.findViewById(R.id.card4);
        mCard5 = (CardView) view.findViewById(R.id.card5);
        mCard6 = (CardView) view.findViewById(R.id.card6);
        mCard7 = (CardView) view.findViewById(R.id.card7);
        mCard8 = (CardView) view.findViewById(R.id.card8);
        mCard9 = (CardView) view.findViewById(R.id.card9);
        mCard10 = (CardView) view.findViewById(R.id.card10);
        mCard11 = (CardView) view.findViewById(R.id.card11);
        mCard1.setVisibility(CardView.GONE);
        mCard2.setVisibility(CardView.GONE);
        mCard3.setVisibility(CardView.GONE);
        mCard4.setVisibility(CardView.GONE);
        mCard5.setVisibility(CardView.GONE);
        mCard6.setVisibility(CardView.GONE);
        mCard7.setVisibility(CardView.GONE);
        mCard8.setVisibility(CardView.GONE);
        mCard9.setVisibility(CardView.GONE);
        mCard10.setVisibility(CardView.GONE);
        mCard11.setVisibility(CardView.GONE);
        mImage0.setOnClickListener(mClickListener);
        mImage1.setOnClickListener(mClickListener);
        mImage2.setOnClickListener(mClickListener);
        mImage3.setOnClickListener(mClickListener);
        mImage4.setOnClickListener(mClickListener);
        mImage5.setOnClickListener(mClickListener);
        mImage6.setOnClickListener(mClickListener);
        mImage7.setOnClickListener(mClickListener);
        mImage8.setOnClickListener(mClickListener);
        mImage9.setOnClickListener(mClickListener);
        mImage10.setOnClickListener(mClickListener);
        mImage11.setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image0: {
                    mDelegate.openImageFragment(mFields.file1);
                    break;
                }
                case R.id.image1: {
                    Log.e(TAG, "openImageFragment " + mFields.file2);
                    mDelegate.openImageFragment(mFields.file2);
                    break;
                }
                case R.id.image2: {
                    mDelegate.openImageFragment(mFields.file3);
                    break;
                }
                case R.id.image3: {
                    mDelegate.openImageFragment(mFields.file4);
                    break;
                }
                case R.id.image4: {
                    mDelegate.openImageFragment(mFields.file5);
                    break;
                }
                case R.id.image5: {
                    mDelegate.openImageFragment(mFields.file6);
                    break;
                }
                case R.id.image6: {
                    mDelegate.openImageFragment(mFields.file7);
                    break;
                }
                case R.id.image7: {
                    mDelegate.openImageFragment(mFields.file8);
                    break;
                }
                case R.id.image8: {
                    mDelegate.openImageFragment(mFields.file9);
                    break;
                }
                case R.id.image9: {
                    mDelegate.openImageFragment(mFields.file10);
                    break;
                }
                case R.id.image10: {
                    mDelegate.openImageFragment(mFields.file11);
                    break;
                }
                case R.id.image11: {
                    mDelegate.openImageFragment(mFields.file12);
                    break;
                }
            }
        }
    };

    public void bind(CarsFields fields, DelegateImageView delegate) {
        mDelegate = delegate;
        mFields = fields;
        loadImage();
    }

    private void loadImage() {
        String path = "http://bc-24.ru:8080/media/";
        Picasso.get().load(path + mFields.file1).into(mImage0, new Callback() {
            @Override
            public void onSuccess() {
                mCard0.setVisibility(CardView.VISIBLE);
                Picasso.get().load(path + mFields.file2).into(mImage1, new Callback() {
                    @Override
                    public void onSuccess() {
                        mCard1.setVisibility(CardView.VISIBLE);
                        Picasso.get().load(path + mFields.file3).into(mImage2, new Callback() {
                            @Override
                            public void onSuccess() {
                                mCard2.setVisibility(CardView.VISIBLE);
                                Picasso.get().load(path + mFields.file4).into(mImage3, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        mCard3.setVisibility(CardView.VISIBLE);
                                        Picasso.get().load(path + mFields.file5).into(mImage4, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                mCard4.setVisibility(CardView.VISIBLE);
                                                Picasso.get().load(path + mFields.file6).into(mImage5, new Callback() {
                                                    @Override
                                                    public void onSuccess() {
                                                        mCard5.setVisibility(CardView.VISIBLE);
                                                        Picasso.get().load(path + mFields.file7).into(mImage6, new Callback() {
                                                            @Override
                                                            public void onSuccess() {
                                                                mCard6.setVisibility(CardView.VISIBLE);
                                                                Picasso.get().load(path + mFields.file8).into(mImage7, new Callback() {
                                                                    @Override
                                                                    public void onSuccess() {
                                                                        mCard7.setVisibility(CardView.VISIBLE);
                                                                        Picasso.get().load(path + mFields.file9).into(mImage8, new Callback() {
                                                                            @Override
                                                                            public void onSuccess() {
                                                                                mCard8.setVisibility(CardView.VISIBLE);
                                                                                Picasso.get().load(path + mFields.file10).into(mImage9, new Callback() {
                                                                                    @Override
                                                                                    public void onSuccess() {
                                                                                        mCard9.setVisibility(CardView.VISIBLE);
                                                                                        Picasso.get().load(path + mFields.file11).into(mImage10, new Callback() {
                                                                                            @Override
                                                                                            public void onSuccess() {
                                                                                                mCard10.setVisibility(CardView.VISIBLE);
                                                                                                Picasso.get().load(path + mFields.file12).into(mImage11, new Callback() {
                                                                                                    @Override
                                                                                                    public void onSuccess() {
                                                                                                        mCard11.setVisibility(CardView.VISIBLE);
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onError(Exception e) {
                                                                                                        mCard11.setVisibility(CardView.GONE);
                                                                                                    }
                                                                                                });
                                                                                            }

                                                                                            @Override
                                                                                            public void onError(Exception e) {
                                                                                                mCard10.setVisibility(CardView.GONE);
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    @Override
                                                                                    public void onError(Exception e) {
                                                                                        mCard9.setVisibility(CardView.GONE);
                                                                                    }
                                                                                });
                                                                            }

                                                                            @Override
                                                                            public void onError(Exception e) {
                                                                                mCard8.setVisibility(CardView.GONE);
                                                                            }
                                                                        });
                                                                    }

                                                                    @Override
                                                                    public void onError(Exception e) {
                                                                        mCard7.setVisibility(CardView.GONE);
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onError(Exception e) {
                                                                mCard6.setVisibility(CardView.GONE);
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onError(Exception e) {
                                                        mCard5.setVisibility(CardView.GONE);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                mCard4.setVisibility(CardView.GONE);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        mCard3.setVisibility(CardView.GONE);
                                    }
                                });
                            }

                            @Override
                            public void onError(Exception e) {
                                mCard2.setVisibility(CardView.GONE);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        mCard1.setVisibility(CardView.GONE);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                mCard0.setVisibility(CardView.GONE);
            }
        });
    }
}
