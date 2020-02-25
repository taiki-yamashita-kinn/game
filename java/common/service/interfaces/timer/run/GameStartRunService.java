package common.service.interfaces.timer.run;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface GameStartRunService {

	/**
	 * ゲームのスタート時と終了時の実行内容
	 * @param context
	 * @throws JobExecutionException
	 */
	void execute(JobExecutionContext context) throws JobExecutionException ;
}
