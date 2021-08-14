package com.rest.ai.myCallimo.services.facade;

import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/***
 * Class description:
 * FlickrService to save image in flickr storage
 * @author yassine
 * @version v.0.0.1
 */

public interface FlickrService {

    /**
     * @param photo photo to save
     * @param email user to update his avatar
     * @return url to access to image
     * @throws FlickrException
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    String savePhoto(InputStream photo, String email) throws FlickrException, IOException, ExecutionException, InterruptedException;
}
