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

package com.sangupta.blogparser.movabletype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sangupta.blogparser.Parser;
import com.sangupta.blogparser.domain.Author;
import com.sangupta.blogparser.domain.Blog;
import com.sangupta.blogparser.domain.BlogPost;
import com.sangupta.blogparser.domain.PostComment;

/**
 * Implementation to parse a MovableType export of a blog.
 * 
 * The implementation is based on the format details as described on
 * the <a href="http://www.movabletype.org/documentation/appendices/import-export-format.html">
 * MovableType Import Export Format</a> page.
 * 
 * @author sangupta
 * @since 1.0
 */
public class MovableTypeParser implements Parser {
	
	// use line numbering to report errors
	int currentLine = 0;

	/**
	 * The internal buffered reader of the input stream.
	 */
	private BufferedReader reader = null;
	
	/**
	 * entry separator with 8 hyphens
	 */
	private static final String ENTRY_SEPARATOR = "--------";

	/**
	 * section separator with 5 hyphens
	 */
	private static final String SECTION_SEPARATOR = "-----";
	
	/**
	 * Parse the XML feed and return the {@link Blog} object.
	 */
	public Blog parse(String blogData) {
		return parse(new StringReader(blogData));
	}
	
	/**
	 * Parse the feed from the given reader and return the {@link Blog} object.
	 * 
	 * @param reader the reader to use for reading contents of the blog export
	 * @throws IOException 
	 * 
	 * @throws IllegalArgumentException if the reader supplied is <code>null</code>
	 */
	public Blog parse(Reader reader) {
		if(reader == null) {
			throw new IllegalArgumentException("Reader cannot be null.");
		}
		
		this.reader = new BufferedReader(reader);
		try {
			return parseLineByLine();
		} catch(IOException e) {
			throw new RuntimeException("Unable to parse feed");
		}
	}
	
	/**
	 * Parse the entire blog line by line.
	 * 
	 * @return
	 * @throws IOException
	 */
	private Blog parseLineByLine() throws IOException {
		String line;
		
		// create a new blog
		Blog blog = new Blog();
		
		// create the first post
		BlogPost post = new BlogPost();
		
		boolean justBackFromReadingSection = false;
		while((line = readLine()) != null) {
			// check for empty lines
			line = line.trim();
			if(line.isEmpty()) {
				continue;
			}
			
			// need to see if the line is another section or not
			if(justBackFromReadingSection) {
				if(line.endsWith(":")) {
					readSection(post, line);
					continue;
				} else {
					justBackFromReadingSection = false;
				}
			}
			
			if(ENTRY_SEPARATOR.equals(line)) {
				blog.addPost(post);
				post = new BlogPost();
				continue;
			}
			
			if(SECTION_SEPARATOR.equals(line)) {
				// this is a new section that we need to read
				readSection(post, null);
				justBackFromReadingSection = true;
				continue;
			}
			
			// this must be the metadata
			int index = line.indexOf(':');
			String attributeName = line.substring(0, index);
			String attributeValue = line.substring(index + 1).trim();

			setPostMetadata(post, attributeName, attributeValue);
		}
		
		return blog;
	}

	/**
	 * Read the section and save it into the given post
	 * 
	 * @param post
	 * @throws IOException
	 */
	private void readSection(BlogPost post, String readLine) throws IOException {
		String line;
		String attributeName = null;
		StringBuilder builder = new StringBuilder();

		do {
			if(readLine != null) {
				line = readLine;
				readLine = null;
			} else {
				line = readLine();
			}
			
			if(line.isEmpty()) {
				builder.append("\n");
				continue;
			}
			
			if(SECTION_SEPARATOR.equals(line)) {
				break;
			}
			
			// read the attribute name from the first line
			if(attributeName == null) {
				if(!line.endsWith(":")) {
					throw new RuntimeException("Invalid section name encountered at line " + this.currentLine);
				}
				
				attributeName = line.substring(0, line.length() - 1);

				// comments are a special kind of section containing metadata
				// and thus they are parsed separately
				if("COMMENT".equals(attributeName)) {
					PostComment comment = parseComment();
					post.addComment(comment);
					return;
				}
				
				continue;
			}
			
			builder.append(line);
			builder.append("\n");
		} while(true);
		
		if("BODY".equals(attributeName)) {
			post.setContent(builder.toString());
			return;
		}
		
		if("EXTENDED BODY".equals(attributeName)) {
			builder.insert(0, '\n');
			builder.insert(0, post.getContent());
			
			post.setContent(builder.toString());
			return;
		}
		
		if("EXCERPT".equals(attributeName)) {
			// ignore
			return;
		}
		
		if("PING".equals(attributeName)) {
			// ignore
			return;
		}
	}

