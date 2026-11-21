package com.vortex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Write-only credential input; never use a generated toString for secrets. */
public class IbkrSyncSettingsUpdate {
    private Long brokerId;
    private String queryId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;
    public Long getBrokerId() { return brokerId; }
    public void setBrokerId(Long value) { brokerId = value; }
    public String getQueryId() { return queryId; }
    public void setQueryId(String value) { queryId = value; }
    public String getToken() { return token; }
    public void setToken(String value) { token = value; }
}
