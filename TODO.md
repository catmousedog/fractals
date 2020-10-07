# TODO

## [0.2] - minor fixes stable
- [x] make FeedbackPanel into a singleton
- [x] UIConsoleHandler -> make complex logging to file and System.out but simple logs to UI
- [x] make uncatched exceptions go to logger
- [x] fix artifacts in julia set -> (slow down repaint?)
- [x] folder for each fractal type
- [x] fix maven building 
- [ ] update comments on 'fractals.workers' package
- [ ] make Settings a singleton?
- [ ] make only one *global* generator / painter work (so canvas and Picture can not do work at the same time?)
- [ ] if already repainting schedule new repaint request and override it of new request comes in so only the last gets executed
- [ ] copy and paste for colours
- [ ] dropdown like 'locations' for unique colours
- [ ] keylistener
- [ ] make logger messages fade
- [ ] create fractal properties files and location if not found
- [ ] julia default fixed point settings
- [ ] advanced setting -> overlay for overlaying mandelbrot over julia set
- [ ] (make abstract sub fractal types? IterativeFractal, ...)
- [ ] checkbox advanced settings (offset in normalized fractals i.e. + 1, intertior algorithm, etc.)
