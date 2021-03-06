/*
 * Copyright 2017-present Open Networking Foundation
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

package org.onosproject.net.pi.runtime;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.onosproject.net.pi.model.PiActionProfileId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Instance of a member of an action group in a protocol-independent pipeline.
 */
@Beta
public final class PiActionGroupMember implements PiEntity {

    private final PiActionProfileId actionProfileId;
    private final PiActionGroupMemberId id;
    private final PiAction action;
    // FIXME: in P4Runtime weight is an attribute of the member reference in a
    // group. Either remove it from this class or define the containing group
    // ID.
    private final int weight;

    private PiActionGroupMember(
            PiActionProfileId actionProfileId, PiActionGroupMemberId id,
            PiAction action, int weight) {
        this.actionProfileId = actionProfileId;
        this.id = id;
        this.action = action;
        this.weight = weight;
    }

    /**
     * Returns the identifier of this member.
     *
     * @return member identifier
     */
    public PiActionGroupMemberId id() {
        return id;
    }

    /**
     * Returns the identifier of the action profile.
     *
     * @return action profile identifier
     */
    public PiActionProfileId actionProfile() {
        return actionProfileId;
    }

    /**
     * Returns the action associated to this member.
     *
     * @return action
     */
    public PiAction action() {
        return action;
    }

    /**
     * Returns the weight associated to this member.
     *
     * @return weight
     */
    public int weight() {
        return weight;
    }

    @Override
    public PiEntityType piEntityType() {
        return PiEntityType.GROUP_MEMBER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PiActionGroupMember)) {
            return false;
        }
        PiActionGroupMember that = (PiActionGroupMember) o;
        return weight == that.weight &&
                Objects.equal(actionProfileId, that.actionProfileId) &&
                Objects.equal(id, that.id) &&
                Objects.equal(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actionProfileId, id, action, weight);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("actionProfile", actionProfileId)
                .add("id", id)
                .add("action", action)
                .add("weight", weight)
                .toString();
    }

    /**
     * Returns a new builder of action group members.
     *
     * @return member builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder of action group members.
     */
    public static final class Builder {

        private PiActionProfileId actionProfileId;
        private PiActionGroupMemberId id;
        private PiAction action;
        private int weight;

        private Builder() {
            // Hides constructor.
        }

        /**
         * Sets the action profile identifier of this member.
         *
         * @param actionProfileId action profile identifier
         * @return this
         */
        public Builder forActionProfile(PiActionProfileId actionProfileId) {
            this.actionProfileId = actionProfileId;
            return this;
        }

        /**
         * Sets the identifier of this member.
         *
         * @param id member identifier
         * @return this
         */
        public Builder withId(PiActionGroupMemberId id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the action of this member.
         *
         * @param action action
         * @return this
         */
        public Builder withAction(PiAction action) {
            this.action = action;
            return this;
        }

        /**
         * Sets the weight of this member.
         * <p>
         * Default value is 0.
         *
         * @param weight weight
         * @return this
         */
        public Builder withWeight(int weight) {
            this.weight = weight;
            return this;
        }

        /**
         * Creates a new action group member.
         *
         * @return action group member
         */
        public PiActionGroupMember build() {
            checkNotNull(actionProfileId);
            checkNotNull(id);
            checkNotNull(action);
            return new PiActionGroupMember(actionProfileId, id, action, weight);
        }
    }
}
