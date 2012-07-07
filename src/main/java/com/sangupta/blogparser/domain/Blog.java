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
import java.util.List;

/**
 * Strongly-typed object representing the entire blog.
 * 
 * @author sangupta
 * @since 1.0
 */
public class Blog {
	
	/**
	 * Title of the blog
	 */
	private String title;
	
	/**
	 * Absolute URL of the blog
	 */
	private String url;
	
	/**
	 * All posts available in this blog
	 */
	private List<BlogPost> posts;
	
	/**
	 * All authors of this blog
	 */
	private List<Author> authors;
	
	/**
	 * Add an author to this blog
	 * 
	 * @param author
	 */
	public void addAuthor(Author author) {
		if(this.authors == null) {
			this.authors = new ArrayList<Author>();
		}
		
		this.authors.add(author);
	}
	
	/**
	 * Add a post to this blog
	 * 
	 * @param post
	 */
	public void addPost(BlogPost post) {
		if(this.posts == null) {
			this.posts = new ArrayList<BlogPost>();
		}
		
		this.posts.add(post);
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
		return "[Blog: " + this.url + "]";
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
	 * @return the posts
	 */
	public List<BlogPost> getPosts() {
		return posts;
	}

	/**
	 * @param posts the posts to set
	 */
	public void setPosts(List<BlogPost> posts) {
		this.posts = posts;
	}

	/**
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return authors;
	}

	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

}
