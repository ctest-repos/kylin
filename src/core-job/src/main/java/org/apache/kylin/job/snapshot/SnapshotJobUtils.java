/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kylin.job.snapshot;

import static org.apache.kylin.job.execution.JobTypeEnum.SNAPSHOT_BUILD;
import static org.apache.kylin.job.execution.JobTypeEnum.SNAPSHOT_REFRESH;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.kylin.common.KylinConfig;
import org.apache.kylin.job.execution.AbstractExecutable;
import org.apache.kylin.job.execution.ExecutableState;
import org.apache.kylin.job.execution.NExecutableManager;
import org.apache.kylin.metadata.cube.model.NBatchConstants;
import org.apache.kylin.metadata.model.NTableMetadataManager;
import org.apache.kylin.metadata.model.TableDesc;

import lombok.val;

public final class SnapshotJobUtils {
    private SnapshotJobUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<TableDesc> getSnapshotTables(KylinConfig kylinConfig, String project) {
        val tableMetadataManager = NTableMetadataManager.getInstance(kylinConfig, project);
        val execManager = NExecutableManager.getInstance(kylinConfig, project);
        List<AbstractExecutable> executables = execManager.listExecByJobTypeAndStatus(ExecutableState::isRunning,
                SNAPSHOT_BUILD, SNAPSHOT_REFRESH);
        return tableMetadataManager.listAllTables().stream()
                .filter(tableDesc -> hasLoadedSnapshot(tableDesc, executables)).distinct().collect(Collectors.toList());
    }

    private static boolean hasLoadedSnapshot(TableDesc tableDesc, List<AbstractExecutable> executables) {
        return tableDesc.isSnapshotHasBroken() || StringUtils.isNotEmpty(tableDesc.getLastSnapshotPath())
                || hasRunningJob(tableDesc, executables);
    }

    private static boolean hasRunningJob(TableDesc tableDesc, List<AbstractExecutable> executables) {
        return executables.stream().map(executable -> executable.getParam(NBatchConstants.P_TABLE_NAME))
                .anyMatch(table -> StringUtils.equals(table, tableDesc.getIdentity()));
    }
}