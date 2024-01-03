package io.github.kylinhunter.commons.utils.json;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-01 23:55
 */
public class JsonToolBuilder {

  private final JsonOption jsonOption = new JsonOption();

  public JsonToolBuilder throwIfFailed(boolean throwIfFailed) {
    this.jsonOption.setThrowIfFailed(throwIfFailed);
    return this;
  }

  public JsonToolBuilder snake(boolean snake) {
    this.jsonOption.setSnake(snake);
    return this;
  }

  public JsonToolBuilder autoType(boolean autoType) {
    this.jsonOption.setAutoType(autoType);
    return this;
  }

  public JsonToolBuilder pretty(boolean pretty) {
    this.jsonOption.setPretty(pretty);
    return this;
  }

  public JsonTool build() {
    return new JsonTool(this.jsonOption);
  }
}