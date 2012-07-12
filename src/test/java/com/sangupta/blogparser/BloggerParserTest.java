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

package com.sangupta.blogparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

import com.sangupta.blogparser.blogger.BloggerParser;
import com.sangupta.blogparser.domain.Author;
import com.sangupta.blogparser.domain.Blog;
import com.sangupta.blogparser.domain.BlogPage;
import com.sangupta.blogparser.domain.BlogPost;
import com.sangupta.blogparser.domain.PostComment;

/**
 * Simple class to test the blogger parser
 * 
 * @author sangupta
 *
 */
public class BloggerParserTest {
	
	private Blog blog = null;

	@Before
	public void setupBlog() throws Exception {
		File file = new File("./samples/blogger-sample.xml");
		FileReader reader = new FileReader(file);
		BloggerParser parser = new BloggerParser();
		blog = parser.parse(reader);
		System.out.println(blog);
	}
	
	@Test
	public void testBlogBasics() {
		assertNotNull(blog);
		assertEquals("blog title", "Poet Inside", blog.getTitle());
		assertEquals("blog url", "http://www.poetinside.com/", blog.getUrl());
		assertNotNull(blog.getPosts());
		assertEquals("num posts", 1, blog.getPosts().size());
		assertNotNull(blog.getPages());
		assertEquals("num pages", 1, blog.getPages().size());
	}
	
	@Test
	public void testAuthors() {
		assertNotNull(blog.getAuthors());
		assertEquals("num authors", 1, blog.getAuthors().size());

		Author author = blog.getAuthors().get(0);
		assertEquals("author email", "noreply@blogger.com", author.getEmail());
		assertEquals("author name", "Sandeep Gupta", author.getName());
		assertEquals("author profile", "https://profiles.google.com/110645714607919970386", author.getProfileUrl());
	}
	
	@Test
	public void testPages() {
		BlogPage page = blog.getPages().get(0);
		assertEquals("page title", "About Me", page.getTitle());
		assertNull(page.getUrl());
		assertEquals("page content", "This is a test page for about me.", page.getContent());
		assertNotNull(page.getAuthor());

		Author author = page.getAuthor();
		assertEquals("author email", "noreply@blogger.com", author.getEmail());
		assertEquals("author name", "Sandeep Gupta", author.getName());
		assertEquals("author profile", "https://profiles.google.com/110645714607919970386", author.getProfileUrl());
	}
	
	@Test
	public void testPosts() {
		BlogPost post = blog.getPosts().get(0);
		assertEquals("post title", "A MAN IS BORN!", post.getTitle());
		assertEquals("post url", "http://www.poetinside.com/2007/06/man-is-born.html", post.getUrl());
		assertEquals("page content", "man is born poem!", post.getContent());
		assertNotNull(post.getAuthor());

		Author author = post.getAuthor();
		assertEquals("author email", "noreply@blogger.com", author.getEmail());
		assertEquals("author name", "Sandeep Gupta", author.getName());
		assertEquals("author profile", "https://profiles.google.com/110645714607919970386", author.getProfileUrl());
		
		assertNotNull(post.getComments());
		assertEquals("num comments", 1, post.getComments().size());
		
		PostComment comment = post.getComments().get(0);
		assertEquals("comment text", "Too good :)", comment.getText());
		assertEquals("comment url", "http://www.poetinside.com/2007/06/man-is-born.html", comment.getPostUrl());
		
		author = comment.getAuthor();
		assertEquals("author email", "noreply@blogger.com", author.getEmail());
		assertEquals("author name", "MyFriend-1", author.getName());
		assertEquals("author profile", "http://www.blogger.com/profile/45263475234685623465", author.getProfileUrl());
	}
	
}
