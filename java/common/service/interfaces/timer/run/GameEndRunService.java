package common.service.interfaces.timer.run;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface GameEndRunService {
	/**
	 * ゲーム終了時に走る処理
	 * @param context
	 * @throws JobExecutionException
	 */
	void execute(JobExecutionContext context) throws JobExecutionException;
}
