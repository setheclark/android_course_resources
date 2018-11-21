package io.sethclark.dogceo.model

class RandomDogImage(val status: String, val message: String)
class DogBreeds(val status: String, val message: Map<String, List<String>>)

open class Breed(val name: String)
class SubBreed(name: String, val parent: String) : Breed(name)