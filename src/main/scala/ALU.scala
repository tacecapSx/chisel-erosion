import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val a = Input(UInt(16.W))
    val b = Input(UInt(16.W))
    val fn = Input(Bool())
    val y = Output(UInt(16.W))
  })

  val addition = io.a + io.b

  val eqZero = io.fn && (io.a === 0.U)

  io.y := Mux(eqZero, 0.U, addition)
}