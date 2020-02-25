package common.service.interfaces.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import common.dto.LiarGameDto;

public interface GoogleCalenderService {
	void registerGameCalendar (LiarGameDto newGame) throws IOException, GeneralSecurityException;
}
