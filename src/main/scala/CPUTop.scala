import chisel3._
import chisel3.util._

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))
  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())

  // Default values
  io.done := false.B

  programCounter.io.run := io.run
  programCounter.io.jump := false.B
  programCounter.io.stop := false.B
  programCounter.io.programCounterJump := 0.U

  dataMemory.io.address := 0.U
  dataMemory.io.writeEnable := false.B
  dataMemory.io.dataWrite := 0.U

  programMemory.io.address := programCounter.io.programCounter
  
  registerFile.io.aSel := 0.U
  registerFile.io.bSel := 0.U
  registerFile.io.writeData := 0.U
  registerFile.io.writeSel := 0.U
  registerFile.io.writeEnable := false.B
  
  controlUnit.io.instruction := programMemory.io.instructionRead

  alu.io.a := 0.U
  alu.io.b := 0.U
  alu.io.fn := false.B

  // Instructions
  when(controlUnit.io.nop) {
    //do nothing
  }
  .elsewhen(controlUnit.io.add) {
    alu.io.fn := false.B
    registerFile.io.aSel := controlUnit.io.regB
    registerFile.io.bSel := controlUnit.io.regC
    alu.io.a := registerFile.io.a
    alu.io.b := registerFile.io.b

    registerFile.io.writeEnable := true.B
    registerFile.io.writeSel := controlUnit.io.regA
    registerFile.io.writeData := alu.io.y
  }
  .elsewhen(controlUnit.io.addIm) {
    alu.io.fn := false.B
    registerFile.io.aSel := controlUnit.io.regB
    alu.io.a := registerFile.io.a
    alu.io.b := controlUnit.io.immediate
    
    registerFile.io.writeEnable := true.B
    registerFile.io.writeSel := controlUnit.io.regA
    registerFile.io.writeData := alu.io.y
  }
  .elsewhen(controlUnit.io.loadIm) {
    registerFile.io.writeEnable := true.B
    registerFile.io.writeSel := controlUnit.io.regA
    registerFile.io.writeData := controlUnit.io.immediate
  }
  .elsewhen(controlUnit.io.loadBy) {
    alu.io.fn := false.B
    registerFile.io.aSel := controlUnit.io.regB
    alu.io.a := registerFile.io.a
    alu.io.b := controlUnit.io.immediate

    
    
    dataMemory.io.address := alu.io.y
    registerFile.io.writeEnable := true.B
    registerFile.io.writeSel := controlUnit.io.regA
    registerFile.io.writeData := dataMemory.io.dataRead
  }
  .elsewhen(controlUnit.io.saveBy) {
    alu.io.fn := false.B
    registerFile.io.aSel := controlUnit.io.regB
    
    alu.io.a := registerFile.io.a
    alu.io.b := controlUnit.io.immediate

    dataMemory.io.writeEnable := true.B
    dataMemory.io.address := alu.io.y

    registerFile.io.bSel := controlUnit.io.regA
    dataMemory.io.dataWrite := registerFile.io.b
  }
  .elsewhen(controlUnit.io.branch) {
    alu.io.fn := true.B
    registerFile.io.aSel := controlUnit.io.regA
    alu.io.a := registerFile.io.a

    when(alu.io.y === 1.U) {
      programCounter.io.jump := true.B
      programCounter.io.programCounterJump := controlUnit.io.immediate
    }
  }
  .elsewhen(controlUnit.io.jump) {
    programCounter.io.jump := true.B
    programCounter.io.programCounterJump := controlUnit.io.immediate
  }
  .elsewhen(controlUnit.io.end) {
    io.done := true.B
    programCounter.io.stop := true.B
  }

  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}