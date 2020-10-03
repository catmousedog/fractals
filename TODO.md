# TODO

## [0.1.5] - concrete additions
- [x] (move setProgress to Logger? extend Logger? but that means I would have to cast it each time to call the concrete methods) (make UIConsole into a singleton?)
- [x] UIConsoleHandler -> make complex logging to file and System.out but simple logs to UI
- [ ] make uncatched exceptions go to logger
- [ ] fix artifacts in julia set -> (slow down repaint?)
- [ ] folder for each fractal type
- [ ] checkbox advanced settings (offset in normalized fractals i.e. + 1, intertior algorithm, etc.)
- [ ] copy and paste for colours
- [ ] dropdown like 'locations' for unique colours
- [ ] if already repainting schedule new repaint request and override it of new request comes in so only the last gets executed
- [ ] keylistener
- [ ] make logger messages fade
- [ ] use java.util.Logger
- [ ] create fractal properties files and location if not found
- [ ] julia default fixed point settings
- [ ] advanced setting -> overlay for overlaying mandelbrot over julia set