package com.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID(); // 서로다른 개체 구별하기 위한 이름부여
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension; // uuid + 확장명(jpg)
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;  //디렉토리와 파일명 조합
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); //파일출력스트림제작
        fos.write(fileData); ///파일출력스트림
        fos.close();
        return savedFileName; //업로드된 파일이름을 반환
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath); //파일의 저장된 경로를 이용하여 파일 객체를 생성
        if(deleteFile.exists()) { // 파일이 존재하면
            deleteFile.delete(); //파일 삭제
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}