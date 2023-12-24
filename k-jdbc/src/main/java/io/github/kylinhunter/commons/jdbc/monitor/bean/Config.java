package io.github.kylinhunter.commons.jdbc.monitor.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 21:06
 */
@Data
public class Config {

  private int threadPoolSize = 2; // thread pool size  for schedule

  private int execInterval = 3000; // thread pool size  for schedule

  private int retryInterval = 5000; // thread pool size  for schedule

  private int errorInterval = 10000; // thread pool size  for schedule


  private int execBefore = 3000; // thread pool size  for schedule

  private int retryBefore = 60 * 1000; // thread pool size  for schedule

}