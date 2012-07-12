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

import com.sangupta.blogparser.blogger.BloggerParser;
import com.sangupta.blogparser.domain.Blog;
import com.sangupta.blogparser.movabletype.MovableTypeParser;
import com.sangupta.blogparser.wordpress.WordpressParser;

/**
 * Utility class to parse blog exports.
 * 
 * @author sangupta
 *
 */
public class BlogParser {

	/**
	 * Parse the blog export of the given blog type.
	 * 
	 * @param blogData
	 * @param blogType
	 * @return
	 */
	public static Blog parse(String blogData, BlogType blogType) {
		if(blogData == null || blogData.isEmpty()) {
			throw new IllegalArgumentException("Blog data cannot be null/empty.");
		}
		
		Parser parser = null;
		switch(blogType) {
			case Blogger:
				parser = new BloggerParser();
				break;
				
			case Wordpress:
				parser = new WordpressParser();
				break;
				
			case MovableType:
				parser = new MovableTypeParser();
				break;
		}
		
		if(parser != null) {
			return parser.parse(blogData);
		}
		
		return null;
	}
}
