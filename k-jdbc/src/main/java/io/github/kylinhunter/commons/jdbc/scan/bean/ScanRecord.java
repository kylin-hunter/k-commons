package io.github.kylinhunter.commons.jdbc.scan.bean;

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
public class ScanRecord implements Serializable {

  private String id;
  private LocalDateTime time;


}