/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.core.analysis.worker;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.oap.server.core.analysis.management.ManagementData;
import org.apache.skywalking.oap.server.core.storage.IManagementDAO;
import org.apache.skywalking.oap.server.core.storage.model.Model;
import org.apache.skywalking.oap.server.core.worker.AbstractWorker;
import org.apache.skywalking.oap.server.library.module.ModuleDefineHolder;

@Slf4j
public class ManagementPersistentWorker extends AbstractWorker<ManagementData> {
    private final Model model;
    private final IManagementDAO configDAO;

    public ManagementPersistentWorker(ModuleDefineHolder moduleDefineHolder, Model model, IManagementDAO configDAO) {
        super(moduleDefineHolder);
        this.model = model;
        this.configDAO = configDAO;
    }

    @Override
    public void in(ManagementData managementData) {
        try {
            configDAO.insert(model, managementData);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
