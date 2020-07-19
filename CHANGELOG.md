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
  - tooltip for all messages
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

## [0.0.3] - non_functional_jpi

***Note*** <br>
The original idea of making the **JPInterface** a class to access and retrieve data has been scrapped. The branch name is therefore misleading. It hasn't lost any of its functionality such as: **save()** and **update()**.

### Added
- functional zoomFactor JTextField
- **jpi.afterRender()** method
- jpi is now passed to Canvas to call **afterRender()** 
- **LinearTransform** toString() for copy paste functionality 
- Copy and Paste buttons and functionality
- **Position** class representing a concrete position
- JComboBox for selecting pre-saved positions
- 'hidden' **default_settings.properties** file and **settings.properties** file on creation for the user to edit
- **Settings** class for storing the user settings and passing them around
	- render_on_changes=true 

### Changed
- **jpi.render()** is now the preferred way to render as it manages the components
- Changed the layout for the user interface, the components now use a vertical BoxLayout
- Changed the **ComponentFactory** to be static and updated the methods to always return JPanel which can be directly added to the user interface
- Maintained zoom x and y aspect ratio when clicking
- Copy and Paste also save iterations

### Removed
- **ComponentFactory.create()** as the JPanel is now created in each factory method

## [0.1.0]

### Added

### Changed

### Removed

