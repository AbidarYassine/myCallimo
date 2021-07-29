package com.rest.ai.myCallimo.services.facade;

import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public interface FlickrService {

    String savePhoto(InputStream photo, String email) throws FlickrException, IOException, ExecutionException, InterruptedException;
}
