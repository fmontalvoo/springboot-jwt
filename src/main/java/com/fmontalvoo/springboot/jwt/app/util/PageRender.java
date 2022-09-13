package com.fmontalvoo.springboot.jwt.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;

	private int totalPages;
	private int currentPage;
	private int itemsPerPage;

	private List<PageItem> pages = new ArrayList<>();

	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.itemsPerPage = this.page.getSize();
		this.totalPages = this.page.getTotalPages();
		this.currentPage = this.page.getNumber() + 1;

		int desde, hasta;
		if (this.totalPages <= this.itemsPerPage) {
			desde = 1;
			hasta = this.totalPages;
		} else {
			if (this.currentPage <= this.itemsPerPage / 2) {
				desde = 1;
				hasta = this.itemsPerPage;
			} else if (this.currentPage >= this.totalPages - this.itemsPerPage / 2) {
				desde = this.totalPages - this.itemsPerPage + 1;
				hasta = this.itemsPerPage;
			} else {
				desde = this.currentPage - this.itemsPerPage / 2;
				hasta = this.itemsPerPage;
			}
		}

		for (int i = 0; i < hasta; i++) {
			this.pages.add(new PageItem(desde + i, this.currentPage == desde + i));
		}

	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}

	public boolean isFirst() {
		return this.page.isFirst();
	}

	public boolean isLast() {
		return this.page.isLast();
	}

	public boolean getNext() {
		return this.page.hasNext();
	}

	public boolean getPrevious() {
		return this.page.hasPrevious();
	}
}
