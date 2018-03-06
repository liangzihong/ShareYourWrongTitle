package Presenters;

import android.content.Context;

import java.util.List;

import Adapters.Group;

/**
 * Created by Liang Zihong on 2018/3/6.
 */

public interface ILoadGroupPresenter {

    public void loadGroup( String[]GroupNames, int[]ProfilePhotos, String[]LastRecords);
    public void loadGroup();
    public List<Group> getGroupList();
}
