/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.jdbc.monitor.manager.dao;

import io.github.kylinhunter.commons.jdbc.dao.BaseDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:50
 */
public interface ScanRecordDAO extends BaseDAO {

  List<ScanRecord> scanSameTime(ScanTable config, LocalDateTime startTime, String lastScanId);

  List<ScanRecord> scanNextTime(ScanTable scanTable, LocalDateTime startTime);
}
