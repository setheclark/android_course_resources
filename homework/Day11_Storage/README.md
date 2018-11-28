# Homework

## Navigation

Add the ability for a user to have more than one favorite breed at a time.  This can be done by storing a value in the breeds table indicating whether or not a particular breed is the user's favorite.  They should be able to mark a breed as their favorite from the dog image screen when they come to it via the breed list screen.  Once a dog is marked as their favorite the button on the image screen should switch such that clicking it will remove the breed as a favorite.  Additionally, there should be some visual indication on the breeds list screen at whether a breed is marked as their favorite.

To help get you started, here are the two of the functions you might need to add to the `BreedDao` interface:

```Kotlin
@Update(onConflict = OnConflictStrategy.REPLACE)
fun updateBreed(breed: Breed)

@Query("SELECT * FROM breeds WHERE favorite=1 ORDER BY RANDOM() LIMIT 1")
fun randomFavoriteBreed(): Breed
```

Going straight from the home screen to the dog image screen should display an image of one of their favorite breeds.  If there are no favorite breeds, then just show a random dog image.

[Example App Video](./day_11_homework.webm)
