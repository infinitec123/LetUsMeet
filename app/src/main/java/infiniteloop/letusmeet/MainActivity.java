package infiniteloop.letusmeet;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ListView mAppList;
    AppAdapter mAdapter;
    private ArrayList<App> mApps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppList = (ListView) findViewById(R.id.listview);
        setSupportActionBar(toolbar);
        listPackages();
    }


    private void listPackages() {
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            if (!isSystemPackage(packageInfo)) {

                String name = packageInfo.loadLabel(pm).toString();
                String packageName = packageInfo.packageName;
                Log.v(TAG, name + ":" + packageName);
                Drawable icon = packageInfo.loadIcon(pm);

                App app = new App(name, packageName, icon);
                mApps.add(app);
            }
        }

        mAdapter = new AppAdapter(this, mApps);
        mAppList.setAdapter(mAdapter);
    } // List of packagesz

    private boolean isSystemPackage(ApplicationInfo packageInfo) {
        return ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }


}
