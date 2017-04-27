# Spring snadbox
1. Implementing new (useful or not) features to extend Spring framework
2. Playing around with interesting Spring solutions

## Features
### @MapAutowired
#### Specs
- Given a Spring component that has a method (i.e. getKey()) annotated as a @MapKey or implemented form a dedicated interface
- When field is annotated with @MapAutowired
- Then a Map is injected to this filed where Key = value returned from getKey() method and Value = Spring bean itself
#### Notes
- Could it be possible to provide a Interface<K> where K is a Key type for Map?
