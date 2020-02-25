package common.service.interfaces.timer.run;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 発言禁止時間に行う処理
 * @author taita
 *
 */
public interface BanSpeakRunService {

	void execute(JobExecutionContext context) throws JobExecutionException ;
}
