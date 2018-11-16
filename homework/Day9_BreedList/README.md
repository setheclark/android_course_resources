# Homework

## Dog Breeds List

Using the architecture patterns we've discussed so far in the course (ViewModel, Repository, ...) write an application that will request the list of dog breeds from the [Dog API](https://dog.ceo/dog-api/documentation/) and present the breeds in a list on the screen.  Sub-breeds should have a different item display in the list than breeds, but should be directly below the associated breed in the list.

#### For an extra challenge

If you complete the initial requirements and wish to truly challenge yourself you can make each item in the list clickable, and when clicked a new page will load with a random image of the selected breed.

Here is a bit of code to help get you started:
```Kotlin
class BreedsResponse(
    val status: String,
    val message: Map<String, List<String>>
)
```

[Example App Video](./day_9_homework.webm)