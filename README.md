## TruSTAR Questionnaire

### How to build
`$ ./gradlew clean build`

`$ java -jar build/libs/plucero-interview-1.0.0.jar`

This will print the result output of the Question 4.

Also, you can run the tests with

`$ ./gradlew clean test`


### Question 5

In Question 1 to 3, a function as a service provider like AWS Lambda could be suitable for the job since we can 
auto-scale them horizontally very easily. Regarding the algorithms, in the Q1 I can use parallelStream() to take 
advantage of multi-cores processors, but in Q2 and Q3 not, since each iteration depends on the previous one. 
Apart from that, the algorithms aren't very complex, they are O(n), so they run in linear time. Maybe the patterns 
to ignore in the Q3 can be done separately from the other part since you can remove them before or after the matching part.
In Question 4, I think the main bottlenecks are the 2 HTTP calls. Maybe they can be handled by 2 different jobs. 
One who has the responsibility to fetch the HTML of GitHub and extract the JSON URLs and other who fetch each JSON 
content and extract the APT info. Also, the latter can be run in parallel using some kind of function as a service provider. 
Finally, we can connect the two by a publisher-subscriber model.