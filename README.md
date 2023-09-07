# WalkBaBa Backend

All backend code for WalkBaBa app

WalkBaba is an app originally developed in just two weeks by KG, as a final project at Salt. Deployed link to follow!

https://github.com/Khafan-Gasten/WalkBaba-backend/assets/109215585/e52541dc-e694-4737-9885-36451d79e3de

AI powered walks to explore your city!

Future Work:

To keep the app deployed: 
- Switch database provider to cheaper alternative to azure (maybe switch to MongoDB). VERY HIGH PRIORITY.
- Work out if you have to pay for Microsoft azure deployment VERY HIGH PRIORITY. 
- Make all API keys private. VERY HIGH PRIORITY.
- Switch google api account. VERY HIGH PRIORITY. Ma Boo's job.
- Reduce the number of google api calls MEDIUM-HIGH PRIORITY:
  - Can we save google images instead of recalling the api?
  - Use the direction data from the backend instead of recalling in the frontend.
  - Can we use the maps embed api instead of the javascript maps api?
  - Can we find an alternative to Cornelis' autocomplete (e.g. react library or do one call ourselves).
  - Improve code efficiency.
  - Use some caching.
- Add adverts to page to cover some running costs LOW PRIORITY.

To Present app and code to employers:
- Make the readMe nice. MEDIUM-HIGH PRIORITY.
  - Upload presentation diagram.
  - Nice description.
  - Talk about the stack. 
- Tidy backend code. MEDIUM-HIGH PRIORITY.
  - Google api service needs overhaul.
  - Change google response classes into records. 
- Tidy frontend code MEDIUM-HIGH PRIORITY.
  - Replace prop drilling (maybe with app context).
  - Can we have less </div>s 
- Bug fixes. HIGH PRIORITY
  - Refresh issue on all pages. (especially saved page @Mahboub-sh).
  - Back button issue on map gallery. (duplication)
- Improve error messages. HIGH PRIORITY.
  - If nothing is returned for gallery or save page show a message.
  - More information passed from back to front end when something goes wrong.

To Improve the app:

- Mobile downloadable app. Priority: When Saghi fancies it.
- Thread ChatGpt requests. LOW PRIORITY.
- Add lots of major cities to the database. LOW PRIORITY.
- Multiple users and user login. LOW PRIORITY.
  - Updating DB.
  - Login page.
  - Authentification.
- Improve photo galleries LOW PRIORITY.
 
