# Changelog

## [0.0.1]
### Added
- Graphics Components framework
  - textfield
  - button
  - label
- Component Factory
- JPanels layout
- JPInterface
- Logger
- Fractal Abstract class
- Pixel class
- Linear Transformations (zoom, translate and rotate)
- Concrete Fractals
  - Iterative Mandelbrot

## [0.0.2]
### Added
- components
  - iterations textfield
  - location label
  - render button

### Changed
- improved Logger
  - multiple messages
- cleaned up render button
- cleaned up JPInterface and Canvas

## [0.0.3]
### Added
- Generator class (SwingWorker) for calculating the fractal asynchronously  
- Added MouseListener to Canvas and fixed mirror bug (retained java axis orientation, might change this later)

### Changed
- Moved all swing components to EDT
- Moved fractal calculation to SwingWorker
- Fixed JProgressBar and progressMessage in feedback panel

### Removed
- unused KeyListener package and class