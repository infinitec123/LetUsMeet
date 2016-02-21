package infiniteloop.letusmeet.rules;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionService {

    private Context mContext;
    private static DecisionService decisionService = null;

    private DecisionService(Context context) {
        this.mContext = context;
    }

    public static DecisionService getInstance(Context context) {
        if (decisionService == null) {
            decisionService = new DecisionService(context);
        }
        return decisionService;
    }

    public void init() {
        String categoriesString = loadFile("appCategories.json");
        AppCategoryRules.load(categoriesString);

        String typeString = loadFile("typeRegex.json");

        NotificationLevelRules.load(typeString);

        loadRules();
    }

    private void loadRules() {
        List<DecisionControlModel.ModelRule> rules = getRules();
        Map<NotificationModel, Boolean> modelRules = new HashMap<NotificationModel, Boolean>();
        for (DecisionControlModel.ModelRule rule : rules) {
            modelRules.put(rule.getModel(), rule.getIsValid());
        }

        DecisionControlModel decisionControlModel = DecisionControlModel.getInstance();
        decisionControlModel.setModelRules(modelRules);

    }

    /**
     * Helper function to load file from assets
     */
    private String loadFile(String fileName) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = mContext.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null) input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    public List<DecisionControlModel.ModelRule> getRules() {

        String typeString = loadFile("defaults.json");

        Type type = new TypeToken<List<DecisionControlModel.ModelRule>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();
        List<DecisionControlModel.ModelRule> rules = gson.fromJson(typeString, type);
        return rules;
    }


    public boolean getModel(String appPackageName, String notificationMessage,
                            boolean isBigImage) {
        Category category = getCategoryForApp(appPackageName);
        NotificationLevel level = getLevel(notificationMessage, isBigImage);
        NotificationModel model = new NotificationModel();
        model.setCategory(category);
        model.setLevel(level);

        return DecisionControlModel.getInstance().decide(model);
    }

    public Category getCategoryForApp(String appPackageName) {
        Category category = AppCategoryRules.APP_CATEGORY_MAP.get(appPackageName);
        if (category == null) {
            return Category.SHOPPING;
        }
        return category;
    }

    public NotificationLevel getLevel(String message, boolean isBigImage) {
        List<NotificationLevelRules.Rule> levelRules = NotificationLevelRules.LEVEL_RULES;
        for (NotificationLevelRules.Rule rule : levelRules) {
            NotificationLevel level = rule.test(message);
            if (level != null) {
                return level;
            }
        }
        return NotificationLevel.ACTIONABLE;
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}
