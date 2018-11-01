package callinterface.android.com.callinterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static callinterface.android.com.callinterface.Constants.PORT_ACTIVITY_REQUEST_CODE;

// ipv4 class C supported

public class DevicesRecyclerAdapter extends RecyclerView.Adapter<DevicesRecyclerAdapter.ViewHolder> {

    private static final String TAG="DevicesRecyclerAdapter";
    private final Activity activity;
    private final List<String> ipList;



    public DevicesRecyclerAdapter(Activity activity, List<String> ipList){


        this.activity = activity;
        this.ipList = ipList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder( LayoutInflater.from(activity).inflate(R.layout.recycler_devices_template,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final String ip = ipList.get(i);
        viewHolder.text_ip.setText(ip);

        viewHolder.text_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) activity).setSelectedIp(ip);
                activity.startActivityForResult(new Intent(activity,PortActivity.class),PORT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ipList.size();
    }

    private int getDeviceIpAddress(@NonNull ArrayList<String> list){

        int min_index = 0;

        for (String ip :
                list) {
            int current = Integer.parseInt(ip.substring(ip.lastIndexOf(".")+1,ip.length()));
            Log.i(TAG, "getDeviceIpAddress_get_current_ip: " + String.valueOf(current));

            if (min_index==0){
                min_index = current;
            }

            if (min_index>current)
                min_index=current;

        }

        Log.i(TAG, "getDeviceIpAddress: " +String.valueOf(min_index));
        return min_index;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_ip)
        TextView text_ip;

        @BindView(R.id.text_tap_to_connect)
        TextView text_tap_to_connect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
