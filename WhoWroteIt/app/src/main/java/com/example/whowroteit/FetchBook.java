package com.example.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void , String> {
    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String title = null;
            String authors = null;

            while (i < itemsArray.length() && (authors == null && title == null)) {
                // lay thong tin item.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                // thu lay tieu de va ten tac gia cua item hien tai,
                // bao loi neu rong va bo qua.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }

            if (title != null && authors != null) {
                mTitleText.get().setText(title);
                mAuthorText.get().setText(authors);
            }
            else {
                mTitleText.get().setText(R.string.no_results);
                mAuthorText.get().setText("");
            }
        }
        catch (Exception e){
            // neu onPostExecute khong nhan 1 chuoi json thich hop,
            // cap nhat Ui show that bai.
            mTitleText.get().setText(R.string.no_results);
            mAuthorText.get().setText("");
            e.printStackTrace();
        }
    }
    FetchBook(TextView tilleText , TextView authorText){
        this.mAuthorText = new WeakReference<>(authorText);
        this.mTitleText = new WeakReference<>(tilleText);
    }
}
