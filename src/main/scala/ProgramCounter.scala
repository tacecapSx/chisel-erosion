import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val stop = Input(Bool())
    val jump = Input(Bool())
    val run = Input(Bool())
    val programCounterJump = Input(UInt(16.W))
    val programCounter = Output(UInt(16.W))
  })

  //Implement this module here (respect the provided interface, since it used by the tester)
  val programCounterReg = RegInit(0.U(16.W))

  when(io.jump){
    programCounterReg := io.programCounterJump
  }.elsewhen(io.run && !io.stop){
    programCounterReg := io.programCounter + 1.U
  }

  io.programCounter := programCounterReg
}