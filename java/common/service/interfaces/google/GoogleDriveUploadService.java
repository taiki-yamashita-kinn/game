package common.service.interfaces.google;

import java.io.IOException;

/**
 * Googledriveにファイルをuploadするクラス
 * @author taita
 *
 */
public interface GoogleDriveUploadService {

	/**
	 * Googleドライブにファイルをアップロードする
	 * @param fileName
	 * @param filePath
	 * @throws IOException
	 */
    void googleUpload(String fileName, java.io.File filePath) throws IOException;

}