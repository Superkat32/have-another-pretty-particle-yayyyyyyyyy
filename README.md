# Have Another Pretty Particle. Yayyyyyyyy!!!
## A collection of custom-made particles for use at BlanketCon 2025!

Yippee particles!

# Particle How-To's
Various how-to's for vanilla particle commands.
### Custom Parameters
Add a `{<param name>:<param value>}` after the particle id.  
Example with param. name of `max_age` with an integer of `50`.
```Particle Command
/particle happy:jellyfish{max_age:50} ~ ~1 ~
```

Floats can be specified with a decimal point added(e.g. `1.0` or `1.56`).

#### Vector3f Paremeter
Vector3f parameters can be added with the value of `[<0f-1f>, <0f-1f>, <0f-1f>]`
Example with param. name of `velocity_after_hit` with a value of `[0.2, 0.3, 0.6]`.
```Particle Command
/particle happy:cloud{velocity_after_hit:[0.2, 0.3, 0.6]} ~ ~1 ~
```

# Particle Docs:
## Jellyfish
```Particle ID
happy:jellyfish
```
- `scale` (float): Determines the size of the particle.
- `max_age` (int): Determines the max amount of ticks the particle will last.
- `bounces` (int): Determines the amount of bounces the jellyfish will preform throughout its max age. This affects the animation speed, and distance traveled.
- `start_color` (Vector3f): If given, the start color of a 2 color transition. If not given, a single random color is chosen, and no transition happens.
- `end_color` (Vector3f): The end color of a 2 color transition.

## Cloud
```Particle ID
happy:cloud
```
- `scale` (float): Determines the size of the particle.
- `max_age` (int): Determines the max amount of ticks the particle will last.
- `max_age_random` (int): The max number of extra ticks the particle may last. A random number from 0 to this number will be picked and added to the max age.
- `hits_until_fade` (int): The max number of times the particle can make contact with a block until it begins to fade out. There is a 10 tick delay between hits, meaning that if it is in a corner, it will count as hitting many blocks.
- `fade_amount` (float): The amount of alpha/opacity to remove each tick while fading out. **Alpha is between 0 and 1**, meaning that uses "0" will result in no fade out, and "1" will result in instant fadeout.
- `velocity_after_hit` (Vector3f): The amount of velocity to add after the particles comes in contact with a block. Note: This velocity is added after EVERY block hit, not just once!

## Sparkle(WIP)
### Colored Presets:
#### Pink sparkle(ID could change!):
```Particle ID
happy:pink_sparkle
```
