package callinterface.android.com.callinterface;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private final SharedPreferences preferences;
    private Context context;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveUrl(String url) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.URL_PREFS_KEY, url);
        editor.apply();
    }

    public String getUrl(){
        return preferences.getString(Constants.URL_PREFS_KEY,"");
    }

}
