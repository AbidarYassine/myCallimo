package com.rest.ai.myCallimo.services.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.rest.ai.myCallimo.dao.AdminDao;
import com.rest.ai.myCallimo.dao.CallerDao;
import com.rest.ai.myCallimo.dao.SupervisorDao;
import com.rest.ai.myCallimo.dto.UserDto;
import com.rest.ai.myCallimo.entities.AdminEntity;
import com.rest.ai.myCallimo.entities.CallerEntity;
import com.rest.ai.myCallimo.entities.SupervisorEntity;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.services.facade.FlickrService;
import com.rest.ai.myCallimo.services.facade.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {
    @Value("${flickr.apiKey}")
    private String apiKey;

    @Value("${flickr.apiSecret}")
    private String apiSecret;

    @Value("${flickr.appKey}")
    private String appKey;

    @Value("${flickr.appSecret}")
    private String appSecret;

    private Flickr flickr;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private SupervisorDao supervisorDao;
    @Autowired
    private CallerDao callerDao;

    @Override
    public String savePhoto(InputStream photo, String email) throws FlickrException, IOException, ExecutionException, InterruptedException {
        connect();
        UserDto userDto = userService.findByEmail(email);
        if (userDto == null) throw new UserNotFoundException("Utilisateur introuvable !!");
        String role = userDto.getRole();
        UploadMetaData uploadMetaData = new UploadMetaData();
        String photoId = flickr.getUploader().upload(photo, uploadMetaData);
        String url = flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
        switch (role) {
            case "ADMIN":
                AdminEntity adminEntity = adminDao.findByEmail(userDto.getEmail());
                adminEntity.setAvatar(url);
                adminDao.save(adminEntity);
                break;
            case "SUPERVISOR":
                SupervisorEntity supervisorEntity = supervisorDao.findByEmail(userDto.getEmail());
                supervisorEntity.setAvatar(url);
                supervisorDao.save(supervisorEntity);
                break;
            case "CALLER":
                CallerEntity callerEntity = callerDao.findByEmail(userDto.getEmail());
                callerEntity.setAvatar(url);
                callerDao.save(callerEntity);
                break;
            default:
                return null;
        }
        return url;
    }

    private void connect() throws InterruptedException, ExecutionException, IOException, FlickrException {
        flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();
        auth.setPermission(Permission.DELETE);
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
    }

    public String uploadAudio(InputStream audio) throws FlickrException, IOException, ExecutionException, InterruptedException {
        connect();
        UploadMetaData uploadMetaData = new UploadMetaData();
        return flickr.getUploader().upload(audio, uploadMetaData);

    }
}
