package my.ivrs;

import java.io.Serializable;

/**
 * 
 * @author Nishant
 *
 */
@SuppressWarnings("serial")
public class SurveyFile implements Serializable {

	public String question;
	public String[] options;
	
	SurveyFile(String que,String[] opt)
	{
		question=que;
		options=opt;
	}
}
