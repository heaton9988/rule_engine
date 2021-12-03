package com.zzj.rule.server.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class FxResult {
    private Boolean isSucc;
    private Map<String, String> customStructuredMsgs;
    private List<FxResult> childResultList;
}
