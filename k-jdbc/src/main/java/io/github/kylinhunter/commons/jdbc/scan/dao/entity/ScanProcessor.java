package io.github.kylinhunter.commons.jdbc.scan.dao.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-03 19:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanProcessor implements Serializable {

  private String id;
  private String dataId;
  private int op;
  private int status;
}