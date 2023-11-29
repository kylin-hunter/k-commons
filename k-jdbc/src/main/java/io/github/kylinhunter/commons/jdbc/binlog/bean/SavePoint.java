package io.github.kylinhunter.commons.jdbc.binlog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavePoint {

  private String name;
  private long position;

}