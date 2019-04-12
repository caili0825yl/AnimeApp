package playerstudio.project;

import android.content.Context;
import android.widget.Toast;

public class Mytoast {
    private static Toast toast;

    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            toast.setText(msg);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }


}
