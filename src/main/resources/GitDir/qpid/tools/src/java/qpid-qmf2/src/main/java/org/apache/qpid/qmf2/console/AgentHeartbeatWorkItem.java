/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.qpid.qmf2.console;

/**
 * Descriptions below are taken from <a href=https://cwiki.apache.org/confluence/display/qpid/QMFv2+API+Proposal>QMF2 API Proposal</a> 
 * <pre>
 * AGENT_HEARTBEAT: When the QMF Console receives heartbeats from an Agent, an AGENT_HEARTBEAT WorkItem
 *                  is pushed onto the work-queue. The WorkItem's getParam() call returns a map which contains
 *                  a reference to the Console Agent instance. The reference is indexed from the map using
 *                  the key string "agent". There is no handle associated with this WorkItem.
 *
 *                  Note: the first heartbeat results in an AGENT_ADDED WorkItem for Agent not an AGENT_HEARTBEAT.
 * </pre>
 * @author Fraser Adams
 */

public final class AgentHeartbeatWorkItem extends AgentAccessWorkItem
{
    /**
     * Construct an AgentHeartbeatWorkItem. Convenience constructor not in API
     *
     * @param agent the Agent used to populate the WorkItem's param
     */
    public AgentHeartbeatWorkItem(final Agent agent)
    {
        super(WorkItemType.AGENT_HEARTBEAT, null, newParams(agent, null));
    }
}

