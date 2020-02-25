package liar.option.vote.service;

import java.util.List;

import liar.option.vote.dto.VoteDto;

public interface VoteService {
	/**
	 * 投票する
	 * @param value
	 * @param gameId
	 */
	void vote(String value, String gameId);
	
	List<VoteDto> getVote(String gameId);

}
