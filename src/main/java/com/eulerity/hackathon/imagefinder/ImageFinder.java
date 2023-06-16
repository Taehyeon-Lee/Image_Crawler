package com.eulerity.hackathon.imagefinder;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The type Image finder.
 */
@WebServlet(
        name = "ImageFinder",
        urlPatterns = {"/main"}
)
public class ImageFinder extends HttpServlet {
  private static final long serialVersionUID = 1L;
	protected static final Gson GSON = new GsonBuilder().create();

//This is just a test array
  public static final String[] testImages = {
          "https://images.pexels.com/photos/545063/pexels-photo-545063.jpeg?auto=compress&format=tiny",
          "https://images.pexels.com/photos/464664/pexels-photo-464664.jpeg?auto=compress&format=tiny",
          "https://images.pexels.com/photos/406014/pexels-photo-406014.jpeg?auto=compress&format=tiny",
          "https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg?auto=compress&format=tiny"
  };

  @Override
  protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // String array that would contain all images
    String[] images;

    String path = req.getServletPath();
    String url = req.getParameter("url");
    System.out.println("Got request of:" + path + " with query param:" + url);

    // no url provided, then nothing
    if (url == null) {
      System.out.println("No url provided");
      return;
    } else if (url.equals("test")) {
      images = testImages;
    } else {
      // if url doesn't have https then add it to the front
      if (!url.startsWith("https")) {
        url = "https://" + url;
      }
      images = imageCrawl(url);
    }

    // render images on the webpage
    try {
      resp.getWriter().print(GSON.toJson(images));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

	/**
	 * Image crawl that collects the images from the given url and the links from the page that have
	 * the same base domain.
	 *
	 * @param url the url
	 * @return the string [ ] of all collected images
	 */
	protected String[] imageCrawl(String url) {
    ArrayList<String> collectedImages;
    String[] imagesToReturn;
    URLHandler urlHandler = new URLHandler(url);
    Thread t = new Thread(urlHandler);

    try {
      t.start();
      t.join(); // wait until all images are collected

      collectedImages = urlHandler.getAllImages();
      System.out.println(collectedImages.size());

      return collectedImages.toArray(new String[collectedImages.size()]);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // return nothing if there's an error
    return null;
  }
}
