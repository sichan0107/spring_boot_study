package board.common;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardFileDto;

@Component
public class FileUtils {
	public List<BoardFileDto> parseFileDto(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws IllegalStateException, IOException{
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		List<BoardFileDto> fileList = new ArrayList<>();
		// ---- 새로운 폴더 생성
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/"+current.format(format);
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for(MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")){
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					
					file = new File(path + "/" +newFileName); // images/yyyyMMdd/nanotime + .확장자명
					multipartFile.transferTo(file); //업로드된 파일을 새로운 이름으로 바꾸어 지정된 경로에 저장함.
				}
			}
		}
		return fileList;
	}
	
}
