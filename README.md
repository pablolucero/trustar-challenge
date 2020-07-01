## TruSTAR Questionnaire

### How to build
`$ ./gradlew clean build`

`$ java -jar build/libs/plucero-interview-1.0.0.jar`

### Question 5
I think the main bottleneck of Question 4 are the 2 HTTP calls. 
Maybe they can be handled by 2 different jobs. 
One who has the responsibility to fetch the HTML of GitHub and extract the JSON URLs 
and other that fetch each JSON content and extract the APT info. 
The latter can be run in parallel also using some kind of function as a service provider. 
Finally, we can connect the 2 by a publisher-subscriber model.