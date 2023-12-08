package io.github.kylinhunter.commons.jdbc.scan.dao.entity;

import io.github.kylinhunter.commons.jdbc.scan.bean.ScanRecord;
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


  public ScanProgress(String id, LocalDateTime nextScanTime, String lastScanId) {
    this.id = id;
    this.nextScanTime = nextScanTime;
    this.lastScanId = lastScanId;
  }

  public ScanProgress(String id, ScanRecord last) {
    this.id = id;
    this.nextScanTime = last.getTime();
    this.lastScanId = last.getId();
  }
}