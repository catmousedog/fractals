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

## [0.1.0] - fractal overhaul

### Added
- *Fractal.filter(Number v)* to colour the fractal after generation.
- *Fractal.addFilter(JPanel jp)* for creating its own user interface to change the colour settings.
- **Savable** interface for user interactable interaces such as **JPInterface** and now **Fractal**
- *Fractal.clone()* so undo will also undo a change in fractal
- Concrete Item
	- fractal ComboBox
- **InitialSize** inner class of **Main** for cleaner code
- fractalsjcb for selecting fractal
- *cancel* allows for cancelling of colouring stage

### Changed
- *Fractal.get()* and *Fractal.filter()* use the abstract **Number** class instead of double.
- **ComboBox** Data is now Object, being the selected Object.
- Feedback panel progress bar now shows *calculating* and *colouring*.

### Fixed
- rotation maintain after *undo*
- iteration maintain after *undo*

## [0.1.1] - colours

### Added
- *Fractal.saveAndColour()* and *Fractal.safeUpdate()* for respectively saving user input and colouring and to update without triggering listeners.
- Concrete colouring **Data** for the **IterativeMandelbrot**
	- r, g, b slider
	- invert button
- **settings** instance added to **Fractal** 
- Items
	- SubTitle
- Concrete Items
	- Repaint Button
- **Normalized Mandelbrot** concrete fractal: *n + 1 - ln(ln(|z|²))/ln(2)*

### Changed
- Made **Logger** only display one generate and colour time
- *Fractal.addFilter(JPanel)* is no longer abstract, it adds items like the **JPInterface** using an array of ordered **Items**

## [0.1.2] - resources and files

### Added
- **Potential Mandelbrot** concrete fractal: *ln(|z|) / 2^n*
- booleans to enable/disable each fractal inside *settings.properties*
- *concrete_fractals* folder
	- *IterativeMandelbrot.properties* resource
	- *NormalizedMandelbrot.properties* resource
- *locations* resource
- **Settings** init methods to load the new and old resources 
- *Fractal.getTip()* for getting a description of each fractal (displayed on combo box)
- **Fractal** minor overhaul to replace **Configuration** 
- **ActiveData** which implements the use of an active listener and *safeUpdate()* method to not trigger those listeners

### Changed
- Moved all **Configuration** necessities to **Canvas**
- *Configuration.copy()* replaced by *Fractal.copy()* as the **LinearTransform** is stored inside **Fractal**.
- all the **Data** to **ActiveData** if needed

### Removed
- **Configuration** class

## [0.1.3] - minor fixes and abstract fractals

### Added
- **Configuration** private class to **Canvas** (only stores the previous config).
- new fractal: Burning Ship fractal: *iterative*, *normalized* and *potential* variant.
- **IterativeFractal** abstract class for all iterative fractals.
- **NormalizedFractal** abstract class
- **PotentialFractal** abstract class

### Changed
- *Canvas.undo()* and *Canvas.savePrevConfig()* to use the new **Configuration** class.
- Moved code from *jcb.setAction* to *JPI.update()* for when Fractal changes as this is a global event not just for the listener. 
- **Fractal** is no longer copied around every render -> **Configuration** class is used inside **Canvas**.
- **Fractal** now use *default_iter* and *bailout* properties inside resources.
- Moved **JPInterface** layout for all **Item**s in the **AllData** class to **AllData** constructor.
- *Settings.initFractals()* reworked to go over *allFractals* array instead of resources.

### Note
- After some testing it has become clear that calculating 3 bytes for rgb values and putting them in one integer is not faster than using *new Color(r, g, b).getRGB()*. 

## [0.1.4] - abstract filters

### Added
- *Fractal.initPanel()* abstract method and implementation for each abstract fractal (*iterative*, *potential*, etc.).
- abstract **Filter** class allowing for various implementations for each abstract fractal such as *iterative*, *normalized*, etc.
- **IterativeLinearFilter**, **NormalizedFilter** concrete filters.
- filter combo box to choose filter for each fractal.
- **SafeSavable** interface
- *postRender* and *preRender* methods to all **Item**s and called them in *JPInterface.pre*- and *postRender()* which in turn makes all **Item**s disable during render.

### Changed
- Moved a lot of unnecessary code to higher abstract classes inside **Fractal** and **Filter**.
- Made cloning fractals easier as they only need to return *new TestFractal(settings, this)*.
- Made **Item** implement **Savable**.
- Enabled the undo button only after calling *JPInterface.save()*.

### Removed
- *Fractal.filter()* as each fractal can now have multiple **Filter**s.
- abstract fractals: **IterativeFractal**, **NormalizedFractal** and **PotentialFractal** as all of their code was moved to **Fractal** or their respective **Filter**.
- **PreSaved** enum.

## [0.1.5] - concrete additions

### Added
- **IterativePeriodicFilter** concrete filter.
- more options for the **NormalizedFilter**.
- *Filter.getTip()*
- *Picture* section
- *Settings.addImage()*
- TextField min and max for safety input

### Changed
- Moved **AllData** to new **GUI** class and new source file

### Removed
- Listener from TextField
