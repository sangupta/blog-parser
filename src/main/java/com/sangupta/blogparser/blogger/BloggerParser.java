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

package com.sangupta.blogparser.blogger;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.sangupta.blogparser.Parser;
import com.sangupta.blogparser.domain.Author;
import com.sangupta.blogparser.domain.Blog;
import com.sangupta.blogparser.domain.BlogPage;
import com.sangupta.blogparser.domain.BlogPost;
import com.sangupta.blogparser.domain.PostComment;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndPersonImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

/**
 * Implementation to parse a Blogger XML export of a blog.
 * 
 * @author sangupta
 * @since 1.0
 */
public class BloggerParser implements Parser {

	/**
	 * Parse the XML feed and return the {@link Blog} object.
	 */
	public Blog parse(String xml) {
		SyndFeed feed = null;
		try {
			feed = new SyndFeedInput().build(new StringReader(xml));
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Illegal arguments when parsing feed", e);
		} catch (FeedException e) {
			throw new RuntimeException("Unable to parse feed", e);
		}
		
		Blog blog = new Blog();
		blog.setUrl(feed.getLink());
		blog.setTitle(feed.getTitleEx().getValue());
		
		// author info
		// email, name, profile url
		SyndPersonImpl blogAuthor = ((SyndPersonImpl) feed.getAuthors().get(0));
		Author author = new Author();
		author.setName(blogAuthor.getName());
		author.setEmail(blogAuthor.getEmail());
		author.setProfileUrl(blogAuthor.getUri());
		
		blog.addAuthor(author);
		
		// create a map to store posts per URL
		// this will be used to store comments
		Map<String, BlogPost> posts = new HashMap<String, BlogPost>();
		
		// get each individual entry
		for(Object obj : feed.getEntries()) {
			SyndEntryImpl entry = (SyndEntryImpl) obj;
			
			// post
			SyndCategoryImpl category = (SyndCategoryImpl) entry.getCategories().get(0);
			if(category.getTaxonomyUri().equals("http://schemas.google.com/g/2005#kind") && category.getName().equals("http://schemas.google.com/blogger/2008/kind#post")) {
				// do as a proper post
				BlogPost post = convertToBlogPost(entry);
				blog.addPost(post);
				continue;
			}
			
			// comment
			if(category.getTaxonomyUri().equals("http://schemas.google.com/g/2005#kind") && category.getName().equals("http://schemas.google.com/blogger/2008/kind#comment")) {
				PostComment comment = convertToPostComment(entry);
				BlogPost post = posts.get(comment.getPostUrl());
				if(post != null) {
					post.addComment(comment);
				}
				continue;
			}
			
			// page
			if(category.getTaxonomyUri().equals("http://schemas.google.com/g/2005#kind") && category.getName().equals("http://schemas.google.com/blogger/2008/kind#page")) {
				// do as a proper post
				BlogPage page = convertToBlogPage(entry);
				blog.addPage(page);
				continue;
			}
		}
		
		return blog;
	}
	
	/**
	 * Convert the syndicated feed entry to the {@link BlogPage} object.
	 * 
	 * @param entry
	 * @return
	 */
	private BlogPage convertToBlogPage(SyndEntryImpl entry) {
		BlogPage page = new BlogPage();
		
		page.setContent(((SyndContentImpl) entry.getContents().get(0)).getValue());
		page.setPublishedOn(entry.getPublishedDate());
		page.setUrl(entry.getLink());
		page.setTitle(entry.getTitleEx().getValue());
		
		// extract the author
		SyndPersonImpl blogAuthor = ((SyndPersonImpl) entry.getAuthors().get(0));
		Author author = new Author();
		author.setName(blogAuthor.getName());
		author.setEmail(blogAuthor.getEmail());
		author.setProfileUrl(blogAuthor.getUri());
		
		page.setAuthor(author);
		
		return page;

	}

	/**
	 * Convert the syndicated feed entry to the {@link BlogPost} object.
	 * 
	 * @param entry
	 * @return
	 */
	private BlogPost convertToBlogPost(SyndEntryImpl entry) {
		BlogPost post = new BlogPost();
		
		post.setContent(((SyndContentImpl) entry.getContents().get(0)).getValue());
		post.setPublishedOn(entry.getPublishedDate());
		post.setUrl(entry.getLink());
		post.setTitle(entry.getTitleEx().getValue());
		
		// extract the author
		SyndPersonImpl blogAuthor = ((SyndPersonImpl) entry.getAuthors().get(0));
		Author author = new Author();
		author.setName(blogAuthor.getName());
		author.setEmail(blogAuthor.getEmail());
		author.setProfileUrl(blogAuthor.getUri());
		
		post.setAuthor(author);
		
		// extract the categories
		for(Object obj : entry.getCategories()) {
			SyndCategoryImpl category = (SyndCategoryImpl) obj;
			if(category.getTaxonomyUri().equals("http://www.blogger.com/atom/ns#")) {
				post.addTag(category.getName());
			}
		}
		
		return post;
	}

	/**
	 * Convert the syndicated feed entry to the {@link PostComment} object.
	 * 
	 * @param entry
	 * @return
	 */
	private PostComment convertToPostComment(SyndEntryImpl entry) {
		PostComment comment = new PostComment();
		
		comment.setText(((SyndContentImpl) entry.getContents().get(0)).getValue());
		comment.setPublishedOn(entry.getPublishedDate());
		
		// extract the author
		SyndPersonImpl blogAuthor = ((SyndPersonImpl) entry.getAuthors().get(0));
		Author author = new Author();
		author.setName(blogAuthor.getName());
		author.setEmail(blogAuthor.getEmail());
		author.setProfileUrl(blogAuthor.getUri());
		
		comment.setAuthor(author);
		
		// find the url of the post
		@SuppressWarnings("unchecked")
		List<Element> elements = (List<Element>) entry.getForeignMarkup();
		for(Element element : elements) {
			if(element.getName().equals("in-reply-to")) {
				String url = element.getAttributeValue("href");
				comment.setPostUrl(url);
				break;
			}
		}
		
		return comment;
	}

}
