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

import java.io.Reader;

import com.sangupta.blogparser.domain.Blog;

/**
 * Contract for all implementation that need to provide support for
 * parsing a text export of the blog and convert it to a strongly
 * typed {@link Blog} object.
 *  
 * @author sangupta
 * @since 1.0
 */
public interface Parser {
	
	/**
	 * Parse the text data of the blog and return the {@link Blog} object.
	 * 
	 * @param blogData text data of the blog export
	 * @return strongly-typed blog object
	 * @throws NullPointerException if blogData is <code>null</code>
	 */
	public Blog parse(String blogData);
	
	/**
	 * Parse a blog reading data from the given reader.
	 * 
	 * @param reader
	 * @return
	 */
	public Blog parse(Reader reader);

}
