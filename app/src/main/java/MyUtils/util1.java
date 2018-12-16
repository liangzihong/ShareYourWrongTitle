package MyUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import BmobModels.BProfilePhoto;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Liang Zihong on 2018/12/15.
 */

public class util1 {

    public static File drawableToFile(Context mContext, int drawableId, String fileName){

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), drawableId);
        String defaultPath = mContext.getFilesDir()
                .getAbsolutePath() + "/imgs";
        File file = new File(defaultPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String defaultImgPath = defaultPath + "/"+fileName;
        file = new File(defaultImgPath);
        try {
            if (file.exists())
                return file;
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 20, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }



}
