package infiniteloop.letusmeet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.Calendar;

import infiniteloop.letusmeet.rules.Category;
import infiniteloop.letusmeet.rules.DecisionService;
import infiniteloop.letusmeet.rules.NotificationLevel;
import infiniteloop.letusmeet.rules.NotificationModel;

public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    DecisionService decisionService;

    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.kpbird.nlsexample.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver, filter);
        decisionService = DecisionService.getInstance(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG, "**********  onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event", "onNotificationPosted :" + sbn.getPackageName() + "\n");
        //sendBroadcast(i);


        /*if (sbn.getNotification().extras.get("android.text").toString().contains("shipped")) {
            // return true;

        } else {
            NLService.this.cancelAllNotifications();
        }*/

        if (sbn.getNotification().extras.get("android.text") == null) {
            return;
        }

        if (!isAllowed(sbn.getPackageName(), sbn.getNotification().extras.get("android.text").toString())) {
            //NLService.this.cancelAllNotifications();
            NLService.this.cancelNotification(sbn.getKey());
        }


        //sbn.getNotification().

        //NLService.this. .cancelAllNotifications();
        /*((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(sbn.getId());
        cancelNotification(sbn.getPackageName(), sbn.getTag(), sbn.getId());*/
        //cancelNotification(sbn.getKey());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "********** onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event", "onNotificationRemoved :" + sbn.getPackageName() + "\n");

        //sendBroadcast(i);
    }

    class NLServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("command").equals("clearall")) {
                NLService.this.cancelAllNotifications();
            } else if (intent.getStringExtra("command").equals("list")) {
                Intent i1 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event", "=====================");
                sendBroadcast(i1);
                int i = 1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event", i + " " + sbn.getPackageName() + "\n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event", "===== Notification List ====");
                sendBroadcast(i3);

            }

        }
    }

    private boolean isAllowed(String pkgName, String message) {
        CacheUtils cacheUtils = new CacheUtils(this);
//        boolean interestedInLocation = cacheUtils.isInterestedInLocation();
        boolean interestedInTime = cacheUtils.isInterestedInTime();
        boolean interestedInOffers = cacheUtils.isInterestedInOffers();

        NotificationModel model = decisionService.getModel(pkgName, message, false);
        if (interestedInOffers) {
            if (model.getCategory() == Category.SHOPPING && model.getLevel() == NotificationLevel.MARKETING) {
                String[] offersTypes = cacheUtils.getOffersPreferences().split(",");
                for (String offer : offersTypes) {
                    if (message.toLowerCase().contains(offer.toLowerCase()))
                        return true;
                }
            }
        }

        boolean topLevelDecision = decisionService.decide(model);
        if (topLevelDecision) {
            if (interestedInTime) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                boolean officeHours = hour > 9 && hour < 17;
                if (model.getLevel() == NotificationLevel.PERSONAL && !officeHours) {
                    return true;
                }
            }
            return true;
        }

        return false;
    }

}
