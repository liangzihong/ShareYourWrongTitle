package Presenters;

import java.util.List;

import Adapters.TitleInfo;

/**
 * Created by Liang Zihong on 2018/12/21.
 */

public interface ILoadTitleInfoPresenter {
    public void loadTitleInfo();

    public List<TitleInfo> getTitleInfoList();

    public void loadMore();
}
