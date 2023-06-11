package io.github.kylinhunter.commons.clazz.exception;

import io.github.kylinhunter.commons.exception.embed.GeneralException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 01:06
 */
public class AgentException extends GeneralException {

  public AgentException(String message) {
    super(message);
  }

  public AgentException(String message, Throwable cause) {
    super(message, cause);
  }
}
