package orangeit.com.boundservicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SimpleService mService;
    boolean mBound = false;
    TextView mTextView;
    Button mBindService;
    Button mUnBindService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.city_temprature);
        mBindService = (Button) findViewById(R.id.bound_service_button);
        mUnBindService = (Button) findViewById(R.id.unbound_service_button);
        mBindService.setOnClickListener(this);
        mUnBindService.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bound_service_button:
                Intent intent = new Intent(this, SimpleService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbound_service_button:
                if (mBound) {
                    unbindService(mConnection);
                    mBound = false;
                }
                break;
        }

    }


    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to SimpleService, cast the IBinder and get SimpleService instance
            SimpleService.LocalBinder binder = (SimpleService.LocalBinder) service;
            mService = binder.getService();
            mTextView.setText("" + mService.getTemparature());
            mBound = true;
            Toast.makeText( getApplicationContext(), "onServiceConnected Method service bound", Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
            mBound = false;
            Toast.makeText( getApplicationContext(), "onServiceDisConnected Method service unbound", Toast.LENGTH_LONG ).show();

        }
    };
}
