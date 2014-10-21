package org.onlab.onos.net.flow;

import org.onlab.onos.ApplicationId;
import org.onlab.onos.net.DeviceId;
import org.onlab.onos.store.Store;

/**
 * Manages inventory of flow rules; not intended for direct use.
 */
public interface FlowRuleStore extends Store<FlowRuleEvent, FlowRuleStoreDelegate> {

    /**
     * Returns the number of flow rule in the store.
     *
     * @return number of flow rules
     */
    int getFlowRuleCount();

    /**
     * Returns the stored flow.
     *
     * @param rule the rule to look for
     * @return a flow rule
     */
    FlowEntry getFlowEntry(FlowRule rule);

    /**
     * Returns the flow entries associated with a device.
     *
     * @param deviceId the device ID
     * @return the flow entries
     */
    Iterable<FlowEntry> getFlowEntries(DeviceId deviceId);

    /**
     * Returns the flow entries associated with an application.
     *
     * @param appId the application id
     * @return the flow entries
     */
    Iterable<FlowRule> getFlowRulesByAppId(ApplicationId appId);

    /**
     * Stores a new flow rule without generating events.
     *
     * @param rule the flow rule to add
     * @return true if the rule should be handled locally
     */
    boolean storeFlowRule(FlowRule rule);

    /**
     * Marks a flow rule for deletion. Actual deletion will occur
     * when the provider indicates that the flow has been removed.
     *
     * @param rule the flow rule to delete
     * @return true if the rule should be handled locally
     */
    boolean deleteFlowRule(FlowRule rule);

    /**
     * Stores a new flow rule, or updates an existing entry.
     *
     * @param rule the flow rule to add or update
     * @return flow_added event, or null if just an update
     */
    FlowRuleEvent addOrUpdateFlowRule(FlowEntry rule);

    /**
     * @param rule the flow entry to remove
     * @return flow_removed event, or null if nothing removed
     */
    FlowRuleEvent removeFlowRule(FlowEntry rule);
}
