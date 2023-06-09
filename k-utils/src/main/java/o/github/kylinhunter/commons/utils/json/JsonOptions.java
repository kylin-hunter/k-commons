package o.github.kylinhunter.commons.utils.json;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 16:39
 */
public class JsonOptions {

  public static final JsonOption FAIL_NO_SNAKE = new JsonOption(true, false);
  public static final JsonOption FAIL_SNAKE = new JsonOption(true, true);
  public static final JsonOption NO_FAIL_NO_SNAKE = new JsonOption(false, false);
  public static final JsonOption NO_FAIL_SNAKE = new JsonOption(false, true);
  public static final JsonOption DEFAULT = FAIL_NO_SNAKE;
  public static final JsonOption NO_FAIL = NO_FAIL_NO_SNAKE;
}
