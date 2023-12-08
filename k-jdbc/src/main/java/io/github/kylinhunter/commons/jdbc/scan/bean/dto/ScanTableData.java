package io.github.kylinhunter.commons.jdbc.scan.bean.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 23:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanTableData implements Serializable {

  private String tableName;
  private LocalDateTime nexScanTime;

}