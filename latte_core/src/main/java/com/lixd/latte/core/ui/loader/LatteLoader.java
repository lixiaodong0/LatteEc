package com.lixd.latte.core.ui.loader;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.WindowManager;

import com.lixd.latte.core.R;
import com.lixd.latte.core.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class LatteLoader {
    //dialog的大小
    private static final int LOADER_SIZE_RATE = 6;

    private static final ArrayList<Dialog> DIALOGS = new ArrayList<>();

    public static final void showLoading(Context context) {
        showLoading(context, LoaderStyle.LineSpinFadeLoaderIndicator);
    }

    public static final void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    public static final void showLoading(Context context, String type) {
        AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(context, type);

        AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        dialog.setContentView(avLoadingIndicatorView);

        if (dialog != null) {
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = DimenUtil.getScreenWidth() / LOADER_SIZE_RATE;
            lp.height = DimenUtil.getScreenHeight() / LOADER_SIZE_RATE;
            lp.gravity = Gravity.CENTER;
            dialog.getWindow().setAttributes(lp);
        }
        dialog.show();

        DIALOGS.add(dialog);
    }

    public static final void hideLoading() {
        for (int i = 0; i < DIALOGS.size(); i++) {
            Dialog dialog = DIALOGS.get(i);
            if (dialog != null && dialog.isShowing()) {
                dialog.cancel();
                DIALOGS.remove(i);
                i--;
            }
        }
    }
}
