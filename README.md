# chisel-erosion
A solution to assignment 2 of Computer Systems (02132) made by Martin Handest [s224755] ([@tacecapSx](https://github.com/tacecapSx)) and Oskar Holland [s224768] ([@pandekagee](https://github.com/pandekagee)) in Scala.

## Instructions
To test the CPU implementation, choose a picture inside of `CPUTopTester` by replacing the `image` variable. Then, to run the erosion algorithm, first enter `sbt`:
```
sbt
```

Then, run the test:
```
Test / runMain CPUTopTester
```

## Progress
- [x] Implement ProgramCounter module
- [x] Define ISA and compile
- [x] Encode instructions
- [x] Draft CPU architecture
- [x] Implement ALU module
- [x] Implement RegisterFile module
- [x] Implement ControlUnit module
- [x] Put everything together in CPUTop module
- [x] Run, test, debug
- [x] Evaluate implementation
- [x] Write report