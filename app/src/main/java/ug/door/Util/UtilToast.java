package ug.door.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2017/8/29.
 */

public class UtilToast {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
