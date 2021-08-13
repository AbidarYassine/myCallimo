package com.rest.ai.myCallimo.request.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagedResponse<T> {
    private List<T> content;
    private long count;
    private long totalCount;
}
