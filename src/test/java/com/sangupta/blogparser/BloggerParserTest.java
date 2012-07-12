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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

import com.sangupta.blogparser.blogger.BloggerParser;
import com.sangupta.blogparser.domain.Author;
import com.sangupta.blogparser.domain.Blog;
import static org.junit.Assert.*;

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
		File file = new File("./samples/blogger-poetinside.xml");
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String line;
		StringBuilder builder = new StringBuilder();
		while((line = br.readLine()) != null) {
			builder.append(line);
			builder.append("\n");
		}
		
		BloggerParser parser = new BloggerParser();
		blog = parser.parse(builder.toString());
	}
	
	@Test
	public void testBlogBasics() {
		assertNotNull(blog);
		assertEquals("blog title", "Poet Inside", blog.getTitle());
		assertEquals("blog url", "http://www.poetinside.com/", blog.getUrl());
		assertNotNull(blog.getPosts());
		assertEquals("num posts", 152, blog.getPosts().size());
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
		
	}
	
	@Test
	public void testPosts() {
		
	}
	
}
