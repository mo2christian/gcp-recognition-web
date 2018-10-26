package com.mo2christian.recognition.web.service;

import com.google.cloud.storage.*;
import com.mo2christian.recognition.web.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Service permettant d'enregistrer les fichiers
 */
public class StorageService {

    private final Logger logger = LogManager.getLogger(DetectService.class);

    private String bucketName;

    private String folder;

    public StorageService(){
    }

    public void init(){
        String bucketEnv = System.getenv("BUCKET_NAME");
        if (bucketEnv != null){
            bucketName = bucketEnv;
        }
        String folderEnv = System.getenv("FOLDER");
        if (folderEnv != null){
            folder = folderEnv;
        }
    }

    public void setDefaultBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setDefaultFolder(String folder) {
        this.folder = folder;
    }

    public Image upload(File file) throws IOException {
        logger.debug("Enregistrement de l'image {}", file.getName());
        String name = new SimpleDateFormat("ssmmHHddMMyyyy").format(Date.from(Instant.now()));
        BlobId blobId = BlobId.of(bucketName, folder+ "/" + name);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Blob blob = storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        String url = blob.signUrl(3, TimeUnit.MINUTES).toString();
        Image image = new Image();
        image.setUrl(url);
        image.setBucketName(blobId.getBucket());
        image.setObjectName(blobId.getName());
        return image;
    }
}
