package infiniteloop.letusmeet.rules;

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


  public static void init () {
    String categoriesString = loadFile("/appCategories.json");
    AppCategoryRules.load(categoriesString);


    String typeString = loadFile("/typeRegex.json");
    NotificationLevelRules.load(typeString);

    loadRules();

  }

  private static DecisionControlModel loadRules() {
    List<DecisionControlModel.ModelRule> rules = getRules();
    Map<NotificationModel, Boolean> modelRules = new HashMap<NotificationModel, Boolean>();
    for(DecisionControlModel.ModelRule rule : rules) {
      modelRules.put(rule.getModel(), rule.getIsValid());
    }

    DecisionControlModel decisionControlModel = new DecisionControlModel();
    decisionControlModel.setModelRules(modelRules);

    return decisionControlModel;
  }

  public static String loadFile(String fileName) {
    InputStream regex = DecisionService.class.getResourceAsStream(fileName);
    String typeString = getStringFromInputStream(regex);
    return typeString;
  }

  public static List<DecisionControlModel.ModelRule> getRules () {

    String typeString = loadFile("/defaults.json");

    Type type = new TypeToken<List<DecisionControlModel.ModelRule>>(){}.getType();
    Gson gson = new GsonBuilder().create();
    List<DecisionControlModel.ModelRule> rules = gson.fromJson(typeString, type);
    return rules;
  }



  public boolean getModel(DecisionControlModel decisionControlModel, String appPackageName, String notificationMessage,
                                    boolean isBigImage) {
    Category category = getCategoryForApp(appPackageName);
    NotificationLevel level = getLevel(notificationMessage, isBigImage);
    NotificationModel model = new NotificationModel();
    model.setCategory(category);
    model.setLevel(level);

    return decisionControlModel.decide(model);
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
