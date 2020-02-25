package common.service.interfaces.twitter;
import twitter4j.TwitterException;

public interface TwitterPostService {

	/**
	 * tweetを投稿する
	 * @throws TwitterException
	 */
	void postTwitter(String text) throws TwitterException;
}

