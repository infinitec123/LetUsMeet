package infiniteloop.letusmeet.rules;

public class NotificationModel {
  Category category;

  NotificationLevel level;

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public NotificationLevel getLevel() {
    return level;
  }

  public void setLevel(NotificationLevel level) {
    this.level = level;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NotificationModel)) {
      return false;
    }

    NotificationModel that = (NotificationModel) o;

    if (category != that.category) {
      return false;
    }
    if (level != that.level) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = category != null ? category.hashCode() : 0;
    result = 31 * result + (level != null ? level.hashCode() : 0);
    return result;
  }
}
