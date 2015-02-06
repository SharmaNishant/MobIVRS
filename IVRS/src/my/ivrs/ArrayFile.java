package my.ivrs;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 
 * @author Nishant
 *
 */
@SuppressWarnings("serial")
public class ArrayFile implements Serializable{

	public ArrayList<SurveyFile> list;
	
	ArrayFile(ArrayList<SurveyFile> temp)
	{
		list=temp;
	}
}
