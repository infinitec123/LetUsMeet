package infiniteloop.letusmeet.rules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AppCategoryRules {

  public static final Map<String, Category> APP_CATEGORY_MAP = new HashMap<String, Category>();

  static {
    APP_CATEGORY_MAP.put("com.solvevolve.flubber", Category.GAMES);

  }

  public static void load(String string) {
    Type type = new TypeToken<Map<String, Category>>(){}.getType();
    Gson gson = new GsonBuilder().create();
    Map<String, Category> rules = gson.fromJson(string, type);
    for (Map.Entry<String, Category> entry: rules.entrySet()) {
      APP_CATEGORY_MAP.put(entry.getKey(), entry.getValue());
    }
  }
}
