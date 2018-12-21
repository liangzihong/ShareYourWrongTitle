package Fragments;

import android.content.Context;
import android.widget.ArrayAdapter;

import Adapters.TitleInfo;

/**
 * Created by Liang Zihong on 2018/12/21.
 */

public interface ILoadTitleInfoFragment {
    public Context getContext();
    public void setTitleInfoAdapter(ArrayAdapter<TitleInfo> adapter);
}
