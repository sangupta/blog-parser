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

import com.sangupta.blogparser.blogger.BloggerParser;
import com.sangupta.blogparser.domain.Blog;

/**
 * Simple class to test the blogger parser
 * 
 * @author sangupta
 *
 */
public class BloggerParserTest {

	public static void main(String[] args) throws Exception {
		File file = new File("c:\\users\\sangupta\\desktop\\blog-07-07-2012.xml");
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String line;
		StringBuilder builder = new StringBuilder();
		while((line = br.readLine()) != null) {
			builder.append(line);
			builder.append("\n");
		}
		
		BloggerParser parser = new BloggerParser();
		Blog blog = parser.parse(builder.toString());
		
		System.out.println(blog);
	}
}
