package com.healthcare.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> {

	private List<T> content;

	private int pageNo;

	private int pageSize;

	private long totalElements;

	private int totalPages;

	private boolean last;
}
