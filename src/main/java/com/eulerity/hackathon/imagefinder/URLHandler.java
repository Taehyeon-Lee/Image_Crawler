package com.eulerity.hackathon.imagefinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * The Url handler. Implemented thread and parse, collect and return all urls
 */
public class URLHandler implements Runnable {
  private String url;
  private ArrayList<String> vistedLink = new ArrayList<>();
  private ArrayList<String> allImages = new ArrayList<>();

  /**
   * Instantiates a new Url handler.
   *
   * @param url the url
   */
  public URLHandler(String url) {
    this.url = url;
  }

  @Override
  public void run() {
    try {
      crawlLinksANDImages(this.url);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Parses the html of the given url and collect the links that are the same base domain.
   *
   * @param url the url
   */
  protected void crawlLinksANDImages(String url) {
    try {
      // collect images on the first url given
      ImageHandler firstURLImage = new ImageHandler(url, allImages);
      Thread firstURLImageThread = new Thread(firstURLImage);
      firstURLImageThread.start();

      // get base url of the first original url and add it into the arraylist
      String originalBase = getBaseURL(url);
      System.out.println("Original url base: " + originalBase);
      vistedLink.add(url);

      // get current thread id
      long curThread = Thread.currentThread().getId();
      System.out.println("Thread " + curThread + " is running: " + url);

      // from the first page parse through and grab all links that are the same base
      Document doc = Jsoup.connect(url).get();
      Elements links = doc.select("a[href]");

      // Iterate over each link
      for (Element link : links) {
        String fullUrl = link.absUrl("href");
        // case when full url is empty or null
        if (fullUrl.equals("") || fullUrl.equals(null) || fullUrl.length() == 0) {
          continue;
        }
        String subLinkBase = getBaseURL(fullUrl);

        // check if the link is within the same base domain and not already in the list
        if (originalBase.equals(subLinkBase) && !vistedLink.contains(fullUrl)) {
          vistedLink.add(fullUrl);

          // instantiate ImageHandler and run thread to collect images
          ImageHandler imageHandler = new ImageHandler(fullUrl, allImages);
          Thread imageThread = new Thread(imageHandler);
          Thread.sleep(100);
          imageThread.start();
        }
      } // end of for loop iterate over links
    } // end of try
    catch (Exception e) {
      e.printStackTrace();
    } // end of catch
  }


  /**
   * Using the URL class gets the base domain url of the given url.
   *
   * @param url the url
   * @return the base url
   */
  protected String getBaseURL(String url) {
    try {
      URL urlObj = new URL(url);
      String host = urlObj.getHost();  // Retrieve the host (base domain) (e.g., www.example.com)

      return host;
    } catch (MalformedURLException e) {
      // Handle any potential MalformedURLException
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Gets all urls collected.
   *
   * @return the all urls
   */
  protected ArrayList<String> getVistedLink() {
    return vistedLink;
  }

  /**
   * Gets all images collected.
   *
   * @return the all urls
   */
  protected ArrayList<String> getAllImages() {
    return allImages;
  }

}
