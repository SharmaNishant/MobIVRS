**NOTE:** This project was done as part of a coursework and the repo is ported
from svn. This is for backup purposes only. The code is not maintained. Please
reach out to the author if you have any questions regarding this work.

# MobIVRS
Implementaion of IVRS on Android (MobIVRS)

## Objective
* IVR based survey on Android – One can easily take the survey about any issue and record it in the database.
* Appointment – One can schedule his/her appointments throughout the day using IVR system.
* More features will be added later.

## Architecture
* Homepage – ActivityHomepage
  * Home page of the application.
  * Contains links to configure the Survey and Appointments (not yet implemented) Options

* Survey Options Page – ActivitySurvey
  Page that contains following options –
  * A button to start or stop the IVRS Service.
  * To set the Survey (Question and corresponding options).
  * To delete the current survey (stops the IVRS service).
  * To view the Responses for the current survey

  * When SET SURVEY is selected
    * Input for Questions and corresponding options are displayed to set.
    * Once done, the previous result would be deleted.
    * “Question” file is used to save the questions to a file.

  * When VIEW RESULT is selected
    * “ShowResult” Activity is used to display the Results.
    * “Result” file is used to populate the responses for the questions.

  * When DELETE SURVEY is selected
    * Any survey or response in the system is deleted, if found.

* Phone State Listening Service - CallHandlerService
  * If service is set ON (by the Homepage), then
    * Application will programmatically receive the call
    * It will start the activity “IVR_Activity” –

* It will show a timer for the call duration.

* And take questions from the “Result” file functions

* Speak out every Question and get the response via DTMF tones

* And store the result using the “Result” file functions

* If the call gets disconnected in between,
  * the responses stored until then would be stored.
  * And the activity stops the timer and gets removed after 10 seconds.

* For the DTMF recognition –
  * The dtmf and math packages (with suitable modifications), are used
    * Reference : Android DTMF decoder
    https://code.google.com/p/dtmf-decoder/

* Appointment Options Page – SetAppointment
  * Page contains the following options (None of them have been implemented yet) -
  * New Schedule
  * View Appointments
  * Delete Appointments

Hardware and Software Dependencies
Requires an Android Phone with Android Version more than 3.0 (HONEYCOMB)

How to Install
  Just install using the APK.

## Contribution
* Nishant Sharma (2011070)
* Rishav Jain (2011088)
* Mayank Garg (2011065)
* Saurabh Arya (2011100)
