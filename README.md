# Have Another Pretty Particle. Yayyyyyyyy!!!
## A collection of custom-made particles for use at BlanketCon 2025!

Yippee particles!

# Particle Docs:
## Jellyfish
Simple: `happy:jellyfish_simple`  
Configurable: `happy:jellyfish`
- `scale` (float): Determines the size of the particle.
- `max_age` (int): Determines the max amount of ticks the particle will last.
- `bounces` (int): Determines the amount of bounces the jellyfish will preform throughout its max age. This affects the animation speed, and distance traveled.
- `start_color` (Vector3f): If given, the start color of a 2 color transition. If not given, a single random color is chosen, and no transition happens.
- `end_color` (Vector3f): The end color of a 2 color transition.

## Cloud
Simple: `happy:cloud_simple`  
Configurable: `happy:cloud`
- `scale` (float): Determines the size of the particle.
- `max_age` (int): Determines the max amount of ticks the particle will last.
- `max_age_random` (int): The max number of extra ticks the particle may last. A random number from 0 to this number will be picked and added to the max age.
- `hits_until_fade` (int): The max number of times the particle can make contact with a block until it begins to fade out. There is a 10 tick delay between hits, meaning that if it is in a corner, it will count as hitting many blocks.
- `fade_amount` (float): The amount of alpha/opacity to remove each tick while fading out. **Alpha is between 0 and 1**, meaning that uses "0" will result in no fade out, and "1" will result in instant fadeout.
- `velocity_after_hit` (Vector3f): The amount of velocity to add after the particles comes in contact with a block. Note: This velocity is added after EVERY block hit, not just once!

## Sparkle(WIP)
### Colored Presets:
Pink: `happy:sparkle`
