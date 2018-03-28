package com.example.dell.meetingapplication;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView imageView;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_home, container, false);
        View contentView = View.inflate(getContext(), R.layout.dialog_choose, null);
        creatDialog(mainView,contentView);

        // Inflate the layout for this fragment
        return mainView;
    }
    public final int CODE_SELECT_IMAGE = 2;//相册RequestCode
    public final int TYPE_TAKE_PHOTO = 1;//Uri获取类型判断
    public final int CODE_TAKE_PHOTO = 1;//相机RequestCode


    //封装的一层函数，显示dialog，供选择拍照还是相册
    public void creatDialog(View mainView,View contentView){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_take_photos:
//                        openCamera();
                        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri photoUri = getMediaFileUri(TYPE_TAKE_PHOTO);
                        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(takeIntent, CODE_TAKE_PHOTO);
                        break;
                    case R.id.btn_photo_album:
//                        openPicture();
                        Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(albumIntent, CODE_SELECT_IMAGE);
                        break;
                    case R.id.btn_cancel:
                        bottomSheetDialog.dismiss();
                        break;
                    case R.id.btn_all:
                        bottomSheetDialog.show();
                        break;
                }
            }
        };


        bottomSheetDialog.setContentView(contentView);
        View parent = (View) contentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        contentView.measure(0, 0);
        behavior.setPeekHeight(contentView.getMeasuredHeight());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        parent.setLayoutParams(params);

        mainView.findViewById(R.id.btn_all).setOnClickListener(clickListener);
        contentView.findViewById(R.id.btn_take_photos).setOnClickListener(clickListener);
        contentView.findViewById(R.id.btn_photo_album).setOnClickListener(clickListener);
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(clickListener);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectPic(data);
                }
                break;
            case CODE_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if (data.hasExtra("data")) {
                            Intent starter =new Intent(getContext(), PhotoActivity.class);
                            starter.putExtra("data", data.getParcelableExtra("data"));
                            getContext().startActivity(starter);

                            Log.i("URI", "data is not null");
                            Bitmap bitmap = data.getParcelableExtra("data");
                            imageView.setImageBitmap(bitmap);//imageView即为当前页面需要展示照片的控件，可替换
                        }
                    }
                }
                break;
        }
    }
    public Uri getMediaFileUri(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "相册名字");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return Uri.fromFile(mediaFile);
    }
    private void selectPic(Intent intent) {
        Intent starter =new Intent(getContext(), PhotoActivity.class);
        starter.setData(intent.getData());
        getContext().startActivity(starter);

        Uri selectImageUri = intent.getData();
//        Log.i("TAG0", "123");
//        Log.i("TAG", selectImageUri.toString());
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }

}
