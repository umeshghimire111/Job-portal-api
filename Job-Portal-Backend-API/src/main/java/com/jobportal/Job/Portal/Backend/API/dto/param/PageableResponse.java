package com.jobportal.Job.Portal.Backend.API.dto.param;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableResponse<T> {
    private List<T> data;
    private long totalCount;
}