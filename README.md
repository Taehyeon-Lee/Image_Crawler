## Goal of this project
The goal of this task is to perform a web crawl on a URL string provided by the user. From the crawl, it will parse out all of the images on that web page and return a JSON array of strings that represent the URLs of all images on the page. I mainly used [Jsoup](https://jsoup.org/) and URL library, and maven to build this image crawler.

### Functionality
- Build a web crawler that can find all images on the web page(s) that it crawls.
- Crawl sub-pages to find more images.
- Implement multi-threading so that the crawl can be performed on multiple sub-pages at a time.
- Keep the crawl within the same domain as the input URL.
- Avoid re-crawling any pages that have already been visited.
- Created the crawler "friendly" - not banned from the site by performing too many crawls.


You have one week to work on the submission from the time when you receive it. To submit you assignment, zip up your project (`imagefinder.zip`) and email it back to me. **Please include a list of URLs that you used to test in your submissions.** You should place them in the attached `test-links.txt` file found in the root of this project.

## Structure and setup
The ImageFinder servlet is found in `src/main/java/com/eulerity/hackathon/imagefinder/ImageFinder.java`. 

The main landing page for this project can be found in `src/main/webapp/index.html`. 

Finally, in the root directory of this project, you will find the `pom.xml`. This contains the project configuration details used by maven to build the project.

### Requirements
- Maven 3.5 or higher
- Java 8
  - Exact version, **NOT** Java 9+ - the build will fail with a newer version of Java

### Running the Crawler
To run the project, use the following command to start the server:

>`mvn clean test package jetty:run`

You should see a line at the bottom that says "Started Jetty Server". Now, if you enter `localhost:8080` into your browser, you should see the `index.html` welcome page! If all has gone well to this point, you're ready to begin!

To clear what has been built, you may run the command:

>`mvn clean`

## What could I have done better if there is more time
- If there is more time, I could improve the design of the front page using React.js and make more user-friendly. For example, implement an active loading page so that it engages with users more. Moreover, improve the performance by maximizing the multi-thread or better algorithm. 
- Implementing a face recognition functionality and differentiate logos and regular photos so that I can divide collect images into separate group.