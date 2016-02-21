package infiniteloop.letusmeet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import infiniteloop.letusmeet.R;

public class BlockedNotificationsActivity extends AppCompatActivity {

    private static final String TAG = "BlockedNotificationsActivity";

    @Bind(R.id.id_text)
    TextView mBlockedNotifications;

    CacheUtils mCacheUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_notifications);
        ButterKnife.bind(this);

        mCacheUtils = new CacheUtils(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> ns = mCacheUtils.getBlockedNotifications();
        StringBuilder builder = new StringBuilder();
        for (String s : ns) {
            builder.append(s + "\n");
        }

        mBlockedNotifications.setText(builder.toString());
    }

}
