package com.jobportal.Job.Portal.Backend.API.service.serviceImpl;


import com.jobportal.Job.Portal.Backend.API.service.UploadFileService;
import com.jobportal.Job.Portal.Backend.API.util.ImageCompressionUtil;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    public String uploadFile(MultipartFile file, String baseDirectory, String finalDirectory, boolean isImage) throws IOException {
        if (file == null) {
            return null;
        }
        String currentDateAndTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String fileExtension = originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : "";
        String refactoredFileName = originalFileName.replace(" ", "-");
        if (isImage) {
            if (!fileExtension.equalsIgnoreCase(".jpg") &&
                    !fileExtension.equalsIgnoreCase(".jpeg") &&
                    !fileExtension.equalsIgnoreCase(".png")) {
                return String.valueOf(ResponseUtil.getFailureResponse("Unsupported file format. Please upload an image in JPG, JPEG, or PNG format."));
            }
        } else {
            if (!fileExtension.equalsIgnoreCase(".pdf")) {
                return String.valueOf(ResponseUtil.getFailureResponse(
                        "Unsupported file format. Please upload a PDF file."));
            }
        }
        String finalFileName = currentDateAndTime + "-" + refactoredFileName;
        File directory = new File(baseDirectory + finalDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = baseDirectory + finalDirectory + finalFileName;
        if (isImage) {
            String format = fileExtension.startsWith(".") ? fileExtension.substring(1) : fileExtension;
            ImageCompressionUtil.compressImage(file.getInputStream(), filePath, 0.9f, format);
        } else {
            Files.copy(file.getInputStream(), Paths.get(filePath));
        }
        return finalFileName;
    }

}
