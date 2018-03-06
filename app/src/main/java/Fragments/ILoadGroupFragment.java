package Fragments;

import android.content.Context;
import android.widget.ArrayAdapter;

import Adapters.Group;

/**
 * Created by Liang Zihong on 2018/3/6.
 */

public interface ILoadGroupFragment {

    public Context getContext();
    public void setAdapter(ArrayAdapter<Group> adapter);

}
