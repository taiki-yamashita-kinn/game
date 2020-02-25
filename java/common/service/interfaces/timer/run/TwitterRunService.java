package common.service.interfaces.timer.run;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface TwitterRunService {

	//実行する内容
	//tweetプログラム
	 void execute(JobExecutionContext context) throws JobExecutionException;

}
