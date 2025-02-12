# Table of contents

1. [About](#about)
2. [General assumptions](#general-assumptions)
3. [MVP](#mvp)

## About

This is a project of a users' public transport routes. As a [Next Level Dev](https://discord.com/invite/gTCCHagd9a) community (🇵🇱) we want to improve our programming skills by conducting such project.

## General assumptions

Currently we decided to use such technologies:

- Java/Kotlin
- Spring

If you want to contribute you are welcome to do so! Of course we are not limited to use only above mentioned technologies. It is welcomed to add there CI/CD tools, frontend, Docker and so on!

## MVP

We decided to use Warsaw public transport API: https://api.um.warszawa.pl/. So Warsaw is out first goal.

1st milestone:
- [ ] Integration with the Warsaw Public Transport API (https://api.um.warszawa.pl/) - real-time location of buses and trams.
- [ ] User's search history of routes.
- [ ] Selection of a route from search history and search for it again.
- [ ] Selection of a route from search history and its reversal.
- [ ] Displaying the most frequently searched route from the last hour/24 hours among searches throughout the entire application.
- [ ] Calculating time for the searched route.
- [ ] Searching for a route with the possibility of stopping at selected places along the way.
- [ ] Planning a route consisting of n points and adding the amount of time I will spend at each point will allow the application to calculate how long the entire journey will take.
- [ ] Change the time that I spend at a given point, and let the app recalculate for me how long the entire trip will take and which connections I should use.
- [ ] Planning trips to parks, museums, or other places. I would like the application to show me how to move between them.
- [ ] Planning a route between n points, taking into account the fact that I want to walk between m of them.
- [ ] Basic route search (start, destination, time optimization).
- [ ] Search history + ability to re-search routes
- [ ] Reversing the route (from A → B to B → A)
- [ ] Estimated travel time (based on communication data)

### New Route Search Functionality

We have added a new route search functionality to the project. This allows users to search for a route from point A to point B using the following endpoint:

- `GET /route/search?from=?&to=?`

This endpoint takes two parameters, `from` and `to`, which represent the starting point and destination point of the route, respectively. The search results will provide the route details between these points.

Draft of "nice to haves":
- [ ] Planning group trips (e.g. friends meet in one place, and the system plans optimal routes for everyone).
- [ ] Trip planning - AI generates ready-made trip plans, taking into account the user's preferences (e.g. visiting museums, dining at restaurants etc.).
- [ ] Integration with weather data (e.g. OpenWeatherAPI) and events.
- [ ] Integration with systems such as, for example, Veturilo or electric scooters.
