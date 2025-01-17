/*
 * Copyright 2013-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.kubernetes.fabric8.config;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class,
		properties = { "spring.main.cloud-platform=KUBERNETES", "spring.application.name=testapp",
				"spring.cloud.kubernetes.client.namespace=testns", "spring.cloud.kubernetes.client.trustCerts=true",
				"spring.cloud.kubernetes.config.namespace=testns", "spring.cloud.kubernetes.secrets.enableApi=true" })
public class CoreTestClientViaSystemProperties {

	@Autowired
	private KubernetesClient client;

	@BeforeAll
	public static void setUpBeforeClass() {
		System.setProperty(Config.KUBERNETES_MASTER_SYSTEM_PROPERTY, "masterURL");
	}

	@Test
	public void kubernetesClientBeanShouldBeConfigurableViaSystemProperties() {
		assertThat(client).isNotNull();
		assertThat(client.getConfiguration().getMasterUrl()).isEqualTo("http://masterURL/");
	}

}
