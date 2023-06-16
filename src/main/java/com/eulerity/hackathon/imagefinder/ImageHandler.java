package com.eulerity.hackathon.imagefinder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Image handler. Implemented thread and parse, collect all images
 */
public class ImageHandler implements Runnable {
  private String url;
  private ArrayList<String> alLImages;

  /**
   * Instantiates a new Image handler.
   *
   * @param url       the url
   * @param alLImages the all images collected throughout the links
   */
  public ImageHandler(String url, ArrayList<String> alLImages) {
    this.url = url;
    this.alLImages = alLImages;
  }

  @Override
  public void run() {
    long id = Thread.currentThread().getId();
    System.out.println("Thread " + id + " is on: " + url);
    try {
      getImages(this.url);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets images of the given url.
   *
   * @param url the url
   */
  protected void getImages(String url) {
    try {
      Connection.Response response = Jsoup.connect(url).execute();
      // if the status code is not 200 then return
      int statusCode = response.statusCode();
      if (statusCode != 200) {
        return;
      }

      // parse and get all image tags
      Document doc = response.parse();
      Elements images = doc.select("img");

      // loop thru image tags
      for (Element image : images) {
        String imageUrl = image.absUrl("src");

        // prevent duplicate images
        if (!alLImages.contains(imageUrl)) {
          alLImages.add(imageUrl);
        }
      }

    } // end of try
    catch (IOException e) {
      e.printStackTrace();
    } // end of catch
  }
}
