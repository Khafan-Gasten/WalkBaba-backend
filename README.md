# WalkBaBa Backend

All backend code for WalkBaBa app

WalkBaba is an app originally developed in just two weeks by KG, as a final project at Salt. Deployed link to follow!

https://github.com/Khafan-Gasten/WalkBaba-backend/assets/109215585/e52541dc-e694-4737-9885-36451d79e3de

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

https://github.com/Khafan-Gasten/WalkBaba-backend/assets/109215585/e52541dc-e694-4737-9885-36451d79e3de


- Improve photo galleries LOW PRIORITY.
 

WalkBaBa Description:

Ever been in a new and exciting city and thought I’d love to take in these new surroundings on a relaxed, charming and picturesque walk? Of course you have!!! But despite the internet’s seemingly infinite knowledge all it has to offer is scattered blog posts, with printed maps that look like they haven’t been updated since before your very old man was born. We feel you mijn vriend, we feel you.


Fear not though jongen, as finally, in July 2023 four brave souls set out to solve this existential problem. Armed with just their wit, dashing good looks and a healthy serving of programming flair, these once in a generation heroes bring you a tool that is set to disrupt the travel industry forever, creating a transformative effect similar to that of the internet, AI or plane travel. Today, we present to you WalkBaBa. It’s so simple, yet so beautiful at the same time. Give WalkBaBa a city and a walk duration (and maybe some extra requests) and it will give the perfect route for you, with HD photos of the route highlights to boot. As if that wasn’t enough, directions to the nearest waypoint on the route will be provided. I know, I know, it sounds tooo good to be true. But there are additional features. You can save your favourite routes for later, see reviews of routes and rate a route you have completed! Worried about being rained on, not with WalkBaBa. Check the weather at your chosen walk time and receive a warning of high wind or rain, together with an alternate walk time suggestion. By now I’m sure you have lept aboard the WalkBaba train as we drive full steam ahead into the future. It’s a brave new world out there Jongen and you are at the forefront. 

"This app is soooo good." - Barack Obama, July 2023 (thanks Barack)
