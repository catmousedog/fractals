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

## [0.0.3] - DataContainer_Graphics

### Added
- Created **Item** and **Data<T>** abstract classes to represent the graphics components containing data.
- Created **AllData** inner class of **JPInterface** which contains all the **Data<T>** and **Items**.
- Created **Configuration** class for passing around the current canvas Configuration.
- **JPInterface** methods: *renderWithout*, *preRender*
- Data (active) (passive)
	- Button (a)
	- Button2 (a)
	- ComboBox (a)
	- Label (p)
	- TextFieldDouble (p)
	- TextFieldInteger (p)
	- SliderDouble (a)
	- SliderInteger (a)
- Items
	- Title
	- Padding
- Concrete Data
	- Undo button
	- Cancel button
- Cancel and Undo buttons disabling when necessary. 
- Painter **SwingWorker** to separate the colouring and calculating (*Generator*).

### Changed
- Moved iterations, fractal and zoomFactor to **Configuration** class
- Rewrote all graphics components in **JPInterface.AllData** class. 
- Moved all *.properties* fields to **Settings** class.
- Redid packages
- Made **Canvas.config** no longer final, use the getter to retrieve it.

### Removed
- **ComponentFactory** class

## [0.1.0]

### Added
- *Fractal.filter(Number v)* to colour the fractal after generation.
- *Fractal.addFilter(JPanel jp)* for creating its own user interface to change the colour settings.
- **Savable** interface for user interactable interaces such as **JPInterface** and now **Fractal**
- *Fractal.clone()* so undo will also undo a change in fractal
- Concrete Data
	- fractal ComboBox
- **InitialSize** inner class of **Main** for cleaner code
- fractalsjcb for selecting fractal
- *cancel* allows for cancelling of colouring stage

### Changed
- *Fractal.get()* and *Fractal.filter()* use the abstract **Number** class instead of double.
- **ComboBox** Data is now Object, being the selected Object.
- Feedback panel progress bar now shows *calculating* and *colouring*.

### Removed
- *Fractal.clone()* as all **Fractals** are created in **Main** and only references are passed around.

### Fixed
- rotation maintain after *undo*
- iteration maintain after *undo*