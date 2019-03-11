package com.dpmall.db;

import java.util.List;

public class Page<T> {
	public static final int DEFAULT_PAGE_SIZE = 30;
    public int start = 0;
    public int pageSize = DEFAULT_PAGE_SIZE;
    public int totalNumber = 0;
    public List<T> items;
	public Page(int start, int pageSize) {
		super();
		this.start = start;
		this.pageSize = pageSize;
	}
    
}
