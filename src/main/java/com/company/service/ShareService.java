package com.company.service;

import com.company.entities.Shares;
import com.company.entities.User;
import com.company.repository.SharesRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class ShareService {


    @Autowired
    private SharesRepository sharesRepository;

    private static final String UPLOAD_DIR = "./images/uploads/";



    public void save(User user, MultipartFile file) throws IOException {
        Shares shares = new Shares();
        String imageName = UUID.randomUUID().toString();
        String filePath = UPLOAD_DIR + imageName;
        File destinationFile = new File(filePath);
        byte[] fileData = file.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
        fileOutputStream.write(fileData);
        fileOutputStream.close();
        shares.setImageName(imageName);
        shares.setUser(user);
        sharesRepository.save(shares);
    }



    public Shares findById(long id){
        return sharesRepository.findById(id).get();
    }

    @SneakyThrows
    public void deleteById(Long sharesId) {
        Shares shares= findById(sharesId);
        if (shares!= null){
            sharesRepository.deleteById(sharesId);
            Files.delete(Path.of(UPLOAD_DIR, shares.getImageName()));
        }
    }
}
