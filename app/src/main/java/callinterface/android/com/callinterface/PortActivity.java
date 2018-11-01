package callinterface.android.com.callinterface;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static callinterface.android.com.callinterface.Constants.PORT_ACTIVITY_REQUEST_CODE;
import static callinterface.android.com.callinterface.Constants.PORT_INTENT_RESULT_ACTION;

public class PortActivity extends AppCompatActivity {

    @BindView(R.id.text_port)
    EditText text_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.bt_done)
    public void onButtonDoneClicked(){
        if (text_port!=null && !text_port.equals("")){
            Intent result = new Intent();
            result.putExtra(PORT_INTENT_RESULT_ACTION,text_port.getText().toString());
            setResult(PORT_ACTIVITY_REQUEST_CODE,result);
            finish();
        }
    }
}


