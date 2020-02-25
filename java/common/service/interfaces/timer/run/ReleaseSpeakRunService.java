package common.service.interfaces.timer.run;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface ReleaseSpeakRunService {
	void execute(JobExecutionContext context) throws JobExecutionException;

}
