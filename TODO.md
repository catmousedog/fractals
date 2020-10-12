# TODO

## [0.2] - minor fixes stable
- [x] make FeedbackPanel into a singleton
- [x] UIConsoleHandler -> make complex logging to file and System.out but simple logs to UI
- [x] make uncatched exceptions go to logger
- [x] folder for each fractal type
- [x] fix maven building 
- [x] fix painter artifacts
- [x] update comments on 'fractals.workers' package
- [x] make only one *global* generator / painter work (so canvas and Picture can not do work at the same time?)
- [ ] settings for disabling scheduled workers
- [ ] two progress bars for painter and generator? Maybe part of GlobalWorker class?
- [ ] make Settings a singleton?
- [ ] copy and paste for colours
- [ ] dropdown like 'locations' for unique colours
- [ ] keylistener
- [ ] make logger messages fade
- [ ] create fractal properties files and location if not found
- [ ] julia default fixed point settings
- [ ] advanced setting -> overlay for overlaying mandelbrot over julia set
- [ ] checkbox advanced settings (offset in normalized fractals i.e. + 1, intertior algorithm, etc.)
- [ ] fix and add concrete locations and settings for all the fractals
