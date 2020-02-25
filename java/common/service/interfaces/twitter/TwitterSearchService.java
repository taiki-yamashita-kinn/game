package common.service.interfaces.twitter;

import twitter4j.TwitterException;

public interface TwitterSearchService {
	/**
	 * tweetを検索する
	 */
	void tweetSearch() throws TwitterException;

}
