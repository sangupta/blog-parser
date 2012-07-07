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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Value object that encapsulates details of one blog entry
 * in the entire blog.
 * 
 * @author sangupta
 * @since 1.0
 */
public class BlogPost {
	
	/**
	 * Title of this post
	 */
	private String title;
	
	/**
	 * Absolute URL of this post
	 */
	private String url;
	
	/**
	 * HTML content of this post
	 */
	private String content;
	
	/**
	 * Date the post was published on
	 */
	private Date publishedOn;
	
	/**
	 * Author of this post
	 */
	private Author author;
	
	/**
	 * Comments on this post
	 */
	private List<PostComment> comments;
	
	/**
	 * Tags applied to this post
	 */
	private List<String> tags;
	
	/**
	 * Add a comment to this post
	 * 
	 * @param comment
	 */
	public void addComment(PostComment comment) {
		if(this.comments == null) {
			this.comments = new ArrayList<PostComment>();
		}
		
		this.comments.add(comment);
	}
	
	/**
	 * Add a tag to this post
	 * 
	 * @param tag
	 */
	public void addTag(String tag) {
		if(this.tags == null) {
			this.tags = new ArrayList<String>();
		}
		
		this.tags.add(tag);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(!(obj instanceof Blog)) {
			return false;
		}
		
		Blog blog = (Blog) obj;
		return this.url.equals(blog.getUrl());
	}
	
	@Override
	public int hashCode() {
		return this.url.hashCode();
	}
	
	@Override
	public String toString() {
		return "[BlogPost: " + this.url + "]";
	}
	
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
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

	/**
	 * @return the comments
	 */
	public List<PostComment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}
	
}
