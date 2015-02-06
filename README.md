# MobIVRS
Implementaion of IVRS on Android (MobIVRS)
<br />
Objective –<br />
• IVR based survey on Android – One can easily take the survey about any issue and record it in the database.<br />
• Appointment – One can schedule his/her appointments throughout the day using IVR system.<br />
• More features will be added later.<br />
<br />
Architecture –<br />
• Homepage – ActivityHomepage<br />
  o Home page of the application.<br />
  o Contains links to configure the Survey and Appointments (not yet implemented) Options<br />
  <br />
• Survey Options Page – ActivitySurvey<br />
  Page that contains following options –<br />
  o A button to start or stop the IVRS Service.<br />
  o To set the Survey (Question and corresponding options).<br />
  o To delete the current survey (stops the IVRS service).<br />
  o To view the Responses for the current survey<br />
  <br />
  o When SET SURVEY is selected<br />
     Input for Questions and corresponding options are displayed to set.<br />
     Once done, the previous result would be deleted.<br />
     “Question” file is used to save the questions to a file.<br />
    <br />
  o When VIEW RESULT is selected<br />
     “ShowResult” Activity is used to display the Results.<br />
     “Result” file is used to populate the responses for the questions.<br />
    <br />
  o When DELETE SURVEY is selected<br />
     Any survey or response in the system is deleted, if found.<br />
<br />
• Phone State Listening Service - CallHandlerService<br />
  o If service is set ON (by the Homepage), then<br />
     Application will programmatically receive the call<br />
     It will start the activity “IVR_Activity” –<br />
<br />
• It will show a timer for the call duration.<br />
<br />
• And take questions from the “Result” file functions<br />
<br />
• Speak out every Question and get the response via DTMF tones<br />
<br />
• And store the result using the “Result” file functions<br />
<br />
• If the call gets disconnected in between,<br />
  o the responses stored until then would be stored.<br />
  o And the activity stops the timer and gets removed after 10 seconds.<br />
<br />
• For the DTMF recognition –<br />
  o The dtmf and math packages (with suitable modifications), are used<br />
     Reference : Android DTMF decoder<br />
    https://code.google.com/p/dtmf-decoder/<br />
<br />
• Appointment Options Page – SetAppointment<br />
  o Page contains the following options (None of them have been implemented yet) -<br />
  o New Schedule<br />
  o View Appointments<br />
  o Delete Appointments<br />
<br />
Hardware and Software Dependencies<br />
Requires an Android Phone with Android Version more than 3.0 (HONEYCOMB)<br />
<br />
How to Install<br />
  Just install using the APK.<br />
<br />
Contribution:<br />
• Nishant Sharma (2011070)<br />
• Rishav Jain (2011088)<br />
• Mayank Garg (2011065)<br />
• Saurabh Arya (2011100)<br />