	/**
	 * Set the post metadata based on the attribute name
	 * @param post
	 * @param attributeName
	 * @param attributeValue
	 */
	private void setPostMetadata(BlogPost post, String attributeName, String attributeValue) {
		if("AUTHOR".equals(attributeName)) {
			post.setAuthor(new Author(attributeValue));
			return;
		}
		
		if("TITLE".equals(attributeName)) {
			post.setTitle(attributeValue);
			return;
		}
		
		if("BASENAME".equals(attributeName)) {
			// ignore
			return;
		}
		
		if("DATE".equals(attributeName)) {
			post.setPublishedOn(parseDate(attributeValue));
			return;
		}
		
		if("PRIMARY CATEGORY".equals(attributeName)) {
			post.addCategory(attributeValue);
			return;
		}
		
		if("CATEGORY".equals(attributeName)) {
			if(post.getCategories() != null  && !post.getCategories().contains(attributeValue)) {
				post.addCategory(attributeValue);
			}
			
			return;
		}
		
		if("TAGS".equals(attributeName)) {
			String[] tokens = attributeValue.split(",");
			for(String token : tokens) {
				token = token.trim();
				if(token.startsWith("\"") && token.endsWith("\"")) {
					token = token.substring(1, token.length() - 2);
					token = token.trim();
				}
				
				post.addTag(token);
			}
			
			return;
		}
		
		// the following ignorable attributes have been
		// added only for future use
		
		if("STATUS".equals(attributeName)) {
			// ignore
			return;
		}
		
		if("ALLOW COMMENTS".equals(attributeName)) {
			// ignore
			return;
		}
		
		if("ALLOW PINGS".equals(attributeName)) {
			// ignore
			return;
		}
		
		if("NO ENTRY".equals(attributeName)) {
			// ignore
			return;
		}

		if("CONVERT BREAKS".equals(attributeName)) {
			// ignore
			return;
		}
	}

	/**
	 * Parses a comment from the comment section.
	 * 
	 * @param string
	 * @return
	 * @throws IOException 
	 */
	private PostComment parseComment() throws IOException {
		String line;
		PostComment comment = new PostComment();
		StringBuilder builder = new StringBuilder();
		
		// signifies if we are done reading metadata
		// any line after that just needs to be appended to the comment text
		boolean metaEnded = false;
		
		// start going line over line
		do {
			line = readLine();
			
			if(SECTION_SEPARATOR.equals(line)) {
				break;
			}
			
			if(!metaEnded) {
				if(line.startsWith("AUTHOR:")) {
					int index = line.indexOf(':');
					String value = line.substring(index + 1).trim();
					Author author = comment.getAuthor();
					if(author == null) {
						author = new Author();
					}
					author.setName(value);
					comment.setAuthor(author);
					continue;
				}
				
				if(line.startsWith("EMAIL:")) {
					int index = line.indexOf(':');
					String value = line.substring(index + 1).trim();
					Author author = comment.getAuthor();
					if(author == null) {
						author = new Author();
					}
					author.setEmail(value);
					comment.setAuthor(author);
					continue;
				}
				
				if(line.startsWith("URL:")) {
					int index = line.indexOf(':');
					String value = line.substring(index + 1).trim();
					Author author = comment.getAuthor();
					if(author == null) {
						author = new Author();
					}
					author.setProfileUrl(value);
					comment.setAuthor(author);
					continue;
				}
				
				if(line.startsWith("DATE:")) {
					int index = line.indexOf(':');
					String value = line.substring(index + 1).trim();
					comment.setPublishedOn(parseDate(value));
					continue;
				}
				
				if(line.startsWith("IP:")) {
					// ignore
					continue;
				}
			}
			
			metaEnded = true;
			
			builder.append(line);
			builder.append("\n");
		} while(true);
		
		comment.setText(builder.toString());
		return comment;
	}
	
	private Date parseDate(String string) {
		Date date = null;
		DateFormat format = null;
		if(string.endsWith("AM") || string.endsWith("PM")) {
			// parse in 12-hour format
			format = new SimpleDateFormat("MM/dd/yyyy KK:mm:ss a");
		} else {
			// parse in 24-hour format
			format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		}

		try {
			date = format.parse(string);
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse date " + string  + " into a valid format on line " + this.currentLine);
		}
		
		return date;
	}

	/**
	 * Read a line from the current reader
	 * 
	 * @return
	 * @throws IOException
	 */
	private String readLine() throws IOException {
		this.currentLine++;
		String line = this.reader.readLine();
		if(line != null) {
			line = line.trim();
		}
		return line;
	}
	
}
