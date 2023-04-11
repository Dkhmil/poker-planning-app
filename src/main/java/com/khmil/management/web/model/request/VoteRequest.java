package com.khmil.management.web.model.request;

import lombok.Data;

@Data
public class VoteRequest {
    String voterName;
    Long voteOption;
}
