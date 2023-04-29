package io.github.kylinhunter.commons.component;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 14:07
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CObject {
  private int order;
  @EqualsAndHashCode.Include private Object object;

  private boolean primary;
  private CConstructor cconstructor;
  private CMethod cmethod;

  public CObject(boolean primary, int order, Object object) {
    this.primary = primary;
    if (primary) {
      this.order = Integer.MIN_VALUE;
    } else {
      this.order = order;
    }
    this.object = object;
  }

  public CObject(CConstructor cconstructor, Object object) {
    this(cconstructor.isPrimary(), cconstructor.getOrder(), object);
    this.cconstructor = cconstructor;
  }

  public CObject(CMethod cmethod, Object object) {
    this(cmethod.isPrimary(), cmethod.getOrder(), object);
    this.cmethod = cmethod;
  }
}
