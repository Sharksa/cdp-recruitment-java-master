## Track and analyse of issues

```
1. Adding review does not work
```

* First I run the app, open the dev tools from Google Chrome at console tab, and test to review by clicking on stars. 
* Then I refresh the page, the review doesn't appear.
* On Google dev tool Console there was no error and on java console there was no call.
* So I checked on Back the EventContoller class, there was no implementation inside the update method
* I add a test with the behavior I expected.
* Then I added the implementation. 
* Update on entity the field bands for propagate the save with cascade = CascadeType.ALL 
* Change Generation type of entities by IDENTITY.
* Launch test, it's ok
* Test on application, it's ok, go to next issue.


```
2. Using the delete button works but elements comes back when i refresh the page 
```
* When I read the issue, I think at first that this is an back issue like something not really deleted on database and so that's come back at front refresh.
* So I checked on Back service in the EventService class.
* There was no transactional context, so the interpreter doesn't know the transaction delimitation.
* I check the test, there was none, so I added a test with the behavior I expected.
* Then I have added the annotation.
* I have updated schema.sql for each foreign key constraint => on update cascade on delete cascade
* Launch test, it's ok
* Test on application, it's ok, 


