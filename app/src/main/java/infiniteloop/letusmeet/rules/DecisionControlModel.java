package infiniteloop.letusmeet.rules;

import java.util.Map;

public class DecisionControlModel {

    Boolean timeBased;
    Boolean locationBased;

    Map<NotificationModel, Boolean> modelRules;

    private static DecisionControlModel decisionControlModel = null;

    private DecisionControlModel() {

    }

    public static DecisionControlModel getInstance() {
        if (decisionControlModel == null) {
            decisionControlModel = new DecisionControlModel();
        }
        return decisionControlModel;
    }

    public Map<NotificationModel, Boolean> getModelRules() {
        return modelRules;
    }

    public void setModelRules(Map<NotificationModel, Boolean> modelRules) {
        this.modelRules = modelRules;
    }

    public static class ModelRule {
        NotificationModel model;
        Boolean isValid;

        public NotificationModel getModel() {
            return model;
        }

        public void setModel(NotificationModel model) {
            this.model = model;
        }

        public Boolean getIsValid() {
            return isValid;
        }

        public void setIsValid(Boolean isValid) {
            this.isValid = isValid;
        }
    }

    public Boolean getTimeBased() {
        return timeBased;
    }

    public void setTimeBased(Boolean timeBased) {
        this.timeBased = timeBased;
    }

    public Boolean getLocationBased() {
        return locationBased;
    }

    public void setLocationBased(Boolean locationBased) {
        this.locationBased = locationBased;
    }


    public boolean decide(NotificationModel model) {
        return false;
    }
}
