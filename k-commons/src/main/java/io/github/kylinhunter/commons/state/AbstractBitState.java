package io.github.kylinhunter.commons.state;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import java.util.StringJoiner;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022/12/8
 */
@Data
public abstract class AbstractBitState<E extends Enum<E>> {

  private long[] bitStates;

  public AbstractBitState() {

    Class<? extends Enum<?>> genericType =
        GenericTypeUtils.getActualTypeArgument(this.getClass().getGenericSuperclass(), 0);
    if (genericType == null) {
      throw new InitException("genericType can't be null");
    }
    if (Enum.class.isAssignableFrom(genericType)) {
      Enum<?>[] enumConstants = genericType.getEnumConstants();
      if (enumConstants.length > 0) {
        bitStates = new long[(enumConstants.length - 1) / 64 + 1];
      } else {
        throw new InitException("enum size is empty");
      }

    } else {
      throw new InitException("genericType not a instance of Enum");
    }
  }

  /**
   * @param states states the enum states to set
   * @return void
   * @title set state
   * @description
   * @author BiJi'an
   * @date 2022-12-09 00:35
   */
  @SafeVarargs
  public final void setState(E... states) {

    for (E status : states) {
      int ordinal = status.ordinal();
      int remainder = ordinal % 64;
      bitStates[ordinal / 64] |= 1L << remainder;
    }
  }

  /**
   * @param states states the enum states to remove
   * @return void
   * @title removeState remove the enum states if existed
   * @description
   * @author BiJi'an
   * @date 2022-12-08 16:46
   */
  @SafeVarargs
  public final void removeState(E... states) {
    for (E status : states) {
      int ordinal = status.ordinal();
      int remainder = ordinal % 64;
      bitStates[ordinal / 64] &= ~(1L << remainder);
    }
  }

  /**
   * @param states states the enum states
   * @return boolean
   * @title hasStatus whether a enum states exists
   * @description
   * @author BiJi'an
   * @date 2022-12-08 16:46
   */
  @SafeVarargs
  public final boolean hasState(E... states) {
    for (E status : states) {
      int ordinal = status.ordinal();
      int remainder = ordinal % 64;
      if ((bitStates[ordinal / 64] & (1L << remainder)) != 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    StringJoiner joiner = new StringJoiner(", ", AbstractBitState.class.getSimpleName() + "[", "]");
    for (int i = 0; i < bitStates.length; i++) {
      joiner.add("bitStates[" + i + "]=" + Long.toBinaryString(bitStates[i]));
    }

    return joiner.toString();
  }
}
