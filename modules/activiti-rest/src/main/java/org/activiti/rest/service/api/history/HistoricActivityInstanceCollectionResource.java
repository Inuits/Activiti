/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.rest.service.api.history;


import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.activiti.rest.common.api.DataResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;

/**
 * @author Tijs Rademakers
 */
@RestController
public class HistoricActivityInstanceCollectionResource extends HistoricActivityInstanceBaseResource {

  @RequestMapping(value="/history/historic-activity-instances", method = RequestMethod.GET, produces = "application/json")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "activityId", value = "activityId", required = false, dataType = "string", paramType = "query")
  })
  public DataResponse getHistoricActivityInstances(HttpServletRequest request) {
    Map<String,String> allRequestParams = new HashMap<String,String>();
    HistoricActivityInstanceQueryRequest query = new HistoricActivityInstanceQueryRequest();

    // Populate query based on request
    if (request.getParameter("activityId") != null) {
      allRequestParams.put("activityId", request.getParameter("activityId"));
      query.setActivityId(allRequestParams.get("activityId"));
    }
    
    if (request.getParameter("activityInstanceId") != null) {
      allRequestParams.put("activityInstanceId", request.getParameter("activityInstanceId"));
      query.setActivityInstanceId(allRequestParams.get("activityInstanceId"));
    }
    
    if (request.getParameter("activityName") != null) {
      allRequestParams.put("activityName", request.getParameter("activityName"));
      query.setActivityName(allRequestParams.get("activityName"));
    }
    
    if (request.getParameter("activityType") != null) {
      allRequestParams.put("activityType", request.getParameter("activityType"));
      query.setActivityType(allRequestParams.get("activityType"));
    }
    
    if (request.getParameter("executionId") != null) {
      allRequestParams.put("executionId", request.getParameter("executionId"));
      query.setExecutionId(allRequestParams.get("executionId"));
    }
    
    if (request.getParameter("finished") != null) {
      allRequestParams.put("finished", request.getParameter("finished"));
      query.setFinished(Boolean.valueOf(allRequestParams.get("finished")));
    }
    
    if (request.getParameter("taskAssignee") != null) {
      allRequestParams.put("taskAssignee", request.getParameter("taskAssignee"));
      query.setTaskAssignee(allRequestParams.get("taskAssignee"));
    }
    
    if (request.getParameter("processInstanceId") != null) {
      allRequestParams.put("processInstanceId", request.getParameter("processInstanceId"));
      query.setProcessInstanceId(allRequestParams.get("processInstanceId"));
    }
    
    if (request.getParameter("processDefinitionId") != null) {
      allRequestParams.put("processDefinitionId", request.getParameter("processDefinitionId"));
      query.setProcessDefinitionId(allRequestParams.get("processDefinitionId"));
    }
    
    if (request.getParameter("tenantId") != null) {
      allRequestParams.put("tenantId", request.getParameter("tenantId"));
      query.setTenantId(allRequestParams.get("tenantId"));
    }
    
    if (request.getParameter("tenantIdLike") != null) {
      allRequestParams.put("tenantIdLike", request.getParameter("tenantIdLike"));
      query.setTenantIdLike(allRequestParams.get("tenantIdLike"));
    }
    
    if(request.getParameter("withoutTenantId") != null) {
      allRequestParams.put("withoutTenantId", request.getParameter("withoutTenantId"));
      query.setWithoutTenantId(Boolean.valueOf(allRequestParams.get("withoutTenantId")));
    }

    return getQueryResponse(query, allRequestParams);
  }
}
