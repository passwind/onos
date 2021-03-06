/*
 * Copyright 2018-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.openstacktelemetry.util;

import com.google.common.base.Strings;
import org.onlab.packet.IPv4;
import org.onosproject.cfg.ConfigProperty;
import org.onosproject.openstacktelemetry.api.TelemetryAdminService;
import org.onosproject.openstacktelemetry.api.config.TelemetryConfig;

import java.util.Dictionary;
import java.util.Optional;
import java.util.Set;

import static org.onlab.util.Tools.get;

/**
 * An utility that used in openstack telemetry app.
 */
public final class OpenstackTelemetryUtil {

    private static final String PROTOCOL_NAME_TCP = "tcp";
    private static final String PROTOCOL_NAME_UDP = "udp";
    private static final String PROTOCOL_NAME_ANY = "any";
    private static final int ARBITRARY_PROTOCOL = 0x0;

    /**
     * Prevents object instantiation from external.
     */
    private OpenstackTelemetryUtil() {
    }

    /**
     * Gets Boolean property from the propertyName
     * Return null if propertyName is not found.
     *
     * @param properties   properties to be looked up
     * @param propertyName the name of the property to look up
     * @return value when the propertyName is defined or return null
     */
    public static Boolean getBooleanProperty(Dictionary<?, ?> properties,
                                              String propertyName) {
        Boolean value;
        try {
            String s = get(properties, propertyName);
            value = Strings.isNullOrEmpty(s) ? null : Boolean.valueOf(s);
        } catch (ClassCastException e) {
            value = null;
        }
        return value;
    }

    /**
     * Obtains the property value with specified property key name.
     *
     * @param properties    a collection of properties
     * @param name          key name
     * @return mapping value
     */
    public static boolean getPropertyValueAsBoolean(Set<ConfigProperty> properties, String name) {
        Optional<ConfigProperty> property =
                properties.stream().filter(p -> p.name().equals(name)).findFirst();

        return property.map(ConfigProperty::asBoolean).orElse(false);
    }

    /**
     * Obtains transport protocol type from the given string.
     *
     * @param str transport protocol name
     * @return transport protocol type
     */
    public static byte getProtocolTypeFromString(String str) {
        switch (str.toLowerCase()) {
            case PROTOCOL_NAME_TCP:
                return IPv4.PROTOCOL_TCP;
            case PROTOCOL_NAME_UDP:
                return IPv4.PROTOCOL_UDP;
            default:
                return ARBITRARY_PROTOCOL;
        }
    }

    /**
     * Obtains protocol name from the protocol type.
     *
     * @param type transport protocol type
     * @return transport protocol name
     */
    public static String getProtocolNameFromType(byte type) {
        switch (type) {
            case IPv4.PROTOCOL_TCP:
                return PROTOCOL_NAME_TCP;
            case IPv4.PROTOCOL_UDP:
                return PROTOCOL_NAME_UDP;
            case ARBITRARY_PROTOCOL:
                return PROTOCOL_NAME_ANY;
            default:
                return PROTOCOL_NAME_ANY;
        }
    }

    /**
     * Initializes the telemetry service due tue configuration changes.
     *
     *
     * @param adminService  telemetry admin service
     * @param config        telemetry configuration
     * @param enable        service enable flag
     */
    public static void initTelemetryService(TelemetryAdminService adminService,
                                            TelemetryConfig config, boolean enable) {
        if (enable) {
            if (adminService.isRunning()) {
                adminService.restart(config);
            } else {
                adminService.start(config);
            }
        } else {
            if (adminService.isRunning()) {
                adminService.stop();
            }
        }
    }
}
