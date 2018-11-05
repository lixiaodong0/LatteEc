package com.lixd.latte.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.lixd.latte.core.app.Latte;
import com.lixd.latte.core.net.callback.IRequest;
import com.lixd.latte.core.net.callback.ISuccess;
import com.lixd.latte.core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object, Void, File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... objects) {
        String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        String name = (String) objects[2];
        final ResponseBody responseBody = (ResponseBody) objects[3];
        InputStream is = responseBody.byteStream();
        if (TextUtils.isEmpty(downloadDir)) {
            downloadDir = "download_dir";
        }
        if (TextUtils.isEmpty(extension)) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (file != null) {
            if (SUCCESS != null) {
                SUCCESS.onSuccess(file.getAbsolutePath());
            }
            if (REQUEST != null) {
                REQUEST.onRequestEnd();
            }
            installApp(file);
        }
    }

    private final void installApp(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
