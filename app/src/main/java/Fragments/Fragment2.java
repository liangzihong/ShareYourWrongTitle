package Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.liangzihong.viewpager.R;

import java.io.File;

import static Fragments.Fragment3.TAKE_PHOTO;

/**
 * Created by Liang Zihong on 2018/2/8.
 */

public class Fragment2 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view2,container,false);
    }


}
