package infiniteloop.letusmeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;
import infiniteloop.letusmeet.rules.DecisionControlModel;
import infiniteloop.letusmeet.rules.DecisionService;

public class ControlPanelActivity extends AppCompatActivity {

    private static final String TAG = "ControlPanelActivity";

    @OnClick(R.id.new_appvice_block)
    void controlAppVice() {
        Intent intent = new Intent(this, AppViceActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DecisionService.getInstance(this).init();
    }

}
