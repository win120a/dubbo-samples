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
 */

package org.apache.dubbo.samples;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.samples.api.DemoService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DemoServiceIT {
    private static String zookeeperHost1 = System.getProperty("zookeeper.address.1", "127.0.0.1");
    private static String zookeeperPort1 = System.getProperty("zookeeper.port.1", "2181");

    @BeforeClass
    public static void setUp() throws Exception {
        zookeeperHost1 = System.getProperty("zookeeper.address", zookeeperHost1);
        ZKTools2.setZookeeperServer1(zookeeperHost1, zookeeperPort1);
        ZKTools2.initClient();
        ZKTools2.generateDubboPropertiesForGlobal();
    }

    @Test
    public void test() throws Exception {
        ConfigCenterConfig configCenter = new ConfigCenterConfig();
        ApplicationConfig applicationConfig = new ApplicationConfig("api-dubbo-consumer");
        configCenter.setAddress("zookeeper://" + zookeeperHost1 + ":" + zookeeperPort1);
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.getApplicationModel().getApplicationConfigManager().setApplication(applicationConfig);
        reference.setConfigCenter(configCenter);
        reference.setInterface(DemoService.class);
        DemoService demoService = reference.get();
        Assert.assertEquals("Hello, you!", demoService.sayHello());
    }
}
