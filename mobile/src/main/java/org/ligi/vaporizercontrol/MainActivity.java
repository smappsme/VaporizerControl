package org.ligi.vaporizercontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity implements VaporizerData.VaporizerUpdateListener {

    @InjectView(R.id.intro_text)
    TextView introText;

    @InjectView(R.id.battery)
    TextView battery;

    @InjectView(R.id.temperature)
    TextView temperature;

    @InjectView(R.id.temperatureSetPoint)
    TextView temperatureSetPoint;

    @InjectView(R.id.tempBoost)
    TextView tempBoost;

    @InjectView(R.id.led)
    TextView led;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    protected void onPause() {
        getApp().getVaporizerCommunicator().destroy();
        super.onPause();
    }

    @Override
    protected void onResume() {
        getApp().getVaporizerCommunicator().connectAndRegisterForUpdates(this);
        onUpdate(getApp().getVaporizerCommunicator().getData());
        super.onResume();
    }

    private App getApp() {
        return (App) getApplication();
    }

    @Override
    public void onUpdate(final VaporizerData data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                introText.setText(Html.fromHtml(getString(R.string.intro_text)));
                introText.setMovementMethod(new LinkMovementMethod());

                battery.setText((data.batteryPercentage == null ? "?" : "" + data.batteryPercentage) + "%");
                temperature.setText((data.currentTemperature == null ? "?" : "" + data.currentTemperature / 10f) + "° / ");
                temperatureSetPoint.setText((data.setTemperature == null ? "?" : "" + data.setTemperature / 10f) + "°");
                tempBoost.setText((data.boostTemperature == null ? "?" : "+" + data.boostTemperature / 10f) + "°");
                led.setText((data.ledPercentage == null ? "?" : "" + data.ledPercentage) + "%");
            }
        });
    }
}
