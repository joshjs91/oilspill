# Oil spill cleanup


## how to run

run the following command:

`$ ./runApp`

## How to use

To create a oil spill cleanup simulation make a request similar to this: 

`curl --location --request POST 'http://localhost:8080/cleanup' \
 --header 'Content-Type: application/json' \
 --data-raw '{
   "areaSize" : [5, 5],
   "startingPosition" : [1, 2],
   "oilPatches" : [
     [1, 0],
     [2, 2],
     [2, 3]
   ],
   "navigationInstructions" : "NNESEESWNWW"
 }'`


## Design decisions 
- never initialised an array that represented the map as this would mean if there was a huge map, there could be memory problems, opposed to just
 storing the locations of the oil spills. 



## Future enhancements with more time
- validate the oil spill locations, in case they are outside of range.
- validate clean initial location, in case it is outside of range.
- validate the length of the oil spill integer arrays are 2. 
- this validation would be using new spring validator
