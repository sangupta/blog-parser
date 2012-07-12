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

package com.sangupta.blogparser.wordpress;

import java.io.File;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;

import com.sangupta.blogparser.Parser;
import com.sangupta.blogparser.domain.Author;
import com.sangupta.blogparser.domain.Blog;
import com.sangupta.blogparser.domain.BlogPost;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

/**
 * Implementation to parse a Wordpress WXR export of a blog.
 * 
 * @author sangupta
 * @since 1.0
 */
public class WordpressParser implements Parser {

	@Override
	public Blog parse(String blogData) {
		SyndFeed feed = null;
		try {
			feed = new SyndFeedInput().build(new StringReader(blogData));
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Illegal arguments when parsing feed", e);
		} catch (FeedException e) {
			throw new RuntimeException("Unable to parse feed", e);
		}
		
		Blog blog = new Blog();
		blog.setTitle(feed.getTitle());
		blog.setUrl(feed.getLink());
		blog.setDescription(feed.getDescription());
		
		// do for each entry
		for(Object object : feed.getEntries()) {
			SyndEntryImpl entry = (SyndEntryImpl) object;
			
			// get the title
			BlogPost post = new BlogPost();
			post.setTitle(entry.getTitle());
			post.setContent(((SyndContentImpl) entry.getContents().get(0)).getValue());
			post.setUrl(entry.getLink());
			post.setPublishedOn(entry.getPublishedDate());
			
			if(entry.getAuthors() == null || entry.getAuthors().isEmpty()) {
				Author author = new Author();
				author.setName(entry.getAuthor());
				post.setAuthor(author);
			} else {
				// TODO: fix issue with multiple authors
			}
			
			// extract the categories
			if(entry.getCategories() != null && !entry.getCategories().isEmpty()) {
				for(Object cat : entry.getCategories()) {
					SyndCategoryImpl category = (SyndCategoryImpl) cat;
					
					String taxonomyUri = category.getTaxonomyUri();
					if(taxonomyUri != null) {
						if("category".equals(taxonomyUri)) {
							post.addCategory(category.getName());
						} else if("tag".equals(taxonomyUri)) {
							post.addTag(category.getName());
						}
					}
				}
			}
			
			// add to the blog object
			blog.addPost(post);
		}
		
		return blog;
	}
	
}
