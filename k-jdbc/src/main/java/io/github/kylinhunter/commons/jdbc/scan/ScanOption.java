package io.github.kylinhunter.commons.jdbc.scan;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-08 21:22
 */

@Data
public class ScanOption {

  private String tableName;
  private LocalDateTime firstScanTime = LocalDateTime.now().minus(10, ChronoUnit.YEARS);

  private String saveDestination = "k_table_scan_processor";

  private String lastScanId = "-1";


  private long scanLimit = 1000;
}