package ru.businesscloud.vin39.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.businesscloud.vin39.R;
import ru.businesscloud.vin39.utils.VinTextWatcher;
import ru.businesscloud.vin39.api.ClientFactory;
import ru.businesscloud.vin39.api.DataHelper;
import ru.businesscloud.vin39.auth.AuthHelper;
import ru.businesscloud.vin39.models.Model;
import ru.businesscloud.vin39.models.Save;

public class AddCarFragment extends Fragment {
    private static final String TAG = "MyLog";
    private Spinner mBrand;
    private Spinner mModel;
    private Spinner mBody;
    private Spinner mEngine;
    private Spinner mCity;
    private Spinner mColor;
    private Spinner mOwnerss;
    private Spinner mEngineCapacity;
    private Spinner mNumberOfDoors;
    private Spinner mEquipment;
    private Spinner mRudder;
    private Spinner mDrive;
    private Spinner mCondition;
    private Spinner mYear;
    private Spinner mGeneration;
    private AppCompatEditText mVin;
    private AppCompatEditText mPrice;
    private AppCompatEditText mMileage;
    private Context context;
    private Button mButton;
    private ImageView mImage11;
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImage4;
    private ImageView mImage5;
    private ImageView mImage6;
    private ImageView mImage7;
    private ImageView mImage8;
    private ImageView mImage9;
    private ImageView mImage10;
    private ImageView mImage12;
    private ImageButton mCameraButton;
    private ImageButton mFileButton;
    //private Bitmap bmap;
    private File directory;
    private ProgressBar mProgres;
    private List<String> brands;
    private List<String> models;
    private String id;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.camera_button: {
                    if (mModel.getSelectedItem().toString() != "-") {
                        try {
                            createDirectory();
                            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
                            startActivityForResult(captureIntent, 1);
                        } catch (ActivityNotFoundException e) {
                            showTost(R.string.no_camera);
                        }
                    } else {
                        showTost(R.string.not_all_data);
                    }
                    break;
                }
                case R.id.file_button: {
                    if (mModel.getSelectedItem().toString() != "-") {
                        Intent intentBrowseFiles = new Intent();
                        intentBrowseFiles.setType("image/*");
                        intentBrowseFiles.setAction(Intent.ACTION_GET_CONTENT);
                        intentBrowseFiles.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        startActivityForResult(Intent.createChooser(intentBrowseFiles, "Select"), 2);
                    } else {
                        showTost(R.string.not_all_data);
                    }
                    break;
                }
                case R.id.save_button: {
                    if (mModel.getSelectedItem().toString() != "-") {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        showProgressBar();
                                    }
                                });
                                save();
                            }
                        }).start();
                    } else {
                        showTost(R.string.not_all_data);
                    }
                    break;
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_cars_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        id = "-";
        mButton = (Button)view.findViewById(R.id.save_button);
        mProgres = (ProgressBar)view.findViewById(R.id.add_cars_progres);
        mCameraButton = (ImageButton)view.findViewById(R.id.camera_button);
        mFileButton = (ImageButton)view.findViewById(R.id.file_button);
        mVin = (AppCompatEditText)view.findViewById(R.id.vin);
        mPrice = (AppCompatEditText)view.findViewById(R.id.price);
        mMileage = (AppCompatEditText)view.findViewById(R.id.mileage);
        mBrand = (Spinner)view.findViewById(R.id.brand);
        mModel = (Spinner)view.findViewById(R.id.model);
        mEquipment = (Spinner)view.findViewById(R.id.equipment);
        mBody = (Spinner)view.findViewById(R.id.body);
        mGeneration = (Spinner)view.findViewById(R.id.generation);
        mEngine = (Spinner)view.findViewById(R.id.engine);
        mDrive = (Spinner)view.findViewById(R.id.drive);
        mCity = (Spinner)view.findViewById(R.id.city);
        mOwnerss = (Spinner)view.findViewById(R.id.ownerss);
        mEngineCapacity = (Spinner)view.findViewById(R.id.engine_capacity);
        mRudder = (Spinner)view.findViewById(R.id.rudder);
        mCondition = (Spinner)view.findViewById(R.id.condition);
        mYear = (Spinner)view.findViewById(R.id.year);
        mColor = (Spinner)view.findViewById(R.id.color);
        mNumberOfDoors = (Spinner)view.findViewById(R.id.number_of_doors);
        mImage1 = (ImageView) view.findViewById(R.id.image1);
        mImage2 = (ImageView) view.findViewById(R.id.image2);
        mImage3 = (ImageView) view.findViewById(R.id.image3);
        mImage4 = (ImageView) view.findViewById(R.id.image4);
        mImage5 = (ImageView) view.findViewById(R.id.image5);
        mImage6 = (ImageView) view.findViewById(R.id.image6);
        mImage7 = (ImageView) view.findViewById(R.id.image7);
        mImage8 = (ImageView) view.findViewById(R.id.image8);
        mImage9 = (ImageView) view.findViewById(R.id.image9);
        mImage10 = (ImageView) view.findViewById(R.id.image10);
        mImage11 = (ImageView) view.findViewById(R.id.image11);
        mImage12 = (ImageView) view.findViewById(R.id.image12);
        mFileButton.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        mCameraButton.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        getBrand();
        getBodyType();
        getCondition();
        getDrive();
        getRudder();
        getEngineType();
        getEquipment();
        getYearOfSsue();
        getCity();
        getColor();
        getOwnerss();
        getEngineCapacity();
        getNumberOfDoors();
        mPrice.setMaxLines(16);
        mPrice.addTextChangedListener(new VinTextWatcher(mPrice));
        mMileage.addTextChangedListener(new VinTextWatcher(mMileage));
        mBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                getModel(brands.get(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        mButton.setOnClickListener(mClickListener);
        mCameraButton.setOnClickListener(mClickListener);
        mFileButton.setOnClickListener(mClickListener);
    }

    private void getGeneration(String model) {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getGeneration(model);
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mGeneration.setAdapter(adapter);
                    mGeneration.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private void getBrand() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getBrand();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    brands = getArray(response.body());
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, brands);
                    mBrand.setAdapter(adapter);
                    mBrand.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private void getModel(String brand) {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getModel(brand);
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mModel.setAdapter(adapter);
                    mModel.setSelection(0, false);
                    models = getArray(response.body());
                    mModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                            getGeneration(models.get(pos));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private void getBodyType() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getBodyType();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mBody.setAdapter(adapter);
                    mBody.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private void getCondition() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getCondition();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mCondition.setAdapter(adapter);
                    mCondition.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private void getDrive() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getDrive();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mDrive.setAdapter(adapter);
                    mDrive.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private void getRudder() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getRudder();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mRudder.setAdapter(adapter);
                    mRudder.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {}

        });
    }

    private void getEngineType() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getEngineType();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mEngine.setAdapter(adapter);
                    mEngine.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) { }

        });
    }

    private void getYearOfSsue() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getYearOfSsue();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mYear.setAdapter(adapter);
                    mYear.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) { }

        });
    }

    private void getCity() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getCity();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mCity.setAdapter(adapter);
                    mCity.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) { }

        });
    }

    private void getColor() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getColor();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mColor.setAdapter(adapter);
                    mColor.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) { }

        });
    }

    private void getOwnerss() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getOwnerss();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mOwnerss.setAdapter(adapter);
                    mOwnerss.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) { }

        });
    }

    private void getEngineCapacity() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getEngineCapacity();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mEngineCapacity.setAdapter(adapter);
                    mEngineCapacity.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) { }

        });
    }

    private void getNumberOfDoors() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getNumberOfDoors();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mNumberOfDoors.setAdapter(adapter);
                    mNumberOfDoors.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) { }

        });
    }

    private void getEquipment() {
        Call<ArrayList<Model>> call = ClientFactory.makeClient().getEquipment();
        call.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                if (response.code() == 200) {
                    ArrayAdapter<String> adapter = new ArrayAdapter(context, R.layout.spinner_dropdown_item, getArray(response.body()));
                    mEquipment.setAdapter(adapter);
                    mEquipment.setSelection(0, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) { }

        });
    }

    private ArrayList<String> getArray(ArrayList<Model> orig) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-");
        for (Model item : orig) {
            arrayList.add(item.fields.name);
        }
        return arrayList;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Bitmap bmap = null;
        if (requestCode == 2) {
            InputStream fileStream = getFile(intent.getData());
            if (fileStream != null) {
                bmap = BitmapFactory.decodeStream(fileStream);
            } else {
                Toast.makeText(context, R.string.error_file, Toast.LENGTH_SHORT).show();
            }
        } else {
            directory = new File(directory.getPath() + "/" + "photo_temp.jpg");
            bmap = BitmapFactory.decodeFile(directory.getAbsolutePath());
        }
        saveImages(bmap);
    }

    private void saveImages(Bitmap bmap) {
        showProgressBar();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "image.png");
        try {
            FileOutputStream fOut1 = new FileOutputStream(file);
            if (bmap != null) bmap.compress(Bitmap.CompressFormat.PNG, 85, fOut1);
            fOut1.flush();
        }
        catch (IOException e) {}
        RequestBody sidRequest = RequestBody.create(MediaType.parse("text/plain"), AuthHelper.getSid());
        RequestBody mBrandRequest = RequestBody.create(MediaType.parse("text/plain"), mBrand.getSelectedItem().toString());
        RequestBody mModelRequest = RequestBody.create(MediaType.parse("text/plain"), mModel.getSelectedItem().toString());
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody idRequest = RequestBody.create(MediaType.parse("text/plain"), id);
        Call<Save> call = ClientFactory.makeClient().saveImages(sidRequest, mBrandRequest, mModelRequest, idRequest, fbody);
        call.enqueue(new Callback<Save>() {
            @Override
            public void onResponse(Call<Save> call, Response<Save> response) {
                if (response.code() == 200) {
                    id = response.body().state;
                    hideProgressBar();
                    setLoadImage(bmap);
                } else {
                    hideProgressBar();
                    showTost(R.string.web_error);
                }
            }

            @Override
            public void onFailure(Call<Save> call, Throwable t) {
                showTost(R.string.net_error);
            }
        });

    }

    public void save() {
        RequestBody mGenerationRequest = RequestBody.create(MediaType.parse("text/plain"), "-");
        if (mGeneration.getSelectedItem() != null) {
            mGenerationRequest = RequestBody.create(MediaType.parse("text/plain"), mGeneration.getSelectedItem().toString());
        }
        RequestBody mEquipmentRequest = RequestBody.create(MediaType.parse("text/plain"), mEquipment.getSelectedItem().toString());
        RequestBody mRudderRequest = RequestBody.create(MediaType.parse("text/plain"), mRudder.getSelectedItem().toString());
        RequestBody mBodyRequest = RequestBody.create(MediaType.parse("text/plain"), mBody.getSelectedItem().toString());
        RequestBody mConditionRequest = RequestBody.create(MediaType.parse("text/plain"), mCondition.getSelectedItem().toString());
        RequestBody mEngineRequest = RequestBody.create(MediaType.parse("text/plain"), mEngine.getSelectedItem().toString());
        RequestBody mDriveRequest = RequestBody.create(MediaType.parse("text/plain"), mDrive.getSelectedItem().toString());
        RequestBody mYearRequest = RequestBody.create(MediaType.parse("text/plain"), mYear.getSelectedItem().toString());
        RequestBody mVinRequest = RequestBody.create(MediaType.parse("text/plain"), mVin.getText().toString());
        RequestBody mPriceRequest = RequestBody.create(MediaType.parse("text/plain"), mPrice.getText().toString());
        RequestBody mMileageRequest = RequestBody.create(MediaType.parse("text/plain"), mMileage.getText().toString());
        RequestBody mOwnersByTCPRequest = RequestBody.create(MediaType.parse("text/plain"),  mOwnerss.getSelectedItem().toString());
        RequestBody mEngineCapacityRequest = RequestBody.create(MediaType.parse("text/plain"), mEngineCapacity.getSelectedItem().toString());
        RequestBody mColorRequest = RequestBody.create(MediaType.parse("text/plain"), mColor.getSelectedItem().toString());
        RequestBody mNumberOfDoorsRequest = RequestBody.create(MediaType.parse("text/plain"), mNumberOfDoors.getSelectedItem().toString());
        RequestBody mCityRequest = RequestBody.create(MediaType.parse("text/plain"), mCity.getSelectedItem().toString());
        RequestBody idRequest = RequestBody.create(MediaType.parse("text/plain"), id);
        Call<Save> call = ClientFactory.makeClient().save(
                mEquipmentRequest,
                mRudderRequest,
                mBodyRequest,
                mConditionRequest,
                mEngineRequest,
                mDriveRequest,
                mYearRequest,
                mVinRequest,
                mPriceRequest,
                mMileageRequest,
                mOwnersByTCPRequest,
                mEngineCapacityRequest,
                mColorRequest,
                mNumberOfDoorsRequest,
                mCityRequest,
                idRequest,
                mGenerationRequest);

        call.enqueue(new Callback<Save>() {
            @Override
            public void onResponse(Call<Save> call, Response<Save> response) {
                if (response.code() == 200) {
                    DataHelper.update();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.root_frame, new MyCarsFragment());
                    ft.commit();
                    Toast.makeText(context, response.body().state, Toast.LENGTH_SHORT).show();
                }  else {
                    showTost(R.string.web_error);
                }
            }

            @Override
            public void onFailure(Call<Save> call, Throwable t) {
                showTost(R.string.net_error);
            }

        });
    }

    private Uri generateFileUri() {
        File file = new File(directory.getPath() + "/" + "photo_temp.jpg");
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "bc");
        if (!directory.exists()) directory.mkdirs();
    }

    private void showTost(int r) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                hideProgressBar();
                Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private InputStream getFile(Uri uri) {
        try {
            return context.getContentResolver().openInputStream(uri);
        }
        catch (IOException e) {
            return null;
        }
    }

    private void showProgressBar() {
        mCameraButton.setVisibility(Button.GONE);
        mFileButton.setVisibility(Button.GONE);
        mButton.setVisibility(Button.GONE);
        mProgres.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideProgressBar() {
        mCameraButton.setVisibility(Button.VISIBLE);
        mFileButton.setVisibility(Button.VISIBLE);
        mButton.setVisibility(Button.VISIBLE);
        mProgres.setVisibility(ProgressBar.GONE);
    }

    private void setLoadImage(Bitmap bmap) {
        if (mImage1.getDrawable() == null) {
            mImage1.setImageBitmap(bmap);
        } else if (mImage2.getDrawable() == null) {
            mImage2.setImageBitmap(bmap);
        } else if (mImage3.getDrawable() == null) {
            mImage3.setImageBitmap(bmap);
        } else if (mImage4.getDrawable() == null) {
            mImage4.setImageBitmap(bmap);
        } else if (mImage5.getDrawable() == null) {
            mImage5.setImageBitmap(bmap);
        } else if (mImage6.getDrawable() == null) {
            mImage6.setImageBitmap(bmap);
        } else if (mImage7.getDrawable() == null) {
            mImage7.setImageBitmap(bmap);
        } else if (mImage8.getDrawable() == null) {
            mImage8.setImageBitmap(bmap);
        } else if (mImage9.getDrawable() == null) {
            mImage9.setImageBitmap(bmap);
        } else if (mImage10.getDrawable() == null) {
            mImage10.setImageBitmap(bmap);
        } else if (mImage11.getDrawable() == null) {
            mImage11.setImageBitmap(bmap);
        } else if (mImage12.getDrawable() == null) {
            mImage12.setImageBitmap(bmap);
        }
    }
}