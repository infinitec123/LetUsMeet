package infiniteloop.letusmeet.rules;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotificationLevelRules {

  public static final List<Rule> LEVEL_RULES = new ArrayList<Rule>();

  public static void load(String string) {
    Type type = new TypeToken<List<Rule>>(){}.getType();
    Gson gson = new GsonBuilder().create();
    List<Rule> rules = gson.fromJson(string, type);
    for (Rule rule : rules) {
      LEVEL_RULES.add(rule);
    }
  }

  public static class Rule {
    String subString;
    String regex;
    NotificationLevel level;

    public String getSubString() {
      return subString;
    }

    public void setSubString(String subString) {
      this.subString = subString;
    }

    public String getRegex() {
      return regex;
    }

    public void setRegex(String regex) {
      this.regex = regex;
    }

    public NotificationLevel getLevel() {
      return level;
    }

    public void setLevel(NotificationLevel level) {
      this.level = level;
    }

    public NotificationLevel test(String message) {
      Log.d("Con","sub-"+subString+"--msg="+message+"--lvl="+level);
      if (subString != null && message.toLowerCase().contains(subString)) {
        return level;
      }
      else if (regex != null && message.matches(regex)) {
        return level;
      }
      return null;
    }
  }

}
