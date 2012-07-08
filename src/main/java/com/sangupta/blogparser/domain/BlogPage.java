/**
 *
 * BlogParser - Parsing library for Blog exports 
 * Copyright (c) 2012, Sandeep Gupta
 * 
 * http://www.sangupta/projects/blog-parser
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.blogparser.domain;

import java.util.Date;

/**
 * Value object that encapsulates one page in the entire
 * blog.
 * 
 * @author sangupta
 * @since 1.0
 */
public class BlogPage {
	
	/**
	 * Human-readable title of this page
	 */
	private String title;
	
	/**
	 * Absolute URL of this page
	 */
	private String url;
	
	/**
	 * Contents of this page
	 */
	private String content;
	
	/**
	 * Date the page was published on
	 */
	private Date publishedOn;
	
	/**
	 * Author of this page
	 */
	private Author author;
	
	// Usual accessors follow

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the contents
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the publishedOn
	 */
	public Date getPublishedOn() {
		return publishedOn;
	}

	/**
	 * @param publishedOn the publishedOn to set
	 */
	public void setPublishedOn(Date publishedOn) {
		this.publishedOn = publishedOn;
	}

	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}
	
}
