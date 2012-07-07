**blog-parser** is a Java library to parse blog exports into strongly-typed data object model. This is particularly useful when you want to allow your application to import data from existing blogs.

**Current Version: 0.9.0**

Usage
-----

Initialize the right parser for your blog export, like

```java
File file = new File("blogger-export.xml");
String xml = org.apache.commons.io.FileUtils.readFileToString(file);
BloggerParser parser = new BloggerParser();
Blog blog = parser.parse(xml);
```

Features
--------

* Support for parsing Blogger XML exports
* Support for parsing blogs, authors, posts and comments

Dependencies
------------

The library depends on the following third-party libraries:
* [Rome RSS parsing library](https://rometools.jira.com/wiki/display/ROME/Home)

Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, the library will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
-------

Copyright 2012, Sandeep Gupta

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
