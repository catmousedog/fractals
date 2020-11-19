# TODO

## [0.2] - workers package
- [x] make FeedbackPanel into a singleton
- [x] UIConsoleHandler -> make complex logging to file and System.out but simple logs to UI
- [x] make uncatched exceptions go to logger
- [x] folder for each fractal type
- [x] fix maven building 
- [x] fix painter artifacts
- [x] update comments on 'fractals.workers' package
- [x] make only one *global* generator / painter work (so canvas and Picture can not do work at the same time?)
- [x] settings for disabling scheduled workers
- [x] two progress bars for painter and generator
- [x] make Settings a singleton
- [x] change folder hierarchy to a single folder with all fractals in their subtype folder. Inside those folders are the *settings*, *locations*, etc. keep images separate though
- [x] create fractal properties files and location if not found

## [0.3] - Fractal Function Filter (fractal_functions)
- [x] ordered fractals (Fractal -> Function -> Filter)
- [x] update resources to new hierarchy
- [x] update Settings to new hierarchy
- [x] keylistener
- [x] fix undo button and action
- [x] fix getResourceAsStream
- [x] add bailout
- [x] sort JPI savable methods to new GUI
- [x] make logger messages fade

## [0.4] - Derivatives
- [x] derivatives in **Fractal**
- [ ] tips
- [ ] update settings for all fractals (eg. remove offset)

## Other
- [ ] julia default fixed point settings
- [ ] advanced setting -> overlay for overlaying mandelbrot over julia set
- [ ] checkbox advanced settings (intertior algorithm, etc.)
- [ ] fix and add concrete locations and settings for all the fractals
- [ ] gridbaglayout + option to change JPI width
- [ ] wide combobox for long fractal names
