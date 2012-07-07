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
 * Value object that holds details of one comment against
 * a given post in the entire blog.
 * 
 * @author sangupta
 * @since 1.0
 */
public class PostComment {
	
	/**
	 * Absolute URL of the post on which the comment was added
	 */
	private String postUrl;
	
	/**
	 * HTML text of the content
	 */
	private String text;
	
	/**
	 * Author of this comment
	 */
	private Author author;
	
	/**
	 * Date when the comment was added
	 */
	private Date publishedOn;

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	 * @return the postUrl
	 */
	public String getPostUrl() {
		return postUrl;
	}

	/**
	 * @param postUrl the postUrl to set
	 */
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

}
