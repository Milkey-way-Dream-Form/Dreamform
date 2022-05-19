package com.milkyway.dreamform.service;

import com.milkyway.dreamform.model.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//빈 사용 가능 애너테이션
@Component
public class ImageService {

    // 파일 로컬 저장 경로
    @Value("${file.dir}")
    private String fileDir;

    // 경로+전달 받은 파일이름을 합해서 리턴해주는 메소드
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    // 전달 받은 파일 이름 존재 시 삭제
    public void deleteFile(String saveFileName) {
        File file = new File(getFullPath(saveFileName));
        if (file.exists()) file.delete();
    }
    
    // 받은 파일들을 전부 List에 담아 반환
    public List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> saveFileList = new ArrayList<>(); //UploadFile List 생성
        for (MultipartFile multipartFile : multipartFiles) { // 받아온 MultipartFile 반복문
            if (!multipartFile.isEmpty()) { // multipartfile이 있으면 UploadFile List에 add
                saveFileList.add(saveFile(multipartFile));
            }
        }
        return saveFileList;
    }

    // 받은 파일
    public UploadFile saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) { // 파일이 없으면 그냥 null 반환
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); //받은 파일의 원본이름
        String saveFileName = createSaveFileName(originalFilename); // 받은 파일의 이름 변형
        multipartFile.transferTo(new File(getFullPath(saveFileName))); // 변형한 파일 이름으로 경로에 파일 생성

        return new UploadFile(originalFilename, saveFileName); //파일의 원본이름과 변형한 이름 반환해 db에 upload
    }

    public MultipartFile Image

    private String createSaveFileName(String originalFilename) { // 파일의 원본 이름을 받아
        String uuid = UUID.randomUUID().toString(); // 중복 방지를 위해 랜덤 코드 생성
        String ext = extractExt(originalFilename); // 원본 파일의 확장자 찾기
        return uuid + "." + ext; // 랜덤코드.확장자 반환
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
