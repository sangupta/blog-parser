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

/**
 * Represents an author in the blog object model - may be a
 * post author, blog author or a comment author.
 * 
 * @author sangupta
 * @since 1.0
 */
public class Author {
	
	/**
	 * The name of the author
	 */
	private String name;
	
	/**
	 * Email address of the author
	 */
	private String email;
	
	/**
	 * Absolute URL to the profile of the author
	 */
	private String profileUrl;
	
	/**
	 * Default constructor
	 */
	public Author() {
		
	}
	
	/**
	 * Convenience constructor
	 * 
	 * @param name
	 */
	public Author(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "[Author: " + this.name + "]";
	}
	
	// Usual accessors follow

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the profileUrl
	 */
	public String getProfileUrl() {
		return profileUrl;
	}

	/**
	 * @param profileUrl the profileUrl to set
	 */
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

}
