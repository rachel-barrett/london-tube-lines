This project aims to be a minimal rest service, that could serve as a template for other rest services.

Things I aim to achieve/ experiment with in this template
* Identification of the best modern scala libraries to use
* No difficult to understand implicits passed around like ActorSystem and ActorMaterializer
* Initialise everything functionally, including config
* Better unit testing of route layer
* No cake
* Co-location of entities and services 

I've noticed this is quite similar to [scala-pet-store](https://github.com/pauljamescleary/scala-pet-store) linked to as an example project from the [cats website](https://typelevel.org/cats/resources_for_learners.html). Since this commit (53c92e6ae319edb51f54bac0df6289abcf9d4091), I've made changes influenced by the project linked.