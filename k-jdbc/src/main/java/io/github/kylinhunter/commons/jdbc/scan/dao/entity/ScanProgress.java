package io.github.kylinhunter.commons.jdbc.scan.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class ScanProgress implements Serializable {

  private String id;
  private String saveDestination;
  private LocalDateTime nextScanTime;
  private String lastScanId;

  public ScanProgress(String tableName) {
    this.id = tableName;
  }
}