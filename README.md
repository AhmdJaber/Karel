# Karel The Robot

This project explores and implements an optimized algorithm for dividing a 2D grid map into 4 equal-sized chambers using Karel the Robot. The challenge lies in placing the minimum number of beepers on various grid sizes to achieve equally sized partitions.

## Problem Overview

Given a map of dimensions `N x M`, the goal is to:
- Divide it into four equal chambers.
- Use the least number of beepers possible.
- Ensure the chambers are maximally sized and balanced.

## Key Concepts

- Supports different types of grid sizes:
  - Odd × Odd
  - Even × Odd
  - Even × Even
  - Special cases like (2 x N), (1 x N), etc.
- Uses mathematical formulas to calculate:
  - Maximum chamber size.
  - Optimal beeper placement.
  - Number of removable/movable beepers.

## Main Formulas

- Maximum chamber size:

  Chamber Size = floor((N × M - (N + M - 2)) / 4)

- Removable beepers (for Even × Odd):

  Removable Beepers = floor((Odd side length - 1) / 4)

- Beepers to move (for Even × Even):

  a = ((N / 2) - 1) * (M / 2)  
  b = (N / 2) * ((M / 2) - 1)  
  Beepers to move = |a - b| / 2

## Implementation Notes

- No use of `pickBeeper()`.
- The beeper map is calculated mathematically and placed directly.

## Author

Ahmad Nabeel Al-Jaber

## Acknowledgement

Thanks for reviewing the approach and strategies explored in this problem.
