package com.example.whowroteit;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
public class BookLoader extends AsyncTaskLoader<String> {

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }
    public BookLoader(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    private String mQueryString;
    BookLoader(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }
}
