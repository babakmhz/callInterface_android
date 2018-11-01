package callinterface.android.com.callinterface;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static callinterface.android.com.callinterface.Constants.PHONE_STATE_REQUEST_CODE;
import static callinterface.android.com.callinterface.Constants.PORT_ACTIVITY_REQUEST_CODE;
import static callinterface.android.com.callinterface.Constants.READ_CONTACTS_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";



    String selected_ip = "";
    String selected_port = "";

    @BindView(R.id.text_url)
    TextView textUrl;
    // TODO: 10/31/18 use alarm manager and work manger later to check status of calls & phones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Prefs prefs = new Prefs(this);
        if (prefs.getUrl()!=null)
            textUrl.setText(prefs.getUrl());
        checkCallPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    registerReceiver(new CallBroadcastReceiver(), new IntentFilter("android.intent.action.PHONE_STATE"));
                }
        }
    }



    public ArrayList<String> getClientList() {
        ArrayList<String> clientList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] clientInfo = line.split(" +");
                String mac = clientInfo[3];
                if (mac.matches("..:..:..:..:..:..")) { // To make sure its not the title
                    clientList.add(clientInfo[0]);
                }
            }
        } catch (java.io.IOException aE) {
            aE.printStackTrace();
            return null;
        }
        return clientList;
    }

    private void checkCallPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                ) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE_REQUEST_CODE);

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_REQUEST_CODE);

        }
    }

    public void setSelectedIp(String selected_ip) {
        this.selected_ip = selected_ip;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PORT_ACTIVITY_REQUEST_CODE) {
            try{
                selected_port = data.getStringExtra(Constants.PORT_INTENT_RESULT_ACTION);
                Toast.makeText(this, selected_ip + ":" + data.getStringExtra(Constants.PORT_INTENT_RESULT_ACTION), Toast.LENGTH_LONG).show();

            }catch (NullPointerException e){

            }
        }
    }

    @OnClick(R.id.bt_done)
    public void bt_done_click(){
        Prefs prefs = new Prefs(this);
        prefs.saveUrl(textUrl.getText().toString());
        Toast.makeText(this, "ready to connect\n"+textUrl.getText().toString(),Toast.LENGTH_LONG).show();

    }

}
